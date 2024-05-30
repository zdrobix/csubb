#ifndef TESTE_H
#define TESTE_H
#include <assert.h>
#include <string.h>
#include <stdio.h>
#include "actiuni.h"
#include "teste.h"
#include "lista.h"


void test_adaugare()
{
    lista* lista_cheltuieli = creareVida(10);
    adaugare(lista_cheltuieli,"ziua1", 500, "mancare");
    Cheltuiala* cheltuiala = (Cheltuiala*)lista_cheltuieli->elems[0];
    assert(cheltuiala->suma == 500);
    distrugere(lista_cheltuieli, eliminare);

    lista* lista_cheltuieli2 = creareVida(2);
    adaugare(lista_cheltuieli2,"ziua1", 500, "mancare");
    adaugare(lista_cheltuieli2,"ziua1", 500, "mancare");
    adaugare(lista_cheltuieli2,"ziua1", 500, "mancare");
    adaugare(lista_cheltuieli2,"ziua1", 500, "mancare");
    assert(lista_cheltuieli2->max_size == 27);
    distrugere(lista_cheltuieli2, eliminare);
}

void test_modificare()
{

    lista* lista_cheltuieli = creareVida(10);
    adaugare(lista_cheltuieli,"ziua1", 500, "mancare");
    adaugare(lista_cheltuieli,"ziua2", 800, "transport");
    modificare(lista_cheltuieli,2,"ziua4",800,"mancare");
    Cheltuiala* cheltuiala = (Cheltuiala*)lista_cheltuieli->elems[1];
    assert(strcmp(cheltuiala->zi,"ziua4") == 0);
    distrugere(lista_cheltuieli, eliminare);
}

void test_stergere()
{

    lista* lista_cheltuieli = creareVida(10);
    adaugare(lista_cheltuieli,"ziua1", 500, "mancare");
    adaugare(lista_cheltuieli,"ziua2", 800, "transport");
    stergere(lista_cheltuieli,1);
    assert(lista_cheltuieli->numar == 1);
    distrugere(lista_cheltuieli, eliminare);
}

void test_filtrare()
{

    int poz_test[30];
    lista* lista_cheltuieli = creareVida(10);
    adaugare(lista_cheltuieli,"ziua1", 500, "mancare");
    adaugare(lista_cheltuieli,"ziua2", 800, "transport");
    adaugare(lista_cheltuieli,"ziua3", 500, "transport");
    assert(filtrare(lista_cheltuieli,500,poz_test) == 2);
    assert(poz_test[0] == 0);
    assert(poz_test[1] == 2);
    assert(filtrare_caracter(lista_cheltuieli, 'a', poz_test) == 3);
    assert(filtrare_caracter(lista_cheltuieli, 't', poz_test) == 2);
    assert(filtrare_caracter(lista_cheltuieli, 'm', poz_test) == 1);

    distrugere(lista_cheltuieli, eliminare);
}

void test_filtrare_tip()
{

    int poz_test[30];
    lista* lista_cheltuieli = creareVida(10);
    adaugare(lista_cheltuieli,"ziua1", 500, "mancare");
    adaugare(lista_cheltuieli,"ziua2", 800, "transport");
    adaugare(lista_cheltuieli,"ziua3", 500, "transport");
    assert(filtrare_tip(lista_cheltuieli,"transport",poz_test) == 2);
    assert(poz_test[0] == 1);
    assert(poz_test[1] == 2);
    distrugere(lista_cheltuieli, eliminare);
}

void test_filtrare_zi()
{

    int poz_test[30];
    lista* lista_cheltuieli = creareVida(10);
    adaugare(lista_cheltuieli,"ziua1", 500, "mancare");
    adaugare(lista_cheltuieli,"ziua2", 800, "transport");
    adaugare(lista_cheltuieli,"ziua3", 500, "transport");
    assert(filtrare_zi(lista_cheltuieli,"ziua3",poz_test) == 1);
    assert(poz_test[0] == 2);
    distrugere(lista_cheltuieli, eliminare);

}

