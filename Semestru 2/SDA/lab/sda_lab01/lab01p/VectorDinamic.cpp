#include "VectorDinamic.h"
#include "IteratorVectorDinamic.h"
#include <stdexcept>

using namespace std;

void VectorDinamic::redim() {
    //theta(n)

	//se aloca spatiu dublu
	TElem *eNou = new int[2 * cp];

	//valorile precedente se copiaza
	for (int i = 0; i < n; i++)
		eNou[i] = e[i];

	//se dubleaza capacitatea
	cp = 2 * cp;

	//zona veche este eliberata
	delete[] e;

	//vectorul va contine zona nou creata
	e = eNou;
    /*
     *Algoritm redim (VectorDinamic v)
     *      Telem *eNou <- new int[2*cp]
     *      pentru i <- 0, n-1 executa:
     *          eNou[i] <- e[i]
     *      sfPentru
     *      cp <- cp * 2
     *      @delete[] e
     *      e <- eNou
     *SfAlg
     */
}


VectorDinamic::VectorDinamic(int cp) {
    //theta(1)

    //se arunca exceptie daca cp = 0
    if (cp <= 0)

        throw out_of_range("Index out of range!!!!");
    //se seteaza capacitatea
	this->cp = cp;

	//se aloca spatiu de memorare pentru vector
	e = new TElem[cp];

	//se seteaza numarul de elemente
	this->n = 0;
    /*
     * Alg VectorDinamic(cp)
         * daca cp <= 0 atunci:
         *      @throw exception
         * sfDaca
         * this.cp <- cp
         * e <- new Telem[cp]
         * thi->n <- 0
     * SfAlg
     */
}



VectorDinamic::~VectorDinamic() {
    //theta(1)

	//se sterge vectorul
    delete[] e;
    /*
     * Alg ~VectorDinamic()
     *      @delete[] e
     * SfAlg
     */
}



int VectorDinamic::dim() const{
    //theta(1)

	//se returneaza dimensiunea
	return n;
    /*
     * Alg dimensiune()
     *      return n
     * SfAlg
     */
}



TElem VectorDinamic::element(int i) const{
    //theta(1)

	//se returneaza elementul de pe pozitia i
    //daca nu exista, se arunca exceptie
    if (i < 0 || i >= n)
        throw out_of_range("Index out of range!!!!");
	return e[i];
    /*
     * Alg element(i)
     *      daca i < 0 sau i >= n atunci
     *          @throw exception
     *      sfDaca
     *      return e[i]
     * SfAlg
     */
}



void VectorDinamic::adaugaSfarsit(TElem e) {
    //theta(1)

    //daca s-a atins capacitatea maxima se redimensioneaza
    //si dupa se adauga elementul la sfarsit
    if (n == cp)
        redim();

    //se adauga la sfarsit
    this->e[n++] = e;
    /*
     * Alg adaugaSfarsit(e)
     *      daca n == cp atunci
     *          redim()
     *      sfDaca
     *      n <- n + 1
     *      this -> e[n] <- e
     * SfAlg
     */
}


void VectorDinamic::adauga(int i, TElem e) {
    //O(n)

    //daca pozitia este invalida, se arunca exceptie
    if (i < 0 || i > n)
        throw out_of_range("Index out of range!");

    //daca s-a atins capacitatea maxima se redimensioneaza
    if (n == cp)
        redim();

    //se muta fiecare element in dreapta cu o pozitie pentru a face loc elementului adaugat
    for ( int index = n; index >= i ; index -- )

        this->e[index+1] = this->e[index];

    //se actualizeaza numarul de elemente
    n ++;

    //se adauga noul element pe pozitia golita
    this->e[i] = e;
    /*
     * Alg adauga(i, e)
     *      daca i < 0 sau i >= n atunci
     *          @throw exception
     *      sfDaca
     *      daca n == cp atunci
     *          redim()
     *      sfDaca
     *      pentru index <- n, i, -1 atunci
     *          this->e[index+1] <- this->e[index]
     *      sfPentru
     *      n <- n + 1
     *      this -> e[n] <- e
     */
}


TElem VectorDinamic::modifica(int i, TElem e) {
    //theta(1)

    //daca pozitia este invalida se arunca exceptie
    if (i < 0 || i >= n)
        throw out_of_range("Index out of range!!");

    //se retine valoarea elementului vechi
    TElem valoare_veche = this->e[i];

    //se adauga noul element pe pozitia i
    this->e[i] = e;

    //se returneaza valoarea veche
    return valoare_veche;
    /*
     * Alg modifica(i, e)
     *      daca i < 0 sau i >= n atunci
     *          @throw exception
     *      sfDaca
     *      valoare_veche <- this->e[i]
     *      this-> e[i] <- e
     *      return valoare_veche
     * SfAlg
     */
}


TElem VectorDinamic::sterge(int i) {
    //O(n)

    //daca pozitia este invalida se arunca exceptie
    if (i < 0 || i >= n)
        throw out_of_range("Index out of range!!!");

    //se retine valoarea elementului vechi
    TElem valoare_veche = this->e[i];

    //de la pozitia elementului sters,
    //se muta toate elementele stanga cu o pozitie
    for ( int index = i; index < n; index ++ )

        this->e[index] = this->e[index+1];

    //se actualizeaza numarul de elemente
    n --;

    //se returneaza valoarea veche
	return valoare_veche;
    /*
     * Alg sterge(i, e)
     *      daca i < 0 sau i >= n atunci
     *          @throw exception
     *      sfDaca
     *      valoare_veche <- this->e[i]
     *      pentru index <- i, n-1 executa
     *          this->e[index] <- this->e[index+1]
     *      sfPentru
     *      n <- n - 1
     *      return valoare_veche
     * SfAlg
     */
}

bool VectorDinamic::suntUnice() const {
    //O(n^2)
    /*
     * //primele doua elemente nu sunt unice => nu se trece print tot vectorul
     * best_case = theta(1)
     *
     * //
     * average_case =
     *
     *
     * worst_case =
     */

    for ( int i = 0; i < this-> n - 2; i ++ )

        for ( int j = i + 1; j < this-> n - 1; j ++)

            if (element(i) == element(j))

                return false;

    return true;
    /*
     * Alg suntUnice(VectorDinamic v)
     *      pentru i <- 0, v.n -2 executa
     *          pentru i <- i + 1, v.n -1 executa
     *              daca v.element(i) == v.element(j) atunci
     *                  return false
     *              sfDaca
     *          sfPentru
     *      sfPentru
     *      return true
     * SfAlg
     */
}

IteratorVectorDinamic VectorDinamic::iterator() {
    //theta(1)

    //se returneaza iteratorul vectorului dinamic
	return  *this;
    /*
     * Alg IteratorVectorDinamic()
     *      return *this
     * sfAlg
     */
}





