
#include <stdio.h>
#include "validator.h"
#include "actiuni.h"
#include "teste.h"
#include <string.h>
#include "lista.h"
#include "cheltuiala.h"


void meniu()
{
    //interfata cu utilizatorul
    printf("\n1.Adaugarea unui cheltuieli\n");
    printf("2.Modificarea unei cheltuieli\n");
    printf("3.Stergerea unei cheluielii\n");
    printf("4.Filtrare\n");
    printf("5.Ordonat dupa suma(crescator/descrescator)\n");
    printf("0.Undo\n\n");
    printf("6.Iesire\n");
}

void actiune1( lista* l, lista* u)
{
    //interfata cu utilizatorul
    //parametrii:pointer catre un struct ce contine lista de cheltuieli si numarul de cheltuieli din lista
    int suma;
    char zi[15], tip[30];
    printf("Introduceti ziua: ");
    scanf("%s", zi);
    printf("Introduceti suma: ");
    scanf("%d", &suma);
    printf("Introduceti tipul: ");
    scanf("%s", tip);
    int rez = validare(zi, suma, tip);
    if(rez == 1) {
        adauga_lista_undo(u, l);
        adaugare(l, zi, suma, tip);
    }
    for(int i = 0; i < l->numar; i ++)
    {
        Cheltuiala* cheltuiala = (Cheltuiala*)l->elems[i];
        printf("Cheltuiala: ");
        printf("%s ", cheltuiala->zi);
        printf("%d ", cheltuiala->suma);
        printf("%s\n", cheltuiala->tip);
    }
    //*numar_cheltuieli += 1;

}

void actiune2(lista* l, lista* u)
{
    //interfata cu utilizatorul
    //parametrii:pointer catre un struct ce contine lista de cheltuieli si numarul de cheltuieli din lista
    printf("Aceasta este lista cheltuielilor actuale: \n");
    for(int i = 0; i < l->numar; i ++)
    {
        printf("%d) ", i+1);
        Cheltuiala* cheltuiala = (Cheltuiala*)l->elems[i];
        printf("%s ", cheltuiala->zi);
        printf("%d ", cheltuiala->suma);
        printf("%s\n", cheltuiala->tip);
    }
    printf("Introduceti numarul cheltuielii pe care doriti sa o modificati: ");
    int nr;
    scanf("%d", &nr);
    int suma;
    char zi[15], tip[30];
    printf("Introduceti ziua: ");
    scanf("%s", zi);
    printf("Introduceti suma: ");
    scanf("%d", &suma);
    printf("Introduceti tipul: ");
    scanf("%s", tip);
    int rez = validare(zi, suma, tip);
    if(rez == 1)
    {
        adauga_lista_undo(u, l);
        modificare(l,nr,zi,suma,tip);
        for(int i = 0; i < l->numar; i ++)
        {
            Cheltuiala* cheltuiala = (Cheltuiala*)l->elems[i];
            printf("Cheltuiala: ");
            printf("%s ", cheltuiala->zi);
            printf("%d ", cheltuiala->suma);
            printf("%s\n", cheltuiala->tip);
        }
    }
}

void actiune3( lista* l, lista* u)
{
    //interfata cu utilizatorul
    //parametrii:pointer catre un struct ce contine lista de cheltuieli si numarul de cheltuieli din lista
    printf("Aceasta este lista cheltuielilor actuale: \n");
    for(int i = 0; i < l->numar; i ++)
    {
        printf("%d) ", i+1);
        Cheltuiala* cheltuiala = (Cheltuiala*)l->elems[i];
        printf("%s ", cheltuiala->zi);
        printf("%d ", cheltuiala->suma);
        printf("%s\n", cheltuiala->tip);
    }
    printf("Introduceti numarul cheltuielii pe care doriti sa o stergeti: ");
    int nr;
    scanf("%d", &nr);
    if (nr < l->numar || nr >= 0) {
        adauga_lista_undo(u, l);
        stergere(l, nr);
    }
    for(int i = 0; i < l->numar; i ++)
    {
        Cheltuiala* cheltuiala = (Cheltuiala*)l->elems[i];
        printf("Cheltuiala: ");
        printf("%s ", cheltuiala->zi);
        printf("%d ", cheltuiala->suma);
        printf("%s ", cheltuiala->tip);
    }
}

