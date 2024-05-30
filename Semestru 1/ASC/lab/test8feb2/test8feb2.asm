bits 32 

global start        

extern exit               
import exit msvcrt.dll    


segment data use32 class=data
    x dw -129, 10+100h+1000b
    y dw 1001h >> 1001b, 128h & 128
    z dw z, $$-z
    w dd x+y-z, w-y+x
    h dw 101b, 11h-11b, h-11;, 101-h
    a db $$-$;, h-11b
    b dd h-b + 0ah - 0bh;, a+b-0bh + 2
    c db z-w;, 3-b
    d dw -513, 128^(~128)
    e dd 'a', 'b', 'c', 'ad', 'ed'
    f dw w-1;, [w-1]
    g times 3 dw 'db'
    k dw c+0ch;, 1 + 2b + 3h + a
    m dd a + 0ah;, a + ah
    s dd start-start1, a-start

segment code use32 class=code
    start:
        
    start1:
    
        push    dword 0 
        call [exit]