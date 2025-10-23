#pragma once
#include<vector>
#include<chrono>

class SequentialConvolutionDynamic {
public:
	int** Result;

	long long Convolute( int** Matrix, int** Kernel, int N, int M, int K) {
		auto t0 = std::chrono::high_resolution_clock::now();

		int outRows = N - K + 1;
		int outCols = M - K + 1;

		Result = new int* [outRows];
		for (int i = 0; i < outRows; i++) {
			Result[i] = new int[outCols];
		}

		for (int i = 0; i < outRows; i++) {
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

		auto t1 = std::chrono::high_resolution_clock::now();
		return std::chrono::duration_cast<std::chrono::nanoseconds>(t1 - t0).count();
	}
};