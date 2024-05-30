#1. Scrieți o funcție care implementează sortarea prin inserție.

def insertion_sort(list):
    for i in range(1, len(list)):
        index = i - 1
        el = list[i]
        while index >= 0 and el < list[index]:
            list[index + 1] = list[index]
            index = index - 1
        list[index + 1] = el
    return list

list = [1, 2, 4, 10, 99, -10, -129, 0, 7, 34, 20]
print(list)
print(insertion_sort(list))

#2. Specificați si testați următoarea funcție

def f2(n):
    if n<=0: raise ValueError()
        #se stabileste domeniul functiei, numerele naturale mai mari sau egale decat 1
    l = [x for x in range(n-1,-1,-1)]
        #se creeaza o lista l, cu numerele de la n-1, pana la 0 inclusiv, de la cel mai mare, la cel mai mic
    for i in range(n-1):
        #se parcurge lista pana la elementul n-2 inclusiv
        l[i+1] += l[i]
            #elementului urmator i se adauga valoarea elementului curent
    return l[-1]
        #se returneaza ultimul element al listei

def test_f2():
    try:
        f2(0)
    except ValueError as e:
        assert True
    else : assert False

    try:
        f2(-1)
    except ValueError as e:
        assert True
    else : assert False

    assert(f2(7) == 21)
    assert(f2(6) == 15)
    assert(f2(5) == 10)
    assert(f2(4) == 6)
    assert(f2(3) == 3)
    assert(f2(2) == 1)
    assert(f2(1) == 0)

test_f2()

#3. Analizați complexitatea timp si spațiu a următorului algoritm.

def f(l):
    if len(l) == 1:
        return l[0]
    if l[0] == 0:
        return 0
    return l[0] * f(l[1:])

"""
caz favorabil (BC) 
    - lungimea listei sa fie 1 sau 0 sa fie primul element
    - complexitatea = 0(1)
    
caz nefavorabil (WC)
    - cand lista nu contine niciun 0, si trebuie sa fie parcursa toata
    - complexitatea = 0(n)
    
caz mediu (AC)
    - P(0) este probabilitatea de a intalni numarul 0, si E(i) numarul de operatii efectuate
    - ∑P(0)E(i), 0 <= i <= n
        = 1/1 * 1 + 1/2 * 2 + 1/3 * 3 + ... + 1/n * n
        = 1 + 2 + 3 + ... + n
        = O(n)
        
BC = WC = AC = 0(n)
"""

#4. Folosind metoda Divide et impera scrieți o funcție pură care calculează numărul de numere
#pozitive într-o lista de numere. Datele trebuie împărțite in 2 parți egale la fiecare pas

def divide_and_conquer(list):
    if len(list) == 1:
        return list[0] >= 0
    mid = len(list) // 2
    return divide_and_conquer(list[:mid]) + divide_and_conquer(list[mid:])

print(divide_and_conquer(list))

#5. Generați toate sublistele unei liste date, cu proprietatea ca sublistele conțin ori doar numere
#negative ori doar numere pozitive. Descrieți schematic soluția (candidat, consistent, soluție)
#bazata pe metoda Backtracking (fără implementare)

"""
            solutie candidat:
                x = (x₁, x₂, ... xk), daca x este o sublista a listei date
            
            conditie consistent:
                x = (x₁, x₂, ... xk), daca nu exista xi % 2 != xj % 2, oricare ar fi i, j <= k si k <= n
            
            conditie solutie:
                x = (x₁, x₂, ... xk), daca x e consistent
"""