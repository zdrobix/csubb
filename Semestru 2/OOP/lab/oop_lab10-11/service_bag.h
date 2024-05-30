#ifndef SERVICE_BAG_H
#define SERVICE_BAG_H
#include <vector>
#include <time.h>
#include <observer.h>

#include "domain.h"
#include "repo.h"

using namespace std;

class Controller_bag : public Observable {
public:
	vector<Product> product_list;
	RepoFile& repo;
	explicit Controller_bag(RepoFile& repo) : repo{ repo }  {};

	void add_product_bag(const Product& p) {
		this->product_list.push_back(p);
		this->notify();
	};

	void delete_product_bag(int position) {
		if (position >= 0 && position < this->product_list.size()) {
			this->product_list.erase(this->product_list.begin() + position);
			this->notify();
		}
	}

	void delete_all() {
		this->product_list.clear();
		this->notify();
	}

	void add_random_product(vector<Product> list, int number) {
		srand(time(NULL));
		for (int i = 0; i < number; i++) {
			this->product_list.push_back(list.at(rand() % list.size()));
		}
		this->notify();
	}

	vector<Product> getAllProducts() const {
		return this->product_list;
	}

	float totalProductCost() {
		float sum = 0;
		for (const auto& prt : this->product_list)
			sum = sum + prt.getPrice();
		return sum;
	}

};
#endif // SERVICE_BAG_H