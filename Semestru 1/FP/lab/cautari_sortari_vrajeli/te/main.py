def partition(l,left,right):
    pivot = l[left]
    i = left
    j = right
    while i!=j:
        while l[j]>=pivot and i<j:
            j = j-1
        l[i] = l[j]
        while l[i]<=pivot and i<j:
            i = i+1
        l[j] = l[i]
    l[i] = pivot
    return i

def quickSort(list, left, right):
    poz = partition(list, left, right)
    print(poz)
    if left < poz - 1:
        quickSort(list, left, poz - 1)
    if poz + 1 < right:
        quickSort(list, poz + 1, right)

list = [1, 1, 5, 8, 9, 2, 3, -1, 0, 2, 10, 12, 7]
quickSort(list, 0, len(list) - 1)
print(list)

"""
def bubble_sort(list, n):
    if n == 1:
        return
    for i in range (n-1):
        if list[i] > list[i + 1]:
            list[i], list[i+1] = list[i+1], list[i]

    bubble_sort(list, n - 1)

list = [1, 1, 5, 8, 9, 2, 3, -1, 0, 2, 10, 12, 7]
bubble_sort(list, len(list) )
print(list)
"""