#ifndef SERVICE_H
#define SERVICE_H
#include <string>
#include "repo.h"
#include "validator.h"
#include <unordered_map>
#include "undo.h"
#include <memory>

using namespace std;

class Controller {
    friend class Repo;

public:
    /*
     * Constructor pentru clasa Controller.
     * Primeste ca parametru clasa un obiect din clasa Repo.
     */
    explicit Controller(RepoAbs& repository, Validator& val): repository{repository}, val{val}{};

    /*
     * Daca exista alt Controller deschis, se sterge.
     * Pentru a asigura buna functionare a programului(fara duplicate).
     */
    //Controller(const Controller& ot) = delete;

    /*
     * Dupa ce datele sunt validate, construieste obiectul Product, si il adauga in memorie.
     * @param: numele produsuli, numele producatorului, tipul si pretul
     * @return: true - daca datele sunt valide si produsul a fost adaugat cu succes.
     *          false - daca datele nu sunt valide si produsul nu a fost adaugat cu succes.
     */
    bool add(const string& name, const string& producer, const string& type, float price);

    /*
     * Dupa ce datele sunt validate, acceseaza produsul prin pozitia furnizata ca parametru, si il modifica
     * @param: pozitia pe care se afla elementul modificat, numele nou, producatorul nou, tipul nou si pretul nou
     * @return: true- daca pozitia si datele sunt valide, iar produsul a putut fi modificat
     *          false- daca pozitia sau datele furnizate nu respecta cerintele, sau nu exista produse in lista.
     */
    bool update(int position, const string& nName, const string& pProducer, const string& tType, float pPrice);

    /*
     * Dupa ce pozitia este verificata, se sterge elementul din memorie.
     * @param: pozitia pe care se afla elementul
     * @return: true- daca pozitia este valida, iar produsul a putut fi sters
     *          false- daca pozitia nu respecta cerintele, sau nu exista produse in lista.
     */
    bool _delete(int position);

    /*
     * Filtreaza produsele din lista dupa pret.
     * @param: counter- cate elemente respecta cerinta de filtrare
     *         pos- vector de pozitii pentru produsele care respecta cerinta de filtrare
     *         price- pretul dupa care se filtreaza elementele
     *         direction- true: se afiseaza produsele sub pretul dat
     *                    false: se afiseaza produsele peste pretul dat
     * @return: counter
     */
    int filterPrice(size_t counter, int pos[100], float price, bool direction);

    /*
     * Filtreaza produsele din lista dupa prima litera a numelui/producatorului.
     * @param: counter- cate elemente respecta cerinta de filtrare
     *         pos- vector de pozitii pentru produsele care respecta cerinta de filtrare
     *         letter- litera dupa care se filtreaza elementele
     *         nameProd- true: se afiseaza produsele cu numele care incepe cu litera data
     *                    false: se afiseaza produsele cu producatorul care incepe cu litera data
     * @return: counter
     */
    int filterName(size_t counter, int pos[100], char letter, bool nameProd);

    /*
     * Sorteaza produsele dupa pret, crescator/descrescator.
     * @param: direction- true: se sorteaza crescator
     *                    false: se sorteaza descrescator
     */
    void sortPrice(bool direction);

    /*
     * Sorteaza produsele dupa nume/tip, crescator/descrescator.
     * @param: direction- true: se sorteaza crescator
     *                    false: se sorteaza descrescator
     *         nameType- true: se sorteaza dupa nume
     *                   false: se sorteaza dupa tip
     */
    void sortName(bool direction, bool nameType);

    /*
     * Sorteaza produsele de acelasi tip, dupa nume.
     * @param: direction- true: se sorteaza crescator
     *                    false: se sorteaza descrescator
     */
    void sortNamePart2(bool direction);
    /*
     * Adauga in memorie produse standard.
     */
    void addStandard();

    /*
     * Returneaza toate produsele adaugate.
     * @return: vector
     */
    vector<Product> getAllProducts();

    /*
     * Deletes all the products.
     */
    void deleteAllProducts();

    /*
     * Returneaza cate produse au fost adaugate(numarul)
     * @return: numarul de produse
     */
    int numberProducts();

    Validator& val;

    const Product& searchByName(const string& search);

    const Product& searchByPosition(int position);

    float totalProductCost();

    unordered_map<string, int> createProductMap();

    void undo();

private:
    RepoAbs& repository;

    vector<unique_ptr<UndoAction>> undoActionList;
};

#endif //SERVICE_H
