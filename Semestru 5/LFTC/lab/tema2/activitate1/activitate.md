# Lab3

Automaton:
    states: set<Stare>
    alphabet: set<char>
    transitions: map<Transition>
    start: string
    hasEpsilon: bool

Transition:
    from: string
    symbol: char
    to: set<string>

Stare:
    nume: string
    eFinal: bool