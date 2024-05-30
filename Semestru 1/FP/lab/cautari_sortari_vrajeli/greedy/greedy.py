def lg_sublist(list):
    lgs = [0] * len(list)
    lgs[-1] = 1
    for i in range( len(list) - 2, -1, -1):
        lgs[i] = 1
        for j in range(i + 1, len(list)):
            if list[i] <= list[j] and lgs[i] < lgs[j] + 1:
                lgs[i] = lgs[j] + 1
    return max(lgs)


print(lg_sublist([1, 2, 3, 4, 5, 6, 7, 8, 0, 10, 11, 0, 13, 14, 15, 16]))


def sublist(list):
    l = [0] * len(list)
    p = [0] * len(list)
    l[-1] = 1
    p[-1] = -1
    for k in range( len(list) - 2, -1, -1):
        p[k] = -1
        l[k] = 1
        for i in range(k+1, len(list)):
            if list[i]>= list[k] and l[k] <l[i] + 1:
                l[k] = l[i] + 1
                p[k] = i
    j = 0
    for i in range(0, len(list)):
        if l[i] > l[j]:
            j = i

    rez = []
    while j != -1:
        rez = rez + [list[j]]
        j = p[j]
    print(p)
    print(l)
    return rez

a = [2, 4, 6, 8, 0, 2, 6, 10, 12, 14, 16, 20]

print(sublist(a))