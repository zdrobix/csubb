%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

extern int line_no;
extern int yylex();
extern FILE *yyin;

typedef struct {
    char name[100];
    int line;
} Symbol;

extern Symbol table[];
extern int symbol_count;

void yyerror(const char *s);
%}

%union {
    char *str;
}

%token <str> ID CONST_INT CONST_DOUBLE CONST_STRING
%token INT DOUBLE STRING MAIN
%token CITIRE_INT CITIRE_DOUBLE AFISEAZA
%token IF ELSE WHILE RETURN
%token SEMICOLON COMMA LPAREN RPAREN LBRACE RBRACE
%token PLUS MINUS MUL DIV MOD
%token EQ NEQ LT GT LTE GTE ASSIGN

%left EQ NEQ
%left LT GT LTE GTE
%left PLUS MINUS
%left MUL DIV MOD
%right ASSIGN

%%

aplicatie:
    INT MAIN LPAREN RPAREN LBRACE lista_initializari lista_instructiuni RBRACE
    {
        printf("Programul este corect din punct de vedere sintactic.\n");
    }
    ;

lista_initializari:
    /* epsilon - lista goala */
    | lista_initializari initializare
    ;

initializare:
    tip ID SEMICOLON
    ;

tip:
    INT
    | DOUBLE
    | STRING
    ;

lista_instructiuni:
    /* epsilon */
    | lista_instructiuni instructiune
    ;

instructiune:
    atribuire
    | ciclare
    | selectie
    | iesire
    | afisare
    ;

atribuire:
    ID ASSIGN expresie SEMICOLON
    | ID ASSIGN CITIRE_INT LPAREN RPAREN SEMICOLON
    | ID ASSIGN CITIRE_DOUBLE LPAREN RPAREN SEMICOLON
    ;

ciclare:
    WHILE LPAREN conditie RPAREN LBRACE lista_instructiuni RBRACE
    ;

selectie:
    IF LPAREN conditie RPAREN LBRACE lista_instructiuni RBRACE
    | IF LPAREN conditie RPAREN LBRACE lista_instructiuni RBRACE ELSE LBRACE lista_instructiuni RBRACE
    ;

conditie:
    expresie
    ;

expresie:
    expresie PLUS expresie
    | expresie MINUS expresie
    | expresie MUL expresie
    | expresie DIV expresie
    | expresie MOD expresie
    | expresie EQ expresie
    | expresie NEQ expresie
    | expresie LT expresie
    | expresie GT expresie
    | expresie LTE expresie
    | expresie GTE expresie
    | LPAREN expresie RPAREN
    | termen
    ;

termen:
    CONST_INT
    | CONST_DOUBLE
    | CONST_STRING
    | ID
    ;

iesire:
    RETURN expresie SEMICOLON
    ;

afisare:
    AFISEAZA LPAREN expresie RPAREN SEMICOLON
    ;

%%

void yyerror(const char *s) {
    fprintf(stderr, "Eroare la linia %d: %s\n", line_no, s);
}

int main(int argc, char **argv) {
    if (argc > 1) {
        FILE *file = fopen(argv[1], "r");
        if (!file) {
            perror("Eroare la deschiderea fisierului");
            return 1;
        }
        yyin = file;
    }
    
    printf("Incepe analiza sintactica\n\n");
    
    int result = yyparse();
    
    if (result == 0) {
        printf("\nTabela de Simboluri:\n");
        for (int i = 0; i < symbol_count; i++) {
            printf("%s : linia #%d\n", table[i].name, table[i].line);
        }
    }
    
    if (argc > 1) {
        fclose(yyin);
    }
    
    return result;
}