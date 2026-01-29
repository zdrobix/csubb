#include <iostream>
#include <fstream>
#include <barrier>
#include <string>
#include <chrono>
#include <thread>
using namespace std;

int p;
int n, m, k;
int **f;
int **c;

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

void aplica_convolutie(int i, int j, int end, int *prev_line, int *curr_line, int *next_line)
{
    int s = 0;
    int diff = (k - 1) / 2;
    for (int x = 0; x < k; x++)
    {
        for (int y = 0; y < k; y++)
        {
            int ii = i - diff + x;
            int jj = j - diff + y;
            if (ii < 0)
                ii = 0;
            if (ii >= n)
                ii = n - 1;
            if (jj < 0)
                jj = 0;
            if (jj >= m)
                jj = m - 1;

            int elem_cov;
            if (ii < i)
                elem_cov = prev_line[jj];
            else if (ii == i)
                elem_cov = curr_line[jj];
            else if (ii >= end)
                elem_cov = next_line[jj];
            else
                elem_cov = f[ii][jj];

            s += elem_cov * c[x][y];
        }
    }
    f[i][j] = s;
}

void run_sequential()
{
    int *previous_line = new int[m];
    int *curr_line = new int[m];
    int *next_line = new int[m];

    for (int i = 0; i < m; i++)
    {
        previous_line[i] = f[0][i];
        curr_line[i] = f[0][i];
        next_line[i] = f[n - 1][i];
    }

    for (int i = 0; i < n; i++)
    {
        if (i != 0)
        {
            for (int j = 0; j < m; j++)
            {
                previous_line[j] = curr_line[j];
                curr_line[j] = f[i][j];
            }
        }
        for (int j = 0; j < m; j++)
            aplica_convolutie(i, j, n, previous_line, curr_line, next_line);
    }

    delete[] previous_line;
    delete[] curr_line;
    delete[] next_line;
}

void rows_thread(int start, int end, std::barrier<> &my_barrier)
{
    int *previous_line = new int[m];
    int *curr_line = new int[m];
    int *next_line = new int[m];

    int previous_line_number = max(start - 1, 0);
    int next_line_number = min(end, n - 1);

    for (int i = 0; i < m; i++)
    {
        previous_line[i] = f[previous_line_number][i];
        curr_line[i] = f[previous_line_number][i];
        next_line[i] = f[next_line_number][i];
    }

    my_barrier.arrive_and_wait();

    for (int i = start; i < end; i++)
    {
        if (i != previous_line_number)
        {
            for (int j = 0; j < m; j++)
            {
                previous_line[j] = curr_line[j];
                curr_line[j] = f[i][j];
            }
        }
        for (int j = 0; j < m; j++)
            aplica_convolutie(i, j, end, previous_line, curr_line, next_line);
    }

    delete[] previous_line;
    delete[] curr_line;
    delete[] next_line;
}

void run_with_rows()
{
    thread *threads = new thread[p];
    int cat = n / p;
    int rest = n % p;
    int start = 0;
    barrier my_barrier{p};

    for (int i = 0; i < p; i++)
    {
        int currentNoRows = cat + (rest > 0 ? 1 : 0);
        if (rest > 0)
            rest--;
        int end = start + currentNoRows;
        threads[i] = thread(rows_thread, start, end, ref(my_barrier));
        start = end;
    }

    for (int i = 0; i < p; i++)
        threads[i].join();

    delete[] threads;
}

int main(int argc, char *argv[])
{
    string filename = "date.txt";
    read_input_file(filename);

    if (argc < 2)
    {
        cout << "Usage: " << argv[0] << " <num_threads>" << endl;
        return 1;
    }

    p = stoi(argv[1]);

    auto start_seq = chrono::high_resolution_clock::now();
    run_with_rows();
    // run_sequential();
    auto end_seq = chrono::high_resolution_clock::now();
    cout << "Timp secvential" << endl;
    cout << chrono::duration_cast<chrono::nanoseconds>(end_seq - start_seq).count();
    write_to_file("output.txt");

    // Reset matrice pentru paralel
    deallocateMemory(f, n);
    // read_input_file(filename);

    // p = 8; // numar de thread-uri
    // auto start_par = chrono::high_resolution_clock::now();
    // run_with_rows();
    // auto end_par = chrono::high_resolution_clock::now();
    // cout << "Timp paralel (ms): "
    //      << chrono::duration_cast<chrono::milliseconds>(end_par - start_par).count() << "\n";
    // write_to_file("output_par.txt");

    // deallocateMemory(f, n);
    deallocateMemory(c, k);
}
