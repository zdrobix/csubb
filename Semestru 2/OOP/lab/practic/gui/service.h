#pragma once
#include "repo.h"
#include "domain.h"

#include <vector>
#include <string>
#include <algorithm>

using namespace std;

class Service {
friend class Repo;
private:
	Repo& repo;

public:
	//construit
	explicit Service(Repo& repo) : repo{ repo } {};

	//adauga un student, dupa ce verifica daca numarul matricol este valid
	void add_student(int id, string name, string school) {
		if (this->repo.get_student(id) == Student{}) {
			this->repo.add_student(Student{ id, name, school });
			return;
		}
	};

	//sorteaza elevii dupa nume, in ordine alfabetica
	void sort_students() {
		vector<Student> list = this->repo.get_all();
		auto compare = [](const Student& a, const Student& b) {
			return a.get_name() < b.get_name();
		};
		sort(list.begin(), list.end(), compare);
		repo.student_list = list;
	};

	//returneaza toti studentii
	vector<Student> get_all() {
		return this->repo.get_all();
	}
};