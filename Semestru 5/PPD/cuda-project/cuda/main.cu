#include <iostream>
#include <fstream>
#include <string>
#include <chrono>
#include <cuda_runtime.h>

using namespace std;

int n, m, k;
int **f;
int **c;

// Check CUDA errors
#define CUDA_CHECK(call)                                                            \
    do                                                                              \
    {                                                                               \
        cudaError_t err = call;                                                     \
        if (err != cudaSuccess)                                                     \
        {                                                                           \
            cerr << "CUDA error in " << __FILE__ << " at line " << __LINE__ << ": " \
                 << cudaGetErrorString(err) << endl;                                \
            exit(EXIT_FAILURE);                                                     \
        }                                                                           \
    } while (0)

void read_input_file(const string &filename)
{
    ifstream fin(filename);
    fin >> n >> m;
    f = new int *[n];
    for (int i = 0; i < n; ++i)
        f[i] = new int[m];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < m; j++)
            fin >> f[i][j];

    fin >> k >> k;
    c = new int *[k];
    for (int i = 0; i < k; ++i)
        c[i] = new int[k];
    for (int i = 0; i < k; i++)
        for (int j = 0; j < k; j++)
            fin >> c[i][j];
}

void write_to_file(const string &filename)
{
    ofstream fout(filename);
    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < m; j++)
            fout << f[i][j] << " ";
        fout << '\n';
    }
}

void deallocateMemory(int **&arr, int rows)
{
    for (int i = 0; i < rows; i++)
        delete[] arr[i];
    delete[] arr;
    arr = nullptr;
}

// Kernel CUDA care procesează întreaga matrice în paralel
// Fiecare thread procesează o celulă (i,j)
// Folosește matrice auxiliară pentru a evita race conditions
__global__ void convolution_kernel(int *d_f, int *d_result, int *d_c, int n, int m, int k)
{
    int i = blockIdx.y * blockDim.y + threadIdx.y; // Linia
    int j = blockIdx.x * blockDim.x + threadIdx.x; // Coloana

    if (i >= n || j >= m)
        return;

    int s = 0;
    int diff = (k - 1) / 2;

    // Aplică convoluția pentru celula (i, j)
    for (int x = 0; x < k; x++)
    {
        for (int y = 0; y < k; y++)
        {
            int ii = i - diff + x;
            int jj = j - diff + y;

            // Boundary conditions - clamp la margini
            if (ii < 0)
                ii = 0;
            if (ii >= n)
                ii = n - 1;
            if (jj < 0)
                jj = 0;
            if (jj >= m)
                jj = m - 1;

            s += d_f[ii * m + jj] * d_c[x * k + y];
        }
    }

    // Scrie în matrice auxiliară pentru a evita race conditions
    d_result[i * m + j] = s;
}

void run_cuda()
{
    // Alocare memorie pe device
    int *d_f;      // Matricea originală
    int *d_result; // Matrice temporară pentru rezultat
    int *d_c;      // Kernel de convoluție

    size_t f_size = n * m * sizeof(int);
    size_t c_size = k * k * sizeof(int);

    CUDA_CHECK(cudaMalloc(&d_f, f_size));
    CUDA_CHECK(cudaMalloc(&d_result, f_size));
    CUDA_CHECK(cudaMalloc(&d_c, c_size));

    // Flatten și copiere matrice f pe device
    int *f_flat = new int[n * m];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < m; j++)
            f_flat[i * m + j] = f[i][j];

    CUDA_CHECK(cudaMemcpy(d_f, f_flat, f_size, cudaMemcpyHostToDevice));

    // Flatten și copiere kernel c pe device
    int *c_flat = new int[k * k];
    for (int i = 0; i < k; i++)
        for (int j = 0; j < k; j++)
            c_flat[i * k + j] = c[i][j];

    CUDA_CHECK(cudaMemcpy(d_c, c_flat, c_size, cudaMemcpyHostToDevice));

    // Configurare grid și block pentru procesare 2D paralelă
    dim3 threadsPerBlock(16, 16); // 16x16 = 256 threads per block
    dim3 blocksPerGrid(
        (m + threadsPerBlock.x - 1) / threadsPerBlock.x,
        (n + threadsPerBlock.y - 1) / threadsPerBlock.y);

    // Lansare kernel - procesează întreaga matrice în paralel
    convolution_kernel<<<blocksPerGrid, threadsPerBlock>>>(
        d_f, d_result, d_c, n, m, k);

    CUDA_CHECK(cudaGetLastError());
    CUDA_CHECK(cudaDeviceSynchronize());

    // Copiere rezultat înapoi în d_f (respectăm constrângerea: matricea inițială conține rezultatul)
    CUDA_CHECK(cudaMemcpy(d_f, d_result, f_size, cudaMemcpyDeviceToDevice));

    // Copiere rezultat final pe host
    CUDA_CHECK(cudaMemcpy(f_flat, d_f, f_size, cudaMemcpyDeviceToHost));

    // Unflatten rezultat înapoi în matrice 2D
    for (int i = 0; i < n; i++)
        for (int j = 0; j < m; j++)
            f[i][j] = f_flat[i * m + j];

    // Cleanup
    delete[] f_flat;
    delete[] c_flat;

    CUDA_CHECK(cudaFree(d_f));
    CUDA_CHECK(cudaFree(d_result));
    CUDA_CHECK(cudaFree(d_c));
}

int main(int argc, char *argv[])
{
    string filename = "date.txt";
    read_input_file(filename);

    auto start = chrono::high_resolution_clock::now();
    run_cuda();
    auto end = chrono::high_resolution_clock::now();

    cout << "Timp CUDA" << endl;
    cout << chrono::duration_cast<chrono::nanoseconds>(end - start).count() << endl;

    write_to_file("output.txt");

    deallocateMemory(f, n);
    deallocateMemory(c, k);

    return 0;
}
