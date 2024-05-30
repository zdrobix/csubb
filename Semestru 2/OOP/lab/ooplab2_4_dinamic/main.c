
#include <stdio.h>
#include <string.h>
#include "service.h"
#include "teste.h"

void print_meniu () {
    ///afiseaza catre utilizator optiunile valabile
    printf("1. Adaugare\n2. Modificare\n3. Stergere\n4. Filtrare\n5. Ordonare\n6. Afisare cheltuieli\n7. Iesire\n\n");
}

char print_optiune () {
    ///intreaba utilizatorul ce optiune vrea sa aleaga
    printf("Introduceti optiunea: ");

    char option;

    scanf(" %c", &option);

    printf("\n");

    return option;
}

void introducere_date ( int* zi, float* suma, char tip[100]) {
    ///utilizatorul introducele datele unei cheltuieli
    int zi_noua; float suma_noua; char tip_nou[100];
    printf("Introduceti ziua: ");

    scanf("%d", &zi_noua);

    printf("Introduceti suma: ");

    scanf("%f", &suma_noua);

    printf("Introduceti tipul: ");

    scanf("%s", tip_nou);

    *zi = zi_noua;

    *suma = suma_noua;

    strcpy(tip, tip_nou);
}

void tiparesteListaCh (ListaCheltuieli* list) {

    int i;

    if ( list-> lg == 0 )

        printf("In lista nu exista cheltuieli.");

    else

        for ( i = 0 ; i < list->lg ; i ++ )
        {
            printf("%d) ", i + 1);

            printf("zi: %d, suma: %.2f, tip: %s\n", list->cht[i].zi, list->cht[i].suma, list->cht[i].tip);
        }
    printf("\n");
}

void meniu_adaugare(ListaCheltuieli* list) {
    /**
        Meniul din care functia de adaugare este apelata
            -prima oara se citesc datele
            -se valideaza cheltuiala creata cu datele citite
            -apoi se adauga in lista de cheltuieli
            -se afiseaza mesajul de succes/eroare
    **/

    int zi; float suma; char tip[100];

    introducere_date(&zi, &suma, tip);

    int ok = adaugare_cheltuiala(list, zi, suma, tip);

    if (ok)

        printf("Adaugarea a fost realizata cu succes!\n\n");

    else

        printf("Adaugarea nu a fost realizata cu succes, date invalide!\n\n");
}

void meniu_modificare(ListaCheltuieli* list) {
    /**
        Meniul de modificare.
            -functia afiseaza toate cheltuielile, alaturi de un index
            -utilizatorul alege cheltuiala dorita, daca aceasta nu exista se va afisa un mesaj corespunzator
            -daca cheltuiala este valida se citesc noile date si se apeleaza functia de modificare
    **/

    tiparesteListaCh(list);

    printf("Ce cheltuiala doriti a fi modificata? Introduceti numarul: ");

    int numar_ch;

    scanf("%d", &numar_ch);

    numar_ch--; // pentru ca indexarea incepe de la 0, iar utilizatorul vede indexul adevarat + 1

    if (numar_ch < 0 || numar_ch >= list->lg)

        printf("Numar invalid. Va rog selectati o cheltuiala valida.\n\n");

    else {
        printf("Cheltuiala %d a fost selectata.\n", numar_ch + 1);

        int zi; float suma; char tip[100];

        introducere_date(&zi, &suma, tip);

        int ok = modificare_cheltuiala(list, numar_ch, zi, suma, tip);

        if (ok == 1)

            printf("Modificare a fost realizata cu succes!\n\n");

        else

            printf("Modificare nu a fost realizata cu succes, date invalide!\n\n");

    }
}

void meniu_stergere(ListaCheltuieli* list) {
    /**
        Meniul de stergere.
            -functia afiseaza toate cheltuielile, alaturi de un index
            -utilizatorul alege cheltuiala dorita, daca aceasta nu exista se va afisa un mesaj corespunzator
            -daca cheltuiala este valida  se apeleaza functia de stergere
    **/

    tiparesteListaCh(list);

    printf("Ce cheltuiala doriti a fi stearsa? Introduceti numarul: ");

    int numar_ch;

    scanf("%d", &numar_ch);

    numar_ch--; // pentru ca indexarea incepe de la 0, iar utilizatorul vede indexul adevarat + 1

    int ok = sterge_cheltuiala(list, numar_ch);

    if (ok == 0)

        printf("Numar invalid. Va rog selectati o cheltuiala valida.\n\n");

    else

        printf("Cheltuiala %d a fost stearsa.\n", numar_ch + 1);

}

