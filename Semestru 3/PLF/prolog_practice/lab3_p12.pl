%submultimi cu aspect de munte
%

submultimi11([], _, 1, []).
submultimi11([H|T], Last, 0, [H|Rez]):-
    H > Last,
    submultimi11(T, Last, 0, Rez).
submultimi11([H|T], Last, 0, [H|Rez]):-
    H < Last,
    submultimi11(T, H, 1, Rez).
submultimi11([H|T], Last, 1, [H|Rez]):-
    H < Last,
    submultimi11(T, H, 1, Rez).
submultimi11([_|T], Last, F, Rez):-
    submultimi11(T, Last, F, Rez).

