#include <iostream>
#include <fstream>
#include <thread>
#include <chrono>
#include <algorithm>
#include <cstring>
#include "Barrier.h"

using namespace std;

int** f = nullptr;
int** c = nullptr;
int n = 0, m = 0, k = 0, p = 0;

void deallocateMemory(int**& arr, int rows) {
    for (int i = 0; i < rows; ++i)
        delete[] arr[i];
    delete[] arr;
    arr = nullptr;
}

void aplicaConvolutie(int i, int j, int end, int* prev_line, int* curr_line, int* next_line) {
    int sum = 0;
    int diff = (k - 1) / 2;

    for (int x = 0; x < k; ++x) {
        for (int y = 0; y < k; ++y) {
            int ii = min(max(i - diff + x, 0), n - 1);
            int jj = min(max(j - diff + y, 0), m - 1);

            int val;
            if (ii < i)
                val = prev_line[jj];
            else if (ii == i)
                val = curr_line[jj];
            else if (ii >= end)
                val = next_line[jj];
            else
                val = f[ii][jj];

            sum += val * c[x][y];
        }
    }

    f[i][j] = sum;
}

void runSequential() {
    int* prev_line = new int[m];
    int* curr_line = new int[m];
    int* next_line = new int[m];

    for (int j = 0; j < m; ++j) {
        prev_line[j] = curr_line[j] = f[0][j];
        next_line[j] = f[n - 1][j];
    }

    for (int i = 0; i < n; ++i) {
        if (i != 0) {
            for (int j = 0; j < m; ++j) {
                prev_line[j] = curr_line[j];
                curr_line[j] = f[i][j];
            }
        }

        for (int j = 0; j < m; ++j)
            aplicaConvolutie(i, j, n, prev_line, curr_line, next_line);
    }

    delete[] prev_line;
    delete[] curr_line;
    delete[] next_line;
}

void processRows(int start, int end, SimpleBarrier& barrier) {
    int* prev_line = new int[m];
    int* curr_line = new int[m];
    int* next_line = new int[m];

    int prev_index = max(start - 1, 0);
    int next_index = min(end, n - 1);

    for (int j = 0; j < m; ++j) {
        prev_line[j] = curr_line[j] = f[prev_index][j];
        next_line[j] = f[next_index][j];
    }

    barrier.arrive_and_wait();

    for (int i = start; i < end; ++i) {
        if (i != prev_index) {
            for (int j = 0; j < m; ++j) {
                prev_line[j] = curr_line[j];
                curr_line[j] = f[i][j];
            }
        }

        for (int j = 0; j < m; ++j)
            aplicaConvolutie(i, j, end, prev_line, curr_line, next_line);
    }

    delete[] prev_line;
    delete[] curr_line;
    delete[] next_line;
}

void runParallelRows() {
    thread* threads = new thread[p];
    int rows_per_thread = n / p;
    int extra_rows = n % p;
    int start = 0;

    SimpleBarrier barrier(p);

    for (int i = 0; i < p; ++i) {
        int num_rows = rows_per_thread + (extra_rows > 0 ? 1 : 0);
        if (extra_rows > 0) --extra_rows;

        int end = start + num_rows;
        threads[i] = thread(processRows, start, end, ref(barrier));
        start = end;
    }

    for (int i = 0; i < p; ++i)
        threads[i].join();

    delete[] threads;
}

void read_input_file(const string& filename) {
    ifstream fin(filename);
    if (!fin) {
        cerr << "Failed to open " << filename << endl;
        exit(1);
    }

    fin >> n >> m;
    f = new int* [n];
    for (int i = 0; i < n; ++i)
        f[i] = new int[m];

    for (int i = 0; i < n; ++i)
        for (int j = 0; j < m; ++j)
            fin >> f[i][j];

    fin >> k >> k;
    c = new int* [k];
    for (int i = 0; i < k; ++i)
        c[i] = new int[k];

    for (int i = 0; i < k; ++i)
        for (int j = 0; j < k; ++j)
            fin >> c[i][j];
}

void write_to_file(const string& filename) {
    ofstream fout(filename);
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < m; ++j)
            fout << f[i][j] << " ";
        fout << '\n';
    }
}

int main(int argc, char* argv[]) {
    //std::cout << "|No. |N |M |k |p |Time |\n|--|--|--|--|--|\n";
    //std::vector<int> kernelStatic(spec.k * spec.k);

        //ManageMatrix::generator(matrixStatic, spec.N * spec.M, INT_MAX);
        //ManageMatrix::generator(kernelStatic, spec.k * spec.k, 10);

    //for (int i = 0; i < 10; i++) {
        //sumSeq += SequentialConvolutionDynamic().Convolute(matrixDynamic, kernelDynamic, spec.N, spec.M, spec.k);
        //sumSeq += SequentialConvolutionStatic().Convolute(matrixStatic, kernelStatic, spec.N, spec.M, spec.k);
    //}
    //for (const auto& p : spec.p) {
        //for (int i = 0; i < 10; i++) {
            //sumHorStatic += ParalelConvolutionStatic().ConvoluteHorizontal(matrixStatic, kernelStatic, spec.N, spec.M, spec.k, p);
            // 

       /* }
        std::cout << "|" << 1 << " |" << spec.N << " |" << spec.M << " |" << spec.k << " |" << p << " |" << sumVerDynamic / 10 << " |\n";
    }*/
    return 0;
}