void actiune4( lista* l)
{
    //interfata cu utilizatorul
    //parametrii:pointer catre un struct ce contine lista de cheltuieli si numarul de cheltuieli din lista
    int poz[30];
    int index;
    int suma;
    char tip[30];
    char zi[30];
    printf("Dupa ce se doreste filtrarea: \n");
    printf("1.Filtrare dupa suma\n");
    printf("2.Filtrare dupa tip\n");
    printf("3.Filtrare dupa zi\n");
    printf("4.Filtrare special\n");

    printf("Introduceti o optiune: ");
    int op;
    scanf("%d",&op);

    if(op == 1){
        printf("Introduceti o suma: ");
        scanf("%d", &suma);
        if(suma < 0)
        {
            printf("Suma nu poate fi mai mica decat 0.\n");
        }

        index = filtrare(l, suma,poz);
        if (index == 0 ) printf("Nu exista cheltuieli.\n");
        for(int i = 0; i < index; i ++)
        {
            Cheltuiala* cheltuiala = (Cheltuiala*)l->elems[poz[i]];
            printf("%s ", cheltuiala->zi);
            printf("%d ", cheltuiala->suma);
            printf("%s\n", cheltuiala->tip);
        }}
    if (op == 2)
    {
        printf("Introduceti tipul: ");
        scanf("%s", tip);
        if(strcmp(tip,"mancare") !=0 && strcmp(tip,"transport") !=0 && strcmp(tip,"telefon&internet") !=0 && strcmp(tip,"imbracaminte") !=0 && strcmp(tip,"altele")!=0 )
        {
            printf("Tipul nu face parte din categoriile date.\n");
        }

        index = filtrare_tip(l, tip,poz);
        if (index == 0 ) printf("Nu exista cheltuieli.\n");
        for(int i = 0; i < index; i ++)
        {
            Cheltuiala* cheltuiala = (Cheltuiala*)l->elems[poz[i]];
            printf("%s ", cheltuiala->zi);
            printf("%d ", cheltuiala->suma);
            printf("%s\n", cheltuiala->tip);
        }
    }
    if(op == 3)
    {
        printf("Introduceti o zi: ");
        scanf("%s", zi);
        if(strcmp(zi," ") == 0)
        {
            printf("Ziua nu poate sa fie goala.\n");
        }

        index = filtrare_zi(l, zi,poz);
        if (index == 0 ) printf("Nu exista cheltuieli.\n");
        for(int i = 0; i < index; i ++)
        {
            Cheltuiala* cheltuiala = (Cheltuiala*)l->elems[poz[i]];
            printf("%s ", cheltuiala->zi);
            printf("%d ", cheltuiala->suma);
            printf("%s\n", cheltuiala->tip);
        }
    }
    if(op == 4)
    {
        printf("Filtrare dupa caracter.\nIntroduceti un caracter: ");
        char ch;
        if( scanf(" %c", &ch) !=1 )
            printf("Caracter invalid\n");
        index = filtrare_caracter(l, ch, poz);
        if (index == 0 ) printf("Nu exista cheltuieli.\n");
        for(int i = 0; i < index; i ++)
        {
            Cheltuiala* cheltuiala = (Cheltuiala*)l->elems[poz[i]];
            printf("%s ", cheltuiala->zi);
            printf("%d ", cheltuiala->suma);
            printf("%s\n", cheltuiala->tip);
        }
    }
}

