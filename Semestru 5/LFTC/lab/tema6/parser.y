%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

extern int yylex();
extern int yylineno;
extern FILE* yyin;

void yyerror(const char* s);
int get_temp_var();

int temp_counter = 0;
int temp_stack[100];
int temp_top = -1;

// Buffere pentru diferite sectiuni
char data_section[10000] = "";
char code_section[10000] = "";
char bss_section[10000] = "";

void add_to_data(const char* str) {
    strcat(data_section, str);
}

void add_to_code(const char* str) {
    strcat(code_section, str);
}

void add_to_bss(const char* str) {
    strcat(bss_section, str);
}

%}

%union {
    int num;
    char* str;
}

%token <str> ID
%token <num> NUM
%token INT READ WRITE ASSIGN
%token PLUS MINUS MULT
%token LPAREN RPAREN SEMI COMMA

%type <str> expr term factor

%%

program:
    statements
    ;

statements:
    statement
    | statements statement
    ;

statement:
    declaration
    | read_stmt
    | write_stmt
    | assign_stmt
    ;

declaration:
    INT id_list SEMI
    ;

id_list:
    ID { 
        char buffer[100];
        sprintf(buffer, "    %s dd 0\n", $1);
        add_to_data(buffer);
        free($1);
    }
    | id_list COMMA ID { 
        char buffer[100];
        sprintf(buffer, "    %s dd 0\n", $3);
        add_to_data(buffer);
        free($3);
    }
    ;

read_stmt:
    READ LPAREN ID RPAREN SEMI { 
        char buffer[200];
        sprintf(buffer, "    ; read(%s)\n    push %s\n    push format_in\n    call _scanf\n    add esp, 8\n", $3, $3);
        add_to_code(buffer);
        free($3); 
    }
    ;

write_stmt:
    WRITE LPAREN ID RPAREN SEMI { 
        char buffer[200];
        sprintf(buffer, "    ; write(%s)\n    push DWORD [%s]\n    push format_out\n    call _printf\n    add esp, 8\n", $3, $3);
        add_to_code(buffer);
        free($3); 
    }
    ;

assign_stmt:
    ID ASSIGN expr SEMI {
        char buffer[200];
        sprintf(buffer, "    mov eax, %s\n    mov [%s], eax\n", $3, $1);
        add_to_code(buffer);
        free($1);
        free($3);
    }
    ;

expr:
    term {
        $$ = $1;
    }
    | expr PLUS term {
        int t = get_temp_var();
        char buffer[300];
        sprintf(buffer, "    mov eax, %s\n    add eax, %s\n    mov [t%d], eax\n", $1, $3, t);
        add_to_code(buffer);
        
        char* result = malloc(20);
        sprintf(result, "[t%d]", t);
        $$ = result;
        
        free($1);
        free($3);
    }
    | expr MINUS term {
        int t = get_temp_var();
        char buffer[300];
        sprintf(buffer, "    mov eax, %s\n    sub eax, %s\n    mov [t%d], eax\n", $1, $3, t);
        add_to_code(buffer);
        
        char* result = malloc(20);
        sprintf(result, "[t%d]", t);
        $$ = result;
        
        free($1);
        free($3);
    }
    ;

term:
    factor {
        $$ = $1;
    }
    | term MULT factor {
        int t = get_temp_var();
        char buffer[300];
        sprintf(buffer, "    mov eax, %s\n    imul eax, %s\n    mov [t%d], eax\n", $1, $3, t);
        add_to_code(buffer);
        
        char* result = malloc(20);
        sprintf(result, "[t%d]", t);
        $$ = result;
        
        free($1);
        free($3);
    }
    ;

factor:
    ID {
        char* result = malloc(strlen($1) + 3);
        sprintf(result, "[%s]", $1);
        free($1);
        $$ = result;
    }
    | NUM {
        char* result = malloc(20);
        sprintf(result, "%d", $1);
        $$ = result;
    }
    | LPAREN expr RPAREN {
        $$ = $2;
    }
    ;

%%

void yyerror(const char* s) {
    fprintf(stderr, "Eroare de sintaxa la linia %d: %s\n", yylineno, s);
}

int get_temp_var() {
    if (temp_top >= 0) {
        return temp_stack[temp_top--];
    }
    return temp_counter++;
}

int main(int argc, char** argv) {
    if (argc < 2) {
        fprintf(stderr, "Utilizare: %s <fisier_sursa> [fisier_iesire]\n", argv[0]);
        return 1;
    }
    
    yyin = fopen(argv[1], "r");
    if (!yyin) {
        fprintf(stderr, "Eroare la deschiderea fisierului %s\n", argv[1]);
        return 1;
    }
    
    FILE* output = stdout;
    if (argc >= 3) {
        // Deschide in modul binar pentru a evita BOM pe Windows
        output = fopen(argv[2], "wb");
        if (!output) {
            fprintf(stderr, "Eroare la crearea fisierului %s\n", argv[2]);
            fclose(yyin);
            return 1;
        }
        
        // Genereaza direct in fisier
        // Genereaza sectiunea .data
        fprintf(output, "; Translatorul Mini-Language -> ASM\n\n");
        fprintf(output, "section .data\n");
        fprintf(output, "    format_in db \"%%d\", 0\n");
        fprintf(output, "    format_out db \"%%d\", 10, 0\n");
        
        // Parse-ul va umple bufferele
        int result = yyparse();
        
        // Scrie sectiunile
        fprintf(output, "%s", data_section);
        
        if (temp_counter > 0) {
            fprintf(output, "\nsection .bss\n");
            for (int i = 0; i < temp_counter; i++) {
                fprintf(output, "    t%d resd 1\n", i);
            }
        }
        
        fprintf(output, "\nsection .text\n");
        fprintf(output, "global __asm_main\n");
        fprintf(output, "extern _printf\n");
        fprintf(output, "extern _scanf\n\n");
        fprintf(output, "__asm_main:\n");
        fprintf(output, "    push ebp\n");
        fprintf(output, "    mov ebp, esp\n\n");
        fprintf(output, "%s", code_section);
        fprintf(output, "\n    mov eax, 0\n");
        fprintf(output, "    mov esp, ebp\n");
        fprintf(output, "    pop ebp\n");
        fprintf(output, "    ret\n");
        
        fclose(output);
        fclose(yyin);
        return result;
    } else {
        // Output la stdout - parseaza si afiseaza
        int result = yyparse();
        
        fclose(yyin);
        return result;
    }
}