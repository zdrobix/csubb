bits 32

global start

;Se da un sir A de cuvinte. Construiti doua siruri de octeti  
;- B1: contine ca elemente partea superioara a cuvintelor din A
;- B2: contine ca elemente partea inferioara a cuvintelor din A

extern exit
import exit msvcrt.dll
extern printf
import printf msvcrt.dll

section data use32 class = data
    sir_a dw 0x1234, 0x5678, 0x9ABC, 0xDEF0, 0
    len_a equ ($-sir_a) / 2 
    sir_b1 db 0
    sir_b2 db 0
    format1 db 'octetii superiori: %s', 10
    format2 db 'octetii inferiori: %s', 10
    
section code use32 class = code
    start:
        mov esi, sir_b1
        mov edi, sir_b2
        
        xor ecx, ecx
        
        jmp start_loop
       
        start_loop:
            cmp ecx, len_a
            jge end_program
            
            mov ax, [sir_a + ecx*2]
            mov [esi], ah
            mov [edi], al
            
            inc esi
            inc edi
            
            add ecx, 1
            jmp start_loop
            
        end_program:
            mov byte [esi], 0
            mov byte [edi], 0
        
            push dword sir_b1
            push dword format1
            call [printf]
            add esp, 4
            
            push dword sir_b2
            push dword format2
            call [printf]
            add esp, 4
            
            push dword 0
            call [exit]
            