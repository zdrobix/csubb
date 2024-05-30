#include "Iterator.h"
#include "DO.h"
#include <iostream>

#include <exception>
using namespace std;

DO::DO(Relatie r) {
	
	this->lg = 0;
	this->first = nullptr;
	this->relatie = r;
}

//adauga o pereche (cheie, valoare) in dictionar
//daca exista deja cheia in dictionar, inlocuieste valoarea asociata cheii si returneaza vechea valoare
//daca nu exista cheia, adauga perechea si returneaza null
TValoare DO::adauga(TCheie c, TValoare v) {

	if (this->vid()) {

		this->lg++;
		this->first = new Nod(TElem(c, v), nullptr);
		return NULL_TVALOARE;
	}

	Iterator i(*this);
	Nod* anterior = nullptr;

	while (i.valid() and this->relatie(c, i.element().first)) {

		anterior = i.curent;
		i.urmator();
	}

	if (i.valid()) {

		if (i.element().first == c) {

			TValoare veche = i.element().second;
			i.curent->elem.second = v;
			return veche;
		}
	}

	this->lg++;
	auto newNode = new Nod(TElem(c, v), i.curent);
	
	if (anterior == nullptr) {

		newNode->next = this->first;
		this->first = newNode;
	}
	else {

		newNode->next = anterior->next;
		anterior->next = newNode;
	}
	
	return NULL_TVALOARE;
}

//cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null
TValoare DO::cauta(TCheie c) const {
	
	if (this->vid()) {

		return NULL_TVALOARE;
	}
	
	Iterator i(*this);

	while (i.valid()) {

		if (i.element().first == c) {

			return i.element().second;
		}
		
		i.urmator();
	}
	return NULL_TVALOARE;	
}

//sterge o cheie si returneaza valoarea asociata (daca exista) sau null
TValoare DO::sterge(TCheie c) {
	
	if (this->vid()) {

		return NULL_TVALOARE;
	}

	if (this->first->elem.first == c) {

		int deleteTValoare = this->first->elem.second;
		this->lg--;
		this->first = this->first->next;
		delete this->first;
		return deleteTValoare;
	}

	Iterator i(*this);
	Nod* anterior = i.curent;

	while (i.valid() and i.element().first != c) {

		anterior = i.curent;
		i.urmator();
	}

	if (i.element().first == c) {

		int deleteTValoare = i.element().second;
		anterior->next = i.curent->next;
		delete i.curent;
		this->lg--;
		i.curent = anterior;
		return deleteTValoare;
	}

	return NULL_TVALOARE;
}

//returneaza numarul de perechi (cheie, valoare) din dictionar
int DO::dim() const {
	
	return this->lg;
}

//verifica daca dictionarul e vid
bool DO::vid() const {
	
	return this->lg == 0;
}

Iterator DO::iterator() const {

	return  Iterator(*this);
}

DO::~DO() {
	
	Iterator i(*this);

	while (i.valid()) {

		this->sterge(this->first->elem.first);
	}
}