void meniu_filtrare (ListaCheltuieli* list) {
    /**
        Meniul de filtrare.
            -utilizatorul este intrebat in functie de ce vrea sa filtreze datele (zi/suma/tip)
            -in functie de optiunea dorita, se citeste respectivul parametru
            -apoi se afiseaza rezultatele filtrari (ex: toate cheltuielile din ziua 1, toate cheltuielile cu suma 500)
    **/

    printf("1. Zi\n2. Suma\n3. Tip\n");

    char optiune = print_optiune();

    if (!strchr("123", optiune)) {

        printf("Optiune invalida!\n");

        return;
    }

    int zi = -1; float suma = -1; char tip[100] = "";

    if (list-> lg == 0) {

        printf("Nu aveti nicio cheltuiala adaugata.\n");

        return;

    } else if (optiune == '1') {

        printf("Introduceti ziua: ");

        scanf("%d", &zi);

    } else if (optiune == '2') {

        printf("Introduceti suma: ");

        scanf("%f", &suma);

    } else if (optiune == '3') {

        printf("Introduceti tipul: ");

        getchar();

        fgets(tip, sizeof(tip), stdin);

        tip[strcspn(tip, "\n")] = '\0';

    } else { printf("Optiune invalida!");}


    printf("Listra filtrata este: \n");

    ListaCheltuieli lista_filtrata = creazaVid();

    filtrareCh(list, &lista_filtrata, zi, suma, tip);

    if (lista_filtrata.lg != 0)

        tiparesteListaCh(&lista_filtrata);

    else

        printf("Nicio cheltuiala nu respecta cerintele dumneavoastra.");

    lista_filtrata.lg = 0;
}


void meniu_ordonare(ListaCheltuieli* list) {
    /**
        Meniul de ordonare.
            -utilizatorul este intrebat in functie de ce vrea sa fie datele ordonate,si directia ordonarii
            -se creeaza o noua lista, care va fi folosita pentru sortare
            -functia de ordonare este apelata cu parametrii: -lista in care sortam
                                                             -directia sortarii
                                                             -zi si suma ---> (1, 0) sau (0, 1)
    **/

    printf("1. Zi\n2. Suma\n");

    char optiune = print_optiune();

    if (optiune != '1' && optiune != '2') {

        printf("Optiune invalida!\n");

        return;
    }

    printf("1. Crescator\n 2. Descrescator\n");

    char optiune2 = print_optiune();

    if (optiune2 != '1' && optiune2 != '2') {

        printf("Optiune invalida!\n");

        return;
    }

    int directie;

    if (list->lg == 0) {

        printf("Nu aveti nicio cheltuiala adaugata.\n");

        return;

    } else if (optiune2 == '1')

        directie = -1;

    else directie = 1;

    ListaCheltuieli lista_ordonata = creazaVid();

    for (int i = 0; i < list->lg; ++i)

        adaugaCh(&lista_ordonata, &(list->cht[i]));


    if (optiune == '1') {

        printf("Lista ordonata dupa zi este: \n");

        sorteazaCh(&lista_ordonata, directie, 1);

    } else {

        printf("Lista ordonata dupa suma este: \n");

        sorteazaCh(&lista_ordonata, directie, 0);

    }
    tiparesteListaCh(&lista_ordonata);

    lista_ordonata.lg = 0;

}

void startApp() {
    ///Aici utilizatorul alege din meniul prestabilit ce functionalitati vrea sa apeleze.

    ListaCheltuieli list = creazaVid();

    int run = 1;

    while (run) {

        print_meniu();

        char optiune = print_optiune();

        if (optiune == '0')

            prestabilit_cheltuieli(&list);

        else if (optiune == '1')

            meniu_adaugare(&list);

        else if (optiune == '2')

            meniu_modificare(&list);

        else if (optiune == '3')

            meniu_stergere(&list);

        else if (optiune == '4')

            meniu_filtrare(&list);

        else if (optiune == '5')

            meniu_ordonare(&list);

        else if (optiune == '6')

            tiparesteListaCh(&list);

        else if (optiune == '7')

            run = 0;

        else printf("Optiune invalida!\n");
    }
    distrugeLista(&list);
}

int main()
{
    startApp();

    runTest();

    return 0;
}
