bits 32

;Se da un nume de fisier (definit in segmentul de date). 
;Sa se creeze un fisier cu numele dat, apoi sa se citeasca de la tastatura cuvinte pana cand se citeste de la tastatura caracterul '$'. 
;Sa se scrie in fisier doar cuvintele care contin cel putin o litera mica (lowercase).

segment .data
    filename db 'inputlab8.txt', 0
    mod_acces db 'w', 0
    format db '%s', 0
    format2 db `%s\n`
    len equ 100
    cuv times len db 100
        ;variabila in care se vor citi cuvintele
    rez times len db 100
        ;variabila in care se vor salva cuvintele ce contin cel putin un lowercase

section .text
    global start
    extern exit, fopen, fread, fclose, fscanf, fprintf, scanf
    import exit msvcrt.dll
    import fopen msvcrt.dll
    import fclose msvcrt.dll
    import fscanf msvcrt.dll
    import fprintf msvcrt.dll
    import fread msvcrt.dll
    import scanf msvcrt.dll

start:
    push mod_acces
    push filename
        ;punem modul de accesare al fisierului si fisierul pe stiva
    call [fopen]
        ;deschidem fisierul   
    add esp, 8
        
    cmp eax, 0
    je end_program
        ;daca s-a produs o eroare, terminam executia programului
        
        
    mov ebx, eax
        ;salvam descriptorul de fisier
        
        
    call read_words
        ;citeste cuvinte din fisier
    ;add esp, 4
        ;se elibereaza stiva
        
        
    jmp close_file
        ;se inchide fisierul
    jmp end_program
        ;se termina executia
        
read_words:
    lea esi, [cuv]
    push esi 
    
    push format
        ; punem pe stiva formatul pentru fscanf
    call [scanf] ;scanf
        ; apelam functia de citire
    add esp, 8
        ; curatam parametrii de pe stiva
    
    call verify_word
        ;incepe verificarea cuvintelor
    cmp byte [esi], '$'
        ;compara cuvantul cu sfarsitul sirului 
    je end_of_file
        ;se termina parcurgerea fisierului
        
    jmp read_words
        ;se continua verificarea
      

verify_word:
    mov esi, cuv
    
    jmp verify_next_char
        ;incepe verificarea litera cu litera

verify_next_char:
    cmp byte [esi], 0
        ;compara caracterul cu finalul cuvantului
    je verify_end_word
        ;daca s-a ajuns la finalul cuvantului se trece la urmatorul cuvant
        
    cmp byte [esi], 'a'
    jl not_lowercase
        ;verifica daca caracterul este dupa a
    cmp byte [esi], 'z'
    jg not_lowercase
        ;verifica daca caracterul este inaintea lui a
    
    push ebx
    push format2
    push cuv
    call [fprintf]
        ;se pune cuvantul pe stiva si se afiseaza
    add esp, 8
        ;se elibereaza stiva
    jmp read_words
    
not_lowercase:
    inc esi     
    cmp byte [esi], 0
        ;compara caracterul cu finalul cuvantului
    jne verify_next_char    
        ;se continua cu verificarea literelor
    je verify_end_word
        ;daca s-a ajuns la finalul cuvantului se trece la urmatorul cuvant
    
verify_end_word:
    cmp byte [esi], '$'
        ;compara cuvantul cu sfarsitul sirului 
    je end_of_file
        ;se termina parcurgerea fisierului
    jmp read_words
        ;daca nu sunt egale continua cu urm cuvant

verify_end:
    ret    
    
end_of_file:
    ret
    
close_file:
    push ebx
    call [fclose]
    add esp, 4
    
end_program:
    push dword 0
    call [exit]
    