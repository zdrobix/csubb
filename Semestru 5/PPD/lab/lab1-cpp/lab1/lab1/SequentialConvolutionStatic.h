#pragma once
#include <vector>
#include <chrono>

class SequentialConvolutionStatic {
public:
	long long Convolute(std::vector<int> matrix, std::vector<int> kernel, int N, int M, int k) {
		auto t0 = std::chrono::high_resolution_clock::now();

		std::vector<int> result(N * M, 0);

		for (int i = 0; i < N; i++) {
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

		auto t1 = std::chrono::high_resolution_clock::now();
		return std::chrono::duration_cast<std::chrono::nanoseconds>(t1 - t0).count();
	}
};
