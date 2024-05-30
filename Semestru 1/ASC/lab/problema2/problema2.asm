bits 32

global start
extern exit, printf

import exit msvcrt.dll
import printf msvcrt.dll

;Se da un sir de octeti S. Sa se obtina sirul D1 ce contine toate numerele pare din S si sirul D2 ce contine toate numerele impare din S

segment data use32 class = data
    s db 0xFF, 0xAB, 0x13, 0x12, 0x14, 0x15, 0xCC, 0xDD, 0xBE, 0xEB
    len equ $-s
            ;lungimea sirului s
    d1 db 0 ;numere pare
    d2 db 0 ;numere impare
    
    output_d1 db 'Numere pare: %s', 0
    output_d2 db 'Numbere impare: %s', 0

segment code use32 class = code
    start:
        mov ecx, len    ;lungimea sirului, pentru instructiunea de ciclare
        mov esi, 0 ;pentru d1, nr pare
        mov edi, 0 ;pentru d2, nr impare
        mov ebx, 0 ;parcurgerea sirului initial
        
        repeta:
            mov al, [s+ ebx]
            test al, 1
            jz d1_add
            jmp d2_add
            
            d1_add:
                mov [d1+esi], al
                inc esi
                jmp next_it
                
            d2_add:
                mov [d2+edi], al
                inc edi
               
            next_it:
                inc ebx
                jnz repeta
        
        push dword [d1]
        push dword output_d1
        call [printf]
        add esp, 4*2
        
        push dword [d2]
        push dword output_d2
        call [printf]
        add esp, 4*2
        
        push dword 0
        call [exit]