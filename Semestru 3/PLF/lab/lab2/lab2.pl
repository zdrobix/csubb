%Problema 10

/*
 * Functie auxiliara: verifica daca numarul primit este o putere de 2
 *
 * putere2(N: intreg)
 *
 * N: numarul pentru care verificam daca este putere de 2
 *
 * model matematic:
 *
 * putere2(N) :  true                 - daca n = 1
 *               false                - daca n % 2 = 1
 *               putere2(N/2) - daca n % 2 = 0
 */

putere2(1):-!.
putere2(N):-
    N > 1,
    0 is N mod 2,
    N2 is N // 2,
    putere2(N2).
putere2():-
    false.

test_putere():-
    assertion(putere2(1)),
    assertion(putere2(2)),
    assertion(putere2(4)),
    assertion(putere2(8)),
    assertion(putere2(16)),
    assertion(not(putere2(0))),
    assertion(not(putere2(3))),
    assertion(not(putere2(7))),
    assertion(not(putere2(10))),
    assertion(not(putere2(12))).



/*
 * 10a) Se da o lista de numere intregi. Se cere sa se adauge in lista
 * dupa primul element, al 3-lea element, al 7-lea element, al 15-lea
 * element .. o valoare data e.
 *
 * adaugaLista(L: lista, E: element, P: intreg, R: lista)
 *
 * model de flux (i, i) sau (o, i)
 * L: lista in care se doreste inserarea lui e pe pozitiile 1,3,7,15..
 * E: elementul care se doreste a fi inserat in lista L
 * P: un intreg auxiliar cu care se determina pozitia unde se insereaza
 * R: lista finala dupa inserarea elementului E pe pozitiile aferente
 *
 * model matematic:
 *
 * adaugaLista (L1..Ln, E, P) =
 *          []                                      - daca n = 0
 *          L1 U E U adaugaLista(L2..Ln, E, P + 1)  - daca P = 2^k - 1
 *          L1 U adaugaLista(L2..Ln, E, P + 1)      - daca P/= 2^k - 1
 *
 *          cu k > 0
 */


adaugaLista([], _, _, []):-!.
adaugaLista([H|T], E, P, [H, E|R]):-
    P1 is P + 1,
    putere2(P1),!,
    adaugaLista(T, E, P1, R).
adaugaLista([H|T], E, P, [H|R]):-
    adaugaLista(T, E, P + 1, R).

apel_adaugaLista([H|T], E, R):-
    adaugaLista([H|T], E, 1, R).

test_adaugaLista():-
    assertion(adaugaLista([], 1, 1, [])),
    assertion(adaugaLista([1, 3], 2, 1, [1, 2, 3])),
    assertion(adaugaLista([1, 2, 3], 8, 1, [1, 8, 2, 3, 8])),
    adaugaLista([], 1, 1, L1),
    adaugaLista([1, 3], 2, 1, L2),
    adaugaLista([1, 2, 3], 8, 1, L3),
    assertion(L1 = []),
    assertion(L2 = [1, 2, 3]),
    assertion(L3 = [1, 8, 2, 3, 8]).

/*
 * 10b) Se da o lista eterogena, formata din numere intregi si liste de
 * numere intregi. Lista incepe cu un numar si nu sunt 2 elemente
 * consecutive care sunt liste. Se cere ca in fiecare sublista sa se
 * adauge dupa primul, al 3-lea, al 7-lea element valoarea care se
 * gaseste inainte de sublista in lista eterogena.
 *
 * listaE(L: lista, E: intreg, R: lista)
 *
 * model de flux (i, i) sau (o, i)
 *
 * L: lista pe care o parcurgem pentru a insera elementul E in subliste
 * E: elementul pe care il inseram in sublistele listei L
 * R: lista finala avand ultimul intreg inainte de fiecare lista pe
 * pozitiile aferente
 *
 * model matematic:
 *
 * listaE(L1..Ln, E) =
 *    []                                         - daca n = 0
 *    listaE(L2..Ln, L1)                         - daca L1 e element
 *    listaE(adaugaLista(L1, E, 1) U L2..Ln, E)  - daca L1 e lista
 *
 */


listaE([], _, []):-!.
listaE([H|T], _, [H|R]):-
    integer(H),!,
    listaE(T, H, R).
listaE([H|T], E, [Lnew|R]):-
    is_list(H),
    adaugaLista(H, E, 1,Lnew),
    listaE(T, E, R).

apel_listaE([H|T], R):-
    listaE([H|T], _, R).


test_listaE():-
    assertion(listaE([], _, [])),
    assertion(listaE([1, [1]], _, [1, [1, 1]])),
    assertion(listaE([0, [1], 9, 0, [1, 1], 9, 8, 0, [1, 1, 1]], _, [0, [1, 0], 9, 0, [1, 0, 1], 9, 8, 0, [1, 0, 1, 1, 0]])),
    listaE([], _, L1),
    listaE([1, [1]], _, L2),
    listaE([0, [1], 9, 0, [1, 1], 9, 8, 0, [1, 1, 1]], _, L3),
    assertion(L1 = []),
    assertion(L2 = [1, [1, 1]]),
    assertion(L3 = [0, [1, 0], 9, 0, [1, 0, 1], 9, 8, 0, [1, 0, 1, 1, 0]]).

test_all():-
    test_putere(),
    test_adaugaLista(),
    test_listaE().