void test_ordonare()
{
    lista* lista_cheltuieli = creareVida(10);
    adaugare(lista_cheltuieli,"ziua1", 800, "mancare");
    adaugare(lista_cheltuieli,"ziua2", 300, "transport");
    adaugare(lista_cheltuieli,"ziua3", 500, "transport");
    ordonare(lista_cheltuieli);
    Cheltuiala* cheltuiala1 = (Cheltuiala*)lista_cheltuieli->elems[0];
    Cheltuiala* cheltuiala2 = (Cheltuiala*)lista_cheltuieli->elems[1];
    Cheltuiala* cheltuiala3 = (Cheltuiala*)lista_cheltuieli->elems[2];
    assert(strcmp(cheltuiala1->zi,"ziua2") == 0);
    assert(strcmp(cheltuiala2->zi,"ziua3") == 0);
    assert(strcmp(cheltuiala3->zi,"ziua1") == 0);
    distrugere(lista_cheltuieli, eliminare);

    lista* lista_cheltuieli2 = creareVida(10);
    adaugare(lista_cheltuieli2,"ziua1", 800, "mancare");
    adaugare(lista_cheltuieli2,"ziua2", 300, "transport");
    adaugare(lista_cheltuieli2,"ziua3", 500, "transport");

    sort(lista_cheltuieli2, cmp_suma);
    cheltuiala1 = (Cheltuiala*)lista_cheltuieli2->elems[0];
    cheltuiala2 = (Cheltuiala*)lista_cheltuieli2->elems[1];
    cheltuiala3 = (Cheltuiala*)lista_cheltuieli2->elems[2];
    assert(strcmp(cheltuiala1->zi,"ziua2") == 0);
    assert(strcmp(cheltuiala2->zi,"ziua3") == 0);
    assert(strcmp(cheltuiala3->zi,"ziua1") == 0);

    sort(lista_cheltuieli2, cmp_zi);
    cheltuiala1 = (Cheltuiala*)lista_cheltuieli2->elems[0];
    cheltuiala2 = (Cheltuiala*)lista_cheltuieli2->elems[1];
    cheltuiala3 = (Cheltuiala*)lista_cheltuieli2->elems[2];
    assert(strcmp(cheltuiala1->zi,"ziua3") == 0);
    assert(strcmp(cheltuiala2->zi,"ziua2") == 0);
    assert(strcmp(cheltuiala3->zi,"ziua1") == 0);

    sort(lista_cheltuieli2, cmp_tip);
    cheltuiala1 = (Cheltuiala*)lista_cheltuieli2->elems[0];
    cheltuiala2 = (Cheltuiala*)lista_cheltuieli2->elems[1];
    cheltuiala3 = (Cheltuiala*)lista_cheltuieli2->elems[2];
    assert(strcmp(cheltuiala1->zi,"ziua2") == 0);
    assert(strcmp(cheltuiala2->zi,"ziua3") == 0);
    assert(strcmp(cheltuiala3->zi,"ziua1") == 0);


    distrugere(lista_cheltuieli2, eliminare);
}

void test_undo(){
    lista* chel = creareVida(10);
    lista* undo = creareVida(10);

    adauga_lista_undo(undo, chel);
    adaugare(chel,"1", 800, "mancare");

    adauga_lista_undo(undo, chel);
    adaugare(chel,"2", 800, "mancare");

    adauga_lista_undo(undo, chel);
    adaugare(chel,"3", 800, "mancare");

    adauga_lista_undo(undo, chel);
    adaugare(chel,"4", 800, "mancare");

    assert(undo->numar == 4);
    assert(chel->numar == 4);
    operatie_undo(undo, chel);
    assert(chel->numar == 3);
    Cheltuiala* cheltuiala = (Cheltuiala*)chel->elems[2];
    assert(strcmp(cheltuiala->zi, "3") == 0);

    distrugere(chel, eliminare);
    for ( int i = undo-> numar - 1 ; i >= 0 ; i -- )
        distrugere(undo->elems[i], eliminare);
    free(undo->elems); //distrugere manuala a listei
    free(undo); //distrugere manuala a listei
    printf(")"); //distrugere manuala a listei
}




#endif //TESTE_H