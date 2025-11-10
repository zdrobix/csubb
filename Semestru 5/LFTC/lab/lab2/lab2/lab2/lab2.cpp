#include "bits/stdc++.h"
#include <fstream>

struct AFD {
	std::set<std::string> states, finals;
	std::set<char> alphabet;
	std::string start;
	std::map<std::pair<std::string, char>, std::string> transitions;

	void read_from_file(std::istream &fin) {
		std::string key;

		while (fin >> key) {
			if (key == "states:") {
				std::string c;
				while (fin >> c && c != "alphabet:") {
					states.insert(c);
				}
				key = c;
			}
			if (key == "alphabet:") {
				std::string c;
				while (fin >> c && c != "start:") {
					alphabet.insert(c[0]);
				}
				key = c;
			}
			if (key == "start:") {
				fin >> start;
				fin >> key;
			}
			if (key == "finals:") {
				std::string c;
				while (fin >> c && c != "transitions:") {
					finals.insert(c);
				}
				key = c;
			}
			if (key == "transitions:") {
				std::string from, to;
				char symbol;
				while (fin >> from >> symbol >> to) {
					transitions[{from, symbol}] = to;
				}
			}
		}
	}

	bool accept(const std::string& word) {
		std::string current_state = start;
		for (char c : word) {
			if (transitions.find({ current_state, c }) == transitions.end()) {
				return false;
			}
			current_state = transitions[{current_state, c}];
		}
		return finals.find(current_state) != finals.end();
	}

	std::string prefix_maxim(const std::string& word) {
		std::string state = start, prefix;
		std::string last_prefix;
		for (char c : word) {
			if (transitions.find({ state, c }) == transitions.end()) {
				break;
			}
			state = transitions[{state, c}];
			prefix += c;
			if (finals.find(state) != finals.end()) {
				last_prefix = prefix;
			}
		}
		return last_prefix;
	}

	void print() {
		std::cout << "States: ";
		for (const auto& state : states) {
			std::cout << state << " ";
		}
		std::cout << "\nAlphabet: ";
		for (const auto& symbol : alphabet) {
			std::cout << symbol << " ";
		}
		std::cout << "\nStart: " << start << "\nFinals: ";
		for (const auto & final : finals) {
			std::cout << final << " ";
		}
		std::cout << "\nTransitions:\n";
		for (const auto& t : transitions) {
			std::cout << t.first.first << " " << t.first.second << " " << t.second << "\n";
		}
	}
};

void run_file() {
	AFD afd;
	std::ifstream fin("inclass.txt");
	afd.read_from_file(fin);
	afd.print();

	while (1) {
		std::cout << "\n1. Check word acceptance\n2. Find maximum accepted prefix\n3. Exit\nChoose an option: ";
		int option;
		std::cin >> option;
		if (option == 3) break;
		std::string s;
		std::cout << "Enter word: ";
		std::cin >> s;
		if (option == 1)
			std::cout << (afd.accept(s) ? "Accepted\n" : "Rejected\n");
		if (option == 2)
			std::cout << "Maximum accepted prefix: " << afd.prefix_maxim(s) << "\n";
	}
}

void run_keyboard() {
	AFD afd;
	afd.read_from_file(std::cin);
	std::cin.clear();
	std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
	afd.print();

	while (1) {
		std::cout << "\n1. Check word acceptance\n2. Find maximum accepted prefix\n3. Exit\nChoose an option: ";
		int option;
		std::cin >> option;
		if (option == 3) break;
		std::string s;
		std::cout << "Enter word: ";
		std::cin >> s;
		if (option == 1)
			std::cout << (afd.accept(s) ? "Accepted\n" : "Rejected\n");
		if (option == 2)
			std::cout << "Maximum accepted prefix: " << afd.prefix_maxim(s) << "\n";
	}
}

int main()
{
	run_file();
	//run_keyboard();
}



