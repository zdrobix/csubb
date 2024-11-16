/*
 * 4. a) predicat care substituie intr-o lista un element, printr-o alta
 * lista
 */

substituie([], _, _, []).
substituie([H|T], H, L, [L|T]):-!.
substituie([H|T], E, L, [H|Rez]):-
    substituie(T, E, L, Rez).

/*
 * 4. b) sa se elimine elementul de pe pozitia n
 */

elimina([], _, []).
elimina([_|T], 0, T):-!.
elimina([H|T], N, [H|Rez]):-
    N > 0,!,
    N1 is N - 1,
    elimina(T, N1, Rez).
