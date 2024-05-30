#1. Scrieți o funcție care sortează crescător, in place, o lista de numere folosind: quicksort.

def partition(list, left, right):
    pivot = list[left]
    i = left
    j = right
    while i != j:
        while list[j] >= pivot and i < j:
            j = j - 1
        list[i] = list[j]

        while list[i] <= pivot and i < j:
            i = i + 1

        list[j] = list[i]

    list[i] = pivot

    return i

def quick_sort(list, left, right):
    poz = partition(list, left, right)

    if left < poz - 1:
        quick_sort(1, left, poz - 1)

    if poz + 1 < right:
        quick_sort(1, poz + 1, right)


list = [1, 2,4, 6, 3, 5, 8, 11, 2, 34255, 3 ,3, 5, 3, 6, 456, 546]
#print(quick_sort(list, 0, len(list)))


#2. Specificați si testați următoarea funcție (2p):
def f(n):
    if n<=0: raise ValueError() #se stabileste domeniul functiei, numere >= decat 1
    l = []  #se creeaza o lista, fara niciun element
    while n>0:  #cat timp numarului nu i-au fost taiate toate cifrele, se executa urmatoarele instructiuni
        c = n % 10  #se retine ultima cifra a numarului n in variabila c
        n = n//10   #se efectueaza o impartire intreaga cu 10, pentru a 'taia' ultima cifra din numarul n
        l.append(c) #se adauga ultima cifra in lista anterior creata
    for i in range(len(l)-1): l[i + 1] += l[i] #dupa ce lista a fost formata, se parcurge lista astfel incat pe o pozitie i avem suma elementelor de pe pozitiile i-1, i-2 .. 0
    return l[-1] #se returneaza ultimul element al listei, adica suma tuturor cifrelor

def test_f():
    try:
        f(0)
    except ValueError as e:
        assert True
    else: assert False

    assert (f(11) == 2)
    assert (f(111) == 3)
    assert (f(9) == 9)
    assert (f(999) == 27)
    assert (f(27) == 9)
    assert (f(1000) == 1)

#4. Folosind metoda Divide et impera scrieți o funcție pură care inversează o lista de numere.
#Datele trebuie împărțite in 2 parți egale la fiecare pas. Ex. [1,2,3,4] -> [4,3,2,1]

def divide_and_conquer(list):
    if len(list) == 1:
        return list
    mid = len(list) // 2
    lista_invers =  divide_and_conquer(list[mid:]) + divide_and_conquer(list[:mid])
    return lista_invers

print(divide_and_conquer([1, 2, 3, 4, 5, 6, 7, 8]))

def test_impar(list):
    if len(list) == 1:
        if list[0] % 2 == 1:
            return [list[0]]
    first_half = list[:len(list) // 2]
    second_half = list[len(list) // 2:]
    return first_half + second_half

print('Rezultat', test_impar(list))


#5. Se dă o listă de numere. Găsiți sublista cea mai lunga cu numere pare descrescătoare. Folosiți
#programare dinamică, se cere: recurența si implementare iterativă în Python. Ex: Pentru lista
#2, 12, 3, 6, 14, 3, 4, 7, 2 soluția este 12, 6, 4, 2






