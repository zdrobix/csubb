from afd import AFD

TOKENS = {
    "ID": 0, "CONST": 1, ";": 2, "int": 3, "double": 4, "string": 5,
    "(": 6, ")": 7, "+": 8, "*": 9, "-": 10, "/": 11, "%": 12,
    "citire_int": 13, "citire_double": 14, "{": 15, "}": 16, ",": 17,
    "?": 18, "==": 19, "!=": 20, "<": 21, ">": 22, "<=": 23, ">=": 24,
    "=": 25, '"': 26, "afiseaza": 27, "if": 28, "else": 29, "while": 30,
    "return": 31
}

MULTI_CHAR_OPS = {"==", "!=", "<=", ">="}
SINGLE_CHAR_OPS = {';', ',', '(', ')', '{', '}', '+', '-', '*', '/', '%', '?', '<', '>', '=', '"'}

def build_automata():
    afd_id = AFD(); afd_id.read_from_file("identificatori.txt")
    afd_int = AFD(); afd_int.read_from_file("int_const.txt")
    afd_real = AFD(); afd_real.read_from_file("real_const.txt")
    return afd_id, afd_int, afd_real

def split_tokens(line):
    tokens = []
    i = 0
    while i < len(line):
        c = line[i]
        if c.isspace():
            i += 1
            continue
        if i + 1 < len(line) and line[i:i+2] in MULTI_CHAR_OPS:
            tokens.append(line[i:i+2])
            i += 2
            continue
        if c in SINGLE_CHAR_OPS:
            tokens.append(c)
            i += 1
            continue
        start = i
        while i < len(line) and not line[i].isspace() and line[i] not in SINGLE_CHAR_OPS and line[i:i+2] not in MULTI_CHAR_OPS:
            i += 1
        tokens.append(line[start:i])

    return tokens


def tokenize_line(line, afd_id, afd_int, afd_real):
    tokens = []
    parts = split_tokens(line)

    for part in parts:
        if part in TOKENS:
            tokens.append((part, TOKENS[part]))
        elif afd_id.accept(part):
            tokens.append(("ID", TOKENS["ID"]))
        elif afd_real.accept(part) or afd_int.accept(part):
            tokens.append(("CONST", TOKENS["CONST"]))
        else:
            tokens.append(("EROARE", part))
    return tokens


def lex_file(filename):
    afd_id, afd_int, afd_real = build_automata()
    result = ""
    with open(filename, "r", encoding="utf-8") as f:
        for nr_linie, linie in enumerate(f, start=1):
            linie = linie.strip()
            if not linie:
                continue
            print(f"\nLinia {nr_linie}: {linie}")
            result += f"\nLinia {nr_linie}: {linie}\n"
            tokens = tokenize_line(linie, afd_id, afd_int, afd_real)
            for t in tokens:
                print(f"  {t[0]:<10} -> {t[1]}")
                result += f"  {t[0]:<10} -> {t[1]}\n"
    with open("output.txt", "w", encoding="utf-8") as out_file:
        out_file.write(result)
