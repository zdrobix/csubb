"""
'''
# produsul numerelor de pe pozitii pare

def prod_pare(lista,st = 0, dr = -2):
    if dr == -2:
        dr = len(lista)-1
    if st == dr:
        if st%2 == 0:
            return lista[st]
        else:
            return 1
    mid = (st+dr)//2
    return prod_pare(lista,st,mid) * prod_pare(lista,mid+1,dr)

lista = [1,2,3,4,5,6]
print(prod_pare(lista))

def f(a, b , c):
    a = a + 1
    b.append(3)
    c = c + [3]

a = 7
b = [1, 2]
c = [1, 2]
f(a, b, c)
print(a, b , c)

def x():
    x = [1, 2, 3]
    x1 = [1] + x[1:]
    x2 = x[:2] + [x[-1]]
    print(x1, x2, '\n')
    print(x1, id(x1) == id(x))
    print(x2, id(x2) == id(x))
    print(id(x1) == id(x2))

print(f'\n,\n')
x()
m = [25, 26, 27, 28, 29]
print(m[3:])
print(m[:3])

class A:
    def f(self, a):
        a = a + 1
    def h(self, l1, l2):
        a = l1[0]
        self.f(a)
        l1[0] = a
        l2 = l2 + l1

a = A()
l1 = [1]
l2 = []
a.h(l1, l2)
print(l1, l2)


class MyInt:
    def __init__(self, n):
        self.__n = n
    def get(self):
        return self.__n
    def __add__(self, nr):
        self.__n += 1
        return self

def increm(i):
    i = i + 1
    if i.get() > 3:
        raise ValueError()

k = MyInt(3)
try:
    print(k.get())
    increm(k)
    print(k.get())
    increm(k)
    print(k.get())
    increm(k)
except ValueError:
    print(0)
"""

def div(list):
    if list == [] or list == None: return
    if len(list) == 1:
        if list[0] >= 0:
            return [list[0] * list[0]]
        else: return [list[0]]
    m = len(list) // 2
    return div(list[:m]) + div(list[m:])

print(div([1, 2, 3, 4, -1, -2, -4, 5]))