#pragma once
#ifndef UNTITLED_ACTIUNI_H
#define UNTITLED_ACTIUNI_H
#include "lista.h"

// se adauga un element in lista de cheltuieli
//parametrii:pointer catre un struct ce contine lista de cheltuieli si numarul de cheltuieli din lista,
//zi-sir de caractere, suma- valoare intreaga, tip-sir de caractere
void adaugare( lista* l,char* zi, int suma, char* tip);

// se modifica un element din lista cu ajutorul parametrului nr
//parametrii:pointer catre un struct ce contine lista de cheltuieli si numarul de cheltuieli din lista,
//zi-sir de caractere, suma- valoare intreaga, tip-sir de caractere,nr-int care contine pozitia-1 a cheltuielii ce trebuie modificate
void modificare(lista* l,int nr,char* zi, int suma, char* tip);

// se sterge un element din lista cu ajutorul parametrului nr
//parametrii:pointer catre un struct ce contine lista de cheltuieli si numarul de cheltuieli din lista,
//zi-sir de caractere, suma- valoare intreaga, tip-sir de caractere,nr-int care contine pozitia-1 a cheltuielii ce trebuie sterse
void stergere( lista* l, int nr);

// se filtreaza lista de cheltuieli in functie de parametrul suma
//se returneaza mnumarul de elemente din vectorul poz
//parametrii:pointer catre un struct ce contine lista de cheltuieli si numarul de cheltuieli din lista,
//suma-nr intreg care este criteriul dupa care se filtreaza, poz- vector de pozitii care retine pozitiile elementelor ce trebuie afisate dupa filtrare
int filtrare( lista* l,int suma,int poz[30]);

// se filtreaza lista de cheltuieli in functie de parametrul suma
//se returneaza mnumarul de elemente din vectorul poz
//parametrii:pointer catre un struct ce contine lista de cheltuieli si numarul de cheltuieli din lista,
//tip-sir de caractere care este criteriul dupa care se filtreaza, poz- vector de pozitii care retine pozitiile elementelor ce trebuie afisate dupa filtrare
int filtrare_tip( lista* l,char* tip,int poz[30]);

// se filtreaza lista de cheltuieli in functie de parametrul suma
//se returneaza mnumarul de elemente din vectorul poz
//parametrii:pointer catre un struct ce contine lista de cheltuieli si numarul de cheltuieli din lista,
//zi-sir de caractere care este criteriul dupa care se filtreaza, poz- vector de pozitii care retine pozitiile elementelor ce trebuie afisate dupa filtrare
int filtrare_zi( lista* l,char* zi,int poz[30]);

//se ordoneaza crescator lista de cheltuieli
//parametrii:pointer catre un struct ce contine lista de cheltuieli si numarul de cheltuieli din lista
void ordonare(lista* l);

void sort(lista* l, int (*cmp)(Cheltuiala,Cheltuiala));

int cmp_suma(Cheltuiala a, Cheltuiala b);
int cmp_zi(Cheltuiala a, Cheltuiala b);
int cmp_tip(Cheltuiala a, Cheltuiala b);

void adauga_lista_undo(lista* lista_undo, lista* lista_cheltuieli);

void operatie_undo(lista* lista_undo, lista* lista_cheltuieli);

int filtrare_caracter(lista* l, char ch, int poz[30]);

#endif //UNTITLED_ACTIUNI_H
