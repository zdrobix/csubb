#ifndef ACTIUNI_H
#define ACTIUNI_H
#include "actiuni.h"
#include <string.h>
#include "cheltuiala.h"
#include "lista.h"



void adaugare( lista* l,char* zi, int suma, char* tip)
{
    Cheltuiala* element = Create(zi,suma,tip);

    add(l,element);
}

void modificare( lista* l,int nr, char* zi, int suma, char* tip)
{
    mod(l,nr-1,zi,suma,tip);
}

void stergere( lista* l, int nr)
{
    /*
     nr = nr-1;
     for(int j = nr; j < l->numar_cheltuieli ; j ++)
         l->cheltuieli[j] = l->cheltuieli[j+1];
     l->numar_cheltuieli = l->numar_cheltuieli -1;
     */
    delete(l,nr-1);
}

int filtrare( lista* l,int suma,int poz[30])
{

    int cont = 0;
    for(int i = 0; i < l->numar; i ++) {
        Cheltuiala* cheltuiala = (Cheltuiala*)l->elems[i];
        if (cheltuiala->suma == suma) {
            poz[cont] = i;
            cont += 1;
        }
    }
    return cont;
}

int filtrare_tip( lista* l,char* tip,int poz[30])
{

    int cont = 0;
    for(int i = 0; i < l->numar; i ++) {
        Cheltuiala* cheltuiala = (Cheltuiala*)l->elems[i];
        if (strcmp(cheltuiala->tip, tip) == 0) {
            poz[cont] = i;
            cont += 1;
        }
    }
    return cont;
}

int filtrare_zi( lista* l,char* zi,int poz[30])
{

    int cont = 0;
    for(int i = 0; i < l->numar; i ++) {
        Cheltuiala* cheltuiala = (Cheltuiala*)l->elems[i];
        if (strcmp(cheltuiala->zi, zi) == 0) {
            poz[cont] = i;
            cont += 1;
        }
    }
    return cont;
}

int filtrare_caracter(lista* l, char ch, int poz[30])
{
    int cont = 0;
    for(int i = 0; i < l->numar; i ++) {
        Cheltuiala* cheltuiala = (Cheltuiala*)l->elems[i];
        if (strchr(cheltuiala->zi, ch) || strchr(cheltuiala->tip, ch))  {
            poz[cont] = i;
            cont += 1;
        }
    }
    return cont;
}

void ordonare( lista* l)
{

    for(int i = 0; i < l->numar-1; i ++)
        for(int j = i+1; j < l->numar; j ++) {
            Cheltuiala* cheltuiala1 = (Cheltuiala*)l->elems[i];
            Cheltuiala* cheltuiala2 = (Cheltuiala*)l->elems[j];
            if (cheltuiala1->suma > cheltuiala2->suma) {
                Cheltuiala aux;
                aux = *cheltuiala1;
                *cheltuiala1 = *cheltuiala2;
                *cheltuiala2 = aux;
            }
        }
}

int cmp_suma(Cheltuiala a, Cheltuiala b)
{
    if(a.suma > b.suma)
        return 1;
    return 0;
}

int cmp_zi(Cheltuiala a, Cheltuiala b)
{
    if(strcmp(a.zi, b.zi) <= 0)
        return 1;
    return 0;
}

int cmp_tip(Cheltuiala a, Cheltuiala b)
{
    if(strcmp(a.tip, b.tip) <= 0)
        return 1;
    return 0;
}

void sort(lista* l, int (*cmp)(Cheltuiala,Cheltuiala))
{
    for(int i=0;i < l->numar-1; i ++)
        for(int j = i+1;j < l->numar;j++) {
            Cheltuiala *cheltuiala1 = (Cheltuiala *) l->elems[i];
            Cheltuiala *cheltuiala2 = (Cheltuiala *) l->elems[j];
            if (cmp(*cheltuiala1, *cheltuiala2) == 1) {
                Cheltuiala aux;
                aux = *cheltuiala1;
                *cheltuiala1 = *cheltuiala2;
                *cheltuiala2 = aux;
            }
        }
}

void adauga_lista_undo(lista* lista_undo,lista*  lista_cheltuieli)
{
    lista* copie = copieLista(lista_cheltuieli, copyCheltuiala);
    add(lista_undo, copie);
}

void operatie_undo(lista* lista_undo, lista* lista_cheltuieli)
{
    for ( int i = lista_cheltuieli->numar - 1; i >= 0; i -- )
        delete(lista_cheltuieli, i);

    lista* ultima_lista_undo = (lista*)lista_undo->elems[lista_undo->numar - 1];
    lista* copie_ultima_lista_undo = copieLista(ultima_lista_undo, copyCheltuiala);

    free(lista_cheltuieli->elems);
    lista_cheltuieli->numar = copie_ultima_lista_undo->numar;
    lista_cheltuieli->max_size = copie_ultima_lista_undo->max_size;
    lista_cheltuieli->elems = copie_ultima_lista_undo->elems;
    free(copie_ultima_lista_undo);
}

#endif //ACTIUNI_H
