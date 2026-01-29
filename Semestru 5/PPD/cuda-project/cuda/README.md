# CUDA Image Convolution Filter - Laborator 2

Implementare CUDA pentru aplicarea unui filtru de convoluție pe o imagine (matrice).

## Cerință

- **Filtrare in-place**: Matricea inițială conține imaginea filtrată
- **Complexitate spațiu O(n)**: NU se alocă matrice temporare, doar vectori
- **Kernel 3x3** de convoluție
- **Distribuție pe linii** similar cu implementarea C++ cu threads

## Structura Proiect

```
cuda/
├── main.cu           - Implementare CUDA principală
├── generare.py       - Generare date de test
├── scriptCUDA.ps1    - Script de testare individual
├── run_all.ps1       - Script complet pentru toate testele
└── README.md         - Acest fișier
```

## Compilare

Necesită NVIDIA CUDA Toolkit instalat și `nvcc` în PATH.

```powershell
nvcc -o main.exe main.cu
```

## Rulare

### Generare date test
```powershell
python generare.py <N> <M> <k>
# Exemplu: python generare.py 1000 1000 3
```

### Rulare program
```powershell
.\main.exe
```

### Testare completă (conform cerințelor)
```powershell
.\run_all.ps1
```

Acest script va rula automat:
- N=M=10, k=3, 10 rulări
- N=M=1000, k=3, 10 rulări  
- N=M=10000, k=3, 10 rulări

Rezultatele se salvează în `outCUDA.csv`.

## Format Date

### date.txt (intrare)
```
N M
<matrice NxM cu valori întregi>
k k
<kernel kxk cu valori întregi>
```

### output.txt (ieșire)
```
<matrice NxM filtrată>
```

## Implementare CUDA

### Arhitectură
1. **Kernel CUDA** (`convolution_kernel`):
   - Fiecare thread procesează o celulă din linia curentă
   - Folosește 3 vectori temporari: previous_line, current_line, next_line
   - Aplică convoluția 3x3 cu boundary conditions

2. **Funcție host** (`run_cuda`):
   - Alocă memorie pe GPU pentru matrice și kernel
   - Procesează linia cu linie (similar implementării C++ secvențiale)
   - Copiază rezultatul înapoi pe host

### Complexitate Spațiu
- **Matrice originală**: N×M (modificată in-place)
- **Vectori temporari**: 3×M = O(M) = O(n)
- **Kernel**: k×k = constant (3×3)
- **Total**: O(n) ✓

## Comparație cu C++

| Aspect | C++ Threads | CUDA |
|--------|-------------|------|
| Paralelism | Multi-threading CPU | Masiv paralel GPU |
| Granularitate | Linii întregi | Celule individuale |
| Sincronizare | Barrier | cudaDeviceSynchronize |
| Memorie | RAM | VRAM + transferuri |

## Rezultate Așteptate

Pentru dimensiuni mari (N=10000), CUDA ar trebui să fie semnificativ mai rapid decât implementarea CPU cu threads, datorită paralelismului masiv la nivel de celulă.

## Note

- Implementarea respectă cerințele: **NU se alocă matrice temporare**
- Folosește doar vectori temporari (O(n) space complexity)
- Filtrarea se face **in-place** în matricea originală
- Kernel 3x3 hardcodat conform cerințelor
