#include <iostream>
#include "VectorDinamic.h"
#include "IteratorVectorDinamic.h"
#include "TestExtins.h"
#include "TestScurt.h"

using namespace std;

void meniu() {

    VectorDinamic v(2);

    v.adaugaSfarsit(10);
    v.adaugaSfarsit(12);
    v.adaugaSfarsit(13);
    v.adaugaSfarsit(15);
    v.adaugaSfarsit(18);
    v.adaugaSfarsit(16);

    IteratorVectorDinamic i = v.iterator();

    while ( i.valid() )
    {
        cout << i.element() << ' ';

        i.urmator();
    }
}

int main() {

 	testAll();
 	testAllExtins();
    testUnice();
    meniu();
	cout<<"End";
    return 0;
}
