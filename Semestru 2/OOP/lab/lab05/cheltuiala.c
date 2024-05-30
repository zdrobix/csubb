#include "cheltuiala.h"
#include "lista.h"
#include "cheltuiala.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

Cheltuiala* Create(char* zi, int suma, char* tip )
{
    Cheltuiala* rez = malloc(sizeof(Cheltuiala));
    rez->tip = malloc(sizeof(char) * 20);
    strcpy(rez->tip, tip);
    rez->zi = malloc(sizeof(char) * 15);
    strcpy(rez->zi, zi);
    rez->suma = suma;
    printf(".");
    return rez;
}

void eliminare(void* element)
{
    Cheltuiala* c = (Cheltuiala*)element;
    free(c->tip);
    free(c->zi);
    free(c);
    printf("'");
}

ElemType copyCheltuiala(ElemType element)
{
    Cheltuiala* c = (Cheltuiala*)element;
    return (ElemType)Create(c->zi, c->suma, c->tip);
}
