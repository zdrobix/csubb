/*
 * 14. Sa se scrie un program care genereaza lista submultimilor de suma
 * S data, cu elementele unei liste.
 */


 /*
  * Calculeaza suma elementelor listei
  *
  * suma_lista(L:lista, S:intreg)
  *
  * model de flux (i, o)
  *
  * L: lista pentru care se verifica suma elementelor
  * S: suma elementelor listei
  *
  * model matematic:
  *
  * suma_lista(l1..ln) = 0, daca n = 0
  *                      l1 + suma_lista(l2..ln), daca n > 0
  *
  */

suma_lista([], 0).
suma_lista([H|T], S):-
    suma_lista(T, R),
    S is H + R.

test_suma_lista():-
    suma_lista([1, 2], 3),
    suma_lista([], 0),
    suma_lista([1, 2, 4], 7).

/*
 * Genereaza toate submultimile unei liste
 *
 * submultimi(L:lista, R:lista)
 *
 * model de flux (i, o)
 *
 * L: lista pentru care se genereaza submultimi
 * R: lista cu submultimile listei date
 *
 * model_matematic:
 *
 * submultimi(l1..ln) =
 *    []                                             - daca n = 0
 *    {l1 U submultimi(l2..ln)} U submultimi(l2..ln) - daca n > 0
 */

submultimi([], []):-!.
submultimi([First|Rest], [First|Sub]):-
    submultimi(Rest, Sub).
submultimi([_|Rest], Sub):-
    submultimi(Rest, Sub).

/*
 * Dintr-o lista de liste, returneaza doar listele care au suma
 * elementelor S
 *
 * verifica_suma(L:lista, S:intreg, R:lista)
 *
 * model de flux (i, i, o)
 *
 * L:lista din care se extrag listele de suma S
 * S:suma elementelor cautata
 * R:lista cu toate listele ce au suma elementelor egala cu S
 *
 * model_matematic:
 *
 * verifica_suma(l1..ln, S) =
 *    []                                   - daca n = 0
 *    l1 U verifica_suma(l2..ln, S)        - daca suma_lista(l1) = S
 *    verifica_suma(l2..ln, S)             - altfel
 */

verifica_suma([], _, []):-!.
verifica_suma([H|T], S, [H|Rez]):-
    suma_lista(H, S),!,
    verifica_suma(T, S, Rez).
verifica_suma([_|T], S, Rez):-
    verifica_suma(T, S, Rez).

test_verifica_suma():-
    verifica_suma([], 0, []),
    verifica_suma([[1], [2], [1]], 2, [[2]]),
    verifica_suma([[0], [1, 2], [4], [3]], 3, [[1, 2], [3]]).

apel_submultimi(L, S, R):-
    findall(Sub, submultimi(L, Sub), ToateSub),
    verifica_suma(ToateSub, S, R).
