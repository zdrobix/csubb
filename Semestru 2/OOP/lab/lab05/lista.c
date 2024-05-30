#include "lista.h"
#include <stdio.h>

lista* creareVida(int maxsize){
    lista *list = malloc(sizeof(lista));
    list->max_size = maxsize;
    list->elems = malloc(sizeof (ElemType)*maxsize);
    list->numar = 0;
    printf("(");
    return list;
}

ElemType get(lista* list, int pozitie)
{
    return list->elems[pozitie];
}

lista* copieLista(lista* first, ElemType (*copy)(ElemType))
{
    lista* copie = creareVida(first->max_size);
    for ( int i = 0; i < first->numar; i ++ ) {
        ElemType el = get(first, i);
        add(copie, copy(el));
    }
    return copie;
}

void distrugere(lista* l, void(*elim)(ElemType) )
{
    for (int i = 0; i < l->numar ; i++)
    {
        elim(l->elems[i]);
    }
    free(l->elems);

    l->numar = 0;
    l->max_size = 0;
    free(l);
    printf(")");
}




int size(lista* l)
{
    return l->numar;
}

void add(lista* l, ElemType elem)
{
    if (l->numar >= l->max_size)
    {
        ElemType* p = malloc(sizeof(ElemType) * (l->max_size + 25));
        for (int i = 0; i < size(l); i++)
        {
            p[i] = l->elems[i];
        }
        free(l->elems);
        l->elems = p;
        l->max_size += 25;
    }

    l->elems[l->numar] = elem;
    l->numar = l->numar + 1;

}
#include <stdio.h>
void delete(lista* l, int poz)
{
    if (poz < 0 || poz >= l->numar ) return;
    eliminare(l->elems[poz]);
    if (poz == l->numar - 1) {
        l->numar = l->numar - 1;
        return;
    }
    for(int i = poz; i < size(l) - 1; i++)
    {
        //eliminare(&l->cheltuieli[i]);
        //l->cheltuieli[i] = Create(l->cheltuieli[i+1].zi, l->cheltuieli[i + 1].suma, l->cheltuieli[i + 1].tip);
        l->elems[i] = l->elems[i+1];

    }
    l->numar--;
    //eliminare(&l->cheltuieli[l->numar_cheltuieli]);
}


void mod(lista* l, int poz, char* zi, int suma, char* tip)
{
    Cheltuiala* element = Create(zi,suma,tip);
    eliminare(l->elems[poz]);
    l->elems[poz] = element;

}



