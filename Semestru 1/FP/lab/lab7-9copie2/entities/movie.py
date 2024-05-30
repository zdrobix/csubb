from colorama import Fore, Back, Style, init
init()

class Movie:
    def __init__(self, id_movie, title, type, duration):
        self.__id_movie = id_movie
        self.__title = title
        self.__type = type
        self.__duration = duration

    def get_id_movie(self):
        return str(self.__id_movie)

    def get_title(self):
        return self.__title

    def get_type(self):
        return self.__type

    def get_duration(self):
        return int(self.__duration)

    def set_id_movie(self, new_id_movie):
        self.__id_movie = new_id_movie

    def set_title(self, new_title):
        self.__title = new_title

    def set_type(self, new_type):
        self.__type = new_type

    def set_duration(self, new_duration):
        self.__duration = new_duration

    def __eq__(self, movie):
        return self.get_id_movie() == movie.get_id_movie()

    def __str__(self):
        return "ID: " + str(self.get_id_movie()) + " Title: " + str(self.get_title()) + " Type: " + str(self.get_type()) + " Duration: " + str(self.get_duration())




        

