import random

N = 10000
M = 10000
k = 3

with open("date.txt", "w") as f:
    f.write(f"{N} {M} {k}\n")
    for i in range(N):
        f.write(" ".join(str(random.randint(0, 255)) for _ in range(M)) + "\n")
    for i in range(k):
        f.write(" ".join(str(random.randint(-2, 2)) for _ in range(k)) + "\n")
