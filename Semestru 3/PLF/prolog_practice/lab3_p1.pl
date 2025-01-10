%Se dau N puncte in plan (coord). Se cere sa se determine toate
% submultimile de puncte coliniare.

verificare_coliniar((X1, Y1), (X2, Y2), (X3, Y3)):-
    (X2 - X1) * (Y3 - Y1) =:= (Y2 - Y1) * (X3 - X1).

adauga([], P, [P]).
adauga([P], P1, [P, P1]).
adauga([P1, P2| R], P, [P1, P2, P|R]):-
    verificare_coliniar(P1, P2, P).

submultimi([], _, []).
submultimi([_|T], Curent, [Curent|Rest]):-
    Curent \= [],
    submultimi(T, Curent, Rest).
submultimi([H|T], Curent,  Rest):-
    adauga(Curent, H, Nou),
    submultimi(T, Nou, Rest).

apel(L, S):-
    findall(S, submultimi(L, [], S), Toate),
    maplist(writeln, Toate).
