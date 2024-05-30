#pragma once
#ifndef DOMAIN_H
#define DOMAIN_H
#include <string>

using namespace std;

class Tractor {
private:
	int id, roti;
	string denumire, tip;

public:
	const int get_id() const {
		return this->id;
	}

	const string get_denumire() const {
		return this->denumire;
	}

	const string get_tip() const {
		return this->tip;
	}

	const int get_roti() const{
		return this->roti;
	}

	bool operator==(const Tractor& other) {
		return this->id == other.id;
	}

	explicit Tractor(int id, string denumire, string tip, int roti) :
		id{ id }, denumire{ denumire }, tip{ tip }, roti{ roti } {};

	Tractor() : id{ -1 }, denumire{ "" }, tip{ "" }, roti{ -1 } {};
};

#endif //DOMAIN_H