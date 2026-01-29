%{
#include <stdio.h>
#include <string.h>
#include "parser.tab.h"

extern int yylineno;
%}

%option yylineno

%%

"int"           { return INT; }
"read"          { return READ; }
"write"         { return WRITE; }
"<-"            { return ASSIGN; }

[a-zA-Z_][a-zA-Z0-9_]*  { 
                    yylval.str = strdup(yytext); 
                    return ID; 
                }

[0-9]+          { 
                    yylval.num = atoi(yytext); 
                    return NUM; 
                }

"+"             { return PLUS; }
"-"             { return MINUS; }
"*"             { return MULT; }
"("             { return LPAREN; }
")"             { return RPAREN; }
";"             { return SEMI; }
","             { return COMMA; }

[ \t\r\n]+      { /* skip whitespace */ }

.               { 
                    fprintf(stderr, "Caracter necunoscut: %s la linia %d\n", 
                            yytext, yylineno); 
                }

%%

int yywrap() {
    return 1;
}