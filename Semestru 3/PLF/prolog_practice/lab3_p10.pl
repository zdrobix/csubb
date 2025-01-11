%submultimile cu suma divizibila cu N


submultimi([], S, N, []):-
    0 is S div N.
submultimi([H|T], S, N, [H|Rez]):-
    S1 is S - H,
    submultimi(T, S1, N, Rez).
submultimi([_|T], S, N, Rez):-
    submultimi(T, S, N, Rez).

