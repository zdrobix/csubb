#pragma once
#include <vector>
#include <chrono>
#include <thread>

class ParalelHorizontalConvolution {
public:
	long long ConvoluteStatic(const std::vector<std::vector<int>> Matrix, const std::vector<std::vector<int>> Kernel, int N, int M, int K, int numThreads) {
		auto t0 = std::chrono::high_resolution_clock::now();

		int outRows = N - K + 1;
		int outCols = M - K + 1;

		std::vector<std::vector<int>> Result(outRows, std::vector<int>(outCols, 0));

		auto worker = [&](int startRow, int endRow) {
			for (int i = startRow; i < endRow; i++) {
				for (int j = 0; j < outCols; j++) {
					int sum = 0;
					for (int ki = 0; ki < K; ki++) {
						for (int kj = 0; kj < K; kj++) {
							sum += Matrix[i + ki][j + kj] * Kernel[ki][kj];
						}
					}
					Result[i][j] = sum;
				}
			}
			};

		std::vector<std::thread> threads;
		int rowsPerThread = outRows / numThreads;
		for (int t = 0; t < numThreads; t++) {
			int startRow = t * rowsPerThread;
			int endRow = (t == numThreads - 1) ? outRows : startRow + rowsPerThread;
			threads.emplace_back(worker, startRow, endRow);
		}

		for (auto& th : threads) {
			th.join();
		}

		auto t1 = std::chrono::high_resolution_clock::now();
		return std::chrono::duration_cast<std::chrono::nanoseconds>(t1 - t0).count();
	};

	long long ConvoluteDynamic(const int** Matrix, const int** Kernel, int N, int M, int K, int numThreads) {
		auto t0 = std::chrono::high_resolution_clock::now();

		int outRows = N - K + 1;
		int outCols = M - K + 1;

		int** Result = new int* [outRows];
		for (int i = 0; i < outRows; i++) {
			Result[i] = new int[outCols];
		}

		auto worker = [&](int startRow, int endRow) {
			for (int i = startRow; i < endRow; i++) {
				for (int j = 0; j < outCols; j++) {
					int sum = 0;
					for (int ki = 0; ki < K; ki++) {
						for (int kj = 0; kj < K; kj++) {
							sum += Matrix[i + ki][j + kj] * Kernel[ki][kj];
						}
					}
					Result[i][j] = sum;
				}
			}
			};

		std::vector<std::thread> threads;
		int rowsPerThread = outRows / numThreads;
		for (int t = 0; t < numThreads; t++) {
			int startRow = t * rowsPerThread;
			int endRow = (t == numThreads - 1) ? outRows : startRow + rowsPerThread;
			threads.emplace_back(worker, startRow, endRow);
		}

		for (auto& th : threads) {
			th.join();
		}

		auto t1 = std::chrono::high_resolution_clock::now();
		return std::chrono::duration_cast<std::chrono::nanoseconds>(t1 - t0).count();
	};
};