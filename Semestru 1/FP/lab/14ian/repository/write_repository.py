from entity.artist_song_relation import Write

class WriteMemoryRepository:

    def __init__(self, filename):
        self.__filename = filename
        self.__write_list = []
        self.__load_file()

    def __load_file(self):
        with open(self.__filename, 'r') as file:
            lines = file.readlines()
            for line in lines:
                if line == '\n':
                    continue
                line = line.split('/')
                id_artist = int(line[0])
                id_song = int(line[1])
                write = Write(id_artist, id_song)
                self.store(write)


    def __save_file(self):
        with open(self.__filename, 'w') as file:
            for write in self.__write_list:
                line = str(write.get_id_artist()) + '/' + str(write.get_id_song()) + '\n'
                file.write(line)

    def store(self, write):
        self.__write_list.append(write)
        self.__save_file()

    def delete(self, write):
        self.__write_list.remove(write)
        self.__save_file()

    def get_all(self):
        return self.__write_list

    def get_artist_for_song(self, id_song):
        id_artists = []
        for write in self.__write_list:
            if write.get_id_song() == id_song:
                id_artists.append(write.get_id_artist())
        return id_artists


