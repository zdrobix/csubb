#include "Iterator.h"
#include "DO.h"
#include <iostream>

#include <exception>
using namespace std;

//Complexitate: theta(n)
//se aloca spatiu pentru lista de TElem, si pentru lista cu urmatoarele pozitii libere
DO::DO(Relatie r) {
	
	this->elems = new TElem[cp];
	this->next = new int[cp];
	this->first = -1;
	this->first_free = 0;
	this->relatie = r;
	this->lg = 0;
	for (int i = 0; i < cp - 1; i++)
		next[i] = i + 1;
	next[cp - 1] = -1; 
}
//Complexitate: theta(1)
int DO::aloca() {
	
	if (first_free == -1)
		throw runtime_error("Error");
	else {
		int free_pos = first_free;
		first_free = next[first_free];
		return free_pos;
	}
}

//Complexitate: theta(1)
void DO::dealoca(int i) {

	next[i] = first_free;
	first_free = i;
}

//Complexitate: theta(1)
int DO::creaza_nod(TElem el) {
	
	if (first_free == -1)
		redim();
	int pos = aloca();
	elems[pos] = el;
	next[pos] = -1;
	return pos;
}

//Complexitate: theta(n)
void DO::redim() {
	
	auto* elems_new = new TElem[this->cp * 2];
	auto* next_new = new int[this->cp * 2];
	
	for (int i = 0; i < this->cp; i ++) {
		elems_new[i] = this->elems[i];
		next_new[i] = this->next[i];
	}

	for (int i = cp; i < cp * 2 - 1; i++)
		next_new[i] = i + 1;

	next_new[cp * 2 - 1] = -1;
	this->first_free = this->cp;
	
	delete[] next;
	delete[] elems;
	this->elems = elems_new;
	this->next = next_new;
	this->cp = (this->cp) * 2;
}

//Complexitate: O(n)
//adauga o pereche (cheie, valoare) in dictionar
//daca exista deja cheia in dictionar, inlocuieste valoarea asociata cheii si returneaza vechea valoare
//daca nu exista cheia, adauga perechea si returneaza null
TValoare DO::adauga(TCheie c, TValoare v) {

	if (this->vid()) { //daca dictionarul e gol

		TElem el(c, v);
		int pos = creaza_nod(el);
		first = pos;
		this->lg = 1;	
		return NULL_TVALOARE;
	}
	int last = -1;
	Iterator it(*this);
    while (it.valid() && this->relatie(it.element().first, c)) {
		if (it.element().first == c) { //daca exista cheia

			TValoare old_value = it.element().second;
			this->elems[it.curent].second = v; 
			return old_value;
		}
		last = it.curent;
		it.urmator();
	}

	TElem el(c, v);
	int pos = creaza_nod(el);
	 
	if (last == -1) {  //daca dictionarul nu e gol, cheia nu exista, si elementul vine inserat pe prima pozitie
		this->next[pos] = first;
		this->first = pos;
	}
	else { //daca cheia nu exista, si elementul vine inserat la mijloc/sfarsit
		this->next[pos] = it.curent;
		this->next[last] = pos;
	}
	this->lg++;
	return NULL_TVALOARE;
}

//Complexitate: O(n)
//cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null
TValoare DO::cauta(TCheie c) const {
	
	if (this->vid()) return NULL_TVALOARE;
	Iterator it(*this);
	while (it.valid() and this->relatie(it.element().first, c)) {
		if (it.element().first == c)
			return it.element().second;
		it.urmator();
	}
	return NULL_TVALOARE;
}

//Complexitate: O(n)
//sterge o cheie si returneaza valoarea asociata (daca exista) sau null
TValoare DO::sterge(TCheie c) {
	
	TValoare to_delete = this->cauta(c);
	if (to_delete == NULL_TVALOARE)
		return to_delete;
	Iterator it(*this);
	int last = -1;
	while (it.valid() and it.element().first != c) {
		
		last = it.curent;
		it.urmator();
	}
	if (it.curent == this->first) {
		this->first = this->next[it.curent];
	}
	else
		this->next[last] = this->next[it.curent];

	dealoca(it.curent);
	this->lg--;
	return to_delete;
}

//Complexitate: theta(1)
//returneaza numarul de perechi (cheie, valoare) din dictionar
int DO::dim() const {
	
	return this->lg;
}

//Complexitate: theta(1)
//verifica daca dictionarul e vid
bool DO::vid() const {
	
	return this->lg == 0;
}

//Complexitate: theta(1)
Iterator DO::iterator() const {

	return  Iterator(*this);
}

//Complexitate: theta(n)
DO::~DO() {
    while(!this->vid())
        this->sterge(this->elems[first].first);
}


//Complexitate: O(n)
/* 
* preconditii: d - DO
* postconditii: diferentaValoareMaxMin(d) - numar intreg
* 
* SubAlgoritm diferentaValoareMaxMin(d):
* 
*	daca vid(d) = true atunci:
*		return -1
*	sfDaca
*	vmax <-  d.elems[first].second
*	vmin <-	d.elems[first].second
*	it <- Iterator(d)
* 
*	cat timp valid(it) executa:
*		daca (element(it).second > vmax) 
*			vmax <- element(it).second)
*		sfDaca
*		daca (element(it).second < vmin)
*			vmin <- element(it).second
*		sfDaca
*		urmator(it)
* 
*	sfCatTimp
*	return vmax - vmin
*	SfSubAlgoritm
*/
int DO::diferentaValoareMaxMin() const
{
	if (this->vid())
		return -1;

	TValoare vmax = this->elems[first].second;
	TValoare vmin = this->elems[first].second;

	Iterator it(*this);
	while (it.valid()) {
		if (it.element().second > vmax)
			vmax = it.element().second;
		if (it.element().second < vmin)
			vmin = it.element().second;
		it.urmator();
	}
	return vmax - vmin;
}


