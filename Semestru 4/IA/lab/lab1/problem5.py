"""
Pentru un șir cu n elemente care conține valori din mulțimea {1, 2, ..., n - 1} astfel încât o singură valoare se repetă de două ori, să se identifice acea valoare care se repetă.
De ex. în șirul [1,2,3,4,2] valoarea 2 apare de două ori.
"""


# me theta(n) -> n e numarul de numere
def find_repeating_number(number_list):
    return sum(number_list) - (len(number_list) * (len(number_list) - 1) / 2)


# chat gpt
def find_duplicate(nums):
    seen = set()
    for num in nums:
        if num in seen:
            return num
        seen.add(num)
    return None

def tests():
    l1, r1 = [1, 2, 3, 4, 5, 6, 2], 2
    l2, r2 = [1, 2, 4, 3, 3], 3
    assert (find_repeating_number(l1) == r1)
    assert (find_repeating_number(l2) == r2)
    assert (find_duplicate(l1) == r1)
    assert (find_duplicate(l2) == r2)

tests()

