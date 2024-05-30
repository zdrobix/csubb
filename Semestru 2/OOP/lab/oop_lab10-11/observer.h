#pragma once
#include <vector>

using namespace std;

class Observer {
public:
	virtual void update_o() = 0;
};

class Observable {
	vector <Observer*> list;
protected:
	void notify() {
		for (auto obs : list) {
			obs->update_o();
		}
	}
public:
	void addObserver(Observer* o) {
		list.push_back(o);
	}
	void removeObserver(Observer* o) {
		list.erase(remove(list.begin(), list.end(), o));
	}

};