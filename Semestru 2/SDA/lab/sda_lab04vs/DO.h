#pragma once

typedef int TCheie;
typedef int TValoare;

#define NULL_TVALOARE -9999
#define CAPACITATE 1000

#include <utility>
#include <iostream>

using namespace std;

typedef pair<TCheie, TValoare> TElem;

class Iterator;

typedef bool(*Relatie)(TCheie, TCheie);

class DO {
	friend class Iterator;

    private:
		//capacitate
		int cp = CAPACITATE;

		//lungime
		int lg;

		//lista cu poz urm(inlantuirea)
		int* next;

		//lista de TElem
		TElem* elems;

		//indicele primului element
		int first;

		//indicele primului element liber
		int first_free;

		//relatia
		Relatie relatie;

		//functie de alocare
		int aloca();

		//functie de dealocare
		void dealoca(int i);

		//functie de creare nod
		int creaza_nod(TElem el);

		//functie de redimensionat 
		void redim();


    public:

	// constructorul implicit al dictionarului
	DO(Relatie r);

	// adauga o pereche (cheie, valoare) in dictionar
	//daca exista deja cheia in dictionar, inlocuieste valoarea asociata cheii si returneaza vechea valoare
	// daca nu exista cheia, adauga perechea si returneaza null: NULL_TVALOARE
	TValoare adauga(TCheie c, TValoare v);

	//cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null: NULL_TVALOARE
	TValoare cauta(TCheie c) const;

	//sterge o cheie si returneaza valoarea asociata (daca exista) sau null: NULL_TVALOARE
	TValoare sterge(TCheie c);

	//returneaza numarul de perechi (cheie, valoare) din dictionar
	int dim() const;

	//verifica daca dictionarul e vid
	bool vid() const;

	// se returneaza iterator pe dictionar
	// iteratorul va returna perechile in ordine dupa relatia de ordine (pe cheie)
	Iterator iterator() const;

	// destructorul dictionarului
	~DO();

	// returneaza diferenta dintre valoarea maxima si valoarea minima (presupunem valori întregi) 
	// Daca dictionarul este vid, se returneaza -1 
	int diferentaValoareMaxMin() const;
};
