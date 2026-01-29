# parser.py - Motorul de analiza LR(1)

class LR1Parser:
    
    # Clasa interna pentru nodurile arborelui sintactic
    class TreeNode:
        def __init__(self, symbol, production_index=None, children=None):
            self.symbol = symbol
            self.production_index = production_index
            self.children = children if children is not None else []
        
        def __str__(self, level=0):
            ret = "  " * level + f"{self.symbol}"
            if self.production_index is not None:
                # Afiseaza indicele productiei folosite pentru a genera acest Non-Terminal
                ret += f" [p{self.production_index}]"
            ret += "\n"
            for child in self.children:
                # Trateaza terminalele care pot fi simple string-uri
                if isinstance(child, str):
                    ret += "  " * (level + 1) + child + "\n"
                else:
                    ret += child.__str__(level + 1)
            return ret

    def __init__(self, parser_table):
        self.table = parser_table
        self.grammar = parser_table.grammar
        
    def format_action(self, action):
        """Formateaza actiunea pentru afisare"""
        if action == 'accept':
            return 'accept'
        elif action[0] == 'shift':
            return f'shift {action[1]}'
        elif action[0] == 'reduce':
            prod_index = action[1]
            left, right = self.grammar.productions[prod_index]
            return f"red {prod_index}: {left}->{' '.join(right)}"
        return str(action)

    def parse(self, input_tokens):
        """Analizeaza o secventa de intrare si construieste arborele"""
        
        # Initializare stive
        stack = [0]       # Stiva de stari (int)
        symbol_stack = [] # Stiva de simboluri (str)
        node_stack = []   # Stiva de noduri (TreeNode)
        
        input_buffer = list(input_tokens) + ['$']
        productions_used = [] # Ordinea productiilor (bottom-up)
        
        position = 0
        step = 0
        
        # Sectiunea de afisare/logging
        print("\n" + "="*120) 
        print("PROCESUL DE ANALIZA")
        print("="*120)
        print(f"{'Step':<6} {'Stack':<40} {'Symbols':<40} {'Input':<30} {'Action':<20}")
        print("-"*120)
        
        while True:
            step += 1
            current_state = stack[-1]
            current_symbol = input_buffer[position]
            
            # Pregatire string-uri pentru logging
            stack_str = ' '.join(map(str, stack))
            if len(stack_str) > 38: stack_str = "..." + stack_str[-35:]
            
            symbols_str = ' '.join(symbol_stack)
            if len(symbols_str) > 38: symbols_str = "..." + symbols_str[-35:]
            
            input_str = ' '.join(input_buffer[position:])
            if len(input_str) > 28: input_str = input_str[:25] + "..."

            action = self.table.action.get(current_state, {}).get(current_symbol)
            
            if action is None:
                error_msg = f"Eroare la pasul {step}: Nu exista actiune pentru starea {current_state} si simbolul '{current_symbol}'"
                print(f"{step:<6} {stack_str:<40} {symbols_str:<40} {input_str:<30} ERROR")
                print(f"\nEroare: {error_msg}")
                return False, error_msg
            
            action_str = self.format_action(action)
            print(f"{step:<6} {stack_str:<40} {symbols_str:<40} {input_str:<30} {action_str:<20}")
            
            # Executa actiunea
            if action == 'accept':
                # La accept, nodul radacina S' ar trebui sa fie singurul ramas pe node_stack.
                if len(node_stack) != 1:
                     return False, "Eroare de constructie arbore: Stiva de noduri nu are o singura radacina la 'accept'."
                     
                print("\nACCEPT - Analiza reusita!")
                # Returneaza un tuplu: (lista_productii_folosite, radacina_arborelui)
                return True, (productions_used, node_stack[0])
            
            elif action[0] == 'shift':
                # Shift: muta simbolul, starea si nodul pe stive
                next_state = action[1]
                
                # 1. Creaza nodul pentru simbolul terminal
                # Nodurile terminale nu au productie_index
                terminal_node = self.TreeNode(current_symbol)
                
                # 2. PUSH pe toate stivele
                stack.append(next_state)
                symbol_stack.append(current_symbol)
                node_stack.append(terminal_node)
                position += 1
            
            elif action[0] == 'reduce':
                prod_index = action[1]
                left, right = self.grammar.productions[prod_index]
                
                productions_used.append(prod_index)
                
                # 1. Pop N noduri (copii) de pe node_stack si N stari/simboluri de pe celelalte stive
                children = []
                if right[0] != 'ε':
                    num_symbols_to_pop = len(right)
                    
                    # Verificam ca stiva e suficient de mare
                    if len(stack) < num_symbols_to_pop or len(node_stack) < num_symbols_to_pop:
                        return False, f"Eroare Reduce: Stiva insuficienta pentru reducerea {left}->{' '.join(right)}"
                    
                    for _ in range(num_symbols_to_pop):
                        # Scoatem nodul copil, starea si simbolul asociat
                        children.append(node_stack.pop()) 
                        stack.pop()
                        if symbol_stack:
                            symbol_stack.pop()
                
                children.reverse() # Restabilim ordinea Stanga -> Dreapta
                
                # 2. Creaza nodul parinte (non-terminal)
                parent_node = self.TreeNode(left, prod_index, children)
                
                # 3. GOTO
                current_state = stack[-1]
                # Folosim self.table.goto_table
                goto_state = self.table.goto_table.get(current_state, {}).get(left)
                
                if goto_state is None:
                    error_msg = f"Eroare GOTO: Nu exista tranzitie GOTO({current_state}, {left})"
                    return False, error_msg
                
                # 4. PUSH noul nod parinte si noua stare GOTO
                stack.append(goto_state)
                symbol_stack.append(left)
                node_stack.append(parent_node)
        
    # --- Metode pentru afisare dupa analiza ---

    def get_leftmost_derivations_indices(self, node, leftmost_indices):
        """Extrage indicii productiilor in ordinea derivarii leftmost din arbore."""
        
        # 1. Handle if 'node' is a list (sometimes parser returns [RootNode] or a production tuple)
        if isinstance(node, (list, tuple)):
            for item in node:
                self.get_leftmost_derivations_indices(item, leftmost_indices)
            return

        # 2. Skip primitives (Terminals like strings, int, float) or None
        if isinstance(node, (str, int, float)) or node is None:
            return

        # 3. Collect the index (Checking explicitly for None, as 0 is a valid index)
        if hasattr(node, 'production_index') and node.production_index is not None:
            leftmost_indices.append(node.production_index)

        # 4. Recursively traverse children
        if hasattr(node, 'children'):
            for child in node.children:
                self.get_leftmost_derivations_indices(child, leftmost_indices)

    def print_derivation(self, root_node):
        """
        Afiseaza derivarea Leftmost folosind arborele deja construit.
        Primeste direct root_node.
        """
        print("\n" + "="*80)
        print("DERIVAREA LEFTMOST (Din Arbore)")
        print("="*80)
        
        # 1. Obtine ordinea Leftmost din arborele gata facut
        leftmost_indices = []
        # Notam: root_node poate fi tuplu (productions_used, root_node) daca nu a fost despachetat in main.py
        if isinstance(root_node, tuple):
             # Presupunem ca nodul este al doilea element din tuplu, conform return-ului din parse()
             # Vom folosi logica de unwrap din get_leftmost_derivations_indices
             pass
        
        self.get_leftmost_derivations_indices(root_node, leftmost_indices)
        
        # 2. Afiseaza pasii
        current_sentence = [self.grammar.start_symbol]
        print(f"0: {' '.join(current_sentence)}")
        
        step = 1
        for prod_idx in leftmost_indices:
            # Sarim productia de start augmentata (S' -> S)
            if self.grammar.productions[prod_idx][0] == self.grammar.augmented_start:
                continue
                
            left, right = self.grammar.productions[prod_idx]
            
            # Gaseste primul non-terminal 'left' in secventa curenta
            found = False
            for i, sym in enumerate(current_sentence):
                if sym == left:
                    # Inlocuieste
                    if right[0] == 'ε':
                        current_sentence[i:i+1] = []
                    else:
                        current_sentence[i:i+1] = right
                    found = True
                    break
            
            if found:
                print(f"{step}: {' '.join(current_sentence)}")
                step += 1

