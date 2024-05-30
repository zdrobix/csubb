bits 32 
;Se citeste de la tastatura un sir de caractere (litere mici si litere mari, cifre, caractere speciale, etc). 
;Sa se formeze un sir nou doar cu literele mici si un sir nou doar cu literele mari. 
;Sa se afiseze cele 2 siruri rezultate pe ecran.



global start  

section .text      
    extern exit,scanf,printf
    import exit msvcrt.dll
    import scanf msvcrt.dll
    import printf msvcrt.dll

extern siruri
    
section .data
    format db "%c ",0
    afis_sir db "Introduceti un sir de caractere: ", 0
    afis_mici db "Litere mici : %s",0
    afis_mari db "Litere mari: %s",0
    chr dw 0,0
    
    lit_mici resw 15
    lit_mari resw 15
    sir_chr resw 15
    
    lun_mici db 0
    lun_mari db 0
    lun_sir db 0
    

start:
    mov edi, sir_chr
        ;adresa sirului de intrare
    push dword afis_sir
    push dword format
    call [printf]
    add esp, 4*2
    
    start1:
    push dword chr
    push dword format
    call [scanf]
    add esp, 4*2
    
    cmp dword [chr], '$'
    je sfarsit_citire
    
    mov ax, [chr]
    stosw
    inc byte[lun_sir]
        ;lungimea sirului citit
    
    jmp start1
    sfarsit_citire:
    
    ;fct siruri(s, lungime, lit_mici, lit_mari)
    push dword lit_mari
    push dword lit_mici
    push dword [lun_sir]
    push dword sir_chr
    call siruri
    add esp, 4*4
    
    push afis_mici
    call [printf]
    add esp, 4
    mov esi, lit_mici
    mov ecx, 0
    mov cl, [lun_mici]
    
    afisare_litere_mici:
        lodsw
        cwde
        push ecx
        
        push dword eax
        push dword format
        call [printf]
        add esp, 4*2
        pop ecx
    loop afisare_litere_mici
    
    push afis_mari
    call [printf]
    add esp, 4
    mov esi, lit_mari
    mov ecx, 0
    mov cl, [lit_mari]
    
    afisare_litere_mari:
        lodsw
        cwde
        push ecx
        
        push dword eax
        push dword format
        call [printf]
        add esp, 4*2
        pop ecx
    loop afisare_litere_mari
    
    push dword 0
    call [exit]
    
    
   