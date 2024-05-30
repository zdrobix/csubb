#include "domain.h"
#include <string>

using namespace std;

const string& Product::getName() const {

    return this->name;
}

const string& Product::getProducer() const {

    return this->producer;
}

const string& Product::getType() const {

    return this->type;
}

float Product::getPrice() const {

    return this->price;
}

void Product::setName (const string& nName ) {

    this->name = nName;
}

void Product::setProducer (const string& pProducer) {

    this->producer = pProducer;
}

void Product::setType (const string& tType) {

    this->type = tType;
}

void Product::setPrice (float pPrice) {

    this->price = pPrice;
}

int Product::contor = 0;
