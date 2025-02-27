/* 10a)sa se intercaleze un element pe pozitia a n-a unei liste.
 *
 * intercalareElement(L: lista, E: element, P: pozitie, R: lista)
 *
 * model de flux(i, i, i, o)
 *
 * L: lista in care intercalam elementul
 * E: elementul pe care il intercalam
 * P: pozitia pe care intercalam elementul
 * R: lista rezultata in urma intercalarii
 *
 * Model matematic:
 * intercalareElement(L1...Ln, E, P) =
 * E U [L1,..,Ln]   - daca P = 1
 * L1 U intercalareElement(L2...Ln, E, P - 1) - daca P > 1
 */

 intercalareElement(L, E, 1, [E|L]):-!.
 intercalareElement([H|T], E, P, [H|R]):-
    P > 1,
    P1 is P - 1,
    intercalareElement(T, E, P1, R).

/* Cel mai mare divizor comun a doua numere
 *
 *  cmmdc(A:intreg, B: intreg, Rez: intreg)
 *
 *  model de flux(i, i, o)
 *  A: primul numar
 *  B: al doilea numar
 *  Rez: cel mai mare divizor comun al lui a si b
 *
 *  Model matematic(A, B):
 *
 *  cmmdc(nr1, nr2) : nr1 - daca nr2 = 0
 *                    cmmdc(nr2, nr1 % nr2) - daca nr2 != 0
 */
cmmdc(A, 0, A):-!.
cmmdc(A, B, Rez):-
    B > 0,
    B1 is A mod B,
    cmmdc(B, B1, Rez).

/*
 * 10b) Definiti un predicat care intoarce cel mai mare divizor comun al
 * nr unei liste.
 *
 * divizorComun(L:lista, D:divizor)
 *
 * model de flux(i, o)
 *
 * L: lista pentru care se cauta cel mai mare divizor comun
 * D: parametru in care se va retine divizorul
 *
 * Apel functie: (L2..Ln, L1)
 *
 * Model matematic:
 * 0          - daca L = []
 * N          - daca L = [N]
 * cmmdc(L1, divizorComun(L2..Ln)) - daca D != 1
 */



divizorComun([], _):-!.
divizorComun([H], H):-!.
divizorComun([H|T], D):-
    divizorComun(T, D1),
    cmmdc(H, D1, D).



test_All():-
    assertion(intercalareElement([1, 2, 3], 99, 1, [99, 1, 2, 3])),
    assertion(intercalareElement([1, 2, 3], 99, 2, [1, 99, 2, 3])),
    assertion(divizorComun([10, 20, 30], 10)),
    assertion(divizorComun([1, 2, 34], 1)),
    assertion(not(divizorComun([1, 2, 3], 2))),
    assertion(not(intercalareElement([1, 2, 3], 1, 1, [1, 2, 3]))),
    assertion(not(intercalareElement([1, 2], 4, 4, [1, 2, 4]))).

