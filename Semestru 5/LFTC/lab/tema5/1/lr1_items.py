class LR1Item:
    """Reprezintă un element LR(1): [A -> α.β, a]"""
    
    def __init__(self, prod_index, dot_position, lookahead, production):
        self.prod_index = prod_index  # Indexul producției în gramatică
        self.dot_position = dot_position  # Poziția punctului
        self.lookahead = lookahead  # Simbol de lookahead
        self.production = production  # Tuplu (left, right)
    
    def __eq__(self, other):
        return (self.prod_index == other.prod_index and 
                self.dot_position == other.dot_position and
                self.lookahead == other.lookahead)
    
    def __hash__(self):
        return hash((self.prod_index, self.dot_position, self.lookahead))
    
    def __repr__(self):
        left, right = self.production
        right_with_dot = list(right)
        right_with_dot.insert(self.dot_position, '•')
        return f"[{left} -> {' '.join(right_with_dot)}, {self.lookahead}]"
    
    def next_symbol(self):
        """Returnează simbolul după punct sau None"""
        left, right = self.production
        if self.dot_position < len(right) and right[0] != 'ε':
            return right[self.dot_position]
        return None
    
    def is_complete(self):
        """Verifică dacă elementul este complet (punct la final)"""
        left, right = self.production
        return self.dot_position >= len(right) or right[0] == 'ε'
    
    def advance(self):
        """Returnează un nou element cu punctul avansat"""
        return LR1Item(self.prod_index, self.dot_position + 1, 
                      self.lookahead, self.production)
    
    def symbols_after_dot(self):
        """Returnează simbolurile după punct (β în A -> α.Bβ)"""
        left, right = self.production
        if right[0] == 'ε':
            return []
        return right[self.dot_position + 1:]
    
class LR1State:
    """Reprezintă o stare (set de elemente LR(1))"""
    
    def __init__(self, items=None):
        self.items = set(items) if items else set()
        self.id = None
    
    def add_item(self, item):
        self.items.add(item)
    
    def __eq__(self, other):
        return self.items == other.items
    
    def __hash__(self):
        return hash(frozenset(self.items))
    
    def __repr__(self):
        result = f"State {self.id}:\n"
        for item in sorted(self.items, key=lambda x: (x.prod_index, x.dot_position)):
            result += f"  {item}\n"
        return result