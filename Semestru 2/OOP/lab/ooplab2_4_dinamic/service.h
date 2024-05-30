
#ifndef SERVICE_H
#define SERVICE_H
#include "repo.h"

int adaugare_cheltuiala(ListaCheltuieli* list, int zi, float suma, const char* tip);
///adauga o cheltuiala in lista primita ca parametru
///parametrii: datele cheltuielii, si lista in care sa fie adaugata
///conditii: cheltuiala creata cu datele specificate sa fie validata

int modificare_cheltuiala(ListaCheltuieli* list, int pozitie, int zi, float suma, const char* tip);
///modifica o cheltuiala in lista primita ca parametru
///parametrii: datele cheltuielii noi, pozitia in lista, si lista in care sa fie adaugata
///conditii: cheltuiala creata cu datele specificate sa fie validata

int sterge_cheltuiala(ListaCheltuieli* list, int pozitie);
///sterge o cheltuiala din lista primita ca parametru
///mai are ca parametru pozitia cheltuielii in lista
///conditii: sa existe cheltuiala

ListaCheltuieli* filtrareCh( ListaCheltuieli* list, ListaCheltuieli* lista_filtrata , int zi, float suma, const char* tip);
///filtreaza lista de cheltuieli dupa optiunile dorite de utilizator
///daca se primeste o lista vida se atrage atentia
///conditii: doar un parametru dintre zi/suma/tip sa fie ales

float comparaCh(Cheltuiala cht1,Cheltuiala cht2, int zi);
///returneaza diferenta dintre ziua/suma cht1 cht2
///poate fi interpretata de functia mama
///conditii: cht1, cht2 sa fie cheltuieli

void sorteazaCh(ListaCheltuieli* list, int directie, int zi);
///sorteaza lista de cheltuieli dupa optiunile dorite de utilizator
///se primesc ca parametrii directia de sortare si criteriul
///nu se returneaza nimic, se sorteaza lista primita ca parametru

void sortare_pointer_comparatie(ListaCheltuieli * list, int (*cmp)(Cheltuiala,Cheltuiala));
///sorteaza lista de cheltuieli

int compara_suma(Cheltuiala a, Cheltuiala b);

int compara_zi(Cheltuiala a, Cheltuiala b);

#endif //SERVICE_H
