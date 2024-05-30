from domain.entity import Melodie

class MelodieRepository:
    def __init__(self, filename):
        self.__filename = filename
        self.__melodie_lista = []
        self.__load_from_file()

    def __load_from_file(self):
        """
        incarca din fisierul dat toate melodiile, care sunt separate prin '/'
        :return:
        """
        with open(self.__filename, mode = 'r') as file:
            lines = file.readlines()
            for line in lines:
                if line == '':
                    continue
                line = line.strip()
                line = line.split('/')
                titlu = line[0]
                artist = line[1]
                gen = line[2]
                data = line[3]
                melodie = Melodie(titlu, artist, gen, data)
                self.store(melodie)

    def __save_to_file(self):
        """
        salveaza in fisierul dat toate melodiile, si le separa prin '/', pentru ca acestea sa poata fi citite
        :return:
        """
        with open(self.__filename, mode = 'w' ) as file:
            for melodie in self.__melodie_lista:
                string_melodie = f'{melodie.get_titlu()}/{melodie.get_artist()}/{melodie.get_gen()}/{melodie.get_data()}\n'
                file.write(string_melodie)

    def store(self, melodie: Melodie):
        """
        adauga in memorie o melodie
        :param melodie:
        :return:
        """
        self.__melodie_lista.append(melodie)
        self.__save_to_file()

    def find(self, titlu, artist):
        """
        gaseste o melodie, pe baza titlului si artistului. daca melodia nu este gasita, se va returna None
        """
        for melodie in self.__melodie_lista:
            if titlu == melodie.get_titlu() and artist == melodie.get_artist():
                return melodie
        return None

    def update(self, melodie, gen, data):
        melodie.set_gen(gen)
        melodie.set_data(data)
        self.__save_to_file()

    def interclasare(self, left, right):
        """
        algoritm de intergclasare pentru functia merge sort
        :param left: o lista
        :param right: o alta lista
        :return: lista interclasata
        """
        i = 0
        j = 0
        lista_intreaga = []
        while i < len(left) and j < len(right):
            if left[i].get_data() < right[j].get_data():
                lista_intreaga.append(left[i])
                i += 1
            else:
                lista_intreaga.append(right[j])
                j += 1

        while i < len(left):
            lista_intreaga.append(left[i])
            i += 1
        while j < len(right):
            lista_intreaga.append(right[j])
            j += 1

        return lista_intreaga

    def merge_sort(self, list):
        """
        sorteaza lista de melodii in functie de data aparitiei
        :return:
        """
        if len(list) == 1:
            return list

        mid = len(list) // 2
        return self.interclasare( self.merge_sort(list[:mid]), self.merge_sort(list[mid:]) )


    def get_all(self):
        """
        returneaza toata lista de melodii din memorie
        :return:
        """
        return self.__melodie_lista

    def delete_all(self):
        """
        se sterg toate melodiile din lista din memorie
        :return:
        """
        self.__melodie_lista = []
        self.__save_to_file()