/*
 * 14. Sa se scrie un program care genereaza lista submultimilor de suma
 * S data, cu elementele unei liste.
 */

/*
 * submultimi_suma(L:lista, S:intreg, R:lista)
 *
 * L: lista pentru care se genereaza submultimi de suma s
 * S: suma elementelor fiecarei submultimi din lista rezultat
 * R: lista care contine toate submultimile listei l, de suma s
 *
 * model de flux(i, i, o)
 *
 * model matematic:
 *
 * submultimi_suma(l1..ln, S) =
 *
 *    []                                - daca n = 0
 *    submultimi(l2..ln, S)
 *    l1 U submultimi(l2..ln, S - l1    - daca n\= 0
 *
 */


submultimi_suma([], 0, []).
submultimi_suma([_|T], S, Sub):-
    submultimi_suma(T, S, Sub).
submultimi_suma([H|T], S, [H|Sub]):-
    S1 is S - H,
    submultimi_suma(T, S1, Sub).

apel_sub(L, S, R):-
    findall(Sub, submultimi_suma(L, S, Sub), R).
