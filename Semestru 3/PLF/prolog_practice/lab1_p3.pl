/*
 * 3. a) un predicat care transforma o lista intr-o multime, in ordinea
 * primei aparitii
 */

apartine_lista([], _):-false.
apartine_lista([E|_], E):-true,!.
apartine_lista([_|T], E):-
    apartine_lista(T, E).


transforma([], []).
transforma([H|T], [H|Rez]):-
    not(apartine_lista(T, H)),!,
    transforma(T, Rez).
transforma([_|T], Rez):-
    transforma(T, Rez).

/*
 * 3. b) un predicat care returneaza: [lista-pare, lista-impare,
 * nr-pare, nr-impare]
 */

descompune([], [[], [], 0, 0]).
descompune([H|T], [[H|Rez], Impare, P, I]):-
    0 is H mod 2,!,
    descompune(T, [Rez, Impare, P1, I]),
    P is P1 + 1.
descompune([H|T], [Pare, [H|Rez], P, I]):-
    1 is H mod 2,
    descompune(T, [Pare, Rez, P, I1]),
    I is I1 + 1.



