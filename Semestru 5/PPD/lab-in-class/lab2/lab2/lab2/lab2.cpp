#include <iostream>
#include <chrono>
#include <thread>
#include <vector>

#define NUM_THREADS 5
#define MAX_SIZE 1000000
#define MAX_VALUE 50000

void generator(std::vector<int>& A, int n, int maxx) {
    for (int i = 0; i < n; i ++)
        A[i] = rand() % maxx;
}

long long sum(const std::vector<int>& A, const std::vector<int>& B, std::vector<int>& C, int n) {
	auto t0 = std::chrono::high_resolution_clock::now();
	for (int i = 0; i < n; i++)
		C[i] = A[i] + B[i];
	auto t1 = std::chrono::high_resolution_clock::now();
    return std::chrono::duration_cast<std::chrono::nanoseconds>(t1 - t0).count();
}

long long runCyclic(const std::vector<int>& A, const std::vector<int>& B, std::vector<int>& C, int numThreads) {
    std::vector<std::thread> threads;
    int p = 0;
    auto t0 = std::chrono::high_resolution_clock::now();
    for (int id = 0; id < numThreads; id ++) {
        threads.emplace_back([id, numThreads, &A, &B, &C]() {
            for (int i = id; i < A.size(); i += numThreads) {
				C[i] = A[i] + B[i];
            }
        });
    }
	for (auto& th : threads) {
		th.join();
	}
	auto t1 = std::chrono::high_resolution_clock::now();
	return std::chrono::duration_cast<std::chrono::nanoseconds>(t1 - t0).count();
}

long long runBlocks(const std::vector<int>& A, const std::vector<int>& B, std::vector<int>& C, int numThreads) {
    std::vector<std::thread> threads;
	auto t0 = std::chrono::high_resolution_clock::now();
	int n = A.size();
	int blockSize = n / numThreads;
	for (int id = 0; id < numThreads; id++) {
		int start = id * blockSize;
		int end = (id == n - 1) ? n : start + blockSize;
		threads.emplace_back([start, end, n, &A, &B, &C]() {
			for (int i = start; i < end; i++) {
				C[i] = A[i] + B[i];
			}
		});
	}
	for (auto& th : threads) {
		th.join();
	}
	auto t1 = std::chrono::high_resolution_clock::now();
	return std::chrono::duration_cast<std::chrono::nanoseconds>(t1 - t0).count();
}

bool verifyEqual(const std::vector<int>& X, const std::vector<int>& Y) {
	if (X.size() != Y.size()) return false;
	for (size_t i = 0; i < X.size(); i++) {
		if (X[i] != Y[i]) return false;
	}
	return true;
}

int main()
{
    int n = MAX_SIZE;
    int maximum = MAX_VALUE;
	int p = NUM_THREADS;

	std::vector<int> A(n);
	std::vector<int> B(n);

	std::vector<int> C(n);
	std::vector<int> C2(n);
	std::vector<int> C3(n);

	generator(A, n, maximum);
	generator(B, n, maximum);

	std::cout  << sum(A, B, C, n) << " nanoseconds = " << " duration for sequential sum" << std::endl;
	std::cout  << runCyclic(A, B, C2, p) << " nanoseconds = " << " duration for cyclic sum " <<  std::endl;
	std::cout  << runBlocks(A, B, C3, p) << " nanoseconds = " << " duration for block sum " << std::endl;

	std::cout << std::endl << "Veryfing C == C1 " << verifyEqual(C, C2) << std::endl;
	std::cout << "Veryfing C2 == C3 " << verifyEqual(C3, C2) << std::endl;

	/// int p = std::thread::hardware_concurrency();

    return 0;
}

