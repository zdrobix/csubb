
class RentalSystem:
    def __init__(self, id_client, id_movie):
        self.__id_movie = id_movie
        self.__id_client = id_client

    def get_id_client_rental(self):
        return self.__id_client

    def get_id_movie_rental(self):
        return self.__id_movie




