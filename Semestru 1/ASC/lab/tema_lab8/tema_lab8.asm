bits 32

section .data
    max dd 0
        ;maximul
        
    number dd 0
        ;variabila prin care trec numerele citite
        
    format db '%d', 0
        ;formatul pentru citit numere
        
    input db 'Introduceti un sir de numere: ', 0
        ;sirul de numere care se termina cu 0
        
    output db 'Maximul este : %d', 0
        ;sirul de caractere care se va afisa
    
section .text
    global start
    extern exit, printf, scanf
    import exit msvcrt.dll
    import printf msvcrt.dll
    import scanf msvcrt.dll
    
;Se citesc de la tastatura numere (in baza 10) pana cand se introduce cifra 0. 
;Determinaţ şi afişaţ cel mai mare număr dintre cele citite.
    
start:
    push input
    call [printf]
    add esp, 4
        ;afisez mesajul, si eliberez parametrul de pe stivax
    jmp citire
    
citire:
    lea eax, [number] ;mov eax, number
    push eax
    push format
    call [scanf]
    add esp, 8
    
    cmp dword [number], 0
        ;se compara cu 0 sa vedem daca sirul s-a terminat
    je afisare_maxim
        ;daca este = 0 se termina executia
    ja compare_max
        ;se compara numarul citit cu maximul
    jmp citire
    

compare_max:
    mov ecx, dword [number]
        ;se pune numarul citit intr-un registru
    cmp ecx, [max]
        ;se compara numarul citit cu maximul
    ja update_max_schimb
        ;sare la schimbarea valorii max
    jmp citire
        ;daca este <=, se continua citirea
    
    
update_max_schimb:
    mov [max], ecx
        ;daca este mai mare, se inlocuieste valoarea, apoi se continua
    jmp citire
    
    
afisare_maxim:
    push dword [max]
    push output
    call [printf]
        ;se afiseaza maximul din sir
    add esp, 8
        ;se elibereaza stiva
        
    jmp end_program
        ;se termina executia programului
    
end_program:
    push dword 0        
    call [exit]