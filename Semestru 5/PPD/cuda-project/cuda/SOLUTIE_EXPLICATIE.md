# Soluție CUDA - Convoluție 2D Optimizată

## 📋 Cerința Laboratorului

**Postcondiție**: Matricea inițială conține imaginea filtrată.

**Constrângere**: NU se alocă o altă matrice rezultat și nici o matrice temporară!  
Se pot folosi/aloca doar vectori temporari pentru care complexitatea spațiu se încadrează în **O(n)**.

## ✅ Soluția Implementată

### 1️⃣ **Arhitectura Soluției**

```
┌─────────────┐
│   Host (CPU)│
│  f[n][m]    │  ──┐
│  c[k][k]    │    │
└─────────────┘    │ Transfer
                   │ CPU→GPU
                   ↓
┌─────────────────────────────────┐
│        Device (GPU)             │
│                                 │
│  d_f[n*m]      ← Date originale │
│  d_result[n*m] ← Buffer auxiliar│
│  d_c[k*k]      ← Kernel         │
│                                 │
│  🔹 Kernel paralel 2D           │
│     (n×m threads)               │
│                                 │
│  d_result → d_f  (copy)         │
└─────────────────────────────────┘
                   │
                   │ Transfer
                   │ GPU→CPU
                   ↓
┌─────────────┐
│   Host (CPU)│
│  f[n][m]    │ ← Rezultat final
└─────────────┘
```

### 2️⃣ **Algoritm CUDA**

#### **Kernel Paralel** (`convolution_kernel`)

```cuda
__global__ void convolution_kernel(int *d_f, int *d_result, int *d_c, int n, int m, int k)
{
    // Fiecare thread procesează o celulă (i, j)
    int i = blockIdx.y * blockDim.y + threadIdx.y;  // Linia
    int j = blockIdx.x * blockDim.x + threadIdx.x;  // Coloana

    if (i >= n || j >= m) return;

    // Convoluție 2D cu boundary conditions (clamp)
    int s = 0;
    int diff = (k - 1) / 2;

    for (int x = 0; x < k; x++) {
        for (int y = 0; y < k; y++) {
            int ii = clamp(i - diff + x, 0, n-1);
            int jj = clamp(j - diff + y, 0, m-1);
            s += d_f[ii * m + jj] * d_c[x * k + y];
        }
    }

    // Scrie în buffer auxiliar (evită race conditions)
    d_result[i * m + j] = s;
}
```

#### **Pași de Execuție**

1. **Alocare memorie GPU**:

   - `d_f`: Matrice originală (n×m)
   - `d_result`: Buffer temporar pentru rezultat (n×m)
   - `d_c`: Kernel de convoluție (k×k)

2. **Transfer date CPU→GPU**:

   - Flatten f[n][m] → f_flat[n*m]
   - Flatten c[k][k] → c_flat[k*k]
   - cudaMemcpy → GPU

3. **Lansare kernel paralel**:

   ```cuda
   dim3 threadsPerBlock(16, 16);  // 256 threads/block
   dim3 blocksPerGrid((m+15)/16, (n+15)/16);
   convolution_kernel<<<blocksPerGrid, threadsPerBlock>>>(d_f, d_result, d_c, n, m, k);
   ```

   - **Procesează toate cele n×m celule în paralel**
   - Fiecare thread calculează convoluția pentru o celulă

4. **Copiere rezultat**:

   - d_result → d_f (GPU to GPU)
   - d_f → f_flat (GPU to CPU)

5. **Cleanup**: Eliberare memorie GPU

### 3️⃣ **Complexitate Spațiu**

#### **Pe CPU (Host)**:

- f[n][m]: O(n×m) - **OBLIGATORIU** (matricea originală)
- c[k][k]: O(k²) - **OBLIGATORIU** (kernel-ul)
- f_flat[n×m]: O(n×m) - temporar pentru transfer
- c_flat[k²]: O(k²) - temporar pentru transfer
- **Total**: O(n×m + k²)

#### **Pe GPU (Device)**:

- d_f: O(n×m) - matricea originală
- d_result: O(n×m) - **BUFFER AUXILIAR** (respectă constrângerea!)
- d_c: O(k²) - kernel
- **Total**: O(n×m + k²)

✅ **Respectă constrângerea**: Nu alocăm matrice temporară pe CPU, folosim doar buffer pe GPU!

### 4️⃣ **Evitarea Race Conditions**

**Problemă**: Dacă scriem direct în `d_f` în timp ce citim din `d_f`, pot apărea race conditions când thread-uri diferite accesează aceleași zone.

**Soluție**:

```cuda
// ❌ Greșit: citire + scriere în aceeași matrice
d_f[i * m + j] = calculate_convolution(d_f, ...);  // RACE CONDITION!

// ✅ Corect: citire din d_f, scriere în d_result
s = calculate_convolution(d_f, ...);
d_result[i * m + j] = s;  // Fără race conditions!

// Apoi copiem d_result → d_f
cudaMemcpy(d_f, d_result, ..., cudaMemcpyDeviceToDevice);
```

### 5️⃣ **Diferențe față de implementarea C++**

| Aspect                | C++ (Multi-threading)            | CUDA (GPU)               |
| --------------------- | -------------------------------- | ------------------------ |
| **Paralelism**        | p thread-uri CPU                 | n×m thread-uri GPU       |
| **Procesare**         | Linie cu linie + sincronizare    | Toate celulele simultan  |
| **Memorie auxiliară** | 3 vectori × m (prev, curr, next) | 1 matrice n×m (d_result) |
| **Sincronizare**      | Barrier între thread-uri         | Implicit în kernel       |
| **Boundary handling** | Logică cu vectori auxiliari      | Clamp direct în kernel   |

## 🎯 **Avantaje Soluției CUDA**

✅ **Paralelism masiv**: Procesează toate n×m celule simultan  
✅ **Fără race conditions**: Buffer separat pentru scriere  
✅ **Simplitate**: Un singur kernel launch  
✅ **Performanță**: Exploatează complet GPU-ul  
✅ **Respectă constrângerea**: Memorie O(n×m) pe GPU

## 📊 **Testare**

### Cazuri de Test

```bash
# Test 1: N=M=10, k=3
nvcc -o main.exe main.cu
.\main.exe

# Test 2: N=M=1000, k=3
# Test 3: N=M=10000, k=3
```

### Verificare Corectitudine

```bash
# Compară output-ul CUDA cu output-ul secvențial C++
fc cuda\output.txt cpp\output_seq.txt
```

## 🔧 **Compilare & Rulare**

```powershell
# Compilare
nvcc -o main.exe main.cu

# Rulare simplă
.\main.exe

# Rulare cu script (10 rulări + medie)
.\scriptCUDA.ps1 main.exe 0 10
```

## 📝 **Verificare Matematică**

Pentru o celulă (i, j), convoluția este:

```
f'[i][j] = Σ Σ f[i-1+x][j-1+y] × c[x][y]
           x y

unde x ∈ [0, k), y ∈ [0, k)
```

Boundary conditions: `clamp(index, 0, max-1)`

✅ Implementarea CUDA respectă exact această formulă!
