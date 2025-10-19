import re

token_table = {
    "ID": 0,
    "CONST": 1,
    ";": 2,
    "int": 3,
    "double": 4,
    "string": 5,
    "(": 6,
    ")": 7,
    "+": 8,
    "*": 9,
    "-": 10,
    "/": 11,
    "%": 12,
    "citire_int": 13,
    "citire_double": 14,
    "{": 15,
    "}": 16,
    ",": 17,
    "?": 18,
    "==": 19,
    "!=": 20,
    "<": 21,
    ">": 22,
    "<=": 23,
    ">=": 24,
    "=": 25,
    '"': 26,
    "afiseaza": 27,
    "if": 28,
    "else": 29,
    "while": 30,
    "return": 31
}

identifier_pattern = re.compile(r'^[a-zA-Z_]\w*$')
const_pattern = re.compile(r'^\d+(\.\d+)?$')

def tokenize(code):
    return re.findall(r'==|!=|<=|>=|[()\{\};,+\-*/%?=<>"]|\w+', code)

def analyze_tokens(code):
    TS = []
    FIP = []

    for index, code in enumerate(code, start = 1):
        tokens = tokenize(code)
        for token in tokens:
            if token in token_table:
                FIP.append((token_table[token], -1))
            elif re.match(identifier_pattern, token):
                if token not in TS:
                    TS.append(token)
                    TS.sort()
                position = TS.index(token) + 1
                FIP.append((token_table["ID"], position))
            elif re.match(const_pattern, token):
                if token not in TS:
                    TS.append(token)
                    TS.sort()
                position = TS.index(token) + 1
                FIP.append((token_table["CONST"], position))

            else:
                print(f"Lexical error at line {index}: unknown token '{token}'")

    return TS, FIP

if __name__ == "__main__":
    with open("./input/Program.txt") as file:
        code_lines = file.readlines()

    TS, FIP = analyze_tokens(code_lines)

    with open("./output/FIP.txt", "w") as file:
        for cod, pos in FIP:
            file.write(f"({cod}, {pos})\n")

    with open("./output/TS.txt", "w") as file:
        for index, token in enumerate(TS, start = 1):
            file.write(f"{index} {token}\n")


print("Done.")