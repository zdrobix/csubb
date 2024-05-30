#include "Iterator.h"
#include "DO.h"
#include <stdexcept>

using namespace std;

//Complexitate: theta(1)
Iterator::Iterator(const DO& d) : dict(d){
	
	prim();
}

//Complexitate: theta(1)
void Iterator::prim(){
	
	this->curent = this->dict.first;
}

//Complexitate: theta(1)
void Iterator::urmator(){
	
	if (!valid())
		throw out_of_range("Invalid iterator!");

	this->curent = this->dict.next[curent];
}

//Complexitate: theta(1)
bool Iterator::valid() const{
	
	return this->curent != -1;
}

//Complexitate: theta(1)
TElem Iterator::element() const{

	if (!this->valid())
		  throw out_of_range("Invalid iterator!");
	
	return this->dict.elems[this->curent];
}

