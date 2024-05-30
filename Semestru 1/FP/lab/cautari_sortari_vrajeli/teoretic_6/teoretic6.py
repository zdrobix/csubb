#1. Scrieți o funcție care implementează sortarea prin selecție.

def selection_sort(list):
    for i in range(0, len(list) - 1):
        index = i
        for j in range(index + 1, len(list)):
            if list[j] < list[index]:
                index = j
        list[i], list[index] = list[index], list[i]
    return list


list = [1, 2, 7, 8, 3, 19, -1, 2, 0]
print(list)
print(selection_sort(list))

#2. Specificați si testați următoarea funcție

def f(n):
    """
    Functia creeaza un sir, si returneaza valoarea de pe pozitia n a acestui sir
    :param n: >= 0, un numar natural
    :return: numarul de pe pozitia n din sirul Fibonacci
    """
    if n < 0:
        raise ValueError()
        #se stabileste domeniul functiei, numerele naturale >=0, altfel, ValueErorr()
    if n <= 1:
        return n
        #se rezolva cazurile particulare n = 0 si n = 1

    l = [0] * (n + 1)
    l[1] = 1
        #se creeaza o lista cu n + 1 elemente egale cu 0, iar al doilea element ia valoarea 1

    for i in range(2, n+1):
        l[i] = l[i - 1] + l[i - 2]
        #elementul de pe pozitia i ia valoarea sumei dintre cele doua valori anterioare

    return l[n]
    #se returneaza elementul de pe pozitia n, din sirul creat (sirul Fibonacci)

def test_f():
    try:
        f(-1)
    except ValueError as e:
        assert True
    else: assert False

    assert(f(0) == 0)
    assert(f(1) == 1)
    assert(f(2) == 1)
    assert(f(3) == 2)
    assert(f(4) == 3)
    assert(f(5) == 5)
    assert(f(6) == 8)
    assert(f(7) == 13)
    assert(f(8) == 21)
    assert(f(9) == 34)
    assert(f(10) == 55)
    assert(f(11) == 89)

#3. Analizați complexitatea timp si spațiu a următorului algoritm.
def f(n):
    s = 0
    for i in range(n*n):
        for j in range(i):
            k = 1
            while k < n:
                k *= 2
                s += 1
"""
caz favorabil (BC)
    - cand este apelat f(0)
    - complexitatea = O(1)
    
caz nefavorabil (WC)
    - cand este apelat f(n), iar n este un numar mare
    - primul loop merge de la 0 pana la n^2, deci o complexitate de n^2, iar al doilea pana la n
    - dar al treilea loop merge de la i la n^2 - 1, adica log2(n)
    - complexitate = O(n^3 + log2(n))
    
caz mediu (AC)
    - complexitatea cazului mediu este apropiata de complexitatea cazului nefavorabil
    - complexitate = O(n^3 + log2(n))
"""

#4. Folosind metoda Divide et impera scrieți o funcție pură care găsește maximul într-o lista de
#numere date. Datele trebuie împărțite in 2 parți egale la fiecare pas. Ex. L = [2,8,4,1,4,5,6,7]
#returnează 8

def divide_and_conquer(list):
    if len(list) == 1:
        return list[0]
    mid = len(list) // 2
    max1 = divide_and_conquer(list[:mid])
    max2 = divide_and_conquer(list[mid:])

    if max1 > max2:
        return max1
    return max2

print(divide_and_conquer(list))

#5. Pentru un n dat generați toate secvențele de paranteze si acolade care se închid corect.
#Exemplu: n=4 → 8 soluții: (()), ()(), (){}, ({}), {()}, {{}}, {}(), {}{}.
#Descrieți schematic soluția (candidat, consistent, soluție) bazată pe metoda Backtracking (fără
#implementare)
"""
    solutie candidat:
        x = (x1, x2, ... xk), k ⋹ (0, n)
    conditie consistent:
        x = (x1, x2, ... xk) e consistenta daca ∀i, xi = '(' si xi+1 ≠ '}'
                                            daca ∀i, xi = '{' si xi+1 ≠ ')'                      
    conditie solutie:
        x = (x1, x2, ... xk), daca k = n
                                daca |'('| == |')'| 
                                 daca |'{'| == |'}'| 
"""
lista = [1, 2, 3]
mid = len(lista) // 2
print(lista[:mid], lista[mid:])