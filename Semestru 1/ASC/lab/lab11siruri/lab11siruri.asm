bits 32 
global siruri

segment code use32=code public

siruri:

;in esp + 4 este adresa sirului de intrare
;esp + 8 = lungimea lui s
;esp + 12 = adresa sirului cu lit_mici
;esp + 16 = adresa sirului cu lit_mari


;se iau de pe stiva parametrii
cld
    ;direction flag = 0
mov esi,[esp+4]
    ;se pune adresa sirului de intrare
mov ecx,[esp+8] 
    ;lungimea sirului de intrare
mov ebx,0
    ;lungimea lui lit_mici init = 0
mov edx,0
    ;lungimea lui lit_mari init = 0

for:

    lodsw
        ;ax = caracter, inc esi 

    jmp comp_litera_mica_z
    
    jmp fin
    
    comp_litera_mica_z:
    cmp ax, 'z'
    jle comp_litera_mica_a
    
    comp_litera_mica_a:
    cmp ax, 'a'
    jge litera_mica
    jl comp_litera_mare_Z
    
    
    comp_litera_mare_Z:
    cmp ax, 'Z'
    jle comp_litera_mare_A
    
    comp_litera_mare_A:
    cmp ax, 'A'
    jge litera_mare
    jl fin
    
    
    litera_mica:
    mov edi,[esp+12]
        ;in edi va fi adresa sirului lit_mici
    add edi,ebx
        ;pozitia in sirul lit_mici baza ebx
    add edi,ebx
        ;se adauga inca odata pentru a avea lungimea de 2 octeti
    stosw
        ;ax = adresa sirului lit_mici
    inc ebx
        ;se trece la urmatorul caracter din sirul lit_mici
    jmp fin
    
    litera_mare:
    mov edi,[esp+16]
        ;in edi va fi adresa sirului lit_mici
    add edi,edx
        ;pozitia in sirul lit_mari pe baza edx
    add edi,edx
        ;se adauga inca odata pentru a avea lungimea de 2 octeti
    stosw
        ;ax = adresa sirului lit_mari
    inc edx
        ;se trece la urmatorul caracter din sirul lit_mari
    
    fin:
    
loop for

ret