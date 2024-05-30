from domain.entity import Locatie

class LocatieRepository:
    def __init__(self, filename):
        self.__filename = filename
        self.__lista_locatii = []
        self.__load_from_file()

    def get_all(self):
        """
        Toate locatiile introduse
        :return: lista cu toate locatiile
        """
        return self.__lista_locatii

    def find(self,id):
        """
        Cauta in lista de locatii, o locatie dupa ID
        :return: Returneaza locatia daca ea exista, si None daca nu
        """
        for locatie in self.__lista_locatii:
            if locatie.get_id() is id:
                return locatie
        return None


    def __load_from_file(self):
        """
        Incarca din fisierul declarat locatiile
        :return: nu returneaza nimic
        """
        with open(self.__filename, mode = "r") as file:
            lines = file.readlines()
            for line in lines:
                line = line.split(',')
                locatie = Locatie(line[0], line[1], line[2], line[3])
                self.__lista_locatii.append(locatie)



