#pragma once

typedef int TElem;
#define NULL_TELEMENT 0
#define MAX_SIZE 10000

class Node {
friend class Matrice;
private:
    int row, col;

    TElem value;

    Node* next;
public:
    //constructorul nodului
    explicit Node(int i, int j, TElem e, Node* n) : row{i}, col{j}, value{e}, next{n} {};

    Node() : row{-1}, col{-1}, value{NULL_TELEMENT}, next{nullptr} {};

    //operatia de !=
    bool operator!=(const Node& other) const {
        return this->value != other.value and this->row != other.row and this->col != other.col and this->next != other.next;
    };

    //operatie de egalitate
    bool operator==(const Node& other) const {
        return !(*this!=other);
    };
};

class Matrice {
private:
    //cate linii are matricea
    int nrLin;

    //cate coloane are matricea
    int nrCol;

    //capacitatea
    int capacity;

    //dimensiunea
    int length;

    //primul element liber
    int firstFree;

    //elemente
    Node* elementList;

    //functia de dispersie
    int hash(int row, int col) const;

    //actualizeaza prima pozitie libera
    void updateFirstEmpty();

public:

	//constructor
	//se arunca exceptie daca nrLinii<=0 sau nrColoane<=0
	Matrice(int nrLinii, int nrColoane);


    //destructor
    ~Matrice();

	//returnare element de pe o linie si o coloana
	//se arunca exceptie daca (i,j) nu e pozitie valida in Matrice
	//indicii se considera incepand de la 0
	TElem element(int i, int j) const;


	// returnare numar linii
	int nrLinii() const;

	// returnare numar coloane
	int nrColoane() const;


	// modificare element de pe o linie si o coloana si returnarea vechii valori
	// se arunca exceptie daca (i,j) nu e o pozitie valida in Matrice
	TElem modifica(int i, int j, TElem);


    //void transpusa(Matrice &other);
};







