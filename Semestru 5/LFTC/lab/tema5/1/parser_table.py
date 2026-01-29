# parser_table.py - Constructia tabelului LR(1)

from lr1_items import LR1Item, LR1State
from first_follow import FirstFollow

class LR1ParserTable:
    def __init__(self, grammar):
        self.grammar = grammar
        self.first_follow = FirstFollow(grammar)
        self.first_follow.compute_all()
        
        self.states = []
        self.state_map = {}     # Mapare state -> id
        self.action = {}        # action[state][terminal] = ('shift', state) | ('reduce', prod) | 'accept'
        self.goto_table = {}    # goto[state][non_terminal] = state
        
        self.conflicts = []     # Lista de conflicte
    
    def closure(self, items):
        """Calculeaza closure pentru un set de elemente LR(1)"""
        closure_set = set(items)
        added = True
        
        while added:
            added = False
            new_items = set()
            
            for item in closure_set:
                next_sym = item.next_symbol()
                
                # Daca urmatorul simbol este non-terminal
                if next_sym and next_sym in self.grammar.non_terminals:
                    # Simbolurile dupa non-terminal (beta in A -> alpha.Bbeta)
                    beta = item.symbols_after_dot()
                    # beta concatenat cu lookahead
                    beta_a = beta + [item.lookahead]
                    
                    # Calculeaza FIRST1(beta a)
                    first_beta_a = self.first_follow.first1(beta_a)
                    
                    # Pentru fiecare productie B -> gamma
                    for prod_idx, (left, right) in self.grammar.get_productions_for(next_sym):
                        # Pentru fiecare b din FIRST1(beta a)
                        for lookahead in first_beta_a:
                            new_item = LR1Item(prod_idx, 0, lookahead, (left, right))
                            
                            if new_item not in closure_set:
                                new_items.add(new_item)
                                added = True
                    
            closure_set.update(new_items)
        
        return closure_set
    
    def goto(self, state, symbol):
        """Calculeaza goto(I, X)"""
        goto_items = set()
        
        for item in state.items:
            next_sym = item.next_symbol()
            
            # Daca simbolul dupa punct este X
            if next_sym == symbol:
                # Avanseaza punctul
                goto_items.add(item.advance())
        
        if not goto_items:
            return None
        
        # Returneaza closure
        return self.closure(goto_items)
    
    def build_canonical_collection(self):
        """Construieste colectia canonica de stari LR(1)"""
        # Starea initiala: [S' -> .S, $]
        initial_prod = self.grammar.productions[0]
        initial_item = LR1Item(0, 0, '$', initial_prod)
        initial_closure = self.closure({initial_item})
        
        initial_state = LR1State(initial_closure)
        initial_state.id = 0
        
        self.states.append(initial_state)
        self.state_map[initial_state] = 0
        
        # Coada de stari de procesat
        queue = [initial_state]
        
        while queue:
            current_state = queue.pop(0)
            
            # Colecteaza toate simbolurile dupa punct
            symbols = set()
            for item in current_state.items:
                next_sym = item.next_symbol()
                if next_sym:
                    symbols.add(next_sym)
            
            # Pentru fiecare simbol, calculeaza goto
            for symbol in symbols:
                goto_state_items = self.goto(current_state, symbol)
                
                if goto_state_items:
                    goto_state = LR1State(goto_state_items)
                    
                    # Verifica daca starea exista deja
                    if goto_state not in self.state_map:
                        goto_state.id = len(self.states)
                        self.states.append(goto_state)
                        self.state_map[goto_state] = goto_state.id
                        queue.append(goto_state)
                    else:
                        goto_state.id = self.state_map[goto_state]
                    
                    # Construieste tranzitia
                    if symbol in self.grammar.terminals:
                        # Action: shift
                        self.add_action(current_state.id, symbol, ('shift', goto_state.id))
                    else:
                        # Goto
                        self.add_goto(current_state.id, symbol, goto_state.id)
    
    def add_action(self, state_id, terminal, action_value):
        """Adauga o actiune in tabel"""
        if state_id not in self.action:
            self.action[state_id] = {}
        
        # Verifica conflicte
        if terminal in self.action[state_id]:
            existing = self.action[state_id][terminal]
            if existing != action_value:
                # ATENTIE: Conflict Shift/Reduce sau Reduce/Reduce
                self.conflicts.append({
                    'state': state_id,
                    'symbol': terminal,
                    'action1': existing,
                    'action2': action_value
                })
                print(f"CONFLICT in state {state_id} on '{terminal}': {existing} vs {action_value}")
        
        self.action[state_id][terminal] = action_value
    
    def add_goto(self, state_id, non_terminal, next_state_id):
        """Adauga o tranzitie goto in tabel"""
        if state_id not in self.goto_table:
            self.goto_table[state_id] = {}
        self.goto_table[state_id][non_terminal] = next_state_id
    
    def build_action_table(self):
        """Construieste tabelul ACTION (reducere si accept)"""
        for state in self.states:
            for item in state.items:
                if item.is_complete():
                    # Reduce sau Accept
                    if item.prod_index == 0:  # S' -> S.
                        # Accept
                        self.add_action(state.id, '$', 'accept')
                    else:
                        # Reduce cu productia
                        self.add_action(state.id, item.lookahead, 
                                        ('reduce', item.prod_index))
    
    def build(self):
        """Construieste intregul tabel de analiza"""
        print("Construiesc colectia canonica LR(1)...")
        self.build_canonical_collection()
        print(f"   -> {len(self.states)} stari generate")
        
        print("Construiesc tabelul ACTION...")
        self.build_action_table()
        
        if self.conflicts:
            print(f"\nATENTIE: {len(self.conflicts)} conflicte detectate!")
            print("Gramatica NU este LR(1)!")
            return False
        else:
            print("Gramatica este LR(1)!")
            return True
    
    def print_table(self):
        """Afiseaza tabelul de analiza"""
        print("\n" + "="*80)
        print("TABELUL DE ANALIZA LR(1)")
        print("="*80)
        
        # Header
        terminals = sorted(self.grammar.terminals) + ['$']
        non_terminals = sorted(self.grammar.non_terminals)
        
        print(f"\n{'State':<8}", end="")
        print("ACTION".center(len(terminals) * 15), end="")
        print("GOTO".center(len(non_terminals) * 15))
        
        print(f"{'':8}", end="")
        for t in terminals:
            print(f"{t:<15}", end="")
        for nt in non_terminals:
            print(f"{nt:<15}", end="")
        print()
        print("-" * 80)
        
        # Randuri
        for state in self.states:
            print(f"{state.id:<8}", end="")
            
            # ACTION
            for t in terminals:
                action = self.action.get(state.id, {}).get(t, '')
                if action == 'accept':
                    print(f"{'acc':<15}", end="")
                elif isinstance(action, tuple):
                    if action[0] == 'shift':
                        print(f"s{action[1]:<14}", end="")
                    elif action[0] == 'reduce':
                        print(f"r{action[1]:<14}", end="")
                else:
                    print(f"{'':<15}", end="")
            
            # GOTO
            for nt in non_terminals:
                goto_state = self.goto_table.get(state.id, {}).get(nt, '')
                if goto_state:
                    print(f"{goto_state:<15}", end="")
                else:
                    print(f"{'':<15}", end="")
            
            print()