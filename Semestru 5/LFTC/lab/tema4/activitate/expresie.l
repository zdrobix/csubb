%{
#include "expresie.tab.h"
%}

digit   [0-9]+
space   [ \t\n]+

%%
"("         { return '('; }
")"         { return ')'; }
{digit}     { return NUM; }
"+"         { return PLUS; }
{space}     ;
.           { /* ignor orice alt caracter */ }
%%

int yywrap(){
    return 1;
}
