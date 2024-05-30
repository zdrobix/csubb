#include "domain.h"
#include <string>

using namespace std;

string Product::getName() const {

    return this->name;
}

string Product::getProducer() const {

    return this->producer;
}

string Product::getType() const {

    return this->type;
}

float Product::getPrice() const {

    return this->price;
}

void Product::setName (string& nName ) {

    this->name = nName;
}

void Product::setProducer (string& pProducer) {

    this->producer = pProducer;
}

void Product::setType (string& tType) {

    this->type = tType;
}

void Product::setPrice (float pPrice) {

    this->price = pPrice;
}


