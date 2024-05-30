#1. Scrieți o funcție care sortează o lista de numere folosind: mergsort.

def merge(left, right):
    merged_list = []
    i = j = 0
    while i < len(left) and j < len(right):
        if left[i] < right[j]:
            merged_list.append(left[i])
            i += 1
        else:
            merged_list.append(right[j])
            j += 1

    while i < len(left):
        merged_list.append(left[i])
        i += 1

    while j < len(right):
        merged_list.append(right[j])
        j += 1

    return merged_list


def merge_sort(list):
    if len(list) <= 1:
        return list

    mid = len(list) // 2
    left_half = list[:mid]
    right_half = list[mid:]

    return merge(merge_sort(left_half), merge_sort(right_half))

my_list = [10, 9, 8, 1, 2, 3, 4, 99, 2, 43, 35, 32, 3, 5]
sorted_list = merge_sort(my_list)
print(sorted_list)

#2. Specificati si testati urmatoarea functie:

def f2(n):
    """
    functia returneaza suma numerelor naturale, pana la n-1 inclusiv
    :param n: un numar mai mare sau egal decat 1
    :return:
    """
    if n <= 0:
        raise ValueError() #domeniul functiei este numerele mai mari sau egale decat 1

    l = [x for x in range(n-1,-1,-1)]
        #se pun in lista l, toate numerele naturale din intervalul inchis [0, n-1]
    for i in range(n-1): #se parcurge lista pana la elementul n-2
        l[i+1] += l[i]
            #se aduna elementul de pe pozitia i, la urmatorul element
    return l[-1]
        #se returneaza ultimul element



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
#negative într-o lista de numere. Datele trebuie împărțite in 2 parți egale la fiecare pas

def divide_and_conquer (list):
    if len(list) == 1:
        return list[0] < 0
    mid = len(list) // 2
    return divide_and_conquer(list[mid:]) + divide_and_conquer(list[:mid])

list_4 = [1, 2, 3, -1, -2, -3, 0, 1, 9]
print(divide_and_conquer(list_4))

#5. Generați toate sublistele unei liste date, cu proprietatea ca sublistele conțin ori doar numere
#pare ori doar numere impare. Descrieți schematic soluția (candidat, consistent, soluție) bazată
#pe metoda Backtracking (fără implementare)
"""
            solutie candidat:
                x = (x₁, x₂, ... xk), daca x este o sublista a listei date
            
            conditie consistent:
                x = (x₁, x₂, ... xk), daca nu exista xi % 2 != xj % 2, oricare ar fi i, j <= k
            
            conditie solutie:
                x = (x₁, x₂, ... xk), daca toate numerele au aceeasi paritate, si x este sublista
"""

