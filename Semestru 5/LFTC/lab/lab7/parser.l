%{
#include <stdio.h>
#include <string.h>
#include "parser.tab.h"

int line_no = 1;

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

[ \t\r]+              { /* ignore whitespace */ }
\n                    { line_no++; }

"int"                 { return INT; }
"double"              { return DOUBLE; }
"string"              { return STRING; }
"main"                { return MAIN; }
"citire_int"          { return CITIRE_INT; }
"citire_double"       { return CITIRE_DOUBLE; }
"afiseaza"            { return AFISEAZA; }
"if"                  { return IF; }
"else"                { return ELSE; }
"while"               { return WHILE; }
"return"              { return RETURN; }

[A-Za-z_][A-Za-z0-9_]* { 
    add_symbol(yytext, line_no);
    yylval.str = strdup(yytext);
    return ID; 
}

[+-]?[0-9]+           { 
    yylval.str = strdup(yytext);
    return CONST_INT; 
}

[+-]?[0-9]+\.[0-9]+   { 
    yylval.str = strdup(yytext);
    return CONST_DOUBLE; 
}

\"([^\"\\]|\\.)*\"    { 
    yylval.str = strdup(yytext);
    return CONST_STRING; 
}

";"                   { return SEMICOLON; }
","                   { return COMMA; }
"("                   { return LPAREN; }
")"                   { return RPAREN; }
"{"                   { return LBRACE; }
"}"                   { return RBRACE; }
"+"                   { return PLUS; }
"-"                   { return MINUS; }
"*"                   { return MUL; }
"/"                   { return DIV; }
"%"                   { return MOD; }
"=="                  { return EQ; }
"!="                  { return NEQ; }
"<="                  { return LTE; }
">="                  { return GTE; }
"<"                   { return LT; }
">"                   { return GT; }
"="                   { return ASSIGN; }

.                     { 
    fprintf(stderr, "EROARE LEXICALA linia %d: caracter nerecunoscut '%s'\n", 
            line_no, yytext); 
}

%%