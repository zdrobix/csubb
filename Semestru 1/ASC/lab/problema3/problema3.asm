bits 32


global start        


extern exit               
import exit msvcrt.dll    
                          


segment data use32 class=data
    name db 'Alex12 Zdroba', 0


segment code use32 class=code
    start:
        mov edx, 33
        mov ecx, name
        mov ebx, 1
        mov eax, 4
        int 0x80
        
        mov dword [name], 'Alex'
        mov dword [name + 4], '_Zdr'
        mov dword [name+8], 'oba!'
        
        mov byte [name + 15], 0
        
        mov edx, 17
        mov ecx, name
        mov ebx, 1
        mov eax, 4
        int 0x80
    
        xor ebx, ebx
        
        push    dword 0    
        call    [exit]      
