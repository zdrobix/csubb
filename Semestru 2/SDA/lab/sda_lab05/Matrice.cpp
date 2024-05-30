#include "Matrice.h"
#include <stdexcept>

using namespace std;

//complexitate: theta(n)
Matrice::Matrice(int nrLinii, int nrColoane) {

	if ( nrLinii * nrColoane <= 0 )
        throw out_of_range("Numar invalid de linii/coloane");

    this->nrLin = nrLinii;
    this->nrCol = nrColoane;
    this->capacity = MAX_SIZE;
    this->length = 0;
    this->firstFree = 0;
    this->elementList = new Node[this->capacity];
}

//complexitate: theta(1)
int Matrice::nrLinii() const{
    return this->nrLin;
}

//complexitate: theta(1)
int Matrice::nrColoane() const{
    return this->nrCol;
}

//complexitate: theta(1)
TElem Matrice::element(int i, int j) const {

    if ( i >= this->nrLin or j >= this->nrCol or i < 0 or j < 0)
        throw out_of_range("pozitie invalida");

    int hash = this->hash(i, j);
    Node* current_node = &this->elementList[hash];
    while(current_node != nullptr) {
        if (current_node->row == i and current_node->col == j) {
            return current_node->value;
        }
        current_node = current_node->next;
    }
    return NULL_TELEMENT;
}


//complexitate: O(1)
TElem Matrice::modifica(int i, int j, TElem e) {

    if (i >= this->nrLin or j >= this->nrCol or i < 0 or j < 0)
        throw out_of_range("pozitie invalida");

    int hash = this->hash(i, j);
    Node *current_node = &this->elementList[hash];
    Node *last = nullptr;


    while (current_node != nullptr) {
        if (current_node->row == i and current_node->col == j) {
            TElem old_value = current_node->value;  //retine valoare veche pentru a o putea returna
            if (e != NULL_TELEMENT) {
                current_node->value = e;
            } else {
                if (last == nullptr)
                    this->elementList[hash] = *(current_node->next);
                else
                    last->next = current_node->next;
                delete current_node;
                this->length--;
            }
            return old_value;
        }
        last = current_node;
        current_node = current_node->next;
    }

    if (e != NULL_TELEMENT) { //elementul nu exista->trebuie adaugat
        this->length++;
        Node *new_node = new Node(i, j, e, nullptr);
        if (last == nullptr)
            this->elementList[hash] = *new_node;
        else
            last->next = new_node;
        this->updateFirstEmpty();
    }
    return NULL_TELEMENT;
}

//complexitate: O(n+m) : n = capacitatea, m = numarul total de noduri
Matrice::~Matrice() {
    for (int i = 0; i < this->capacity; ++i) {
        Node* current_node = &this->elementList[i];
        while (current_node != nullptr) {
            Node* next_node = current_node->next;
            delete current_node->next;
            current_node = next_node;
        }
    }
    delete[] this->elementList;
}

//complexitat: theta(1)
int Matrice::hash(int row, int col) const {
    return (row * 11 + col * 3) % ((this->nrCol + this->nrLin)% this->capacity);
}

//complexitate: O(1)
void Matrice::updateFirstEmpty() {
    this->firstFree ++;
    while(this->firstFree < this->capacity and this->elementList[firstFree].value != NULL_TELEMENT)
        this->firstFree ++;

    if(this->firstFree == this->capacity)
        this->firstFree = -1;
}

/*
void Matrice::transpusa(Matrice &other) {
    for(int index=0;index<other.capacity;index++){
        if(other.elementList[index].value!=NULL_TELEMENT){
            this->modifica(other.elementList[index].col,other.elementList[index].row,other.elementList[index].value);
        }
    }
}*/