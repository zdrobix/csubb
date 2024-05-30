#include <string.h>
#include "service.h"
#include "repo.h"
#include "cheltuiala.h"
#include <assert.h>
#include <stdlib.h>

void test_creeazaVid() {

    ListaCheltuieli list;

    list = creazaVid();

    assert(list.lg == 0);

    assert(list.lg != 1);

    distrugeLista(&list);
}

void test_validare_ch() {

    assert(validareCh (5, 50, "Ch OK")== 1);

    assert(validareCh( 0, 50, "Zi Invalid") == 0);

    assert(validareCh( 1, -10, "Suma Invalid") == 0);

    assert(validareCh( 10, 1010, "") == 0);
}

void test_creeaza_ch() {

    Cheltuiala* cht = creeazaCh(10, 50, "Transport");


    assert(cht->zi == 10);

    assert(cht->suma == 50);

    assert(strcmp(cht->tip, "Transport") == 0);


    assert(cht->zi != 99999);

    assert(cht->suma != 99999);

    assert(strcmp(cht->tip, "Mancare") != 0);


    eliminaCh(cht);

    free(cht);
}

void test_adauga_sterge() {

    ListaCheltuieli list = creazaVid();


    assert(list.lg == 0);

    assert(adaugare_cheltuiala(&list, 1, 132, "Haine") == 1);


    assert(list.lg == 1);

    assert(adaugare_cheltuiala(&list, 2, 132, "Mancare") == 1);


    assert(list.lg == 2);

    assert(adaugare_cheltuiala(&list, 4, 100, "Bus") == 1);


    assert(list.lg == 3);

    assert(adaugare_cheltuiala(&list, 3, 132, "Salut") == 1);


    assert(adaugare_cheltuiala(&list, 900, 132, "Zi prea mare") == 0);

    assert(adaugare_cheltuiala(&list, 0, 132, "Zi prea mica") == 0);

    assert(adaugare_cheltuiala(&list, 10, 0, "Suma mica") == 0);

    assert(adaugare_cheltuiala(&list, 3, 132, "") == 0);


    distrugeLista(&list);

}

void test_sterge() {

    ListaCheltuieli list = creazaVid();

    assert(adaugare_cheltuiala(&list, 1, 132, "Haine") == 1);

    assert(adaugare_cheltuiala(&list, 1, 132, "Haine") == 1);

    assert(adaugare_cheltuiala(&list, 1, 132, "Haine") == 1);

    assert(adaugare_cheltuiala(&list, 1, 132, "Haine") == 1);


    assert(list.lg == 4);

    assert(list.lg != 0);

    assert(1 == sterge_cheltuiala(&list, 0));

    assert(list.lg  == 3);

    assert(1 == sterge_cheltuiala(&list, 0));

    assert(list.lg ==2);

    assert(1 == sterge_cheltuiala(&list, 0));

    assert(list.lg == 1);

    assert(1 == sterge_cheltuiala(&list, 0));

    assert(list.lg == 0);

    assert(0 == sterge_cheltuiala(&list, 1));

    distrugeLista(&list);
}

void test_modificare_cheltuiala() {

    ListaCheltuieli lista = creazaVid();


    adaugare_cheltuiala(&lista, 1, 50, "Mancare");

    adaugare_cheltuiala(&lista, 2, 301, "Transport");

    adaugare_cheltuiala(&lista, 3, 90, "Haine");


    assert(1 == modificare_cheltuiala(&lista, 0, 5, 40, "Altele"));

    assert(1 == modificare_cheltuiala(&lista, 1, 6, 70, "Divertisment"));

    assert(1 == modificare_cheltuiala(&lista, 2, 7, 100, "Facturi"));

    assert(0 == modificare_cheltuiala(&lista, 0, 0, 40, "Altele"));

    assert(0 == modificare_cheltuiala(&lista, 1, 100, 70, "Divertisment"));

    assert(0 == modificare_cheltuiala(&lista, 2, 7, 100, ""));

    assert(0 == modificare_cheltuiala(&lista, 1000, 7, 100, "Pozitie invalida"));

    assert(0 == modificare_cheltuiala(&lista, -1, 7, 100, "Poz inv"));


    assert(lista.cht[0].zi == 5);

    assert(lista.cht[0].suma == 40);

    assert(strcmp(lista.cht[0].tip, "Altele") == 0);


    assert(lista.cht[1].zi == 6);

    assert(lista.cht[1].suma == 70);

    assert(strcmp(lista.cht[1].tip, "Divertisment") == 0);


    assert(lista.cht[2].zi == 7);

    assert(lista.cht[2].suma == 100);

    assert(strcmp(lista.cht[2].tip, "Facturi") == 0);

    distrugeLista(&lista);
}

