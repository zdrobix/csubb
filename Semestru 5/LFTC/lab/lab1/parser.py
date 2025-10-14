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
}

identifier_pattern = re.compile(r'^[a-zA-Z_]\w*$')
const_pattern = re.compile(r'^\d+(\.\d+)?$')

def tokenize(code):
    tokens = re.findall(r'==|!=|<=|>=|[()\{\};,+\-*/%?=<>"]|\w+', code)

    result = []
    for token in tokens:
        if token in token_table:
            result.append((token, token_table[token]))
        elif re.match(identifier_pattern, token):
            result.append((token, token_table["ID"]))
        elif re.match(const_pattern, token):
            result.append((token, token_table["CONST"]))
        else:
            result.append((token, "UNKNOWN"))

    return result

if __name__ == "__main__":
    code = '''
    int main() {
    double raza;
    double pi;

    raza = 0
    raza = 3.14

    raza = citire_double();

    double aria = pi * raza * raza;
    double circumferinta = 2 * pi * raza;

    afiseaza(aria);
    afiseaza(circumferinta);

    return raza;
}
    '''
    tokens = tokenize(code)
    for tok, cod in tokens:
        print(f"{tok:<15} -> {cod}")