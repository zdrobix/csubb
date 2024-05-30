bits 32


segment .data
    input db 'Introduceti un sir de caractere: ', 0
        ;mesaj
    format db '%s', 0
        ;formatul inputului
    sirinput resb 100
        ;sirul de intrare
    sirmici resb 100
        ;sirul unde se vor pune literele mici
    sirmari resb 100
        ;sirul unde se vor pune literele mari
    

section .text
    global start
    extern exit, printf, scanf
    import exit msvcrt.dll
    import printf msvcrt.dll
    import scanf msvcrt.dll
    
start:
    push input
    call [printf]
    add esp, 4
    
    lea eax, [sirinput]
    push eax
    lea eax, [format]
    push format
    call [scanf]
    add esp, 8
    
    mov esi, sirinput   ;inceputul sirului de intrare
    mov edi, sirmici   ;inceputul sirului de litere mici
    mov edx, sirmari   ;inceputul sirului de litere mari

    
citire:
    mov al, [esi]
    cmp al, '$'
        ;verifica daca s-a ajuns la finalul inputului   
    je end_program
    
    cmp al, 'a'
    jl not_lowercase
    
    cmp al, 'z'
    jg not_lowercase
    
    mov [edi], al
    inc edi
    jmp continue
    
not_lowercase:
    cmp al, 'A'
    jl continue
    
    cmp al, 'Z'
    jg continue
    
    mov [edx], al
    inc edx
    
continue:
    inc esi
    jmp citire
    
    
    
end_program:
    mov byte [edi], 0
    mov byte [edx], 0

    push dword [sirmici]
    push dword [format]
    call [printf]
    add esp, 8
    
    push dword [sirmari]
    push dword [format]
    call [printf]
    add esp, 8
    
    push dword 0
    call [exit]
    

;Se citeste de la tastatura un sir de caractere (litere mici si litere mari, cifre, caractere speciale, etc). Sa se formeze un sir nou doar cu literele mici si un sir nou doar cu literele mari. Sa se afiseze cele 2 siruri rezultate pe ecran.