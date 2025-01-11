%descompunerea unui numar ca suma de numere naturale consecutive

descompunere(0, _, _, _, []).
descompunere(N, CopieN, Prim, Ultim, [Ultim|Rez]):-
    N1 is N - Ultim,
    N1 >= 0,!,
    Curent is Ultim + 1,
    descompunere(N1, CopieN, Prim, Curent, Rez).
descompunere(N, _, _, _, []):-
    N < 0.
descompunere(_, CopieN, Prim, _, Rez):-
    Prim < CopieN,
    NouPrim is Prim + 1,
    descompunere(CopieN, CopieN, NouPrim, NouPrim, Rez).


apel_11(N, S):-
    findall(D, descompunere(N, N, 1, 1, D), S),
    maplist(writeln, S).
