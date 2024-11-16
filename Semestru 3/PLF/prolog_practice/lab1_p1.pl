/*
 * 1. a) un predicat care intoarce diferenta a doua multimi.
 */


apartine([], _):-fail.
apartine([E|_], E):-!.
apartine([_|T], E):-
    apartine(T, E).


diferenta_multimi([],_, []).
diferenta_multimi([H|T], L, [H|Rez]):-
    not(apartine(L, H)),
    diferenta_multimi(T, L, Rez).
diferenta_multimi([H|T], L, Rez):-
    apartine(L, H),
    diferenta_multimi(T, L, Rez).


/*
 * 1. b) un predicat care adauga intr-o lista nr 1 dupa fiecare nr par
 */

adauga_par([], []).
adauga_par([H|T], [H, 1| Rez]):-
    H mod 2 =:= 0,!,
    adauga_par(T, Rez).
adauga_par([H|T], [H | Rez]):-
    adauga_par(T, Rez).

