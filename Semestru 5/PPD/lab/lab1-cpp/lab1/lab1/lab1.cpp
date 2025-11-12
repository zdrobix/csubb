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

void run_sequential_static() {
    int count = 1;
    std::cout << "|No. |N |M |k |Time(nano) |\n|--|--|--|--|--|\n";
    for (auto spec : specifications) {
        long long sum = 0;
        std::string filename = "./input/data-" + std::to_string(spec.N) + "-" + std::to_string(spec.M) + ".txt";
        auto data = ManageFile().ReadFileStatic(filename);
        for (int i = 0; i < 10; i++)
        {
            auto seq = SequentialConvolutionStatic();
            sum += seq.Convolute(data.matrix, data.kernel, data.N, data.M, data.k);
        }
        
        std::cout << "|" + count << " |" + spec.N << " |" + spec.M << " |" + spec.k << " |" + sum / 10 << " |" + '\n';
        count++;
    }
}

void run_sequential_dynamic() {
    int count = 1;
    std::cout << "|No. |N |M |k |Time(nano) |\n|--|--|--|--|--|\n";
    for (auto spec : specifications) {
        long long sum = 0;
        std::string filename = "./input/data-" + std::to_string(spec.N) + "-" + std::to_string(spec.M) + ".txt";
        auto data = ManageFile().ReadFileDynamic(filename);
        for (int i = 0; i < 10; i++)
        {
            auto seq = SequentialConvolutionDynamic();
            sum += seq.Convolute(data.matrix, data.kernel, data.N, data.M, data.k);
            seq.DeleteResult(data.N);
        }
        std::cout << "|" + count << " |" + spec.N << " |" + spec.M << " |" + spec.k << " |" + sum / 10 << " |" + '\n';
        count++;
    }
}

void run_horizontal_static() {
    int count = 1;
    std::cout << "|No. |N |M |k |p |Time(nano) |\n|--|--|--|--|--|--|\n";
    for (auto spec : specifications) {
        std::string filename = "./input/data-" + std::to_string(spec.N) + "-" + std::to_string(spec.M) + ".txt";
        auto data = ManageFile().ReadFileStatic(filename);
        for (auto p : spec.p) {
            long long sum = 0;
            for (int i = 0; i < 10; i++) {
                auto par = ParalelConvolutionStatic();
                sum += par.ConvoluteHorizontal(data.matrix, data.kernel, data.N, data.M, data.k, p);
            }
            std::cout << "|" + count << " |" + spec.N << " |" + spec.M << " |" + spec.k << " |" + p << " |" + sum / 10 << " |" + '\n';
            count++;
        }
    }
}

void run_horizontal_dynamic() {
    int count = 1;
    std::cout << "|No. |N |M |k |p |Time(nano) |\n|--|--|--|--|--|--|\n";
    for (auto spec : specifications) {
        std::string filename = "./input/data-" + std::to_string(spec.N) + "-" + std::to_string(spec.M) + ".txt";
        auto data = ManageFile().ReadFileDynamic(filename);
        for (auto p : spec.p) {
            long long sum = 0;
            for (int i = 0; i < 10; i++) {
                auto par = ParalelConvolutionDynamic();
                sum += par.ConvoluteHorizontal(data.matrix, data.kernel, data.N, data.M, data.k, p);
                par.DeleteResult(data.N);
            }
            std::cout << "|" + count << " |" + spec.N << " |" + spec.M << " |" + spec.k << " |" + p << " |" + sum / 10 << " |" + '\n';
            count++;
        }
    }
}

void run_vertical_static() {
    int count = 1;
    std::cout << "|No. |N |M |k |p |Time(nano) |\n|--|--|--|--|--|--|\n";
    for (auto spec : specifications) {
        std::string filename = "./input/data-" + std::to_string(spec.N) + "-" + std::to_string(spec.M) + ".txt";
        auto data = ManageFile().ReadFileStatic(filename);
        for (auto p : spec.p) {
            long long sum = 0;
            for (int i = 0; i < 10; i++) {
                auto par = ParalelConvolutionStatic();
                sum += par.ConvoluteVertical(data.matrix, data.kernel, data.N, data.M, data.k, p);

            }
            std::cout << "|" + count << " |" + spec.N << " |" + spec.M << " |" + spec.k << " |" + p << " |" + sum / 10 << " |" + '\n';
            count++;
        }
    }
}

void run_vertical_dynamic() {
    int count = 1;
    std::cout << "|No. |N |M |k |p |Time(nano) |\n|--|--|--|--|--|--|\n";
    for (auto spec : specifications) {
        std::string filename = "./input/data-" + std::to_string(spec.N) + "-" + std::to_string(spec.M) + ".txt";
        auto data = ManageFile().ReadFileDynamic(filename);
        for (auto p : spec.p) {
            long long sum = 0;
            for (int i = 0; i < 10; i++) {
                auto par = ParalelConvolutionDynamic();
                sum += par.ConvoluteVertical(data.matrix, data.kernel, data.N, data.M, data.k, p);
                par.DeleteResult(data.N);
            }
            std::cout << "|" + count << " |" + spec.N << " |" + spec.M << " |" + spec.k << " |" + p << " |" + sum / 10 << " |" + '\n';
            count ++;
        }
    }
}

int main()
{
    run_sequential_static();
    run_sequential_dynamic();
    run_horizontal_static();
    run_horizontal_dynamic();
    run_vertical_static();
    run_vertical_dynamic();
}

