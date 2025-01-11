%se da sirul a1..an, sa se determine toate subsirurile strict crescatoare
%ale sirului a

subsir3([], _, []).
subsir3([H|T], Last, [H|Rez]):-
    H > Last,
    subsir3(T, H, Rez).
subsir3([_|T], Last, Rez):-
    subsir3(T, Last, Rez).



