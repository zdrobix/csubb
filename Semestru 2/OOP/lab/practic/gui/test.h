#pragma once
#include "service.h"
#include "repo.h"
#include "domain.h"
#include <fstream>

using namespace std;

void test_domain() {
	Student alex{ 1, "Alex", "LTNB" };
	Student alex2{ 1, "Alex", "LTNB" };
	assert(alex == alex2);
	assert(alex.get_id() == 1);
	assert(alex.get_id() != 2);
	assert(alex.get_name() == "Alex");
	assert(alex.get_name() != "Marian");
	assert(alex.get_school() == "LTNB");
	assert(alex.get_school() != "LICEU ALTUL");
}

void test_add() {
	Repo repo{ "./input_test.txt" };
	Service service{ repo };
	Student alex{ 1, "Alex", "LTNB" };
	service.add_student(1, "Alex", "LTNB");
	assert(repo.get_student(1) == alex);
	int size = repo.get_all().size();

	service.add_student(1, "ACELASI ID", "ACELASI ID");
	assert(repo.get_all().size() == size);

	repo.delete_all();
}

void test_sort() {
	Repo repo{ "./input_test.txt" };
	Service service{ repo };
	service.add_student(1, "Alex", "LTNB");
	service.add_student(2, "Bercea", "LTNB");
	service.add_student(3, "Zmail", "LTNB");
	service.add_student(4, "Maria", "LTNB");
	Student s1(1, "Alex", "LTNB"), s2(2, "Bercea", "LTNB"), s3(3, "Zmail", "LTNB"), s4(4, "Maria", "LTNB");
	service.sort_students();
	vector<Student> list = repo.get_all();
	assert(list.at(0) == s1);
	assert(list.at(1) == s2);
	assert(list.at(2) == s4);
	assert(list.at(3) == s3);
}

void test_all() {
	test_domain();
	test_add();
	test_sort();
	ofstream out("./input_test.txt");
	out << "";
	out.close();
}