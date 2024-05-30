#include "repo.h"
#include "vector.h"
#include <vector>
#include <cstdlib>

using namespace std;

Product* Repo::getAll() const {

    return list.allElems();
}

int Repo::numberProducts() const {

    return list.size();
}

void Repo::addProduct(const Product& participant) {

    list.add(participant);
}

void Repo::updateProduct(int position, const string &nName, const string &pProducer, const string &tType, float pPrice) {

    Iterator i{list, position};

    i.element().setName(const_cast<string &>(nName));

    i.element().setProducer(const_cast<string &>(pProducer));

    i.element().setType(const_cast<string &>(tType));

    i.element().setPrice(pPrice);
}

void Repo::deleteProduct(int position) {

    if (position < 0 || position >= list.size() ) return;

    list.erase(position);
}


