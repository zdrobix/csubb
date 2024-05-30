"""
Se da o lista de numere intregi. Generati toate permutarile listei pentru care numerele au aspect de munte(cresc pana la un punct de unde descresc)
ex: 1, 2, 3, 4, 18, 17, 13, 3, 2

lista_input = 1, 2, 3, 4, 5, 6, 7, 8


"""
lista_input = [1, 2, 3, 4, 5]
lista_back = [0] * len(lista_input)

def verificare_munte(lista):
    if max(lista) in [lista[0], lista[-1]]:
        return False
    i = 0
    while lista[i] < max(lista):
        if lista[i] > lista[i + 1]:
            return False
        i = i + 1
    i = len(lista) - 1
    while lista[i] < max(lista):
        if lista[i] > lista[i - 1]:
            return False
        i = i - 1
    return True

def backtrack_iterativ_permutari(lista):
    global count_i
    n = len(lista)
    while True:
        for index1 in range(n - 1, 0, -1):
            if lista[index1 - 1] < lista[index1]:
                break
        else:
            break

        if index1 == 1 and lista[0] >= lista[1]:
            break

        index2 = n - 1
        while lista[index2] <= lista[index1 - 1]:
            index2 = index2 - 1

        lista[index1 - 1], lista[index2] = lista[index2], lista[index1 - 1]

        lista[index1:] = reversed(lista[index1:])
        if verificare_munte(lista):
            count_i += 1
            print(lista)

def verificare1(k):
    for index in range (0, k):
        if lista_back[k] == lista_back[index]:
            return False
    return True

def backtrack_recursiv_permutari(nr):
    global count_r
    for index in range(0, len(lista_input)):
        lista_back[nr] = lista_input[index]
        if verificare1(nr):
            if nr == len(lista_input) - 1:
                if verificare_munte(lista_back):
                    count_r += 1
                    print(lista_back)
            else:
                backtrack_recursiv_permutari(nr + 1)


count_i = 0
print('\n','Permutarile in forma de munte, generate cu ajutorul variantei iterative sunt: ')
backtrack_iterativ_permutari(lista_input)

count_r = 0
print('\n','Permutarile in forma de munte, generate cu ajutorul recursive iterative sunt: ')
backtrack_recursiv_permutari(0)

assert(count_r == count_i)
if count_r == count_i:
    print('\n', 'Este acelasi numar de permutari, pentru fiecare varianta')