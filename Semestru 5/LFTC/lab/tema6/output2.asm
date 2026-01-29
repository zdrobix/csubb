; Translatorul Mini-Language -> ASM

section .data
    format_in db "%d", 0
    format_out db "%d", 10, 0
    a dd 0

section .text
global __asm_main
extern _printf
extern _scanf

__asm_main:
    push ebp
    mov ebp, esp

    ; read(a)
    push a
    push format_in
    call _scanf
    add esp, 8
    ; write(a)
    push DWORD [a]
    push format_out
    call _printf
    add esp, 8

    mov eax, 0
    mov esp, ebp
    pop ebp
    ret
