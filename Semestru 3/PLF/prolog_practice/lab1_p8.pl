/*
 * 8. a) un predicat care verifica daca lista e multime
 */

aparee([], _):-false.
aparee([H|_], H):-true.
aparee([_|T], E):-
    aparee(T, E).


e_multime(_):-true,!.
e_multime([H|T]):-
    not(aparee(T, H)),
    e_multime(T).

/*
 * 8. b) un predicat care elimina primele 3 aparitii ale unui numar, sau
 * toate daca nr apare de mai putin de 3 ori
 */

elimina([], _, _, []).
elimina([H|T], H, Nr, Rez):-
    Nr > 0,
    Nr1 is Nr - 1,!,
    elimina(T, H, Nr1, Rez).
elimina([H|T], E, Nr, [H|Rez]):-
    elimina(T, E, Nr, Rez).
