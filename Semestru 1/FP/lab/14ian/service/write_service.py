from repository.write_repository import WriteMemoryRepository
from entity.artist_song_relation import Write

class WriteController:

    def __init__(self, repo: WriteMemoryRepository):
        self.__repo = repo

    def add_write(self, id_artist, id_song):
        if self.find_write(id_artist, id_song) is not None:
            raise ValueError('Duplicate error.')
        write = Write(id_artist, id_song)
        self.__repo.store(write)

    def delete_write(self, id_artist, id_song):
        write = Write(id_artist, id_song)
        if self.find_write(id_artist, id_song) is  None:
            raise ValueError('Invalid song.')
        return self.__repo.delete(write)

    def find_write(self, id_artist, id_song):
        for write in self.__repo.get_all():
            if write.get_id_song() == id_song and write.get_id_artist() == id_artist:
                return write
        return None

    def get_artist_for_song(self, id_song):
        return self.__repo.get_artist_for_song(id_song)

    def delete_songs_from_artist(self, id_artist):
        id_song_list = []
        for write in self.__repo.get_all():
            if write.get_id_artist() == id_artist:
                id_song_list.append(write.get_id_song())
                self.__repo.delete(write)
        return id_song_list

