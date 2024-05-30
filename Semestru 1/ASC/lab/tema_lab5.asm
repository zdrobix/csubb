bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class = data

    s1 DB '+', '4', '2', 'a', '8', '4', 'X', '5'
    lens1 EQU $-s1  ;lungimea sirului s1
    
    s2 DB '1', '2', '3'
    lens2 EQU $-s2  ;lungimea sirului s2
    
    d TIMES (lens1 + 1)/3 + lens2 DB 0 ;lens1 + 1 pentru ca si pozitia 0 = multiplu de 3
    lend DD (lens1 + 1)/3 + lens2
    
    lens1mul3 DB (lens1 + 1)/3
    
segment code use32 class = code

    start:
    ;Se dau doua siruri de caractere S1 si S2. 
    ;Sa se construiasca sirul D prin concatenarea elementelor de pe pozitiile 
        ;multiplu de 3 din sirul S1 cu elementele sirului S2 in ordine inversa
        
    MOV ecx, 0                  ;eliberam ecx, pentru a pune variabila de contor in ecx
    
    MOV esi, s1                 ;esi = adresa sirului s1
    MOV edi, s2                 ;edi = adresa sirului s2
    
    MOV ebx, d                  ;punem in ebx sirul nou creat
    
    ;se creaza  eticheta de concatenat
    concateneaza:
    
        CMP ecx, [lend]         ;compara ecx cu lungimea asignata sirului D
        JE end_program          ;se termina programul
    
        CMP ecx, [lens1mul3]    ;verifica daca s-au pus toate elementele ( de pe poz mul de 3) din sirul s1
        JB copiaza_s1
        
        MOV al, [edi+lens2-1]   ;se ia caracterul curent din s2
        MOV [ebx], al           ;se adauga la sfarsitul sirului D
        
        INC ebx                 ;se trece la urmatoarea pozitie din sirul D
        DEC edi                 ;se trece la precedentul termen din s2
        INC ecx                 ;se mareste contorul
        
        JMP concateneaza        ;se continua programul
        
    copiaza_s1:
    
        MOV al, [esi]           ;se ia caracterul curent din S1
        MOV [ebx], al           ;se adauga la sfarsitul sirului D
        
        INC ebx                 ;se trece la urmatorul element din sirul D
        ADD esi, 3              ;se trece la urmatorul termen pe pozitie multipla de 3 din s1
        INC ecx                 ;se mareste contorul
        
        JMP concateneaza        ;se continua programul

    end_program:
    
        push dword 0
        call [exit]
