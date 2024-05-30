from repo.repository import LocatieRepository
from domain.entity import Locatie


class LocatieController:
    def __init__(self, repo: LocatieRepository):
        self.__repo = repo

    def get_all(self):
        """
        :return: Lista tuturor locatiilor introduse
        """
        return self.__repo.get_all()

    def find_locatie(self, id):
        """
        Cauta in lista de locatii, o locatie dupa ID
        :return: Returneaza locatia daca ea exista, si None daca nu
        """
        return self.__repo.find(id)

    def locatie_acelasi_tip(self, tip):
        """
        Parcurge lista de locatii, si le pune pe cele cu tipul corespunzator intr-o lista
        :param tip: tipul introdus de user
        :return: lista de locatii cu tipul cerut
        """
        list = []
        for locatie in self.__repo.get_all():
            if locatie.get_tip() == str(tip):
                list.append(locatie)
        return list
