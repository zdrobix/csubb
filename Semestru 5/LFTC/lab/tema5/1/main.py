import sys
from grammar import Grammar
from parser_table import LR1ParserTable
from parser import LR1Parser

def read_fip(filename):
    """Citeste FIP-ul dintr-un fisier
    Format: (cod_atom, pozitie_TS) sau (cod_atom, -1) pentru cuvinte cheie
    
    Returns: lista de tokeni
    """
    tokens = []
    
    # Mapare cod atom -> token
    atom_map = {
        0: 'int', 1: 'double', 2: 'string',
        3: 'main', 4: 'if', 5: 'else', 6: 'while', 7: 'return',
        8: 'afiseaza', 9: 'citire_int', 10: 'citire_double',
        11: 'ID', 12: 'CONST',
        13: '(', 14: ')', 15: '{', 16: '}', 17: ';',
        18: '=', 19: '+', 20: '-', 21: '*', 22: '/', 23: '%',
        24: '==', 25: '!=', 26: '<', 27: '>', 28: '<=', 29: '>='
    }
    
    try:
        with open(filename, 'r', encoding='utf-8') as f:
            for line in f:
                line = line.strip()
                if not line or line.startswith('#'):
                    continue
                
                # Parse (cod, pozitie)
                parts = line.strip('()').split(',')
                if len(parts) == 2:
                    cod = int(parts[0].strip())
                    token = atom_map.get(cod, f'UNKNOWN_{cod}')
                    tokens.append(token)
    except FileNotFoundError:
        print(f"Eroare: Fisierul {filename} nu a fost gasit.")
        return None
    except Exception as e:
        print(f"Eroare la citirea FIP: {e}")
        return None
    
    return tokens


def test_simple_grammar():
    """Test cu o gramatica simpla"""
    print("="*80)
    print("TEST 1: Gramatica Simpla - Expresii Aritmetice")
    print("="*80)
    
    grammar = Grammar()
    
    # E -> E + T | T
    # T -> T * F | F  
    # F -> ( E ) | id
    
    grammar.add_production('E', ['E', '+', 'T'])
    grammar.add_production('E', ['T'])
    grammar.add_production('T', ['T', '*', 'F'])
    grammar.add_production('T', ['F'])
    grammar.add_production('F', ['(', 'E', ')'])
    grammar.add_production('F', ['id'])
    
    grammar.start_symbol = 'E'
    grammar.augment_grammar()
    
    print("\n" + str(grammar))
    
    # Construim tabelul
    parser_table = LR1ParserTable(grammar)
    is_lr1 = parser_table.build()
    
    if not is_lr1:
        print("\nGramatica nu este LR(1)!")
        return False
    
    # Test 1: id + id * id
    print("\n" + "="*80)
    print("TEST: id + id * id")
    print("="*80)
    
    parser = LR1Parser(parser_table)
    success, result = parser.parse(['id', '+', 'id', '*', 'id'])
    
    if success:
        parser.print_derivation(result)
        
        tree = parser.build_parse_tree(result)
        print("\nARBORELE DE DERIVARE:")
        print(tree)
    
    # Test 2: ( id )
    print("\n" + "="*80)
    print("TEST: ( id )")
    print("="*80)
    
    success, result = parser.parse(['(', 'id', ')'])
    
    if success:
        parser.print_derivation(result)
    
    # Test 3: Eroare - id + * id
    print("\n" + "="*80)
    print("TEST EROARE: id + * id")
    print("="*80)
    
    parser.parse(['id', '+', '*', 'id'])
    
    return True


def test_minilanguage():
    """Test cu gramatica mini-limbajului (versiune simplificata)"""
    print("\n\n" + "="*80)
    print("TEST 2: Mini-Limbaj Simplificat")
    print("="*80)
    
    grammar = Grammar()
    
    # Versiune simplificata pentru test
    # <program> -> int main ( ) { <stmts> }
    # <stmts> -> <stmt> | <stmt> <stmts>
    # <stmt> -> <decl> | <assign>
    # <decl> -> int ID ;
    # <assign> -> ID = CONST ;
    
    grammar.add_production('<program>', ['int', 'main', '(', ')', '{', '<stmts>', '}'])
    grammar.add_production('<stmts>', ['<stmt>'])
    grammar.add_production('<stmts>', ['<stmt>', '<stmts>'])
    grammar.add_production('<stmt>', ['<decl>'])
    grammar.add_production('<stmt>', ['<assign>'])
    grammar.add_production('<decl>', ['int', 'ID', ';'])
    grammar.add_production('<assign>', ['ID', '=', 'CONST', ';'])
    
    grammar.start_symbol = '<program>'
    grammar.augment_grammar()
    
    print("\n" + str(grammar))
    
    # Construim tabelul
    parser_table = LR1ParserTable(grammar)
    is_lr1 = parser_table.build()
    
    if not is_lr1:
        print("\nGramatica nu este LR(1)!")
        return False
    
    # Test: int main() { int x; x = 5; }
    print("\n" + "="*80)
    print("TEST: int main() { int x; x = 5; }")
    print("="*80)
    
    tokens = ['int', 'main', '(', ')', '{', 'int', 'ID', ';', 
              'ID', '=', 'CONST', ';', '}']
    
    parser = LR1Parser(parser_table)
    success, result = parser.parse(tokens)
    
    if success:
        parser.print_derivation(result)
    
    return True


