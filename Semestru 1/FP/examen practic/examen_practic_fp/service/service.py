from repository.repository import MelodieRepository
from domain.entity import Melodie
import random

class MelodieController:
    def __init__(self, repository: MelodieRepository):
        self.__repo = repository

    def verificare_date(self, titlu, artist, gen, data):
        """
        daca se vor introduce aceleasi date pentru gen si data, se va afisa eroare

        daca se va introduce o data invalida, sau un gen invalid, se va afisa eroarea

        erorile se vor returna sub forma de string
        """
        errors = ''
        if gen not in ['Rock', 'Pop', 'Jazz']:
            errors += 'Ati introdus un gen invalid.\n'

        zi_data = int(data.split('.')[0])
        luna_data = int(data.split('.')[1])
        an_data = int(data.split('.')[2])

        if zi_data > 31 or luna_data > 12 or an_data < 1900:
            errors += 'Ati introdus o data invalida.\n'

        melodie = self.__repo.find(titlu, artist)
        if melodie is None:
            errors += 'Melodia nu a fost gasita.\n'
        else:
            if melodie.get_data == data and melodie.get_gen == gen:
                errors += 'Datele introduse sunt la fel!\n'

        return errors

    def modifica_melodie(self, titlu, artist, gen, data):
        """
        modifica genul si data melodiei specificate. melodia este identificata dupa titlu si artist.
        inainte de a modifica, datele se verifica folosind functia verifica_date
        """
        errors = self.verificare_date(titlu, artist, gen, data)
        if errors == '':
            melodie = self.__repo.find(titlu, artist)
            self.__repo.update(melodie, gen, data)
        else:
            raise ValueError(errors)

    def creare_aleator_melodii(self, numar, lista_titluri, lista_artisti):
        """
        folosind random, se genereaza doua numere la intamplare de la 1 la n, si se alege
        cate un artist si un titlu din listele input.

        astfel alegem si un gen si data la intamplare, si adaugam melodia in memorie
        """
        lista_melodii = []
        while numar > 0:
            nr_artist = random.randint(0, numar - 1)
            nr_titlu = random.randint(0, numar - 1)

            rand_artist = lista_artisti[nr_artist]
            rand_titlu = lista_titluri[nr_titlu]
            #se salveaza artistul si piesa alese la intamplare

            lista_titluri.remove(rand_titlu)
            lista_artisti.remove(rand_artist)
            numar = numar - 1
            #se sterge artistul si piesa deja alese
            #se scade din numarul pieselor si artistilor ramasi

            rand_gen = random.choice(['Rock', 'Pop', 'Jazz'])
            rand_data = str(random.randint(1, 31)) + '.' + str(random.randint(1,12)) + '.' + str(random.randint(1900, 2024))

            melodie = Melodie(rand_titlu, rand_artist, rand_gen, rand_data)
            lista_melodii.append(melodie)
            self.__repo.store(melodie)
        return lista_melodii

    def export_melodii_fisier(self, nume_fisier):
        """
        creeaza fisierul cu numele specificat de utilizator, apoi apeleaza functia de merge sort in functie de data melodiei
        :param nume_fisier:
        :return:
        """
        lista_melodii = self.get_all()
        lista_melodii_sortate = self.__repo.merge_sort(lista_melodii)
        with open(nume_fisier, mode = 'x' ) as file:
            for melodie in lista_melodii_sortate:
                string_melodie = f'{melodie.get_titlu()},{melodie.get_artist()},{melodie.get_gen()},{melodie.get_data()}\n'
                file.write(string_melodie)



    def get_all(self):
        """
        returneaza intreaga lista de melodi
        :return:
        """
        return self.__repo.get_all()

