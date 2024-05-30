
#ifndef CHELTUIALA_H
#define CHELTUIALA_H

typedef struct {

    int zi;

    float suma;

    char* tip;

}Cheltuiala;
///Cheltuiala care contine zi, suma, si tip

Cheltuiala* creeazaCh(int zi, float suma, const char *tip);
///creeaza o cheltuiala, prin parametrii zi, suma, si tip
///returneaza cheltuiala
///o cheltuiala este creata doar daca este validata

int validareCh(int zi, float suma, const char *tip);
///Valideaza datele cheltuielii inainte de a fi adaugata in lista.
///se returneaza 1 sau 0. 0 - invalid. 1 - valid

void eliminaCh(Cheltuiala *cht);
///sterge o cheltuiala existenta
///elibereaza memoria ocupata de aceasta, elibereaza tip/suma/zi

#endif //CHELTUIALA_H
