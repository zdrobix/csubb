#pragma once
#ifndef REPO_H
#define REPO_H
#include "domain.h"

#include <string>
#include <vector>
#include <fstream>

using namespace std;

class Repo {
friend class Service;
private:
	vector<Tractor> list;
	string filename;

public:
	explicit Repo(const string& filename) : filename{ filename } {
		
		load_from_file();
	};

	void load_from_file() {
		ifstream in(filename);
		if (!in.is_open())
			return;
		string id, denumire, tip, roti;
		while (getline(in, id)) {
			getline(in, denumire);
			getline(in, tip);
			getline(in, roti);
			this->add_tractor(Tractor{ stoi(id), denumire, tip, stoi(roti) });
		}
		in.close();
	}

	void save_to_file() {
		ofstream out(filename);
		if (!out.is_open())
			return;
		for (const auto& tractor : list) {
			out << tractor.get_id() << endl
				<< tractor.get_denumire() << endl
				<< tractor.get_tip() << endl
				<< tractor.get_roti() << endl;
		}
		out.close();
	}

	void add_tractor(const Tractor& tractor) {
		list.push_back(tractor);
		this->save_to_file();
	}

	void delete_tractor(const Tractor& tractor) {
		for (auto it = list.begin(); it != list.end(); ++it) {
			if (*it == tractor) {
				list.erase(it);
			}
		}
		this->save_to_file();
	}

	const Tractor get_tractor(int id) {
		for (const auto& tractor : list) {
			if (tractor.get_id() == id)
				return tractor;
		}
		return Tractor{};
	}

	bool existing_id(int id) {
		for (const auto& tractor : list) {
			if (tractor.get_id() == id)
				return true;
		}
		return false;
	}

	vector<Tractor> get_all() {
		return this->list;
	}
};

#endif //REPO_H