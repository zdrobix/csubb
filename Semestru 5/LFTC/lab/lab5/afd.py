class AFD:
    def __init__(self):
        self.states = set()
        self.alphabet = set()
        self.start = None
        self.finals = set()
        self.transitions = {}

    def read_from_file(self, filename):
        with open(filename, "r", encoding="utf-8") as f:
            lines = [line.strip() for line in f if line.strip()]
        section = None
        for line in lines:
            parts = line.split()
            if parts[0] == "states:":
                section = "states"
                self.states.update(parts[1:])
            elif parts[0] == "alphabet:":
                section = "alphabet"
                self.alphabet.update(parts[1:])
            elif parts[0] == "start:":
                self.start = parts[1]
            elif parts[0] == "finals:":
                self.finals.update(parts[1:])
            elif parts[0] == "transitions:":
                section = "transitions"
            elif section == "transitions":
                if len(parts) == 3:
                    src, sym, dst = parts
                    self.transitions[(src, sym)] = dst

    def accept(self, word: str) -> bool:
        state = self.start
        for c in word:
            if (state, c) not in self.transitions:
                return False
            state = self.transitions[(state, c)]
        return state in self.finals
