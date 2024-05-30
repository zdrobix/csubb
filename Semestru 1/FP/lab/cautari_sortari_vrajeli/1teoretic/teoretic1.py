#Let us consider a list a1, a2, … an of integer numbers. Using the “Divide et Impera” programming method, write,
#specify and test a function to compute the number of even elements from the list.

list = [1, 2, 3, 4, 5, 6, 7, 8]

def divide_and_conquer (list):
    if list is []:
        return 0
    if len(list) == 1:
        if list[0] % 2 == 0:
            return 1
        return 0
    if list[0] % 2 == 0:
        return 1 + divide_and_conquer(list[1:])
    return 0 + divide_and_conquer(list[1:])

def test_divide_and_conquer():
    full_list = [2, 4, 6, 66, 666, 888 ,80, 88]
    assert(divide_and_conquer(full_list)== 8)

    no_list = [1, 3, 5, 7, 9]
    assert(divide_and_conquer(no_list)== 0)

    regular_list = [1, 2, 3, 4, 5, 6, 7 ,8, 9]
    assert (divide_and_conquer(regular_list)== 4)

test_divide_and_conquer()
print(divide_and_conquer(list))

