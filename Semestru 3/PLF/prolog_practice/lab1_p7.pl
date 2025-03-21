/*
 * 7. a) un predicat care intoarce reuniunea a doua multimi
 */

apare([], _):-false.
apare([H|_], H):-true.
apare([_|T], E):-
    apare(T, E).


reuniune([], L, L).
reuniune([H|T], L, [H|Rez]):-
    not(apare(L, H)),!,
    reuniune(T, L, Rez).
reuniune([H|T], L, Rez):-
    apare(L, H),!,
    reuniune(T, L, Rez).

/*
 * 7. b) [a, b, c, d] -> [[a, b], [a, c], [a, d], [b, c], [b, d], [c,d]]
 */

perechi([], _, []):-!.
perechi([_|T], [], Rez):-
    perechi(T, T, Rez).
perechi([H|T], [H1|T1], [[H,H1]|Rez]):-
        H \= H1,!,
        perechi([H|T], T1, Rez),!.
perechi([H|T], [_|T1], Rez):-
        perechi([H|T], T1, Rez),!.


apel(L, R):-
    perechi(L, L, R).
