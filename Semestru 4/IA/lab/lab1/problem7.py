"""
Să se determine al k-lea cel mai mare element al unui șir de numere cu n elemente (k < n). De ex. al 2-lea cel mai mare element din șirul [7,4,6,3,9,1] este 7.
"""
import heapq


# me O(n log n)
def k_th_max(number_list, k):
    return sorted(number_list)[-k]



# chat gpt
def kth_largest(nums, k):
    return heapq.nlargest(k, nums)[-1]


def tests():
    l1, k1, r1 = [7,4,6,3,9,1], 2, 7
    l2, k2, r2 = [1, 2, 9, 10, 2], 3, 2
    assert(k_th_max(l1, k1) == r1)
    assert(k_th_max(l2, k2) == r2)
    assert (kth_largest(l1, k1) == r1)
    assert (kth_largest(l2, k2) == r2)

tests()

