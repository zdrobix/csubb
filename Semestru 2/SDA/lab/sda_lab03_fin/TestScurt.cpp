#include "TestScurt.h"

#include <assert.h>
#include <exception>


#include "Lista.h"
#include "IteratorLP.h"



using namespace std;

void testAll(){
    Lista lista = Lista();
    assert(lista.dim() == 0);
    assert(lista.vida());

    lista.adaugaSfarsit(1);
    assert(lista.dim() == 1);
    assert(!lista.vida());

    IteratorLP it = lista.cauta(1);
    assert(it.valid());
    assert(it.element() == 1);
    it.urmator();
    assert(!it.valid());
    it.prim();
    assert(it.valid());
    assert(it.element() == 1);

    assert(lista.sterge(it) == 1);
    assert(lista.dim() == 0);
    assert(lista.vida());

    lista.adaugaInceput(1);
    assert(lista.dim() == 1);
    assert(!lista.vida());

    //teste functionalitate noua
    lista.adaugaInceput(1);
    lista.adaugaInceput(1);
    lista.goleste();
    assert(lista.vida());

    lista.adaugaInceput(1);
    lista.adaugaInceput(1);
    lista.adaugaInceput(1);
    lista.adaugaInceput(1);
    lista.adaugaInceput(1);
    lista.adaugaInceput(1);
    assert(!lista.vida());
    assert(lista.dim()==6);
    lista.goleste();
    assert(lista.vida());
}
