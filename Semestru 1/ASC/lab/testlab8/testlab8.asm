section .data
    filename db 'inputlab8.txt', 0
    mod_acces db 'w', 0
    format db '%s', 0
    descriptor dd -1
    
    cuv db 100 dup(0) ; Variabila în care se vor citi cuvintele

section .text
    global start
    extern exit, fopen, fclose, fscanf, printf
    import exit msvcrt.dll
    import fopen msvcrt.dll
    import fclose msvcrt.dll
    import fscanf msvcrt.dll
    import printf msvcrt.dll

start:
    push mod_acces
    push filename
    call [fopen]
    mov ebx, eax
    cmp eax, 0
    je end_program
        
    push ebx
    call read_words
    add esp, 4
        
    push ebx
    call [fclose]
    jmp end_program
        
read_words:
    mov edi, dword [esp+4]
    push edi
    push format
    call [fscanf]
    add esp, 8
    
    push cuv
    push format
    call [printf]
    add esp, 8
    
    jmp read_words
    
end_program:
    push dword 0
    call [exit]
