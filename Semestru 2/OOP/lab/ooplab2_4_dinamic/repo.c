#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "repo.h"
#include "cheltuiala.h"

ListaCheltuieli creazaVid() {

    ListaCheltuieli listaCreata;

    listaCreata.lg = 0;

    listaCreata.cp = 50;

    listaCreata.cht = (Cheltuiala*)malloc(listaCreata.cp * sizeof(Cheltuiala));

    printf("(");

    return listaCreata;
}

void distrugeLista(ListaCheltuieli* list) {

    if (list == NULL) return;

    for (int i = 0; i < list->lg; i++) {

        eliminaCh(&list->cht[i]);
    }
    list->lg = 0;

    free(list->cht);

    printf(")");
}

void redim_list(ListaCheltuieli* list) {

    Cheltuiala *cht_nou = (Cheltuiala*)malloc(2* list->cp * sizeof (Cheltuiala));

    if (cht_nou == NULL) return;

    for ( int i = 0 ; i < list->lg; i ++ )

        cht_nou[i] = list->cht[i];

    list->cp = 2 * list->cp;

    free(list->cht);

    list->cht = cht_nou;

}

void adaugaCh(ListaCheltuieli* list, Cheltuiala* cht) {

    if (list->lg >= list->cp)

        redim_list(list);

    list->cht[list->lg] = *cht;

    list->lg++;
}

void modificareCh (ListaCheltuieli* list, int pozitie, int zi_n, float suma_n, const char* tip_n) {

    list->cht[pozitie].zi = zi_n;

    list->cht[pozitie].suma = suma_n;

    strcpy(list->cht[pozitie].tip, tip_n);
}

void stergeCh(ListaCheltuieli* list, int pozitie) {

    eliminaCh(&(list->cht[pozitie]));

    for ( int i = pozitie + 1; i < list->lg; i ++ )

        list->cht[i - 1] = list->cht[i];

    list->lg = list->lg - 1;
}