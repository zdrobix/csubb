class FirstFollow:
    def __init__(self, grammar):
        self.grammar = grammar
        self.first_sets = {}
        self.follow_sets = {}
    
    def compute_first(self):
        """Calculează mulțimile FIRST pentru fiecare simbol (nerecursiv)"""
        for terminal in self.grammar.terminals:
            self.first_sets[terminal] = {terminal}
        
        for non_terminal in self.grammar.non_terminals:
            self.first_sets[non_terminal] = set()
        
        self.first_sets['ε'] = {'ε'}
        self.first_sets['$'] = {'$'}
        
        # Iterare până la punct fix
        changed = True
        while changed:
            changed = False
            
            for left, right in self.grammar.productions:
                old_size = len(self.first_sets[left])
                
                if right[0] == 'ε':
                    self.first_sets[left].add('ε')
                else:
                    # Pentru fiecare simbol din partea dreaptă
                    all_have_epsilon = True
                    for symbol in right:
                        # Adaugă FIRST(symbol) - {ε}
                        first_symbol = self.first_sets.get(symbol, {symbol})
                        self.first_sets[left].update(first_symbol - {'ε'})
                        
                        # Dacă simbolul nu are ε, oprim
                        if 'ε' not in first_symbol:
                            all_have_epsilon = False
                            break
                    
                    # Dacă toți simbolii au ε, adăugăm ε
                    if all_have_epsilon:
                        self.first_sets[left].add('ε')
                
                if len(self.first_sets[left]) != old_size:
                    changed = True
    
    def compute_follow(self):
        """Calculează mulțimile FOLLOW pentru fiecare non-terminal (nerecursiv)"""
        # Inițializare
        for non_terminal in self.grammar.non_terminals:
            self.follow_sets[non_terminal] = set()
        
        # FOLLOW(S) conține $
        start = self.grammar.augmented_start or self.grammar.start_symbol
        self.follow_sets[start] = {'$'}
        
        # Iterare până la punct fix
        changed = True
        while changed:
            changed = False
            
            for left, right in self.grammar.productions:
                if right[0] == 'ε':
                    continue
                
                # Pentru fiecare simbol din partea dreaptă
                for i, symbol in enumerate(right):
                    if symbol not in self.grammar.non_terminals:
                        continue
                    
                    old_size = len(self.follow_sets[symbol])
                    
                    # Simbolurile după simbolul curent
                    beta = right[i + 1:]
                    
                    if beta:
                        # Calculează FIRST(β)
                        first_beta = self.first_of_sequence(beta)
                        
                        # Adaugă FIRST(β) - {ε} la FOLLOW(symbol)
                        self.follow_sets[symbol].update(first_beta - {'ε'})
                        
                        # Dacă ε ∈ FIRST(β), adaugă FOLLOW(left)
                        if 'ε' in first_beta:
                            self.follow_sets[symbol].update(self.follow_sets[left])
                    else:
                        # Simbolul este ultimul, adaugă FOLLOW(left)
                        self.follow_sets[symbol].update(self.follow_sets[left])
                    
                    if len(self.follow_sets[symbol]) != old_size:
                        changed = True
    
    def first_of_sequence(self, sequence):
        """Calculează FIRST pentru o secvență de simboluri"""
        if not sequence:
            return {'ε'}
        
        result = set()
        all_have_epsilon = True
        
        for symbol in sequence:
            first_symbol = self.first_sets.get(symbol, {symbol})
            result.update(first_symbol - {'ε'})
            
            if 'ε' not in first_symbol:
                all_have_epsilon = False
                break
        
        if all_have_epsilon:
            result.add('ε')
        
        return result
    
    def first1(self, sequence):
        """Calculează FIRST₁(βa) pentru LR(1)
        Returnează primele simboluri terminale care pot începe derivarea lui βa
        """
        result = self.first_of_sequence(sequence)
        # Returnăm doar terminalii (fără ε)
        return result - {'ε'}
    
    def compute_all(self):
        """Calculează ambele mulțimi"""
        self.compute_first()
        self.compute_follow()
    
    def __str__(self):
        result = "FIRST:\n"
        for symbol in sorted(self.first_sets.keys()):
            if symbol in self.grammar.non_terminals:
                result += f"  {symbol}: {{{', '.join(sorted(self.first_sets[symbol]))}}}\n"
        
        result += "\nFOLLOW:\n"
        for symbol in sorted(self.follow_sets.keys()):
            if symbol in self.grammar.non_terminals:
                result += f"  {symbol}: {{{', '.join(sorted(self.follow_sets[symbol]))}}}\n"
        
        return result