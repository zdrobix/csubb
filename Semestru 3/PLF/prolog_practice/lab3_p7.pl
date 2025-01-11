%sa se genereze lista aranjamentelor de k elemente cu elementele unei
% liste date.

insert7(E, L, [E|L]).
insert7(E, [H|T], [H|R]):-
    insert7(E, T, R).

permutari7([], []).
permutari7([H|T], R):-
    permutari7(T, R2),
    insert7(H, R2, R).

combinari7([H|_], 1, [H]).
combinari7([H|T], K, [H|Rez]):-
    K > 1,
    K1 is K - 1,
    combinari7(T, K1, Rez).
combinari7([_|T], K, Rez):-
    combinari7(T, K, Rez).

aranjamente7(L, K, Sol):-
    combinari7(L, K, Combinari),
    permutari7(Combinari, Sol).
