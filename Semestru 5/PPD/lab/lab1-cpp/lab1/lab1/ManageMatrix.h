#pragma once
#include <vector>
#include <sstream>
#include <string>

class ManageMatrix {
public:
	static void generator(std::vector<int>& A, int n, int maxx) {
		for (int i = 0; i < n; i++)
			A[i] = rand() % maxx;
	}

	static int** CreateMatrixDynamic(int rows, int cols, int max) {
		int** matrix = new int* [rows];
		for (int i = 0; i < rows; i++) {
			matrix[i] = new int[cols];
			for (int j = 0; j < cols; j++) {
				matrix[i][j] = rand() % max;
			}
		}
		return matrix;
	}

	static std::string MatrixToString(const std::vector<std::vector<int>>& matrix, int rows, int cols) {
		std::ostringstream oss;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				oss << matrix[i][j];
				if (j < cols - 1) oss << " ";
			}
			oss << "\n";
		}
		return oss.str();
	}

	static std::string MatrixToString(int** matrix, int rows, int cols) {
		std::ostringstream oss;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				oss << matrix[i][j];
				if (j < cols - 1) oss << " ";
			}
			oss << "\n";
		}
		return oss.str();
	}

	static std::vector<std::vector<int>> StringToMatrixStatic(const std::string& data, int rows, int cols) {
		std::vector<std::vector<int>> matrix(rows, std::vector<int>(cols, 0));
		std::istringstream iss(data);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				iss >> matrix[i][j];
			}
		}
		return matrix;
	}

	static int** StringToMatrixDynamic(const std::string& data, int rows, int cols) {
		int** matrix = new int* [rows];
		std::istringstream iss(data);
		for (int i = 0; i < rows; i++) {
			matrix[i] = new int[cols];
			for (int j = 0; j < cols; j++) {
				iss >> matrix[i][j];
			}
		}
		return matrix;
	}
};