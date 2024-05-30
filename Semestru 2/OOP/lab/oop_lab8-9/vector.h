#pragma once

#include "domain.h"
typedef Product Element;

class Iterator;

template <typename Element>
class DynamicArray
{
friend class Iterator;
public:
    /*
    Constructor default
    Alocam loc pentru 5 elemente
    */
    DynamicArray(): cp{ 20 }, elems{ new Element[cp] }, lg{ 0 } {};

    /*
    Constructor de copiere
    */
    DynamicArray(const DynamicArray& ot){

        elems = new Element[ot.cp];

        for ( int i = 0; i < ot.lg; i ++ ) {

            elems[i] = ot.elems[i];
        }
        lg = ot.lg;

        cp = ot.cp;

    }; //rule of 3

    /*
    Destructor
    */
    ~DynamicArray(){

        delete[] elems;
    };

    /*
      Operator assgnment
      elibereaza ce era in obiectul curent (this)
      aloca spatiu pentru elemente
      copieaza elementele din ot in this
    */
    DynamicArray& operator=(const DynamicArray& ot) {

        if (this == &ot)  return *this;

        delete[] elems;

        elems = new Element[ot.cp];

        for (int i = 0; i < ot.lg; i++)

            elems[i] = ot.elems[i];

        lg = ot.lg;

        cp = ot.cp;

        return *this;
    };


    /*
     Move constructor
     Apelat daca construim un nou vector dintr-un r-value (ex temporary, expresie de la return)
     Obiectul ot nu se mai foloseste astfel se poate refolosi interiorul lui
    */
    DynamicArray(DynamicArray&& ot)  noexcept {

        elems = ot.elems;

        lg = ot.lg;

        cp = ot.cp;

        ot.elems = nullptr;

        ot.lg = 0;

        ot.cp = 0;
    };

    /*
    Move assignment
    Similar cu move constructor
    Folosit la assignment
    */
    DynamicArray& operator=(DynamicArray&& ot) noexcept {

        if (this == &ot) return *this;

        delete[] elems;

        elems = ot.elems;

        lg = ot.lg;

        cp = ot.cp;

        ot.elems = nullptr;

        ot.lg = 0;

        ot.cp = 0;

        return *this;
    };

    //adauga la sfarsit
    void add(const Element& el){

        ensureCapacity();

        elems[lg++] = el;
    };

    //sterge un element
    void erase(int position) {
        if (position < 0 || position >= lg) return;

        for (int i = position; i < lg - 1; ++i)

            elems[i] = elems[i + 1];

        --lg;
    }

    //returneaza elementul de pe o pozitie anume
    Element& get(int poz) const {

        return elems[poz];
    };

    //adauga un element pe o pozitie anume
    void set(int poz, const Element& el) {

        elems[poz] = el;
    };

    //lungimea vectorului
    int size() const noexcept {

        return lg;
    };

    Element* allElems() const {

        return this->elems;
    };


private:
    int lg;//numar elemente

    int cp;//capacitate

    Element* elems;//elemente

    void ensureCapacity() {

        if (lg < cp)  return;

        cp *= 2;

        Element* aux = new Element[cp];

        for (int i = 0; i < lg; i++)

            aux[i] = elems[i];

        delete[] elems;

        elems = aux;
    };
};

class Iterator {

private:
    const DynamicArray<Element>& v;

    int poz = 0;

public:
    explicit Iterator(const DynamicArray<Element>& v) noexcept: v{ v }{};

    Iterator(const DynamicArray<Element>& v, int poz)noexcept: v{ v }, poz{ poz } {};

    bool valid()const noexcept {

        return poz < v.lg;
    };

    Element& element() const noexcept {

        return v.elems[poz];
    };

    void next() noexcept {

        poz++;
    };

    Element& operator*() const {

        return element();
    };

    Iterator& operator++() {

        next();

        return *this;
    };

    bool operator==(const Iterator& ot) const noexcept {

        return poz == ot.poz;
    };

    bool operator!=(const Iterator& ot) const noexcept {

        return !(*this == ot);
    };
};