void test_fitrare () {

    ListaCheltuieli lista = creazaVid();

    adaugaCh(&lista, creeazaCh(1, 50, "Mancare"));

    adaugaCh(&lista, creeazaCh(2, 301, "Transport"));

    adaugaCh(&lista, creeazaCh(3, 90, "Haine"));

    adaugaCh(&lista, creeazaCh(1, 301, "Transport"));

    adaugaCh(&lista, creeazaCh(2, 90, "Mancare"));

    adaugaCh(&lista, creeazaCh(3, 50, "Haine"));


    ListaCheltuieli lista_filtrata_tip = creazaVid();

    ListaCheltuieli lista_filtrata_suma = creazaVid();

    ListaCheltuieli lista_filtrata_zi = creazaVid();


    filtrareCh(&lista, &lista_filtrata_tip, -1, -1, "Mancare");

    assert(lista_filtrata_tip.lg == 2);

    assert(lista_filtrata_tip.cht[0].zi == 1);

    assert(lista_filtrata_tip.cht[1].zi == 2);


    filtrareCh(&lista, &lista_filtrata_suma, -1, 301, "");

    assert(lista_filtrata_suma.lg == 2);

    assert(lista_filtrata_suma.cht[0].zi == 2);

    assert(lista_filtrata_suma.cht[1].zi == 1);


    filtrareCh(&lista, &lista_filtrata_zi, 2, -1, "");

    assert(lista_filtrata_zi.lg == 2);

    assert(lista_filtrata_zi.cht[0].suma == 301);

    assert(lista_filtrata_zi.cht[1].suma == 90);


    lista_filtrata_suma.lg = 0;

    lista_filtrata_tip.lg = 0;

    lista_filtrata_zi.lg = 0;

    distrugeLista(&lista);

    distrugeLista(&lista_filtrata_zi);

    distrugeLista(&lista_filtrata_tip);

    distrugeLista(&lista_filtrata_suma);
}

void test_redimensionare() {

    ListaCheltuieli listaCreata;

    listaCreata.lg = 0;

    listaCreata.cp = 1;

    listaCreata.cht = (Cheltuiala*)malloc(listaCreata.cp * sizeof(Cheltuiala));

    Cheltuiala* cht = creeazaCh(1, 20, "Mancare");

    adaugaCh(&listaCreata, cht);

    assert(listaCreata.cp == 1);

    assert(listaCreata.lg == 1);

    adaugaCh(&listaCreata, cht);

    assert(listaCreata.cp == 2);

    assert(listaCreata.lg == 2);

    eliminaCh(cht);

    free(listaCreata.cht);
}

void test_comparatie() {

    Cheltuiala* ch1 = creeazaCh(10, 500, "tip1");

    Cheltuiala* cht2 = creeazaCh(11, 900, "tip2");

    Cheltuiala* cht3 = creeazaCh(20, 400, "tip3");


    assert(comparaCh(*ch1, *cht2, 1) < 0);

    assert(comparaCh(*cht2, *ch1, 1) > 0);

    assert(comparaCh(*ch1, *ch1, 1) == 0);

    assert(comparaCh(*ch1, *cht2, 1) == -1);

    assert(comparaCh(*cht2, *ch1, 1) == 1);

    assert(comparaCh(*ch1, *cht3, 1) < 0);

    assert(comparaCh(*cht3, *ch1, 1) > 0);

    assert(comparaCh(*ch1, *cht3, 1) == -10);

    assert(comparaCh(*cht3, *ch1, 1) == 10);


    eliminaCh(ch1);

    eliminaCh(cht2);

    eliminaCh(cht3);
}

