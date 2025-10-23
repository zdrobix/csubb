#pragma once
#include <thread>
#include <vector>
#include <chrono>

class ParalelConvolutionStatic {
public:
	long long ConvoluteHorizontal(const std::vector<int> matrix, const std::vector<int> kernel, int N, int M, int k, int p) {
		auto t0 = std::chrono::high_resolution_clock::now();

		std::vector<int> result(N * M, 0);


		auto worker = [&](int startRow, int endRow) {
			for (int i = startRow; i < endRow; i++) {
				for (int j = 0; j < M; j++) {
					int sum = 0;
					for (int ki = 0; ki < k; ki++) {
						for (int kj = 0; kj < k; kj++) {
							int mi = i + ki - k / 2;
							int mj = j + kj - k / 2;
							if (mi >= 0 && mi < N && mj >= 0 && mj < M) {
								sum += matrix[mi * M + mj] * kernel[ki * k + kj];
							}
						}
					}
					result[i * M + j] = sum;
				}
			}
			};

		std::vector<std::thread> threads;
		int rowsPerThread = N / p;
		for (int i = 0; i < p; i++) {
			int startRow = i * rowsPerThread;
			int endRow = (i == p - 1) ? N : startRow + rowsPerThread;
			threads.emplace_back(worker, startRow, endRow);
		}

		for (auto& t : threads) {
			t.join();
		}

		auto t1 = std::chrono::high_resolution_clock::now();
		return std::chrono::duration_cast<std::chrono::nanoseconds>(t1 - t0).count();
	}

	long long ConvoluteVertical(std::vector<int> matrix, std::vector<int> kernel, int N, int M, int k, int p) {
		auto t0 = std::chrono::high_resolution_clock::now();

		std::vector<int> result(N * M, 0);

		auto worker = [&](int startCol, int endCol) {
			for (int j = startCol; j < endCol; j++) {
				for (int i = 0; i < N; i++) {
					int sum = 0;
					for (int ki = 0; ki < k; ki++) {
						for (int kj = 0; kj < k; kj++) {
							int mi = i + ki - k / 2;
							int mj = j + kj - k / 2;
							if (mi >= 0 && mi < N && mj >= 0 && mj < M) {
								sum += matrix[mi * M + mj] * kernel[ki * k + kj];
							}
						}
					}
					result[i * M + j] = sum;
				}
			}
			};

		std::vector<std::thread> threads;
		int colsPerThread = M / p;
		for (int i = 0; i < p; i++) {
			int startCol = i * colsPerThread;
			int endCol = (i == p - 1) ? M : startCol + colsPerThread;
			threads.emplace_back(worker, startCol, endCol);
		}

		for (auto& t : threads) {
			t.join();
		}

		auto t1 = std::chrono::high_resolution_clock::now();
		return std::chrono::duration_cast<std::chrono::nanoseconds>(t1 - t0).count();
	}
};