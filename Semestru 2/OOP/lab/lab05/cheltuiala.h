#ifndef CHELTUIALA_H
#define CHELTUIALA_H
#include <stdlib.h>
#include "lista.h"

typedef void* ElemType;

typedef struct
{
    char* zi;
    int suma;
    char* tip;
}Cheltuiala;

Cheltuiala* Create(char* zi, int suma, char* tip );

void eliminare(void* element);

ElemType copyCheltuiala(ElemType element);
#endif //CHELTUIALA_H
