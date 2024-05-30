import datetime


class Truck:
    def __init__(self, id, denumire, pret, model, data):
        self.__id = id
        self.__denumire = denumire
        self.__pret = pret
        self.__model = model
        self.__data = datetime.date.fromisoformat(data)


    def get_id(self):
        return self.__id

    def get_denumire(self):
        return self.__denumire

    def get_pret(self):
        return self.__pret

    def get_model(self):
        return self.__model

    def get_data(self):
        return self.__data


    def __str__(self):
        if self.__data < datetime.date.today():
            return f'ID: {self.__id:<3} Denumire: *{self.__denumire:<15} Pret: {self.__pret:<7} Model: {self.__model:<20} Data: {str(self.__data):<12}'
        else:
            return f'ID: {self.__id:<3} Denumire:  {self.__denumire:<15} Pret: {self.__pret:<7} Model: {self.__model:<20} Data: {str(self.__data):<12}'