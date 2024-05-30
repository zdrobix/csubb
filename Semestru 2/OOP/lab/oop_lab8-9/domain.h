#ifndef DOMAIN_H
#define DOMAIN_H
#include <string>
#include <iostream>

using namespace std;

class Product {

private:
    string name;

    string producer;

    string type;

    float price;

public:

    static int contor;

    Product(const string& name, const string& producer, const string& type, const float price){
        contor++;
        this-> name = name;
        this-> producer = producer;
        this-> type = type;
        this-> price = price;
    };

    Product(){
        contor++;
        this-> name = "";
        this-> producer = "";
        this-> type = "";
        this-> price = 0;
    };

    ~Product() {
        contor--;
        this-> name = "";
        this-> producer = "";
        this-> type = "";
        this-> price = 0;
    };

    Product(const Product& ot) {
        contor++;
        name = ot.name;
        producer = ot.producer;
        type = ot.type;
        price = ot.price;
    };

    bool operator==(const Product& ot) const {

        return this->name == ot.name and this->price == ot.price and this->producer == ot.producer and this->type == ot.type;
    }

    const string& getName() const;

    const string& getProducer() const;

    const string& getType() const;

    float getPrice() const;

    void setName (const string& nName);

    void setProducer (const string& pProducer);

    void setType (const string& tType);

    void setPrice (float pPrice);
};

#endif //DOMAIN_H
