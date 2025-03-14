/*
 * 6. a) un predicat care elimina toate elementele care se repeta
 */

aparitii([], _, 0).
aparitii([H|T], H, Nr):-
    aparitii(T, H, Nr1),
    Nr is Nr1 + 1,
    !.
aparitii([_|T], E, Nr):-
    aparitii(T, E, Nr).


elimina_daca([], []).
elimina_daca([H|T], Rez):-
    aparitii(T, H, Nr),
    Nr > 0,!,
    elimina_daca(T, Rez).
elimina_daca([H|T], [H|Rez]):-
    elimina_daca(T, Rez).

/*
 * 6. b) sa se elimine toate aparitiile elementului maxim
 */

maxim([H], H):-!.
maxim([H|T], H):-
    maxim(T, M),
    H > M,!.
maxim([_|T], M):-
    maxim(T, M).

elimina_el([], _, []).
elimina_el([H|T], H, Rez):-
    elimina_el(T, H, Rez),!.
elimina_el([H|T], E, [H|Rez]):-
    elimina_el(T, E, Rez).

apel(L, R):-
    maxim(L, Max),
    elimina_el(L, Max, R).
