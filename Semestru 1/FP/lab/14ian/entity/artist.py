

class Artist:

    def __init__(self, id, name):
        self.__id = id
        self.__name = name

    def get_id(self):
        return self.__id

    def get_name(self):
        return self.__name.strip().replace('-',' ')

    def __eq__(self, other):
        if other is None:
            return 0
        return self.get_id() == other.get_id()
