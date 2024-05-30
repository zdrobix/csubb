from repository.song_repository import SongMemoryRepository
from entity.song import Song
from entity.validator import SongValidator

class SongController:

    def __init__(self, repo:SongMemoryRepository, validator: SongValidator):
        self.__repo = repo
        self.__validator = validator

    def add_song(self, id, name):
        if self.find_song(id) is not None:
            raise ValueError('The ID has already been used.')
        else:
            song = Song(id, name)
            self.__validator.validate_song(song)
            self.__repo.store(song)

    def delete_song(self, id):
        if self.find_song(id) is None:
            raise ValueError('The song you want to delete does not exist.')
        return self.__repo.delete(self.find_song(id))
    def find_song(self, id):
        for song in self.__repo.get_all():
            if song.get_id() == id:
                return song
        return None

    def get_all(self):
        return self.__repo.get_all()