void test_sortare() {

    ListaCheltuieli lista = creazaVid();

    adaugaCh(&lista, creeazaCh(3, 50, "Mancare"));

    adaugaCh(&lista, creeazaCh(1, 300, "Transport"));

    adaugaCh(&lista, creeazaCh(2, 90, "Haine"));


    sorteazaCh(&lista, -1, 1); //zi cresc

    assert(lista.cht[0].zi == 1);

    assert(lista.cht[2].zi == 3);


    sorteazaCh(&lista, 1, 1); //zi desc

    assert(lista.cht[0].zi == 3);

    assert(lista.cht[2].zi == 1);

    assert(lista.cht[0].zi != 1);

    assert(lista.cht[2].zi != 3);


    sorteazaCh(&lista, -1, 0); //suma cresc

    assert(lista.cht[0].suma == 50);

    assert(lista.cht[2].suma == 300);


    sorteazaCh(&lista, 1, 0); //suma desc

    assert(lista.cht[0].suma == 300);

    assert(lista.cht[2].suma == 50);

    assert(lista.cht[0].suma != 50);

    assert(lista.cht[2].suma != 300);

    distrugeLista(&lista);
}

void test_sortare_2() {

    ListaCheltuieli lista = creazaVid();

    adaugaCh(&lista, creeazaCh(3, 50, "Mancare"));

    adaugaCh(&lista, creeazaCh(1, 300, "Transport"));

    adaugaCh(&lista, creeazaCh(2, 90, "Haine"));


    sortare_pointer_comparatie(&lista, compara_zi); //zi cresc

    assert(lista.cht[0].zi == 1);

    assert(lista.cht[2].zi == 3);


    sortare_pointer_comparatie(&lista, compara_suma); //suma cresc

    assert(lista.cht[0].suma == 50);

    assert(lista.cht[2].suma == 300);

    assert(compara_suma(lista.cht[0], lista.cht[0]) == 0);

    distrugeLista(&lista);
}


void test_distruge() {

    ListaCheltuieli lista = creazaVid();

    adaugaCh(&lista, creeazaCh(3, 50, "Mancare"));

    adaugaCh(&lista, creeazaCh(1, 300, "Transport"));

    adaugaCh(&lista, creeazaCh(2, 90, "Haine"));

    assert(lista.lg== 3);

    distrugeLista(&lista);

    assert(lista.lg == 0);
}

void prestabilit_cheltuieli(ListaCheltuieli* list) {
    ///adaugarea unor cheltuieli prestabilite

    adaugaCh(list, creeazaCh(1, 20, "Mancare"));

    adaugaCh(list, creeazaCh(2, 30, "Divertisment"));

    adaugaCh(list, creeazaCh(3, 3, "Transport"));

    adaugaCh(list, creeazaCh(3, 200, "Benzinarie"));

    adaugaCh(list, creeazaCh(5, 9000, "Rata"));

    adaugaCh(list, creeazaCh(1, 234, "Rata"));

    adaugaCh(list, creeazaCh(6, 756, "Rata"));

    adaugaCh(list, creeazaCh(7, 132, "Rata"));

    adaugaCh(list, creeazaCh(6, 178, "Electronice"));

    adaugaCh(list, creeazaCh(2, 105, "Factura"));

    adaugaCh(list, creeazaCh(1, 760, "Factura"));

    adaugaCh(list, creeazaCh(3, 255, "Factura"));

    adaugaCh(list, creeazaCh(1, 90, "Factura"));

    adaugaCh(list, creeazaCh(6, 250, "Factura"));

    adaugaCh(list, creeazaCh(6, 100, "Factura"));
}

void runTest() {
    ///Testele aplicatiei
    test_creeazaVid();

    test_validare_ch();

    test_creeaza_ch();

    test_adauga_sterge();

    test_sterge();

    test_modificare_cheltuiala();

    test_fitrare();

    test_comparatie();

    test_sortare();

    test_sortare_2();

    test_distruge();

    test_redimensionare();
}