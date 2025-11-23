#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <iomanip>
#include <mpi.h>

using namespace std;

using Digit = unsigned char;
using Digits = vector<Digit>;

bool read_number(const string& filename, Digits& digits) {
	ifstream fin(filename);
	if (!fin) return false;

	int N;
	fin >> N;
	digits.assign(N, 0);

	for (int i = 0; i < N; i++) {
		int tmp;
		fin >> tmp;
		digits[i] = static_cast<unsigned char>(tmp);
	}
    fin.close();
	return true;
}

bool write_number(const string& filename, const Digits& digits) {
	ofstream fout(filename);
	if (!fout) return false;

	fout << digits.size() << endl;
	for (size_t i = 0; i < digits.size(); i++) {
		fout << int(digits[i]);
		if (i + 1 < digits.size()) fout << " ";
	}
	fout << endl;
    fout.close();
	return true;
}

void pad_numbers(Digits& a, Digits& b) {
	size_t N = max(a.size(), b.size());
	a.resize(N, 0);
	b.resize(N, 0);
}

void suma_varianta0() {
    Digits a, b;
    if (!read_number("numar1.txt", a) || !read_number("numar2.txt", b)) {
        cerr << "Eroare la citirea din fisier.\n";
        return;
    }
    pad_numbers(a, b);
    reverse(a.begin(), a.end()); 
    reverse(b.begin(), b.end());

    size_t N = a.size();
    Digits Rezultat(N);
    int carry_flag = 0;

    for (size_t i = 0; i < N; i++) {
        int s = a[i] + b[i] + carry_flag;
        Rezultat[i] = s % 10;
        carry_flag = s / 10;
    }

    if (carry_flag) Rezultat.push_back(carry_flag);
    reverse(Rezultat.begin(), Rezultat.end()); 

    write_number("numar3_v0.txt", Rezultat);
    cout << "Varianta0: gata!" << endl;
}


