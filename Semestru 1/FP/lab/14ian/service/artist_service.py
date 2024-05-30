from repository.artist_repository import ArtistMemoryRepository
from entity.artist import Artist
from entity.validator import ArtistValidator

class ArtistController:

    def __init__(self, repo:ArtistMemoryRepository, validator: ArtistValidator):
        self.__repo = repo
        self.__validator = validator

    def add_artist(self, id, name):
        if self.find_artist(id) is not None:
            raise ValueError('The ID has already been used.')
        else:
            artist = Artist(id, name)
            self.__validator.validate_artist(artist)
            self.__repo.store(artist)

    def delete_artist(self, id):
        return self.__repo.delete(self.find_artist(id))
    def find_artist(self, id):
        for artist in self.__repo.get_all():
            if artist.get_id() == id:
                return artist
        return None

    def search_artist(self, search):
        similar_artists = []
        for artist in self.__repo.get_all():
            if search.lower() in artist.get_name().lower() or artist.get_name().lower() in search.lower():
                similar_artists.append(artist)
        return similar_artists

    def get_all(self):
        return self.__repo.get_all()