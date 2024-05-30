bits 32 

global start        

;Se citeste de la tastatura un nume de fisier si un numar N (0<= N < 8). 
;Programul va lua cel de-al N-ule bit din fiecare caracter(byte) din fisier si va numara bitii 1. 
;Programul va afisa numarul de biti la consola.

section .text
    extern exit, fopen, scanf, printf, fclose, fscanf
    import exit msvcrt.dll
    import fopen msvcrt.dll
    import scanf msvcrt.dll
    import printf msvcrt.dll
    import fclose msvcrt.dll
    import fscanf msvcrt.dll

               
section .data
    filename db '', 0
    mod_acces db "r", 0
    mesaj_input db "Se da numele unui fisier : ", 0
    mesaj_input2 db "Se da un numar : ", 0
    numar_citit db 0
    format db "%s", 0
    char db 0
    descriptor db 0
    
start:
    push mesaj_input
    call [printf]
    add esp, 4
    
    mov eax, 0
    lea eax, [filename]
    push eax
    push format
    call [scanf]
    add esp, 4*2
        ;se citeste de la tastatura numele fisierului
    
    
    push dword mod_acces
    push dword filename
    call [fopen]
    add esp, 4*2
        ;se deschide fisierul, si se salveaza descriptorul de fisier
    
    mov [descriptor], eax
        
    push mesaj_input2
    call [printf]
    add esp, 4
    
    lea ebx, [numar_citit]
    push ebx
    push format
    call [scanf]
    add esp, 4*2
        ;citim un numar de la tastatura
        
    jmp citire_fisier
    
citire_fisier:
    push dword char
    push dword format
    push dword [descriptor]
    call [fscanf]
    add esp, 4*3
        ;citeste fiecare numar din fisier
    
    cmp dword [char], 0
    je end_program
        ;citirea se termina atunci cand se intalneste cifra 0
      
    push dword char
    call [printf]
    add esp, 4
        ;se afiseaza numarul citit
    
    jmp citire_fisier
        ;se repeta

  
end_program:
    mov ebx, eax
    call [fclose]
        ;inchide fisierul
    push dword 0
    call [exit]
        
