bits 32 

global start  

section .text
    extern exit, scanf, printf
    import exit msvcrt.dll
    import scanf msvcrt.dll
    import printf msvcrt.dll
    
    
    
section .data
    input1 db '1) (c+d)-(a+d)+b  ', 10, 0
    
    inputext db '9)Exit the program.',10 , 0
    inputopt db 'Enter the option: ', 0
    
    option db 0
    format db '%d', 0
    
    p1a db 0
    p1b dw 0
    p1c dd 0
    p1d dq 0
    inputpb1 db 'Enter values for a, b, c and d:', 0
    inputpb1rez db '(c+d)-(a+d)+b= %d', 10, 0
    
    
start:
    push input1
    call [printf]
    add esp, 4
    
    push inputext
    call [printf]
    add esp, 4
    
    push inputopt
    call [printf]
    add esp, 4
    
    lea edx, [option]
    push edx
    push format
    call [scanf]
    add esp, 4*2
    
    cmp byte [option], 1
    je problema1
    
    cmp byte [option], 9
    je end_program
    
    
problema1:
    push inputpb1
    call [printf]
    add esp, 4
    
    lea ebx, [p1a]
    push ebx
    push format
    call [scanf]
    add esp, 4*2
    
    lea ebx, [p1b]
    push ebx
    push format
    call [scanf]
    add esp, 4*2
    
    lea ebx, [p1c]
    push ebx
    push format
    call [scanf]
    add esp, 4*2
    
    lea ebx, [p1d]
    push ebx
    push format
    call [scanf]
    add esp, 4*2
    
end_program:
    push dword 0
    call [exit]
    
    
    
    
    