void actiune5( lista* l)
{
    //interfata cu utilizatorul
    //parametrii:pointer catre un struct ce contine lista de cheltuieli si numarul de cheltuieli din lista
    printf("1. Ordonare dupa suma\n2. Ordonare dupa tip\n");
    int optiune;
    printf("Introduceti optiunea: ");
    scanf("%d", &optiune);
    if (optiune == 1)

        ordonare(l);

    else if (optiune == 2) sort(l, cmp_tip);

    else return;

    for(int i = 0; i < l->numar; i ++)
    {
        printf("Cheltuiala: ");
        Cheltuiala* cheltuiala = (Cheltuiala*)l->elems[i];
        printf("%s ", cheltuiala->zi);
        printf("%d ", cheltuiala->suma);
        printf("%s\n", cheltuiala->tip);
    }
}

void actiune6(lista* lista_undo, lista* lista_cheltuieli)
{
    if (lista_undo->numar <= 1) {
        printf("Operatia de undo nu a fost realizata cu succes.");
        return;
    }
    operatie_undo(lista_undo, lista_cheltuieli);
    delete(lista_undo, lista_undo->numar - 1);
    printf("Operatia de undo a fost realizata cu succes. Undo disponibil: %d", lista_undo->numar - 1);
}

void print_all(lista* list)
{
    for ( int i = 0; i < list->numar; i ++ )
    {
        Cheltuiala* cheltuiala1 = (Cheltuiala*)list->elems[i];
        printf("%d) %s, %d, %s\n", i + 1, cheltuiala1->zi, cheltuiala1->suma, cheltuiala1->tip);
    }
}

void adauga_prestabilit(lista* list, lista* undo)
{
    adauga_lista_undo(undo, list);
    adaugare(list, "1", 100, "altele");
    adauga_lista_undo(undo, list);
    adaugare(list, "2", 120, "mancare");
    adauga_lista_undo(undo, list);
    adaugare(list, "3", 12, "altele");
    adauga_lista_undo(undo, list);
    adaugare(list, "4", 546, "mancare");
    adauga_lista_undo(undo, list);
    adaugare(list, "7", 354, "transport");
    adauga_lista_undo(undo, list);
    adaugare(list, "9", 243, "mancare");
    adauga_lista_undo(undo, list);
    adaugare(list, "10", 234, "transport");

}

int main() {
    //apel de teste
    //
    test_adaugare();

    test_modificare();
    test_stergere();
    test_filtrare();
    test_filtrare_tip();
    test_filtrare_zi();
    test_ordonare();
    test_undo();


    lista* lista_cheltuieli = creareVida(10);
    lista* lista_undo = creareVida(100);
    //interfata cu utilizatorul

    while(1)
    {
        meniu();
        int optiune;
        printf("Introduceti o optiune: ");
        scanf("%d", &optiune);
        if(optiune == 1)
        {
            actiune1(lista_cheltuieli, lista_undo);
        }
        if(optiune == 2)
        {
            actiune2(lista_cheltuieli, lista_undo);
        }
        if(optiune == 3)
        {
            actiune3(lista_cheltuieli, lista_undo);
        }
        if(optiune == 4)
        {
            actiune4(lista_cheltuieli);
        }
        if(optiune == 5)
        {
            actiune5(lista_cheltuieli);
        }
        if(optiune == 0)
        {
            actiune6(lista_undo, lista_cheltuieli);
        }
        if(optiune == 7 ) print_all(lista_cheltuieli);
        if(optiune == 8 ) adauga_prestabilit(lista_cheltuieli, lista_undo);
        if(optiune == 6)
        {
            distrugere(lista_cheltuieli, eliminare);
            for ( int i = lista_undo-> numar - 1 ; i >= 0 ; i -- )
                distrugere(lista_undo->elems[i], eliminare);
            free(lista_undo->elems);
            free(lista_undo);
            printf(")"); //distrugere manuala a listei
            break;}
        }
}
