bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class = data
    x db '░↑←∟█☺', 0
    
segment code use32 class = code
    start:
        mov ax, 0x1000
        mov bl, 1000b + 10b
        div bl
    
        push dword 0
        call [exit]