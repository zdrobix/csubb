#include "IteratorLP.h"
#include "Lista.h"
#include <stdexcept>

using namespace std;

//Complexitate = theta(1)
IteratorLP::IteratorLP(const Lista& l):lista(l) {

    prim();
}

//Complexitate = theta(1)
void IteratorLP::prim(){

    curent = lista.first;
}

//Complexitate = theta(1)
void IteratorLP::urmator(){

    if ( valid() )

        curent = curent->next;

    else throw out_of_range("Invalid iterator");
}

//Complexitate = theta(1)
bool IteratorLP::valid() const{

    return curent != nullptr;
}

//Complexitate = theta(1)
TElem IteratorLP::element() const{

    if ( valid() )

        return curent->value;

    else throw out_of_range("Invalid iterator");
}
