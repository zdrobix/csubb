from entity.song import Song

class SongMemoryRepository:

    def __init__(self, filename):
        self.__filename = filename
        self.__song_list = []
        self.__load_file()

    def __load_file(self):
        with open(self.__filename, 'r') as file:
            lines = file.readlines()
            for line in lines:
                if line == '\n':
                    continue
                line = line.split('/')
                id = int(line[0])
                title = str(line[1])
                song = Song(id, title)
                self.store(song)


    def __save_file(self):
        with open(self.__filename, 'w') as file:
            for song in self.__song_list:
                line = str(song.get_id()) + '/' + str(song.get_title()) + '\n'
                file.write(line)

    def store(self, song):
        self.__song_list.append(song)
        self.__save_file()

    def delete(self, song):
        if song is None:
            raise ValueError('The song you want to delete does not exist')
        self.__song_list.remove(song)
        self.__save_file()
        return song


    def get_all(self):
        return self.__song_list

    def get_id_list(self):
        list = []
        for song in self.__song_list:
            list.append(song.get_id())



