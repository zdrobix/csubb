

class Song:

    def __init__(self, id, title):
        self.__id = id
        self.__title = title

    def get_id(self):
        return self.__id

    def get_title(self):
        return self.__title.strip().replace('-', ' ')

    def __eq__(self, other):
        if other is None:
            return 0
        return self.get_id() == other.get_id()