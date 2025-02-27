%5. Se sorteaza numerele impare dintr-o listsa de intregi cu pastrarea
% dublurilor. De exL [4, 7, 3, 2, 3, 7] -> [3, 3, 7, 7]

/*
 * nr_aparitii(L:lista, E:intreg, Nr:intreg)
 *
 * model de flux (i, i, o)
 *
 * model matematic:
 *
 * nr_aparitii(l1..ln, E) = 0, daca n = 0
 *                          1 + nr_aparitii(l2..ln, E), daca l1 = E
 *                          nr_aparitii(l2..ln, E), daca l1 \= E
 */

nr_aparitii([], _, 0).
nr_aparitii([H|T], H, Nr):-
    nr_aparitii(T, H, Nr1),!,
    Nr is Nr1 + 1.
nr_aparitii([_|T], E, Nr):-
    nr_aparitii(T, E, Nr).

test_nr_aparitii():-
    assertion(nr_aparitii([], 1, 0)),
    assertion(nr_aparitii([1, 2, 3], 4, 0)),
    assertion(nr_aparitii([1, 1, 1, 3, 3], 1, 3)).

/*
 * elimina_par(L:lista, R:lista)
 *
 * model de flux (i, o)
 *
 * model matematic:
 *
 * elimina_par(l1..ln) = [], daca n = 0
 *                       l1 U elimina_par(l2..ln), daca l1 % 2 = 1
 *                       elimina_par(l2..ln), daca l1 % 2 = 0
 */

elimina_par([], []).
elimina_par([H|T], Rez):-
    0 is H mod 2,!,
    elimina_par(T, Rez).
elimina_par([H|T], [H|Rez]):-
    1 is H mod 2,
    elimina_par(T, Rez).

test_elimina_par():-
    assertion(elimina_par([], [])),
    assertion(elimina_par([1, 2, 3, 4], [1, 3])),
    assertion(elimina_par([1, 3, 5], [1, 3, 5])).


/*
 * elimina_unice(L:lista, LCopie:lista, R:lista)
 *
 * model de flux (i, i, o)
 *
 * model matematic:
 *
 * elimina_unice(l1..ln, l1..ln) =
 *         [], daca n = 0
 *         l1 U elimina_unice(l2..ln, l1..ln), daca nr_aparitii(l1) > 1
 *         elimina_unice(l2..ln, l1..ln), daca nr_aparitii(l1) = 1
 */

elimina_unice([], _, []).
elimina_unice([H|T], L, [H|Rez]):-
    nr_aparitii(L, H, Nr),
    Nr > 1,!,
    elimina_unice(T, L, Rez).
elimina_unice([_|T], L, Rez):-
    elimina_unice(T, L, Rez).


test_elimina_unice():-
    assertion(elimina_unice([], [], [])),
    assertion(elimina_unice([1, 1, 2, 2], [1, 1, 2, 2], [1, 1, 2, 2])),
    assertion(elimina_unice([1, 2, 3, 3], [1, 2, 3, 3], [3, 3])).

/*
 * minim(L:lista, E:intreg)
 *
 * model de flux (i, o)
 *
 * apel: minim(l2..ln, l1)
 *
 * model matematic:
 *
 * minim(l2..ln, l1) = l1, daca  n = 0
 *                     l1, daca l1 < l2
 *                     l2, daca l2 < l1
 */

minim([], _).
minim([H|T], E):-
    E < H,
    minim(T, E).
minim([H|_], H).

minim_reimplementat(H, _, H).
minim_reimplementat([H|T], LastMin, LastMin2):-
    LastMin < H,
    minim_reimplementat(T, LastMin2, LastMin).

test_minim():-
    minim([1, 2, 3], 1),
    minim([1, 3, 3, 3, 4, 1], 1).

/*
 * sorteaza(L:lista, R:rezultat)
 *
 * model de flux (i, o)
 *
 * model matematic:
 *
 * sorteaza(l1..ln) = [], daca n = 0
 *                    l1 U sorteaza(l2..ln), l1 = minim(l1..ln)
 *
 */

elimina([], _, []).
elimina([H|T], H, T):-!.
elimina([_|T], E, Rez):-
    elimina(T, E, Rez).

sorteaza([], []).
sorteaza([H|T], [Rez|Minim]):-
    minim_reimplementat(T, H, Minim),
    elimina([H|T], Minim, Restul),
    sorteaza(Restul, Rez).


apel(L, R):-
    elimina_par(L, FaraPare),
    elimina_unice(FaraPare, FaraPare, FaraUnice),
    sorteaza(FaraUnice, R).




