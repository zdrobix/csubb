#pragma once
#include <vector>
#include <chrono>
#include <thread>

class ParalelVerticalConvolution {
public:
	long long ConvoluteStatic(const std::vector<std::vector<int>> Matrix, const std::vector<std::vector<int>> Kernel, int N, int M, int K, int numThreads) {
		auto t0 = std::chrono::high_resolution_clock::now();

		int outRows = N - K + 1;
		int outCols = M - K + 1;

		std::vector<std::vector<int>> Result(outRows, std::vector<int>(outCols, 0));

		auto worker = [&](int startCol, int endCol) {
			for (int j = startCol; j < endCol; j++) {
				for (int i = 0; i < outRows; i++) {
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
		int colsPerThread = outCols / numThreads;
		for (int t = 0; t < numThreads; t++) {
			int startCol = t * colsPerThread;
			int endCol = (t == numThreads - 1) ? outCols : startCol + colsPerThread;
			threads.emplace_back(worker, startCol, endCol);
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

		auto worker = [&](int startCol, int endCol) {
			for (int j = startCol; j < endCol; j++) {
				for (int i = 0; i < outRows; i++) {
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
		int colsPerThread = outCols / numThreads;
		for (int t = 0; t < numThreads; t++) {
			int startCol = t * colsPerThread;
			int endCol = (t == numThreads - 1) ? outCols : startCol + colsPerThread;
			threads.emplace_back(worker, startCol, endCol);
		}
		for (auto& th : threads) {
			th.join();
		}
		auto t1 = std::chrono::high_resolution_clock::now();
		return std::chrono::duration_cast<std::chrono::nanoseconds>(t1 - t0).count();
	};
};