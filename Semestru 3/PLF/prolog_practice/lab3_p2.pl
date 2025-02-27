%Fiind dat un numar n pozitiv sa se determine toate descompunerile sale
% ca suma de numere prime distincte.

prim(0, _):-false.
prim(1, _):-false.
prim(2, _):-true.
prim(N, D):-
    D * D =< N,
    0 is N mod D,!,
    false.
prim(N, D):-
    D * D =< N,
    D1 is D + 1,
    prim(N, D1).
prim(_, _):-true.

genereaza_prim(M, M1):-
    M1 is M + 1,
    prim(M1, 2),!.
genereaza_prim(M, M1):-
    M2 is M + 1,
    genereaza_prim(M2, M1).

append2([], L, L).
append2([H|T], L, [H|R]):-
    append2(T, L, R).

descompunee(0, Curent, _, [Curent]).
descompunee(N, Curent, Prim, Rest):-
    N > 0,
    genereaza_prim(Prim, P),
    N1 is N - P,
    N1 >= 0,
    descompunee(N1, [P|Curent], P, Rest1),
    descompunee(N, Curent, Prim, Rest2),
    append2(Rest1, Rest2, Rest).
descompunee(N, _, _, []):-
    N < 0.

apel(N, Toate):-
    findall(D, descompunee(N, [], 2, D), Toate),
    maplist(writeln, Toate).

