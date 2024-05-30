#ifndef SDA_LAB03_LISTA_H
#define SDA_LAB03_LISTA_H
#pragma once

typedef int TElem;

class IteratorLP;

class Lista {
private:
    friend class IteratorLP;

    struct Nod {
        TElem value;

        Nod* next;

        explicit Nod(const TElem& e, Nod* x = nullptr) : value(e), next(x) {};
    };

    Nod* first;

public:

    // constructor
    Lista ();

    // returnare dimensiune
    int dim() const;

    // verifica daca lista e vida
    bool vida() const;

    // prima pozitie din lista
    IteratorLP prim() const;

    // returnare element de pe pozitia curenta
    //arunca exceptie daca poz nu e valid
    static TElem element(IteratorLP poz) ;

    // modifica element de pe pozitia poz si returneaza vechea valoare
    //arunca exceptie daca poz nu e valid
    TElem modifica(IteratorLP poz, TElem e);

    // adaugare element la inceput
    void adaugaInceput(TElem e);

    // adaugare element la sfarsit
    void adaugaSfarsit(TElem e);

    // adaugare element dupa o pozitie poz
    //dupa adaugare poz este pozitionat pe elementul adaugat
    //arunca exceptie daca poz nu e valid
    void adauga(IteratorLP& poz, TElem e);

    // sterge element de pe o pozitie poz si returneaza elementul sters
    //dupa stergere poz este pozitionat pe elementul de dupa cel sters
    //arunca exceptia daca poz nu e valid
    TElem sterge(IteratorLP& poz);

    void sterge_prim();

    // cauta element si returneaza prima pozitie pe care apare (sau iterator invalid)
    IteratorLP cauta(TElem e) const;

    //destructor

    ~Lista();

    //afiseaza toate elementele
    void printAll();

    //goleste toate elementele din lista
    void goleste();
};
#endif //SDA_LAB03_LISTA_H