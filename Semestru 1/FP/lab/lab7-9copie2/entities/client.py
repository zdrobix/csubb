from colorama import Fore, Back, Style, init
init()

class Client:
    def __init__(self, id_client, name, cnp):
        self.__id_client = id_client
        self.__name = name
        self.__cnp = cnp

    def get_id_client(self):
        return self.__id_client

    def get_name(self):
        return self.__name

    def get_cnp(self):
        return self.__cnp

    def set_id_client(self, new_id_client):
        self.__id_client = new_id_client

    def set_name(self, new_name):
        self.__name = new_name

    def set_cnp(self, new_cnp):
        self.__cnp = new_cnp

    def __eq__(self, client):
        return self.get_id_client() == client.get_id_client()

    def __str__(self):
        return  "ID: " + str(self.get_id_client()) + " Name: " + str(self.get_name()) +  " CNP: " + str(self.get_cnp())
