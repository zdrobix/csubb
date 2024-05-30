#pragma once
#include <vector>
#include <string>
#include <fstream>
#include "domain.h"

using namespace std;

class Repo {
friend class Service;
private:
	vector<Student> student_list;
	string filename;
public:
	//constructor
	explicit Repo(const string& filename) : filename{ filename } {
		this->load_from_file();
	};

	//incarca studentii din fisier
	void load_from_file() {
		ifstream in(filename);
		if (!in.is_open()) {
			throw "error";
			return;
		}
		string id, name, school;
		while (getline(in, id)) {
			getline(in, name);
			getline(in, school);
			this->add_student(Student{ stoi(id), name, school });
		}
		in.close();
	}

	//salveaza studentii in fisier
	void save_to_file() {
		ofstream out(filename);
		if (!out.is_open()) {
			throw "error";
			return;
		}
		for (const auto& stud : this->student_list) {
			out << stud.get_id() << endl
				<< stud.get_name() << endl
				<< stud.get_school() << endl;
		}
		out.close();
	}

	//adauga in memorie un student, si salveaza in fisier
	void add_student(const Student& stud) {
		this->student_list.push_back(stud);
		this->save_to_file();
	}

	//cauta studentul dorit, si il sterge din fisier
	void delete_student(const Student& stud) {
		for (auto it = this->student_list.begin(); it <= this->student_list.end(); ++it) {
			if (*it == stud) {
				this->student_list.erase(it);
			}
		}
	}

	//returneaza studentul dorit pe baza numarului matricol(id)
	Student get_student(const int id) {
		for (const auto& stud: this->student_list) {
			if (stud.get_id() == id) {
				return stud;
			}
		}
		return Student{}; //student null
	}

	//returneaza vectorul de elevi
	vector<Student> get_all() {
		return this->student_list;
	}

	//sterge toate elementele
	void delete_all() {
		this->student_list.clear();
	}
};