void suma_varianta1(int rank, int p) {
    int tag = 100;

    if (rank == 0) {
        Digits A, B;
        if (!read_number("Q:/info/csubb/Semestru 5/PPD/lab/lab3/lab3/lab3/numar1.txt", A) || !read_number("Q:/info/csubb/Semestru 5/PPD/lab/lab3/lab3/lab3/numar2.txt", B)) {
            cerr << "Eroare citire!\n";
            MPI_Abort(MPI_COMM_WORLD, 1);
        }

        pad_numbers(A, B);
        reverse(A.begin(), A.end());
        reverse(B.begin(), B.end());

        int N = A.size();
        int workers = p - 1;
        int Bsize = (N + workers - 1) / workers;

        for (int id = 1; id <= workers; ++id) {
            int start = (id - 1) * Bsize;
            int len = min(Bsize, N - start);

            MPI_Send(&len, 1, MPI_INT, id, tag, MPI_COMM_WORLD);
            if (len > 0) {
                MPI_Send(A.data() + start, len, MPI_UNSIGNED_CHAR, id, tag + 1, MPI_COMM_WORLD);
                MPI_Send(B.data() + start, len, MPI_UNSIGNED_CHAR, id, tag + 2, MPI_COMM_WORLD);
            }
        }

        Digits R(N + 1, 0);
        for (int id = 1; id <= workers; ++id) {
            int start = (id - 1) * Bsize;
            int len;
            MPI_Recv(&len, 1, MPI_INT, id, tag + 3, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            if (!len) continue;

            vector<unsigned char> part(len);
            MPI_Recv(part.data(), len, MPI_UNSIGNED_CHAR, id, tag + 4, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

            for (int i = 0; i < len; ++i)
                R[start + i] = part[i];
        }

        int carry_final = 0;
        MPI_Recv(&carry_final, 1, MPI_INT, p - 1, tag + 5, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        if (carry_final) R[A.size()] = carry_final;
        
        reverse(R.begin(), R.end());
        write_number("Q:/info/csubb/Semestru 5/PPD/lab/lab3/lab3/lab3/numar3_v1.txt", R);
        cout << "Varianta1: gata\n";
    }
    else {
        int len;
        MPI_Recv(&len, 1, MPI_INT, 0, tag, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        if (!len) return;

        vector<unsigned char> A(len), B(len);
        MPI_Recv(A.data(), len, MPI_UNSIGNED_CHAR, 0, tag + 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        MPI_Recv(B.data(), len, MPI_UNSIGNED_CHAR, 0, tag + 2, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

        vector<unsigned char> C(len);
        int carry = 0;
        for (int i = 0; i < len; ++i) {
            int s = A[i] + B[i] + carry;
            C[i] = s % 10;
            carry = s / 10;
        }

        int carry_in = 0;
        if (rank != 1)
            MPI_Recv(&carry_in, 1, MPI_INT, rank - 1, tag + 6, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

        if (carry_in) {
            int i = 0, c = carry_in;
            while (i < len && c) {
                int s = C[i] + c;
                C[i] = s % 10;
                c = s / 10;
                ++i;
            }
            if (c) carry = 1;
        }

        if (rank != p - 1)
            MPI_Send(&carry, 1, MPI_INT, rank + 1, tag + 6, MPI_COMM_WORLD);
        else
            MPI_Send(&carry, 1, MPI_INT, 0, tag + 5, MPI_COMM_WORLD);

        MPI_Send(&len, 1, MPI_INT, 0, tag + 3, MPI_COMM_WORLD);
        MPI_Send(C.data(), len, MPI_UNSIGNED_CHAR, 0, tag + 4, MPI_COMM_WORLD);
    }
}

void suma_varianta2(int rank, int p) {
    int chunk = 0;
    Digits A, B;

    if (rank == 0) {
        if (!read_number("Q:/info/csubb/Semestru 5/PPD/lab/lab3/lab3/lab3/numar1.txt", A) || !read_number("Q:/info/csubb/Semestru 5/PPD/lab/lab3/lab3/lab3/numar2.txt", B)) {
            cerr << "Eroare citire!\n";
            MPI_Abort(MPI_COMM_WORLD, 1);
        }
        pad_numbers(A, B);
        reverse(A.begin(), A.end());
        reverse(B.begin(), B.end());

        int N = A.size();
        chunk = (N + p - 1) / p;
        A.resize(chunk * p, 0);
        B.resize(chunk * p, 0);
    }

    MPI_Bcast(&chunk, 1, MPI_INT, 0, MPI_COMM_WORLD);

    vector<unsigned char> partA(chunk), partB(chunk);
    MPI_Scatter(A.data(), chunk, MPI_UNSIGNED_CHAR, partA.data(), chunk, MPI_UNSIGNED_CHAR, 0, MPI_COMM_WORLD);
    MPI_Scatter(B.data(), chunk, MPI_UNSIGNED_CHAR, partB.data(), chunk, MPI_UNSIGNED_CHAR, 0, MPI_COMM_WORLD);

    vector<unsigned char> C(chunk);
    int carry = 0;

    for (int i = 0; i < chunk; ++i) {
        int s = partA[i] + partB[i] + carry;
        C[i] = s % 10;
        carry = s / 10;
    }

    int carry_in = 0;
    if (rank != 0)
        MPI_Recv(&carry_in, 1, MPI_INT, rank - 1, 300, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

    if (carry_in) {
        int i = 0, c = carry_in;
        while (i < chunk && c) {
            int s = C[i] + c;
            C[i] = s % 10;
            c = s / 10;
            ++i;
        }
        if (c) carry = 1;
    }

    if (rank != p - 1)
        MPI_Send(&carry, 1, MPI_INT, rank + 1, 300, MPI_COMM_WORLD);
    else
        MPI_Send(&carry, 1, MPI_INT, 0, 301, MPI_COMM_WORLD);

    vector<unsigned char> gathered(chunk * p);
    MPI_Gather(C.data(), chunk, MPI_UNSIGNED_CHAR, gathered.data(), chunk, MPI_UNSIGNED_CHAR, 0, MPI_COMM_WORLD);

    if (rank == 0) {
        int final_carry;
        MPI_Recv(&final_carry, 1, MPI_INT, p - 1, 301, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

        Digits R(gathered.begin(), gathered.end());
        if (final_carry) R.push_back(1);
        
        reverse(R.begin(), R.end());
        write_number("Q:/info/csubb/Semestru 5/PPD/lab/lab3/lab3/lab3/numar3_v2.txt", R);
        cout << "Varianta2: gata!\n";
    }
}

void suma_varianta3(int rank, int p) {
    int tag = 400;

    if (rank == 0) {
        Digits A, B;
        if (!read_number("Q:/info/csubb/Semestru 5/PPD/lab/lab3/lab3/lab3/numar1.txt", A) || !read_number("Q:/info/csubb/Semestru 5/PPD/lab/lab3/lab3/lab3/numar2.txt", B)) {
            cerr << "Eroare citire!\n";
            MPI_Abort(MPI_COMM_WORLD, 1);
        }

        pad_numbers(A, B);
        reverse(A.begin(), A.end());
        reverse(B.begin(), B.end());
        int N = A.size();
        int workers = p - 1;
        int Bsize = (N + workers - 1) / workers;

        vector<MPI_Request> reqs;

        for (int id = 1; id <= workers; ++id) {
            int start = (id - 1) * Bsize;
            int len = min(Bsize, N - start);

            MPI_Request r;
            MPI_Isend(&len, 1, MPI_INT, id, tag, MPI_COMM_WORLD, &r);
            reqs.push_back(r);

            MPI_Isend(A.data() + start, len, MPI_UNSIGNED_CHAR, id, tag + 1, MPI_COMM_WORLD, &r);
            reqs.push_back(r);

            MPI_Isend(B.data() + start, len, MPI_UNSIGNED_CHAR, id, tag + 2, MPI_COMM_WORLD, &r);
            reqs.push_back(r);
        }
        MPI_Waitall(reqs.size(), reqs.data(), MPI_STATUSES_IGNORE);

        Digits R(N + 1, 0);

        for (int id = 1; id <= workers; ++id) {
            int start = (id - 1) * Bsize;
            int len;
            MPI_Recv(&len, 1, MPI_INT, id, tag + 3, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

            vector<unsigned char> part(len);
            MPI_Recv(part.data(), len, MPI_UNSIGNED_CHAR, id, tag + 4, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

            for (int i = 0; i < len; ++i)
                R[start + i] = part[i];
        }

        int carry_final;
        MPI_Recv(&carry_final, 1, MPI_INT, p - 1, tag + 5, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        if (carry_final) R[A.size()] = carry_final;

        reverse(R.begin(), R.end());
        write_number("Q:/info/csubb/Semestru 5/PPD/lab/lab3/lab3/lab3/numar3_v3.txt", R);
        cout << "Varianta3: gata!\n";
    }
    else {
        int len;
        MPI_Request reqLen;
        MPI_Irecv(&len, 1, MPI_INT, 0, tag, MPI_COMM_WORLD, &reqLen);
        MPI_Wait(&reqLen, MPI_STATUS_IGNORE);

        if (!len) return;

        vector<unsigned char> A(len), B(len);
        MPI_Request r2[2];
        MPI_Irecv(A.data(), len, MPI_UNSIGNED_CHAR, 0, tag + 1, MPI_COMM_WORLD, &r2[0]);
        MPI_Irecv(B.data(), len, MPI_UNSIGNED_CHAR, 0, tag + 2, MPI_COMM_WORLD, &r2[1]);
        MPI_Waitall(2, r2, MPI_STATUSES_IGNORE);

        vector<unsigned char> C(len);
        int carry = 0;

        for (int i = 0; i < len; ++i) {
            int s = A[i] + B[i] + carry;
            C[i] = s % 10;
            carry = s / 10;
        }

        int carry_in = 0;
        MPI_Request reqCarry = MPI_REQUEST_NULL;
        if (rank != 1) MPI_Irecv(&carry_in, 1, MPI_INT, rank - 1, tag + 6, MPI_COMM_WORLD, &reqCarry);
        if (rank != 1) MPI_Wait(&reqCarry, MPI_STATUS_IGNORE);

        if (carry_in) {
            int i = 0, c = carry_in;
            while (i < len && c) {
                int s = C[i] + c;
                C[i] = s % 10;
                c = s / 10;
                ++i;
            }
            if (c) carry = 1;
        }

        MPI_Request sendCarry;
        if (rank != p - 1)
            MPI_Isend(&carry, 1, MPI_INT, rank + 1, tag + 6, MPI_COMM_WORLD, &sendCarry);
        else
            MPI_Isend(&carry, 1, MPI_INT, 0, tag + 5, MPI_COMM_WORLD, &sendCarry);

        MPI_Send(&len, 1, MPI_INT, 0, tag + 3, MPI_COMM_WORLD);
        MPI_Send(C.data(), len, MPI_UNSIGNED_CHAR, 0, tag + 4, MPI_COMM_WORLD);
        MPI_Wait(&sendCarry, MPI_STATUS_IGNORE);
    }
}

int main(int argc, char** argv) {
    MPI_Init(&argc, &argv);

    int rank, p;
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &p);

    if (rank == 0) {
        cout << "numar de procese active: " << p << "\n";
    }

    MPI_Barrier(MPI_COMM_WORLD);
    double start_time = MPI_Wtime();

     if (rank == 0) suma_varianta0();
    //suma_varianta1(rank, p);
     //suma_varianta2(rank, p);
    //suma_varianta3(rank, p);

    MPI_Barrier(MPI_COMM_WORLD);
    double end_time = MPI_Wtime();

    if (rank == 0) {
        cout << fixed << setprecision(6);
        cout << "Timp: " << (end_time - start_time) << " secunde\n";
    }

    MPI_Finalize();
    return 0;
}