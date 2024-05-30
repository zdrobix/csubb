#pragma once

typedef int TCheie;
typedef int TValoare;

#define NULL_TVALOARE -1

#include <utility>

using namespace std;

typedef pair<TCheie, TValoare> TElem;

class Iterator;

typedef bool(*Relatie)(TCheie, TCheie);


class Nod {
	friend class DO;
	friend class Iterator;

	private:
		TElem elem;
		Nod* next;

	public:
		Nod(TElem elem, Nod* next) : elem(elem), next(next) {};
};
#include <iostream>
class DO {
	friend class Iterator;

    private:
		//lungimea dictionarului
		int lg;

		//referinta catre primul element
		Nod* first;

		//relatia dictionarului ordonat
		Relatie relatie;

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
};
