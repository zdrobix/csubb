#include "Iterator.h"
#include "DO.h"
#include <stdexcept>

using namespace std;

Iterator::Iterator(const DO& d) : dict(d){

    this->curent = this->dict.first;
}

void Iterator::prim(){
	
	this->curent = this->dict.first;
}

void Iterator::urmator(){
	
	if (!valid())
		throw out_of_range("Invalid iterator!");

	this->curent = this->curent->next;

}

bool Iterator::valid() const{
	
	return curent != nullptr;
}

TElem Iterator::element() const{

	if (!valid())
		throw out_of_range("Invalid iterator!");
	
	return curent->elem;
}

