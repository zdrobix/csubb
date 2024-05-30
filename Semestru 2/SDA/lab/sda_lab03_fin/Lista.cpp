#include <exception>
#include "IteratorLP.h"
#include "Lista.h"
#include <iostream>

using namespace std;

//Complexitate = theta(1)
Lista::Lista() {

    first = nullptr;
}

//Complexitate = theta(n)
int Lista::dim() const {

    IteratorLP i{*this};

    int lg = 0;

    while ( i.valid() ) {

        ++ lg;

        i.urmator();
    }
    return lg;
}

//Complexitate = theta(n)
bool Lista::vida() const {

    return dim() == 0;
}

//Complexitate = theta(1)
IteratorLP Lista::prim() const {

    return IteratorLP(*this);
}

//Complexitate = theta(1)
TElem Lista::element(IteratorLP poz) {

    return poz.element();
}


//Complexitate = O(n)
TElem Lista::sterge(IteratorLP& poz) {

    if (vida()) throw underflow_error("The list is empty!");

    TElem toDelete = poz.element();

    if ( poz.curent == first) {

        Nod* aux = first;

        first = first->next;

        delete aux;
    } else {

        Nod* prec = first;

        while (prec->next != poz.curent) {

            prec = prec->next;
        }
        prec->next = poz.curent->next;

        delete poz.curent;
    }
    poz.urmator();

    return toDelete;
}

//Complexitate = O(n)
IteratorLP Lista::cauta(TElem e) const{

    IteratorLP i{*this};

    while (i.valid()) {

        if (i.element() == e)

            return i;

        i.urmator();
    }
    return IteratorLP{*this, false};
}

//Complexitate = theta(1)
TElem Lista::modifica(IteratorLP poz, TElem e) {

    if (vida()) throw underflow_error("The list is empty!");

    TElem toModify = poz.element();

    poz.curent->value = e;

    return toModify;
}

//Complexitate = theta(1)
void Lista::adauga(IteratorLP& poz, TElem e) {

    if (!poz.valid() and !vida()) throw out_of_range("Invalid iterator!");

    if (vida() or poz.curent == nullptr) adaugaSfarsit(e);

    else {

        Nod* nou = new Nod{e, poz.curent->next};

        poz.curent->next = nou;

        poz.urmator();
    }
}
//Complexitate = theta(1)
void Lista::adaugaInceput(TElem e) {

    Nod* nou = new Nod{e, first};

    first = nou;
}

//Complexitate = O(n)
void Lista::adaugaSfarsit(TElem e) {

    if (vida()) adaugaInceput(e);

    else {

        Nod* last = first;

        while (last->next != nullptr)

            last = last->next;

        Nod* nou = new Nod{e};

        last->next = nou;
    }
}

//Complexitate = theta(n)
Lista::~Lista() {

    while ( first != nullptr) {

        Nod* aux = first;

        first = first->next;

        delete aux;
    }
}


#include <iostream>
void Lista::printAll() {

    IteratorLP i{*this};

    while ( i.valid()) {

        cout << i.element() << " ";

        i.urmator();
    }
}

/*
 * preconditii:
 *  -lista : Lista
 *
 * Algoritm sterge_prim(lista):
 *      aux <- first(lista)
 *      first <- next(first(lista))
 *      @delete aux
 * SfAlg
 *
 * Complexitate = theta(1)
 */
void Lista::sterge_prim() {

    Nod* aux = first;

    first = first->next;

    delete aux;
}

/*
 * preconditii:
 *  -lista : Lista
 *
 * postconditii:
 *  -lista'=/vid
 * Algoritm goleste(lista):
 *      cat timp !vida(lista) executa:
 *          aux <- first
 *          first <- next(first)
 *          @delete aux
 *      sfCatTimp
 * SfAlg
 *
 * Complexitate = theta(n)
 */
void Lista::goleste() {

    while (!this->vida())

        sterge_prim();

}
