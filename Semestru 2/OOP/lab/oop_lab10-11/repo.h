#ifndef REPO_H
#define REPO_H
#include "domain.h"
#include <vector>
#include <unordered_map>
#include "exception.h"


class RepoAbs {
public:
    RepoAbs() = default;

    RepoAbs(const RepoAbs &o) = delete;

    virtual void addProduct(const Product &p) = 0;

    virtual void updateProduct(Product &prod, const string& nName, const string& pProducer, const string& tType, float pPrice) = 0;

    virtual void deleteProduct(const Product &p) = 0;

    virtual int numberProducts() = 0;

    virtual const Product& get(int position) = 0;

    virtual bool exist(Product &prod) = 0;

    virtual void deleteAll() = 0;

    virtual vector<Product>& getAll() = 0;

    virtual Product find(Product &prod) = 0;
};

class Repo: public RepoAbs {

public:
    Repo() = default;

    /*
    * Primeste ca parametru obiectul Product, si il adauga in memorie.
    * @param: Product
    */
    void addProduct(const Product& product) override;

    /*
     * Acceseaza produsul.
     * @param: produsul ce trebuie modificat, numele nou, producatorul nou, tipul nou si pretul nou
     */
    void updateProduct(Product &prod, const string& nName, const string& pProducer, const string& tType, float pPrice) override;

    /*
     * Sterge elementul din memorie.
     * @param: elementul ce trebuie sters
     */
    void deleteProduct(const Product &prod) override;

    /*
     * Daca exista alt Repo deschis, se sterge.
     * Pentru a asigura buna functionare a programului(fara duplicate).
     */
    Repo(const Repo& ot) = delete;

    /*
     * Returneaza toate produsele adaugate.
     * @return: vector
     */
    vector<Product>& getAll() override;

    /*
     * Deletes all products.
     */
    void deleteAll() override;

    /*
     * Returneaza cate produse au fost adaugate(numarul)
     * @return: numarul de produse
     */
    int numberProducts() override;

    /*
     * Returneaza un numar de la o pozitie anume.
     */
    const Product& get(int position) override;

    /*
     * Cauta produsul in memorie.
     * @param: produsul cautat
     * @return : true - daca exista
     *           false - daca nu exista
     */
    bool exist(Product &prod) override;

    Product find(Product &prod) override;

private:
    vector<Product> list;
};


class RepoFile : public Repo {
private:
    string filename;

    void loadFromFile();

    void writeToFile();

public:
    explicit RepoFile( string& filename) : Repo{}, filename{filename} {

        loadFromFile();
    }

    RepoFile() : Repo{} {};

    void addProduct(const Product &prod) override {

        Repo::addProduct(prod);

        writeToFile();
    }

    void deleteProduct(const Product &prod) override {

        Repo::deleteProduct(prod);

        writeToFile();
    }
};


class RepoMap : public RepoAbs {
private:
    unordered_map<string, Product> map;

public:
    RepoMap() : RepoAbs() {};

    RepoMap(const RepoMap& o) = delete;

    void addProduct(const Product &prod) override {

        if (map.find(prod.getName()) != map.end())

            throw MyException((string&)"The product does not exist.");

        map[prod.getName()] = prod;
    }

    void deleteProduct(const Product &prod) override {

        if (map.find(prod.getName()) == map.end())

            throw MyException((string&)"The product does not exist.");

        map.erase(prod.getName());
    }

    void updateProduct( Product& prod, const string& name, const string& producer, const string& type, const float price) override {

        if (map.find(prod.getName()) == map.end())

            throw MyException((string&)"Oferta nu exista!");

        Product newProd{name, producer, type, price};

        deleteProduct(prod);

        addProduct(newProd);
    }

    bool exist( Product& prod) override {

        return map.find(prod.getName()) != map.end();
    }

    Product find( Product& prod) override {

        auto it = map.find(prod.getName());

        if (it != map.end())

            return it->second;

        return Product{};
    }

    unordered_map<string, Product> getMap()  {

        return map;
    }

    int numberProducts() override { return 0; }
    const Product& get(int position) override { return Product{}; }
    void deleteAll() override {}
    vector<Product>& getAll() override { static vector<Product> dummy; return dummy; }
};

#endif //REPO_H