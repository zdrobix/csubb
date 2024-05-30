bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class = data
    ;am marcat finalul sirurilor cu 0
    sirx DB 1, 2, 3, 4, 5, 1, 2, 3, 0 
    siry DB 1, 2, 0
    
segment code use32 class = code
    start:
        ;Dandu-se un sir de octeti si un subsir al sau, sa se elimine din primul sir toate aparitiile subsirului.
        cld                 ;direction fag = 0
        mov esi, sirx         ;esi = adresa sirului de intrare
        mov edi, sirx         ;edi = adresa sirului, pentru a salva rezultatul
    
    cauta_loop:
        ;verifica daca s-a ajuns la sfarsitul sirului (sirx)
        lodsb                   ;se incarca octetul
        cmp al, 0               ;se compara cu 0 (sfarsitul sirului, caracterul nul)
        je end_program          ;daca este egal cu 0, se termina programul
    
        mov ecx, 0              ;counter register
    
    compara_loop:
        ;verifica daca s-a ajuns la sfarsitul subsirului (siry)
        lodsb               
        cmp al, 0               ;incarca octetul si il compara cu 0
        je gasit_subsir         ;daca s-a ajuns la sfarsitul subsirului, se trece la eticheta
        
        ;se verifica daca elementele sunt la fel
        cmp al, [siry + ecx]    ;compara octetul incarcat cu corespondenta din sir
        jne nu_suntlafel        ;daca nu se potrivesc, sare la eticheta 'nu sunt la fel'
        
        ;elementele corespund, continua executia
        inc ecx                 ;se incrementeaza counter-ul
        jmp compara_loop        ;se trece inapoi la parcurgerea sirului
        
    nu_suntlafel:
        ;daca elementele nu corespund, se reia cautarea de la urm pozitie in sirx
        mov ecx, 0              ;se elibereaza ecx
        inc esi                 ;se trece la urmatorul element
        jmp cauta_loop          ;continua executia
    
    gasit_subsir:
        ;se sterge subsirul gasit din sirul de intrare
        mov ecx, 0              ;
        sub edi, ecx            ;destinatia = pozitia inainte de a gasi elementul la fel
          
        ;se copiaza caracterele ramase in sirul de intrare
        stosb
        jmp cauta_loop          ;se continua executia
    
    end_program:
        push dword 0
        call [exit]