"""
Pentru un șir cu n numere întregi care conține și duplicate, să se determine elementul majoritar (care apare de mai mult de n / 2 ori).
De ex. 2 este elementul majoritar în șirul [2,8,7,2,2,5,2,3,1,2,2].
"""

# me theta(n)
def get_repeating_max_number(number_list):
    count_list = [0] * 100
    for number in number_list:
        count_list[number] += 1
    new_count_list = []
    count = 0
    for number in count_list:
        if number > len(number_list) // 2:
            new_count_list += [(count, number)]
        count += 1
    return new_count_list[-1][0]


# chat gpt
def majority_element(nums):
    candidate, count = None, 0
    for num in nums:
        if count == 0:
            candidate = num
        count += 1 if num == candidate else -1

    if nums.count(candidate) > len(nums) // 2:
        return candidate
    return None

def tests():
    l1, r1 = [1, 1, 1, 1, 1, 1, 1, 99, 99], 1
    l2, r2 = [10, 10, 10, 99, 99], 10
    l3, r3 = [5, 5, 5, 5, 5, 6, 7, 8, 9], 5
    assert(get_repeating_max_number(l1) == r1)
    assert(get_repeating_max_number(l2) == r2)
    assert(get_repeating_max_number(l3) == r3)
    assert(majority_element(l1) == r1)
    assert(majority_element(l2) == r2)
    assert(majority_element(l3) == r3)

tests()

