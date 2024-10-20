/* 10a)sa se intercaleze un element pe pozitia a n-a unei liste.
 *
 * intercalareElement(L: lista, E: element, P: pozitie)
 *
 * L: lista in care intercalam elementul
 * E: elementul pe care il intercalam
 * P: pozitia pe care intercalam elementul
 *
 * Model matematic:
 * E U [L1,..,Ln]   -daca P = 1
 * L1 U intercalareElement(L2...Ln, E, P - 1) - daca P > 1
 */

 intercalareElement(L, E, 1, [E|L]):-!.
 intercalareElement([H|T], E, P, [H|R]):-
    P > 1,
    P1 is P -1,
    intercalareElement(T, E, P1, R).


/*
 * 10b) Definiti un predicat care intoarce cel mai mare divizor comun al
 * nr unei liste.
 *
 * divizorComun(L:lista, D:divizor)
 *
 * L: lista pentru care se cauta cel mai mare divizor comun
 * D: parametru in care se va retine divizorul
 *
 * Apel functie: (L2..Ln, L1)
 *
 * Model matematic:
 * 0          - daca L = []
 * N          - daca L = [N]
 * divizorComun(L2..Ln, cmmdc(D, L1) - daca D != 1
 */


/* Cel mai mare divizor comun a doua numere
 *
 *  Model matematic:
 *
 *  cmmdc(nr1, nr2) : nr1 - daca nr2 = 0
 *                    cmmdc(nr2, nr1 % nr2) - daca nr2 != 0
 */
cmmdc(A, 0, A):-!.
cmmdc(A, B, Rez):-
    B > 0,
    B1 is A mod B,
    cmmdc(B, B1, Rez).


divizorComun([], 0):-!.
divizorComun([H], H):-!.
divizorComun([H|T], D):-
    divizorComun(T, D1),
    cmmdc(H, D1, D).

