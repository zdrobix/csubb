import random
import sys

# Parametri: N, M, k
N = int(sys.argv[1]) if len(sys.argv) > 1 else 10000
M = int(sys.argv[2]) if len(sys.argv) > 2 else 10000
k = int(sys.argv[3]) if len(sys.argv) > 3 else 3

with open("date.txt", "w") as f:
    f.write(f"{N} {M}\n")
    for i in range(N):
        f.write(" ".join(str(random.randint(0, 255)) for _ in range(M)) + "\n")
    f.write(f"{k} {k}\n")
    for i in range(k):
        f.write(" ".join(str(random.randint(-2, 2)) for _ in range(k)) + "\n")

print(f"Generat fisier date.txt cu matrice {N}x{M} si kernel {k}x{k}")