def process_from_files(grammar_file, input_file):
    """Proceseaza gramatica si input-ul din fisiere"""
    print("="*80)
    print("PROCESARE DIN FISIERE")
    print("="*80)
    
    # Citeste gramatica
    print(f"\nCitesc gramatica din {grammar_file}...")
    grammar = Grammar()
    
    try:
        grammar.read_from_file(grammar_file)
        grammar.augment_grammar()
        print("Gramatica citita cu succes!")
        print(f"   - {len(grammar.non_terminals)} non-terminali: {', '.join(grammar.non_terminals)}")
        print(f"   - {len(grammar.terminals)} terminali: {', '.join(grammar.terminals)}")
        print(f"   - {len(grammar.productions)} productii")
    except Exception as e:
        print(f"Eroare la citirea gramaticii: {e}")
        return False
    
    # Construieste tabelul
    print("\nConstruiesc tabelul LR(1)...")
    parser_table = LR1ParserTable(grammar)
    is_lr1 = parser_table.build()
    
    if not is_lr1:
        print("\nGramatica nu este LR(1)!")
        print("Nu se poate continua analiza.")
        return False
    
    # Afiseaza starile (primele 10)
    print("\n" + "="*80)
    print("PRIMELE 10 STARI DIN COLECTIA CANONICA")
    print("="*80)
    for state in parser_table.states[:10]:
        print(state)
    
    # Citeste input-ul
    print(f"\nCitesc secventa de intrare din {input_file}...")
    
    try:
        with open(input_file, 'r', encoding='utf-8') as f:
            content = f.read().strip()
            tokens = content.split()
        
        print("Secventa citita cu succes!")
        print(f"   - {len(tokens)} tokeni")
        print(f"   - Tokeni: {' '.join(tokens[:20])}{'...' if len(tokens) > 20 else ''}")
    except Exception as e:
        print(f"Eroare la citirea input-ului: {e}")
        return False
    
    # Analizeaza
    print("\n" + "="*80)
    print("INCEPE ANALIZA")
    print("="*80)
    
    # ... inside process_from_files ...
    
    parser = LR1Parser(parser_table)
    success, result = parser.parse(tokens)
    
    if success:
        print("\nANALIZA REUSITA!")
        
        # If the parser returns a tuple (e.g., (Node, State) or (State, Node)),
        # we need to find the actual Node object inside it.
        if isinstance(result, tuple):
            # Try to find the item that has 'production_index'
            for item in result:
                if hasattr(item, 'production_index'):
                    result = item
                    break

        parser.print_derivation(result)
        
        # 1. Extract indices
        derivation_indices = []
        parser.get_leftmost_derivations_indices(result, derivation_indices)
        
        # --- DEBUG PRINT ---
        print(f"DEBUG: Indici gasiti: {derivation_indices}")
        # -------------------

        # 2. Save to file
        output_file = "parse_result.txt"
        with open(output_file, 'w', encoding='utf-8') as f:
            f.write("PRODUCTII APLICATE (in ordinea derivarii):\n")
            
            if not derivation_indices:
                f.write("(Nu au fost gasiti indici de productie - verifica structura arborelui)\n")
            
            for i, prod_idx in enumerate(derivation_indices, 1):
                try:
                    left, right = grammar.productions[prod_idx]
                    f.write(f"{i}. {left} -> {' '.join(right)}\n")
                except Exception as e:
                    print(f"Eroare la scrierea productiei {prod_idx}: {e}")
                    f.write(f"{i}. [Eroare index {prod_idx}]\n")
        
        print(f"\nRezultatul salvat in {output_file}")
        return True


def main():
    """Functia principala"""
    print("-"*80)
    print("ANALIZOR SINTACTIC LR(1)")
    print("="*80)
    
    input_folder = "./input-2/"
    grammar_file = input_folder + "grammar.txt"
    input_file = input_folder + "input.cs"     
    process_from_files(grammar_file, input_file)

    


if __name__ == "__main__":
    main()