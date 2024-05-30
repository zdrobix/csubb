#ifndef VALIDATOR_H
#define VALIDATOR_H
#include <stdio.h>
#include <string.h>
#include "validator.h"

int validare( char* zi, int suma, char* tip){

    int contor = 0;
    if(suma < 0)
    {
        printf("Suma nu poate fi mai mica decat 0.\n");
        contor +=1;
    }
    if(strcmp(tip,"mancare") !=0 && strcmp(tip,"transport") !=0 && strcmp(tip,"telefon&internet") !=0 && strcmp(tip,"imbracaminte") !=0 && strcmp(tip,"altele")!=0 )
    {
        printf("Tipul nu face parte din categoriile date.\n");
        contor +=1;
    }
    if(strcmp(zi," ") == 0)
    {
        printf("Ziua nu poate sa fie goala.\n");
        contor +=1;
    }
    if(contor > 0)
        return 0;
    return 1;

}

#endif