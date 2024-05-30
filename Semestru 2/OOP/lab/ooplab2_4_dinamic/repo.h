#ifndef REPO_H
#define REPO_H
#include "cheltuiala.h"

typedef struct {

    Cheltuiala *cht;

    int lg; //lungimea listei

    int cp; //capacitatea listei

}ListaCheltuieli;
///Lista de cheltuieli, care contine un vector si lungimea sa

///functii
ListaCheltuieli creazaVid();
///Se creeaza o lista vida, se seteaza lungimea la 0, si se returneaza lista

void adaugaCh(ListaCheltuieli* list, Cheltuiala* cht);
///se adauga la sfarsitul listei o cheltuiala
///se mareste lungimea listei cu 1
///conditii: lista sa existe, si cheltuiala sa fie creata(si valida)

void modificareCh (ListaCheltuieli* list, int numar_ch, int zi_n, float suma_n, const char* tip_n);
///se modifica cheltuiala de pe pozitia numar_ch, cu datele din antet, introduse de utilizator
///conditii: sa existe cheltuiala aleasa, iar datele introduse sa fie validate

void stergeCh(ListaCheltuieli* list, int pozitie);
///sterge o cheltuiala de pe o pozitie anume in lista
///se micsoreaza lungimea listei cu 1
///conditii: sa existe cheltuiala si pozitia sa nu fie mai mare decat lungimea listei

void distrugeLista(ListaCheltuieli *list);
///se distruge lista, se elibereaza
///conditii: sa existe lista data ca parametru

void redim_list(ListaCheltuieli* list);
///redimensioneaza vectorul dinamic de cheltuieli
///daca se ajunge la capacitatea maxima, se dubleaza
///conditii: sa existe lista precedenta de cheltuieli

#endif // REPO_H