#include "repo.h"
#include "vector.h"
#include "exception.h"
#include <fstream>
#include <vector>

using namespace std;

vector<Product>& Repo::getAll() {

    return this->list;
}

void Repo::deleteAll() {

    list.clear();
}

int Repo::numberProducts() {

    return int(list.size());
}

void Repo::addProduct(const Product& participant) {

    list.push_back(participant);
}

void Repo::updateProduct(Product &prod, const string &nName, const string &pProducer, const string &tType, float pPrice) {

    prod.setName(nName);

    prod.setProducer(pProducer);

    prod.setType(tType);

    prod.setPrice(pPrice);
}

void Repo::deleteProduct(const Product &prod) {

    for (auto it = list.begin(); it != list.end(); ++it) {

        if (*it == prod) {

            list.erase(it);

            break;
        }
    }
}

const Product& Repo::get(int position) {

    return list.at(position);
}

bool Repo::exist(Product &prod) {

    for ( const auto& prt : Repo::getAll() )

        if (prt == prod)

            return true;

    return false;
}

Product Repo::find(Product &prod) {

    for ( const auto& prt : Repo::getAll() )

        if (prt == prod)

            return prt;

    return Product{};
}

void RepoFile::loadFromFile() {

    ifstream in(filename);

    if (!in.is_open())

        throw MyException((string&)"The file has not been opened succesfully");

    string name, producer, type, priceStr;

    while (getline(in, name)) {

        getline(in, producer);

        getline(in, type);

        getline(in, priceStr);

        float price = stof(priceStr);

        Product prod{name, producer, type, price};

        this->addProduct(prod);
    }

    if (in.bad())

        throw MyException((string&)"The file has not been close succesfully");

    in.close();
}

void RepoFile::writeToFile() {

    ofstream out(filename);

    if (!out.is_open()) throw MyException((string&)"Eroare la deschiderea fisierului!!!");

    for (const auto &prod: this->getAll()) {

        out << prod.getName();

        out << endl;

        out << prod.getProducer();

        out << endl;

        out << prod.getType();

        out << endl;

        out << prod.getPrice();

        out << endl;
    }
    out.close();
}
