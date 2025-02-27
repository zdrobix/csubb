/*
 * 2. a) un predicat care detrmina cel mai mic multiplu comun al el unei
 * liste
 */

/*calculeaza cel mai mic divizor comun*/
cmmdc(A, 0, A):-!.
cmmdc(A, B, Rez):-
    B > 0,
    B1 is A mod B,
    cmmdc(B, B1, Rez).

cmmmc(A, B, Rez):-
    cmmdc(A, B, D),
    Rez is A * B / D.



multiplu_lista([], 0).
multiplu_lista([H], H):-!.
multiplu_lista([H|T], M):-
    multiplu_lista(T, M1),
    cmmmc(H, M1, M).


/*
 * 2. b) un predicat care adauga dupa elementul 1, 2, 4, 8.. o valoare v
 */

putere(1).
putere(X):-
    0 is X mod 2,
    X2 is X // 2,
    putere(X2).
putere2():-false.

adauga_lista([], _, _, []).
adauga_lista([H|T], V, P, [H, V|Rez]):-
    putere(P),!,
    P1 is P + 1,
    adauga_lista(T, V, P1, Rez).
adauga_lista([H|T], V, P, [H|Rez]):-
    P1 is P + 1,
    adauga_lista(T, V, P1, Rez).


apel(L, V, R):-
    adauga_lista(L, V, 1, R).
