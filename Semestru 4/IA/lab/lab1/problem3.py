"""
Să se determine produsul scalar a doi vectori rari care conțin numere reale.
Un vector este rar atunci când conține multe elemente nule. Vectorii pot avea oricâte dimensiuni.
De ex. produsul scalar a 2 vectori unisimensionali [1,0,2,0,3] și [1,2,0,3,1] este 4.
"""


# me theta(n) -> n este maximul dintre numerele de elemente al lui x si y
def scalar_product(x, y):
    x_indices = []
    y_indices = []
    count = 0
    for a in x:
        if a != 0:
            x_indices += [count]
        count += 1
    count = 0
    for b in y:
        if b != 0:
            y_indices += [count]
        count += 1
    indices = list(set(x_indices) & set(y_indices))
    result = 0
    for i in indices:
        result += x[i] * y[i]
    return result


# chat gpt
def sparse_dot_product(v1, v2):
    sparse_v1 = {i: v1[i] for i in range(len(v1)) if v1[i] != 0}
    sparse_v2 = {i: v2[i] for i in range(len(v2)) if v2[i] != 0}

    common_indices = set(sparse_v1.keys()) & set(sparse_v2.keys())
    return sum(sparse_v1[i] * sparse_v2[i] for i in common_indices)


def tests():
    (x1, y1, z1) = ([1, 0, 2, 0, 3], [1, 2, 0, 3, 1], 4)
    (x2, y2, z2) = ([1, 1, 0, 1], [0, 0, 0, 0, 0, 0], 0)
    (x3, y3, z3) = ([1, 0, 1], [0, 10, 10], 10)
    assert(scalar_product(x1, y1) == z1)
    assert(scalar_product(x2, y2) == z2)
    assert(scalar_product(x3, y3) == z3)
    assert (sparse_dot_product(x1, y1) == z1)
    assert (sparse_dot_product(x2, y2) == z2)
    assert (sparse_dot_product(x3, y3) == z3)


tests()
