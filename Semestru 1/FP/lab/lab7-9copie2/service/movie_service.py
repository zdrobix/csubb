from entities.movie import Movie
from repository.movie_repository import MovieMemoryRepository
from entities.validator import MovieValidator
import random

class MovieController:
    def __init__(self, repository: MovieMemoryRepository, movie_validator: MovieValidator):
        self.__movie_validator = movie_validator
        self.__repo = repository

    def add_movie(self, id: str, title: str, type: str, duration: int):
        movie = Movie(id, title, type, duration)
        self.__movie_validator.validate_movie(movie)
        self.__repo.store(movie)

    def delete_movie(self, id: str):
        return self.__repo.delete(id)

    def update_movie(self, id: str, new_title: str, new_type: str, new_duration: int):
        self.__movie_validator.validate_movie(Movie(id, new_title, new_type, new_duration))
        return self.__repo.update(id, new_title, new_type, new_duration)

    def find_movie(self, id: str):
        return self.__repo.find(id)

    def get_all_movie(self):
        return self.__repo.get_all()

    def rand_movie(self, nr):
        added = []
        for i in range(nr):
            title_words = random.choice([1, 2, 4, 5])

            id = ""
            for j in range (0, 6):
                id += random.choice(
                ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
                'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '@', '!', '#', '$', '%', '&', '*', '+'])
                if j == 6 and self.__repo.find(id) is not None:
                    id = ""
                    j = 0

            title = ""
            if title_words == 1:
                for j in range(random.randint(4, 12)):
                    if j == 0:
                        title += random.choice(
                        ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'P', 'R', 'Z', 'S'])
                    elif j % 2 == 0:
                        title += random.choice(['a', 'e', 'i', 'o', 'u'])
                    else:
                        title += random.choice(
                        ['b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y',
                        'z'])
            if title_words == 2:
                for j in range(random.randint(4, 10)):
                    if j == 0:
                        title += random.choice(
                        ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'P', 'R', 'Z', 'S'])
                    elif j % 2 == 0:
                        title += random.choice(['a', 'e', 'i', 'o', 'u'])
                    else:
                        title += random.choice(
                        ['b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y',
                        'z'])
                title += random.choice([' I', ' II', ' III', ' IV', ' V', ' X'])
            if title_words == 4 or title_words == 5:
                for j in range(random.randint(4, 10)):
                    if j == 0:
                        title += random.choice(
                        ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'P', 'R', 'Z', 'S'])
                    elif j % 2 == 0:
                        title += random.choice(['a', 'e', 'i', 'o', 'u'])
                    else:
                        title += random.choice(
                        ['b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y',
                        'z'])
                title += random.choice([' at the ', ' at ', ' of the ', ' of ', ' in the ' ,' in ', ' from the ', ' from ', ' among the ', ' among '])
                for j in range(random.randint(3, 7)):
                    if j == 0:
                        title += random.choice(
                        ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'P', 'R', 'Z', 'S'])
                    elif j % 2 == 0:
                        title += random.choice(['a', 'e', 'i', 'o', 'u'])
                    else:
                        title += random.choice(
                        ['b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y',
                        'z'])
                if title_words == 5:
                    title += random.choice([' I', ' II', ' III', ' IV', ' V', ' X'])
            duration = random.randint(100, 199)
            type = random.choice(['action', 'horror', 'comedy', 'drama', 'family', 'romance', 'musical', 'war'])

            movie =Movie(id, title, type, duration)
            added.append(movie)
            self.__movie_validator.validate_movie(movie)
            self.__repo.store(movie)
        return added

    def delete_all(self):
        self.__repo.delete_all()