#pragma once
#include <string>

using namespace std;

class Student {
private:
	int id;
	string name, school;
public:
	//constructor
	explicit Student(int id, string name, string school) : id{ id }, name{ name }, school{ school } {};

	//constructor de student null
	Student() : id{ -1 }, name{ "" }, school{ "" } {};

	//returneaza numarul matricol
	const int get_id() const {
		return this->id;
	};

	//returneaza numele elevului
	const string get_name() const {
		return this->name;
	};

	//returneaza scoala elevului
	const string get_school() const {
		return this->school;
	};

	//operatorul de egalitate
	bool operator==(const Student& other) {
		return this->id == other.id and this->name == other.name and this->school == other.school;
	};
};