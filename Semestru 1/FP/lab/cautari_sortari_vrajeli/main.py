list = [10, 20, 30 , 40, 199, 999, 7777, 4, 7, 8]

def find_max_dc(list):
    if len(list) == 1:
        return list[0]
    mid = len(list) // 2
    max1 = find_max_dc(list[:mid])
    max2 = find_max_dc(list[mid:])
    if max1 > max2:
        return max1
    return max2

def find_max_dc_11(list):
    if len(list) == 1:
        return list[0]
    max = find_max_dc_11(list[1:])
    if max > list[0]:
        return max
    return list[0]

print("The list is : ", list)
print("The maximum value is : ", find_max_dc_11(list), find_max_dc(list))


def is_ok(x):
    if len(x) > 1:
        for i in range(len(x) - 1):
            if x[i] == 2 and x[i+1] == 3 or x[i] == 3 and x[i+1] == 2 :
                return False
    return True

def generate(x, DIM):
    if not is_ok(x):
        return
    if len(x) == DIM:
        if is_ok(x):
            print(x)
        return
    x.append(0)
    for i in range(0, DIM):
        x[-1] = i
        if is_ok(x):
            generate(x, DIM)
    x.pop()

generate([], 3)


c = [1, 2]

c = c + [3]

print('c',c)