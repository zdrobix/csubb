from entities.rental import RentalSystem
from colorama import Fore, Back, Style, init
init()

class RentalMemoryRepository:
    def __init__(self, filename):
        self.__rental_list = []
        self.__filename = filename
        self.__load_from_file()


    def __load_from_file(self):
        with open(self.__filename, mode = 'r') as file:
            lines = file.readlines()
            for line in lines:
                if line == '\n':
                    continue
                if line != '':
                    line = line.split(',')
                    id_client = line[0]
                    id_movie = line[1]
                    self.store(id_client, id_movie)


    def store(self, id_client, id_movie):
        rental = RentalSystem(id_client, id_movie)
        if self.find(id_client, id_movie):
            raise ValueError(Fore.CYAN + "This movie has already been rented by the client." + Style.RESET_ALL)
        self.__rental_list.append(rental)
        self.__save_to_file()

    def find(self, id_client, id_movie):
        if RentalSystem(id_client, id_movie) in self.__rental_list:
            return RentalSystem(id_client, id_movie)
        return None

    def __save_to_file(self):
        with open(self.__filename, "w") as file:
            for rental in self.__rental_list:
                str_rental = f"{rental.get_id_client_rental()},{rental.get_id_movie_rental()}\n"
                file.write(str_rental)

    def get_clients(self, id_movie):
        client_list = []
        for rental in self.__rental_list:
            if rental.get_id_movie_rental() == id_movie:
                if rental.get_id_client_rental() not in client_list:
                    client_list.append(rental.get_id_client_rental())
        return client_list
    def get_movies(self, id_client):
        movie_list = []
        for rental in self.__rental_list:
            if rental.get_id_client_rental() == id_client:
                if rental.get_id_movie_rental() not in movie_list:
                    movie_list.append(rental.get_id_movie_rental())
        return movie_list

    def get_movie_number(self, id_movie):
        nr = 0
        for rental in self.__rental_list:
            if rental.get_id_movie_rental().strip() == id_movie:
                nr += 1
        return nr
                
    def get_client_number(self, id_client):
        nr = 0
        for rental in self.__rental_list:
            if rental.get_id_client_rental() == id_client:
                nr += 1
        return nr

    def create_tuple(self):
        list = []
        for rental in self.__rental_list:
            id_movie = rental.get_id_movie_rental().strip()
            tuple = (id_movie, self.get_movie_number(id_movie))
            if tuple not in list:
                list.append(tuple)
        return list
    def create_tuple_clients(self):
        list = []
        for rental in self.__rental_list:
            id_client = rental.get_id_client_rental().strip()
            tuple = (id_client, self.get_client_number(id_client))
            if tuple not in list:
                list.append(tuple)
        return list

    def delete_all(self):
        self.__rental_list = []
        self.__save_to_file()

    def size(self):
        return len(self.__rental_list)

    def get_all(self):
        return self.__rental_list


