bits 32

global start

extern exit, printf, scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

;Se da un numar natural negativ a (a: dword). Sa se afiseze valoarea lui in baza 10 si in baza 16, in urmatorul format: "a = <base_10> (baza 10), a = <base_16> (baza 16)"

segment data use32 class = data
    a dd 0
    input_start db 'Se da un numar negativ: ', 0
    input_end_wr_in db 'Nu ai introdus un numar negativ!', 0
    format1 db '%s'
    format2 db '%d'
    output_b10 db 'a = %d (baza 10), ', 0
    output_b16 db 'a = %x (baza 16)', 0

segment code use32 class = code
    start:
        push dword input_start
        call [printf]
        add esp, 4
            ;se afiseaza mesajul de inceput
        
        lea eax, [a]
            ;se pune adresa lui a in eax
        
        push eax
        push dword format2
        call [scanf]
        add esp, 4*2
            ;se citeste un numar si se pune la adresa lui a
        
        cmp dword [a], 0
        jge end_program_afis1
            ;se verifica daca numarul este negativ
        
        push dword [a]
        push output_b10
        call [printf]
        add esp, 2*4
        
        push dword [a]
        push output_b16
        call [printf]
        add esp, 2*4
        
        jmp end_program_ok
    
    end_program_afis1:
        push dword input_end_wr_in
        call [printf]
        add esp, 4
            ;daca numarul nu este negativ, se termina programul si se afiseaza un mesaj corespunzator
        
        push dword 0
        call [exit]
        
    end_program_ok:
        push dword 0
        call [exit]