"""
Să se determine distanța Euclideană între două locații identificate prin perechi de numere. De ex. distanța între (1,5) și (4,1) este 5.0
"""
import math


# me theta(1)
def euclidean_distance(x, y):
    return math.sqrt((x[0] - y[0]) ** 2 + (x[1] - y[1]) ** 2)


# chat gpt
def euclidean_distance2(p1, p2):
    return math.sqrt((p2[0] - p1[0]) ** 2 + (p2[1] - p1[1]) ** 2)


def tests():
    x1, y1, r1 = [1, 5], [4, 1], 5
    x2, y2, r2 = [3, 5], [6, 1], 5
    assert (euclidean_distance(x1, y1) == r1)
    assert (euclidean_distance(x2, y2) == r2)
    assert (euclidean_distance2(x1, y1) == r1)
    assert (euclidean_distance2(x2, y2) == r2)


tests()

