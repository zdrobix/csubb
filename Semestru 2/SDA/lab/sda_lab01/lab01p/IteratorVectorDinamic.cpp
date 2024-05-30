#include "IteratorVectorDinamic.h"
#include "VectorDinamic.h"


IteratorVectorDinamic::IteratorVectorDinamic(const VectorDinamic& _v) : v(_v) {
	/*
	 * primeste un vector dinamic ca parametru si arata spre un prim element din acel vector dinamic
	 * curent ia valoarea 0
	 *
	 * theta(1)
	 * */
    curent = 0;
}



void IteratorVectorDinamic::prim() {
	/*
	 * arata spre un prim element dintr-un vector dinamic
	 * curent ia valoarea 0
	 *
	 * theta(1)
	 * */
    curent = 0;
}



bool IteratorVectorDinamic::valid() const{
	/*
	 * returneaza True daca nu s-a ajuns la sfarsitul vectorului dinamic
	 * verifica daca curent este mai mic decat dimensiunea vectorului
	 * @return True/False
	 *
	 * theta(1)
	 * */
    if (curent < v.dim() )
        return true;
	return false;
}



TElem IteratorVectorDinamic::element() const{
	/*
	 * @return elementul curent din vectorul dinamic
	 *
	 * theta(1)
	 * */
    return v.e[curent];
}



void IteratorVectorDinamic::urmator() {
	/*
	 * se trece la urmatorul element din vector
	 *
	 * theta(1)
	 * */
    curent ++;
}

