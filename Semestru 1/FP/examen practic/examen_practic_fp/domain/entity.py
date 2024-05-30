
class Melodie:
    def __init__(self, titlu, artist, gen, data):
        self.__titlu = titlu
        self.__artist = artist
        self.__gen = gen
        self.__data = data

    def get_titlu(self):
        return self.__titlu

    def get_artist(self):
        return self.__artist

    def get_gen(self):
        return self.__gen

    def get_data(self):
        return self.__data

    def set_gen(self, new_gen):
        self.__gen = new_gen

    def set_data(self, new_data):
        self.__data = new_data

    def __str__(self):
        return f'{self.__titlu:<10}{self.__artist:<12}{self.__gen:<7}{self.__data:<11}'

    def __cmp__(self, other):
        """"
        pentru a compara datele, impart in zi luna si an datele
        apoi se executa diferenta dintre acestea, iar daca diferenta anilor este negativa => other este mai mare
        """
        data_melodie = self.__data.split('.')
        data_other = other.split('.')
        dif_zi = data_melodie[0] - data_other[0]
        dif_luna = data_melodie[1] - data_other[1]
        dif_an = data_melodie[2] - data_other[2]

        if dif_an < 0:
            return self.__data < other
        if dif_an > 0:
            return self.__data > other
        if dif_an == 0:
            if dif_luna < 0:
                return self.__data < other
            if dif_luna > 0:
                return self.__data > other
            if dif_luna == 0:
                if dif_zi < 0:
                    return self.__data < other
                if dif_zi > 0:
                    return self.__data > other
            else:
                return self.__data == other
