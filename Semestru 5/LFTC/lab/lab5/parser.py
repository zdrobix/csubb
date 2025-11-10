# analyzer_af.py

token_table = {
    "ID": 0, "CONST": 1, ";": 2, "int": 3, "double": 4, "string": 5,
    "(": 6, ")": 7, "+": 8, "*": 9, "-": 10, "/": 11, "%": 12,
    "citire_int": 13, "citire_double": 14, "{": 15, "}": 16, ",": 17,
    "?": 18, "==": 19, "!=": 20, "<": 21, ">": 22, "<=": 23, ">=": 24,
    "=": 25, '"': 26, "afiseaza": 27, "if": 28, "else": 29, "while": 30,
    "return": 31
}

def is_identifier(s: str):
    if not s: return False
    state = "q0"
    for ch in s:
        if state == "q0":
            if ch.isalpha() or ch == "_":
                state = "q1"
            else:
                return False
        elif state == "q1":
            if not (ch.isalnum() or ch == "_"):
                return False
    return state == "q1"

def is_int_const(s: str):
    if not s: return False
    state = "q0"
    for ch in s:
        if state == "q0" and ch.isdigit():
            state = "q1"
        elif state == "q1" and ch.isdigit():
            continue
        else:
            return False
    return state == "q1"

def is_real_const(s: str):
    if not s: return False
    state = "q0"
    for ch in s:
        if state == "q0" and ch.isdigit():
            state = "q1"
        elif state == "q1":
            if ch.isdigit():
                continue
            elif ch == ".":
                state = "q2"
            else:
                return False
        elif state == "q2":
            if ch.isdigit():
                state = "q3"
            else:
                return False
        elif state == "q3" and ch.isdigit():
            continue
        else:
            return False
    return state == "q3"

def tokenize_line(line):
    tokens, current, i = [], "", 0
    while i < len(line):
        ch = line[i]
        if ch.isspace():
            if current:
                tokens.append(current)
                current = ""
        elif ch in [';', ',', '(', ')', '{', '}', '+', '-', '*', '/', '%', '=', '<', '>', '!', '?', '"']:
            if current:
                tokens.append(current)
                current = ""
            if i + 1 < len(line) and line[i:i+2] in ["==", "!=", "<=", ">="]:
                tokens.append(line[i:i+2])
                i += 1
            else:
                tokens.append(ch)
        else:
            current += ch
        i += 1
    if current:
        tokens.append(current)
    return tokens

def analyze_tokens(lines):
    TS, FIP = [], []
    for index, line in enumerate(lines, start=1):
        tokens = tokenize_line(line)
        for token in tokens:
            if token in token_table:
                FIP.append((token_table[token], -1))
            elif is_identifier(token):
                if token not in TS:
                    TS.append(token)
                    TS.sort()
                FIP.append((token_table["ID"], TS.index(token) + 1))
            elif is_int_const(token) or is_real_const(token):
                if token not in TS:
                    TS.append(token)
                    TS.sort()
                FIP.append((token_table["CONST"], TS.index(token) + 1))
            else:
                print(f"Eroare lexicala la linia {index}: token necunoscut '{token}'")
    return TS, FIP

if __name__ == "__main__":
    with open("./input/Program2.txt") as f:
        code_lines = f.readlines()

    TS, FIP = analyze_tokens(code_lines)

    with open("./output/FIP2.txt", "w") as f:
        for cod, pos in FIP:
            f.write(f"({cod}, {pos})\n")

    with open("./output/TS2.txt", "w") as f:
        for i, token in enumerate(TS, start=1):
            f.write(f"{i} {token}\n")

    print("Analiza lexicala finalizata.")
