#1. Implementați căutare binara recursiv si nerecursiv.

def cautare_binara_recursiv(list, number, left, right):
    if left >= right - 1:
        return right
    mid = (left + right) // 2
    if number < list[mid]:
        return cautare_binara_recursiv(list, number, left, mid)
    else:
        return cautare_binara_recursiv(list, number, mid, right)

def bin(list, number):
    if len(list) == 0:
        return -1
    if number < list[0]:
        return -1
    if number > list[-1]:
        return -1
    return cautare_binara_recursiv(list, number, 0, len(list))

def cautare_binara_iterativ(list, number):
    if len(list) == 0: return -1
    if number < list[0]:
        return -1
    if number > list[-1]:
        return -1
    right = len(list)
    left = 0
    while right - left > 1:
        mid = (left + right) // 2
        if number < list[mid]:
            right = mid
        else:
            left = mid
    return right


list = [1, 2, 3, 5, 9, 10, 17, 90, 900, 1202]
print(bin(list, 0))
print(bin(list, 10))
print(bin(list, 900))
print(bin(list, 12309))
print(cautare_binara_iterativ(list, 0))
print(cautare_binara_iterativ(list, 10))
print(cautare_binara_iterativ(list, 900))
print(cautare_binara_iterativ(list, 12309))


#2. Specificați si testați următoarea funcție
def f(n):
    if  n<= 0:
        raise ValueError() #se stabileste domeniul functiei, numerele strict mai mari decat 0
    while n > 0: #cat timp n este mai mare decat 0 se executa urmatoarele instructiuni
        c = n % 10 #c ia valoarea ultimei cifre a lui n
        n = n // 10 #se taie ultima cifra a lui n
        if (c%2==0): #se verifica daca ultima cifra este para
            return True #daca este para, se returneaza True
    return False # daca nu s-a gasit nicio cifra para, se returneaza False

def test_f():
    try:
        f(-1)
    except ValueError as e:
        assert True
    else: assert False

    assert (f(13579) == False)
    assert (f(29999) == True)
    assert (f(20000) == True)
    assert (f(11111) == False)
    assert (f(22222) == True)
    assert (f(33333) == False)

test_f()


#4. Folosind metoda Divide et impera scrieți o funcție pură care verifică dacă o listă de numere
#conține cel puțin un număr par. Datele trebuie împărțite in 2 parți egale la fiecare pas. Ex:
#[1,3,4,5] -> Adevărat

def divide_and_conquer(list):
    if len(list) == 1:
        return list[0] % 2 == 0

    mid = len(list) // 2
    return divide_and_conquer(list[:mid]) or divide_and_conquer(list[mid:])

list1 = [1, 3, 5, 7, 9, 11, 13, 15]
list2 = [2, 4, 5, 6, 7, 12, 14, 15]
print(divide_and_conquer(list1), ' ', divide_and_conquer(list2))

#5. Se dă o listă de numere. Găsiți sublista cea mai lunga cu numere prime crescătoare. Folosiți
#programare dinamică, se cere: recurența si implementare iterativă în Python. Ex: Pentru lista
#21, 2, 11, 3, 4, 7, 13 soluția este 2, 3, 7, 13

"""
            solutie candidat:
                x = (x₁, x₂, ... xk), 0 < k <= n 

            conditie consistent:
                x = (x₁, x₂, ... xk), oricare ar fi i <= n, xi = prim
                                        oricare ar fi i <= n, xi <= xi+1

            conditie solutie:
                x = (x₁, x₂, ... xk), daca x e consistent si k = maxim
"""





