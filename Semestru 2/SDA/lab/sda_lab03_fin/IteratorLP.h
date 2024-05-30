#ifndef SDA_LAB03_ITERATORLP_H
#define SDA_LAB03_ITERATORLP_H

#pragma once
#include "Lista.h"

class IteratorLP{
    friend class Lista;
private:

    //constructorul primeste o referinta catre Container
    //iteratorul va referi primul element din container

    explicit IteratorLP(const Lista& lista);

    //constructor pentru un iterator invalid
    IteratorLP(const Lista& lista, bool valid) : lista(lista), curent(nullptr) {};

    //contine o referinta catre containerul pe care il itereaza
    const Lista& lista;

    //nodul curent
    Lista::Nod* curent{};

public:

    //reseteaza pozitia iteratorului la inceputul containerului
    void prim();

    //muta iteratorul in container
    // arunca exceptie daca iteratorul nu e valid
    void urmator();

    //verifica daca iteratorul e valid (indica un element al containerului)
    bool valid() const;

    //returneaza valoarea elementului din container referit de iterator
    //arunca exceptie daca iteratorul nu e valid
    TElem element() const;

};

#endif //SDA_LAB03_ITERATORLP_H