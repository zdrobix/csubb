%lista de submultimi cu N elemente.



submultimi([], 0, []).
submultimi([_|T], N, Sub):-
    submultimi(T, N, Sub).
submultimi([H|T], N, [H|Sub]):-
    N1 is N - 1,
    submultimi(T, N1, Sub).

apel_sub_nr(L, N, R):-
    findall(Sub, submultimi(L, N, Sub), R),
    maplist(writeln, R).
