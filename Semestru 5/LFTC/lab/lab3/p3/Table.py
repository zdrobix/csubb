class Table:
    def __init__(self, size=211):
        self.size = size
        self.table = [[] for _ in range(size)]
        self.entries = [] 
        self.positions = {}      
 
 
    def insert(self, key):
        if key in self.positions:
            return self.positions[key]
 
        self.entries.append(key)
        self.positions[key] = len(self.entries)
        self.sort()
        return self.lookup(key)
 
    def lookup(self, key):
        return self.positions.get(key, None)
 
    def __str__(self):
        return "\n".join(self.entries)
    