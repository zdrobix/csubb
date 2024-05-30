from entities.movie import Movie
from colorama import Fore, Back, Style, init
init()

class MovieMemoryRepository:
    def __init__(self, filename):
        self.__movie_list = []
        self.__filename = filename
        self.__load_from_file()

    def __load_from_file(self):
        with open(self.__filename, mode='r') as file:
            lines = file.readlines()
            for line in lines:
                if line == "\n":
                    continue
                line = line.split(',')
                if len(line[0].strip()) == 6:
                    id_movie = str(line[0].strip())
                    title = str(line[1].strip())
                    type = str(line[2].strip())
                    duration = int(line[3].strip())
                    movie = Movie(id_movie, title, type, duration)
                    self.__movie_list.append(movie)

    def store(self, movie: Movie):
        """
        Stores the movie in memory, and checks for double used ID's.
        """
        if self.find(movie.get_id_movie()):
            raise ValueError(Fore.CYAN + "The ID had already been used."+ Style.RESET_ALL)
        self.__movie_list.append(movie)
        self.__save_to_file()

    def __save_to_file(self):
        with open(self.__filename, "w") as file:
            for movie in self.__movie_list:
                str_movie = str(movie.get_id_movie()) + "," + str(movie.get_title()) + "," + str(movie.get_type()) + "," + str(movie.get_duration()) + "\n"
                file.write(str_movie)

    def delete(self, id):
        """
        Deletes the client with the given ID.
        If the ID doesn't exist, an errror appears.
        The deleted client is returned.
        """
        movie_to_delete = self.find(id)
        if movie_to_delete is None:
            raise ValueError(Fore.CYAN + "The movie you want to delete doesn't exist."+ Style.RESET_ALL)
        self.__movie_list.remove(movie_to_delete)
        self.__save_to_file()
        return movie_to_delete

    def update(self, id, title, type, duration):
        movie_to_update = self.find(id)
        if movie_to_update is None:
            raise ValueError(Fore.CYAN + "The movie you want to update doesn't exist."+ Style.RESET_ALL)
        movie_to_update.set_title(title)
        movie_to_update.set_type(type)
        movie_to_update.set_duration(duration)
        self.__save_to_file()
        return movie_to_update
        
    def find(self, id):
        """
        Searches in the element list whether the ID does already exist.
        If it does exist, it returns the element, else, it returns 'None'
        """
        for movie in self.__movie_list:
            if movie.get_id_movie() == id:
             return movie
        return None
    
    def get_all(self):
        """
        Returns the entire list of elements.
        """
        return self.__movie_list

    def delete_all(self):
        self.__movie_list = []
        self.__save_to_file()

    def size(self):
        return len(self.__movie_list)