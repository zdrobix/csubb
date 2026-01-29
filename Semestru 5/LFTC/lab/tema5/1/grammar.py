# grammar.py - Clasa pentru reprezentarea gramaticii

class Grammar:
    def __init__(self):
        self.non_terminals = set()
        self.terminals = set()
        self.productions = []  # Lista de tuple (Non-terminal, listă_simboluri)
        self.start_symbol = None
        self.augmented_start = None
        
    def add_production(self, left, right):
        """Adaugă o producție la gramatică"""
        self.non_terminals.add(left)
        self.productions.append((left, right))
        
        # Identifică terminalii și non-terminalii
        for symbol in right:
            if symbol != 'ε' and not self.is_non_terminal(symbol):
                self.terminals.add(symbol)
    
    def is_non_terminal(self, symbol):
        """Verifică dacă un simbol este non-terminal"""
        return symbol in self.non_terminals or symbol.startswith('<')
    
    def augment_grammar(self):
        """Îmbogățește gramatica: S' -> S"""
        if not self.start_symbol:
            # Primul non-terminal devine simbolul de start
            self.start_symbol = self.productions[0][0]
        
        self.augmented_start = self.start_symbol + "'"
        # Adaugă producția S' -> S la început
        self.productions.insert(0, (self.augmented_start, [self.start_symbol]))
        self.non_terminals.add(self.augmented_start)
    
    def get_productions_for(self, non_terminal):
        """Returnează toate producțiile pentru un non-terminal"""
        return [(i, prod) for i, prod in enumerate(self.productions) 
                if prod[0] == non_terminal]
    
    def read_from_file(self, filename):
        """Citește gramatica dintr-un fișier
        Format: A -> B C | D E
        """
        with open(filename, 'r', encoding='utf-8') as f:
            for line in f:
                line = line.strip()
                if not line or line.startswith('#'):
                    continue
                
                # Parse producția
                if '->' in line:
                    left, right_part = line.split('->', 1)
                    left = left.strip()
                    
                    # Procesează alternativele (|)
                    alternatives = right_part.split('|')
                    for alt in alternatives:
                        right = alt.strip().split()
                        if not right:
                            right = ['ε']
                        self.add_production(left, right)
    
    def __str__(self):
        result = "Gramatica:\n"
        for i, (left, right) in enumerate(self.productions):
            result += f"{i}: {left} -> {' '.join(right)}\n"
        return result