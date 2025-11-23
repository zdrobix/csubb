%{
#include <stdio.h>

int line_no = 1;

#define ID 0
#define CONST 1
#define SEMICOLON 2
#define INT 3
#define DOUBLE 4
#define STRING 5
#define LPAREN 6
#define RPAREN 7
#define PLUS 8
#define MUL 9
#define MINUS 10
#define DIV 11
#define MOD 12
#define CITIRE_INT 13
#define CITIRE_DOUBLE 14
#define LBRACE 15
#define RBRACE 16
#define COMMA 17
#define QUESTION 18
#define EQ 19
#define NEQ 20
#define LT 21
#define GT 22
#define LTE 23
#define GTE 24
#define ASSIGN 25
#define QUOTE 26
#define AFISEAZA 27
#define IF 28
#define ELSE 29
#define WHILE 30
#define RETURN 31

#define CATTIMP 32
#define EXECUTA 33
#define SFCATTIMP 34

typedef struct {
    char name[100];
    int line;
} Symbol;

Symbol table[1000];
int symbol_count = 0;

void add_symbol(const char* name, int line) {
    for (int i = 0; i < symbol_count; i++) {
        if (strcmp(table[i].name, name) == 0)
            return;
    }

    strcpy(table[symbol_count].name, name);
    table[symbol_count].line = line;
    symbol_count++;
}
%}

%option noyywrap

%%

[ \t\r]+        ; 

\r?\n         {line_no++; }

"int"                { printf("INT(%d)\n", line_no); }
"double"             { printf("DOUBLE(%d)\n", line_no);  }
"string"             { printf("STRING(%d)\n", line_no);  }
"citire_int"         { printf("CITIRE_INT(%d)\n", line_no);  }
"citire_double"      { printf("CITIRE_DOUBLE(%d)\n", line_no);  }
"afiseaza"           { printf("AFISEAZA(%d)\n", line_no);  }
"if"                 { printf("IF(%d)\n", line_no); }
"else"               { printf("ELSE(%d)\n", line_no);  }
"while"              { printf("WHILE(%d)\n", line_no);  }
"return"             { printf("RETURN(%d)\n", line_no);  }

"cattimp"            { printf("CATTIMP(%d)\n", line_no); }
"executa"            { printf("EXECUTA(%d)\n", line_no); }
"sfcattimp"          { printf("SFCATTIMP(%d)\n", line_no); }



[A-Za-z_][A-Za-z0-9_]* { 
    printf("ID(%d)\n", line_no); 
    add_symbol(yytext, line_no);
}

[0-9]+(\[0-9]+)?     { printf("CONST_INT(%d)\n", line_no);  }
[0-9]+(\.[0-9]+)?     { printf("CONST_DOUBLE(%d)\n", line_no);  }


";"                   { printf(";\n");  }
","                   { printf(",\n"); }
"("                   { printf("(\n");  }
")"                   { printf(")\n");  }
"{"                   { printf("{\n");  }
"}"                   { printf("}\n");  }
"+"                   { printf("+\n");  }
"-"                   { printf("-\n");  }
"*"                   { printf("*\n");  }
"/"                   { printf("/\n");  }
"%"                   { printf("%\n");  }
"?"                   { printf("?\n");  }
"=="                  { printf("==\n");  }
"!="                  { printf("!=\n");  }
"<="                  { printf("<=\n");  }
">="                  { printf(">=\n");  }
"<"                   { printf("<\n");  }
">"                   { printf(">\n");  }
"="                   { printf("=\n");  }
"\""                  { printf("\"\n");  }


.       { printf("EROARE LEXICALA linia %d: %s\n", line_no, yytext); }

%%

int main()
{
    yylex();

    printf("\n------\n");
    for (int i = 0; i < symbol_count; i++) {
        printf("%s : #%d\n", 
               table[i].name, table[i].line);
    }

    return 0;
}