#pragma once
#include <fstream>
#include <iostream>
#include <string>

class ManageFile {
public:
	const static bool WriteToFile(const std::string& data, const std::string filename) {
		std::ofstream outFile;
		outFile.open(filename);
		if (!outFile.is_open()) {
			std::cerr << "Error opening file for writing: " << filename << std::endl;
			return false;
		}
		outFile << data;
		outFile.close();
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