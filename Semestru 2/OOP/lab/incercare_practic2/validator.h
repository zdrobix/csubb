#pragma once
#ifndef VALIDATOR_H
#define VALIDATOR_H
#include <string>
#include <stdexcept>

using namespace std;

class Validator {
public:
	Validator() {};

	void validate(string denumire, string tip, int roti) {
		string errors = "";
		if (denumire.empty())
			errors += "Invalid name.\n";
		if (tip.empty())
			errors += "Invalid type.\n";
		if (roti % 2 == 1)
			errors += "Wheels must be an even number.\n";
		if (roti > 21)
			errors += "Too many wheels!!!\n";
		if (errors != "")
			throw runtime_error(errors);
	}
};

#endif //VALIDATOR_H