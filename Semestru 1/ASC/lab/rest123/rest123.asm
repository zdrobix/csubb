bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class = data
    x db 1, 2, 3, 4

    
    

segment code use32 class = code
    start:
        mov al, 5
        mov bl, 170
        mul bl
        
    push dword 0
    call [exit]