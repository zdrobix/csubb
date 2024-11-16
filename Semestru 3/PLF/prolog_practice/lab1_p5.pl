/*
 * 5. a) un predicat care sterge aparitiile unui atom dintr-o lista
 */

sterge_atom([], _, []).
sterge_atom([H|T], H, Rez):-!,
    sterge_atom(T, H, Rez).
sterge_atom([H|T], A, [H|Rez]):-
    sterge_atom(T, A, Rez).

/*
 * 5. b) un predicat care dintr o lista de atomi produce o lista de
 * perechi atom+ nr aparitii
 */
nr_aparitii([], _, 0).
nr_aparitii([H|T], H, Nr) :-
    nr_aparitii(T, H, Nr1),
    Nr is Nr1 + 1.
nr_aparitii([_|T], E, Nr) :-
    nr_aparitii(T, E, Nr).

apare([], _):-false.
apare([(H, _)|_], H):-true.
apare([(_, _)|T], E):-
    apare(T, E).

perechi([], []).
perechi([H|T], [P|Rez]):-
    not(apare(Rez, H)),
    nr_aparitii([H|T], H, Nr),
    P = (H, Nr),
    perechi(T, Rez).
perechi([_|T], Rez):-
    perechi(T, Rez).

