bits 32 

global start        

extern exit               
import exit msvcrt.dll    


segment data use32 class=data
    y dw -256, 256h
    x dw 256|-256, 256h & 256
    z db $-z, y-x
    db 'y' - 'x', 'x' - 'y'
    a db 512 >> 2, -512 << 2
    b dw z-a
    c dd ($-b) + (d-$);, $-2*y + 3
    d db -128, 128^(~128)
    e times 2 resw 6
    times 2 dd 1234h, 5678h


segment code use32 class=code
    start:
        
        
    
        push    dword 0 
        call    [exit]   