def main_example():
    """Exemplu de utilizare"""
    # Importul este necesar doar daca rulam parser.py independent
    # din fisierul principal, ar trebui sa vina prin main.py
    try:
        from grammar import Grammar
        from parser_table import LR1ParserTable
    except ImportError:
        print("Va rugam asigurati-va ca fisierele grammar.py si parser_table.py sunt disponibile.")
        return

    grammar = Grammar()
    grammar.add_production('E', ['E', '+', 'T'])
    grammar.add_production('E', ['T'])
    grammar.add_production('T', ['T', '*', 'F'])
    grammar.add_production('T', ['F'])
    grammar.add_production('F', ['(', 'E', ')'])
    grammar.add_production('F', ['id'])
    grammar.start_symbol = 'E'
    grammar.augment_grammar()

    parser_table = LR1ParserTable(grammar)
    parser_table.build()

    parser = LR1Parser(parser_table)
    
    input_tokens = ['id', '+', 'id', '*', 'id']
    print(f"\n\nAnalizez secventa: {' '.join(input_tokens)}")
    
    # Apelul parse returneaza (success, (productions_used, root_node))
    success, result = parser.parse(input_tokens)
    
    if success:
        # Despachetare: result este (lista_productii, radacina_arborelui)
        productions, root_node = result 
        
        print("\nAnaliza a reusit!")
        
        # Pasam nodul radacina pentru a afisa derivarea
        parser.print_derivation(root_node)
        
        print("\n" + "="*80)
        print("ARBORELE DE DERIVARE")
        print("="*80)
        print(root_node)

if __name__ == "__main__":
    main_example()