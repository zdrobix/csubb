%{
#include <stdio.h>
int yylex();
void yyerror(const char *s);
int gresit = 0; 
%}

%token NUM PLUS

%%
expr: term
    | expr PLUS term
    ;

term: NUM
    | '(' expr ')'
    ;
%%

void yyerror(const char *s){
    gresit = 1;
}

int main(){
    yyparse();
    if(!gresit)
        printf("se inchid corect\n");
    else
        printf("nu se inchid corect\n");
    return 0;
}
