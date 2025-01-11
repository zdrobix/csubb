% 2 nr naturale: m si n. sa se afiseze in toate modurile posibile toate
% numerele de la 1 la n astfel incat intre orice doua numere afisate pe
% pozitii consecutive diferenta in modul sa fie >= m.


generare(_, _, _, []).
generare(N, M, Last, [N|Rez]):-
    N >= 1,
    (N - Last) * (N - Last) >= M * M,
    N1 is N - 1,
    generare(N1, M, N, Rez).
generare(N, M, _, Rez):-
    N1 is N - 1,
    generare(N1, M, N, Rez).

apel(N, M, Sol):-
    findall(S, generare(N, M, 0, S), Sol),
    maplist(writeln, Sol).
