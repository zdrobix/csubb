; Translatorul Mini-Language -> ASM

section .data
    format_in db "%d", 0
    format_out db "%d", 10, 0
    x dd 0
    y dd 0

section .bss
    t0 resd 1
    t1 resd 1
    t2 resd 1
    t3 resd 1
    t4 resd 1

section .text
global __asm_main
extern _printf
extern _scanf

__asm_main:
    push ebp
    mov ebp, esp

    ; read(x)
    push x
    push format_in
    call _scanf
    add esp, 8
    mov eax, [x]
    imul eax, [x]
    mov [t0], eax
    mov eax, [x]
    add eax, [t0]
    mov [t1], eax
    mov eax, [t1]
    add eax, [x]
    mov [t2], eax
    mov eax, [t2]
    add eax, 5
    mov [t3], eax
    mov eax, [t3]
    sub eax, 5
    mov [t4], eax
    mov eax, [t4]
    mov [y], eax
    ; write(y)
    push DWORD [y]
    push format_out
    call _printf
    add esp, 8

    mov eax, 0
    mov esp, ebp
    pop ebp
    ret
