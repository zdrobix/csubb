#pragma once
#include <fstream>
#include <iostream>
#include <string>

class ManageFile {
public:
	struct DataStatic {
		int N, M, k;
		std::vector<int> matrix;
		std::vector<int> kernel;
	};

	struct DataDynamic {
		int N, M, k;
		int** matrix;
		int** kernel;
	};

	const static DataStatic ReadFileStatic(const std::string& filename) {
		std::ifstream fin(filename);
		auto d = new DataStatic();
		fin >> d->N >> d->M >> d->k;
		d->matrix.resize(d->N * d->M);
		d->kernel.resize(d->k * d->k);
		for (int i = 0; i < d->N; i++) {
			for (int j = 0; j < d->M; j++)
				fin >> d->matrix[i * d->M + j];
		}
		for (int i = 0; i < d->k; i++) {
			for (int j = 0; j < d->k; j++)
				fin >> d->kernel[i * d->k + j];
		}
		return *d;
	}

	const static DataDynamic ReadFileDynamic(const std::string& filename) {
		std::ifstream fin(filename);
		DataDynamic d;
		fin >> d.N >> d.M >> d.k;

		d.matrix = new int* [d.N];
		for (int i = 0; i < d.N; i++) {
			d.matrix[i] = new int[d.M];
			for (int j = 0; j < d.M; j++)
				fin >> d.matrix[i][j];
		}

		d.kernel = new int* [d.k];
		for (int i = 0; i < d.k; i++) {
			d.kernel[i] = new int[d.k];
			for (int j = 0; j < d.k; j++)
				fin >> d.kernel[i][j];
		}

		return d;
	}

	const static bool WriteToFile(const std::string& data, const std::string filename) {
		std::ofstream outFile(filename);
		if (!outFile.is_open()) {
			std::cerr << "Error opening file for writing: " << filename << std::endl;
			return false;
		}
		outFile << data;
		return true;
	}

	const static std::string ReadFromFile(const std::string filename) {
		std::ifstream inFile;
		inFile.open(filename);
		if (!inFile.is_open()) {
			std::cerr << "Error opening file for reading: " << filename << std::endl;
			return "";
		}

		std::string data((std::istreambuf_iterator<char>(inFile)), std::istreambuf_iterator<char>());
		inFile.close();
		return data;
	}
};