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
 * Genereaza submultimile unei liste, daca au suma egala cu S
 *
 * submultimi(L:lista, S:intreg)
 *
 * model de flux (i, i)
 *
 * L: lista pentru care se genereaza submultimi de suma S
 * S: suma care trebuie sa o aiba elementele submultimiilor generate
 *
 * model_matematic:
 *
 * submultimi(l1..ln, S) = [],
 *
 */