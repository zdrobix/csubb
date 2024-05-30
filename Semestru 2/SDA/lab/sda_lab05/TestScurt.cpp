#include "TestScurt.h"
#include <assert.h>
#include "Matrice.h"
#include <iostream>

using namespace std;

void testAll() { //apelam fiecare functie sa vedem daca exista
	Matrice m(4,4);
	assert(m.nrLinii() == 4);
	assert(m.nrColoane() == 4);
	//adaug niste elemente
	m.modifica(1,1,5);
	assert(m.element(1,1) == 5);
	m.modifica(1,1,6);
	assert(m.element(1,2) == NULL_TELEMENT);
}

/*
void testNou() {
    Matrice matrice(4, 4);
    matrice.modifica(1, 1, 34);
    matrice.modifica(2,1,45);
    matrice.modifica(3,1,3);
    matrice.modifica(2,2,8);

    Matrice other(4,4);
    other.transpusa(matrice);

    assert(other.element(1, 1) == 34);
    assert(other.element(1, 2) == 45);
    assert(other.element(1, 3) == 3);
    assert(other.element(2, 2) == 8);
}*/
