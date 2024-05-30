bits 32

global start

extern exit
import exit msvcrt.dll



segment data use32 class = data
    input_string db 1, 2, 3, 4, 5, 1, 2, 0
    sub_string db 1, 2, 0

    
segment code use32 class = code
    _start:
        ; Set up registers
        lea esi, [input_string]  ; ESI will contain the address of the input string
        lea edi, [input_string]  ; EDI will contain the address of the input string for storing the result

    search_loop:
        ; Check if we have reached the end of the input string
        lodsb
        cmp al, 0
        je end_program

        ; Try to match the substring starting from the address in ESI
        mov ecx, 0  ; Counter for matching characters

    compare_loop:
        ; Check if we have reached the end of the substring
        lodsb
        cmp al, 0
        je found_substring

        ; Check if the characters match
        cmp al, [sub_string + ecx]
        jne not_match

        ; Characters match, continue the comparison
        inc ecx
        jmp compare_loop

    not_match:
        ; If characters don't match, restart the search from the next position in the input string
        dec esi   ; Adjust ESI to restart the search from the next position
        jmp search_loop

    found_substring:
        ; Remove the found substring from the input string
        mov ecx, 0
        sub esi, ecx   ; Adjust ESI to point to the position before the match

        ; Copy the remaining characters to the input string
        stosb
        jmp search_loop

    end_program:
        ; End of the program
        ; (You can add additional code or output here, if necessary)

        ; Exit
        mov eax, 1         ; System call for exit
        xor ebx, ebx       ; Return code 0
        int 0x80           ; Perform syscall
