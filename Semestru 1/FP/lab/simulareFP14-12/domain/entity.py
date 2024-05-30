
class Locatie:
    def __init__(self, id: int, nume: str, tip: str, pret_zi: int):
        self.__id = id
        self.__nume = nume
        self.__tip = tip
        self.__pret_zi = pret_zi

    def get_id(self):
        return self.__id

    def get_nume(self):
        return self.__nume

    def get_tip(self):
        return self.__tip

    def get_pret(self):
        return int(self.__pret_zi)

class BookingInquiry:
    def __init__(self, locatie: Locatie, buget: int):
        self.__locatie = locatie
        self.__buget = buget

    def get_number_of_days(self):
        """
        Impartirea reala dintre buget si costul locatiei.
        :return:
        """
        return self.__buget // self.__locatie.get_pret()

