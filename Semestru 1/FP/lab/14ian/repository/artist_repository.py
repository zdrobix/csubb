from entity.artist import Artist

class ArtistMemoryRepository:

    def __init__(self, filename):
        self.__filename = filename
        self.__artist_list = []
        self.__load_file()

    def __load_file(self):
        with open(self.__filename, 'r') as file:
            lines = file.readlines()
            for line in lines:
                if line == '\n':
                    continue
                line = line.split('/')
                id = int(line[0])
                name = str(line[1])
                artist = Artist(id, name)
                self.store(artist)


    def __save_file(self):
        with open(self.__filename, 'w') as file:
            for artist in self.__artist_list:
                line = str(artist.get_id()) + '/' + str(artist.get_name()) + '\n'
                file.write(line)

    def store(self, artist):
        self.__artist_list.append(artist)
        self.__save_file()

    def delete(self, artist):
        self.__artist_list.remove(artist)
        self.__save_file()
        return artist


    def get_all(self):
        return self.__artist_list

    def get_id_list(self):
        list = []
        for artist in self.__artist_list:
            list.append(artist.get_id())



