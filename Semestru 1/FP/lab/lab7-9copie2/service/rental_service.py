from repository.rental_repository import RentalMemoryRepository
from entities.rental import RentalSystem
import random
def sort(list, method = 'quick', key = None, reverse = False):
    """
    Generic sorting function, with optional parameters: key and reverse
    """
    if method == 'quick':
        sorted_list = quick_sort(list, key, reverse)
    elif method == 'gnome':
        sorted_list = gnome_sort(list, key, reverse)
    else:
        raise ValueError("Unknown method")

    return sorted_list

def quick_sort(list, key = None, reverse = False):
    if len(list) <= 1:
        return list
    pivot = key(list[len(list)//2]) if key else list[len(list)//2]
    left_list = [n for n in list if (key(n) if key else n) > pivot]
    mid_list = [n for n in list if (key(n) if key else n) == pivot]
    right_list = [n for n in list if (key(n) if key else n) < pivot]
    if reverse:
        return quick_sort(right_list, key = key, reverse = reverse) + mid_list + quick_sort(left_list, key = key, reverse = reverse)
    return quick_sort(left_list, key = key, reverse = reverse) + mid_list + quick_sort(right_list, key = key, reverse = reverse)

def gnome_sort(list, key = None, reverse = False, i = 0):
    #https://www.sortvisualizer.com/gnomesort/
    if i == len(list):
        if reverse:
            return list[::-1]
        return list
    if i == 0 or (key(list[i - 1]) if key else list[i - 1]) <= (key(list[i]) if key else list[i]):
        return gnome_sort(list, key=key, reverse=reverse, i=i + 1)
    else:
        list[i], list[i - 1] = list[i - 1], list[i]
        return gnome_sort(list, key=key, reverse=reverse, i=max(0, i - 1))


def gnome_sort(data, key=None, reverse=False):
    """
    Implementare a algoritmului de sortare Gnome Sort.
    """
    i = 0
    while i < len(data):
        if i == 0 or (key(data[i-1]) if key else data[i-1]) <= (key(data[i]) if key else data[i]):
            i += 1
        else:
            data[i], data[i-1] = data[i-1], data[i]
            i -= 1

    if reverse:
        return data[::-1]
    else:
        return data

class RentalController:
    def __init__(self, repository: RentalMemoryRepository):
        self.__repo = repository

    def rent_movie(self, id_client, id_movie):
        self.__repo.store(id_client, id_movie)

    def get_all_clients_for_movie(self, id_movie):
        return self.__repo.get_clients(id_movie)

    def get_all_movies_for_client(self, id_client):
        return self.__repo.get_movies(id_client)

    def get_movie_number(self, id_client):
        return self.__repo.get_movie_number(id_client)

    def get_client_number(self, id_movie):
        return self.__repo.get_client_number(id_movie)

    def rand_rent(self, clients, movies,nr):
        added = []
        for i in range(nr):
            client = random.choice(clients)
            movie = random.choice(movies)
            rental = RentalSystem(client.get_id_client(), movie.get_id_movie())
            self.__repo.store(client.get_id_client(), movie.get_id_movie())
            added.append(rental)
        return added

    def create_tuple_list(self):
        list = self.__repo.create_tuple()
        list = sort(list, method = 'gnome', key = lambda movie: movie[1] if movie[1] is not None else 0, reverse=True)
        return list

    def create_tuple_list_least(self):
        list = self.__repo.create_tuple()
        list = sort(list, method = 'gnome', key = lambda movie: movie[1] if movie[1] is not None else 0, reverse = False)
        return list

    def create_tuple_client_alph(self):
        list = self.__repo.create_tuple_clients()
        #list = sort(list, method='quick', key=lambda client: client[0], reverse=True)
        list = sort(list, method = 'gnome', key=lambda client: (client[0],client[1]), reverse=True)
        return list

    def create_tuple_client(self):
        list = self.__repo.create_tuple_clients()
        list = sort(list, method = 'gnome', key=lambda client: client[1], reverse=True)
        return list

    def delete_all(self):
        return self.__repo.delete_all()

    def get_all_rentals(self):
        return self.__repo.get_all()