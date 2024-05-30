#ifndef LISTA_H
#define LISTA_H
#include "cheltuiala.h"

typedef void* ElemType;

typedef struct{
    ElemType* elems;
    int numar;
    int max_size;
}lista;

//se creaza o lista vida
lista* creareVida(int maxsize);

ElemType get(lista* list, int pozitie);

lista* copieLista(lista* first, ElemType (*copy)(ElemType));

void distrugere(lista* l, void (*elim)(ElemType));

int size(lista* l);

void add(lista* l, ElemType elem);

void delete(lista* l, int poz);

void mod(lista* l, int poz, char* zi, int suma, char* tip);



#endif //LISTA_H
