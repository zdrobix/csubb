#pragma once
#ifndef SERVICE_H
#define SERVICE_H
#include "repo.h"
#include "validator.h"
#include "domain.h"
#include <vector>
#include <string>
#include <time.h>
#include <stdexcept>

using namespace std;

class Service {
friend class Repo;
private:
	Repo& repo;
	Validator& val;

public:
	explicit Service(Repo& repo, Validator& val) : repo{ repo }, val{ val } {};

	void add_tractor(string denumire, string tip, int roti) {
		val.validate(denumire, tip, roti);
		srand(time(NULL));
		int id = rand() % 1000;
		while (this->repo.existing_id(id))
			id = rand() % 1000;
		this->repo.add_tractor(Tractor{ id, denumire, tip, roti });
	}

	void delete_tractor(int id) {
		if (!this->repo.existing_id(id))
			throw runtime_error("Critical error");
		this->repo.delete_tractor(this->repo.get_tractor(id));
	}

	int number_same_type(const Tractor& tractor) {
		int number = 0;
		for (const auto& tractor_itt : this->repo.get_all()) {
			if (tractor_itt.get_tip() == tractor.get_tip()) {
				++number;
			}
		}
		return number;
	}

	vector<Tractor> get_all() {
		return this->repo.get_all();
	}

	void sort_denumire() {
		auto compare = [](const Tractor& a, const Tractor& b) {
			return a.get_denumire() < b.get_denumire();
		};
		sort(this->repo.list.begin(), this->repo.list.end(), compare);
	}

	Tractor get(int id) {
		return this->repo.get_tractor(id);
	}
};

#endif //SERVICE_H