#include <iostream>
#include "ManageMatrix.h"
#include "ManageFile.h"
#include "SequentialConvolutionDynamic.h"
#include "SequentialConvolutionStatic.h"
#include "ParalelConvolutionDynamic.h"
#include "ParalelConvolutionStatic.h"

struct Spec {
	int N, M, k;
	std::vector<int> p;

	Spec(int n, int m, int kernelSize, const std::vector<int>& nrT) : N(n), M(m), k(kernelSize), p(nrT) {}
};

std::vector<Spec> specifications = {
    Spec(10, 10, 3, {4}),
    Spec(1000, 1000, 5, {2, 4, 8, 16}),
    Spec(10, 10000, 5, {2, 4, 8, 16}),
    Spec(10000, 10, 5, {2, 4, 8, 16}),
    Spec(10000, 10000, 5, {2, 4, 8, 16})
};

int main()
{
    std::cout << "|No. |N |M |k |p |Time |\n|--|--|--|--|--|\n";
	for (const auto& spec : specifications) {
		long long sumSeqDynamic = 0;
		long long sumSeqStatic = 0;
		long long sumHorDynamic = 0;
		long long sumHorStatic = 0;
		long long sumVerDynamic = 0;
		long long sumVerStatic = 0;

		//std::vector<int> matrixStatic(spec.N * spec.M);
		//std::vector<int> kernelStatic(spec.k * spec.k);

		//ManageMatrix::generator(matrixStatic, spec.N * spec.M, INT_MAX);
		//ManageMatrix::generator(kernelStatic, spec.k * spec.k, 10);

		auto matrixDynamic = ManageMatrix::CreateMatrixDynamic(spec.N, spec.M, INT_MAX);
		auto kernelDynamic = ManageMatrix::CreateMatrixDynamic(spec.k, spec.k, 10);


		for (int i = 0; i < 10; i++) {
			//sumSeqDynamic += SequentialConvolutionDynamic().Convolute(matrixDynamic, kernelDynamic, spec.N, spec.M, spec.k);
			//sumSeqStatic += SequentialConvolutionStatic().Convolute(matrixStatic, kernelStatic, spec.N, spec.M, spec.k);
		}
		for (const auto& p : spec.p) {
			for (int i = 0; i < 10; i++) {
				//sumHorStatic += ParalelConvolutionStatic().ConvoluteHorizontal(matrixStatic, kernelStatic, spec.N, spec.M, spec.k, p);
				//sumHorDynamic += ParalelConvolutionDynamic().ConvoluteHorizontal(matrixDynamic, kernelDynamic, spec.N, spec.M, spec.k, p);
				sumVerDynamic += ParalelConvolutionDynamic().ConvoluteVertical(matrixDynamic, kernelDynamic, spec.N, spec.M, spec.k, p);
				//sumVerStatic += ParalelConvolutionStatic().ConvoluteVertical(matrixStatic, kernelStatic, spec.N, spec.M, spec.k, p);
				// 

			}
			std::cout << "|" << 1 << " |" << spec.N << " |" << spec.M << " |" << spec.k << " |" << p << " |" << sumVerDynamic / 10 << " |\n";
		}
		
	}
}

