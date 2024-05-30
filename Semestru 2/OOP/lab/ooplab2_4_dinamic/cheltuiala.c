#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "cheltuiala.h"


Cheltuiala* creeazaCh(int zi, float suma, const char* tip) {

    Cheltuiala* chtl = malloc(sizeof(Cheltuiala));

    int nrT = (int)strlen(tip) + 1;

    chtl->tip = malloc(nrT * sizeof (char));

    strcpy(chtl->tip, tip);

    chtl->zi = zi;

    chtl->suma = suma;

    printf(".");

    return chtl;

}

int validareCh(int zi, float suma, const char *tip) {

    if ( zi <= 0 || zi > 31  )

        return 0;

    if ( suma <= 0 )

        return 0;

    if ( strcmp(tip, "") == 0 )

        return 0;

    return 1;
}

void eliminaCh(Cheltuiala *cht) {

    cht -> zi = 0;

    cht -> suma = 0;

    free(cht->tip);

    printf("'");
    //free(cht);
}