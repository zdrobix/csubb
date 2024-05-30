from entities.client import Client
from colorama import Fore, Back, Style, init
init()

class ClientMemoryRepository:
    def __init__(self, filename):
        self.__client_list = []
        self.__filename = filename
        self.__load_from_file()

    def __load_from_file(self):
        with open(self.__filename, mode='r') as file:
            lines = file.readlines()
            for line in lines:
                if line == "\n":
                    continue
                line = line.split(',')
                if len(line[0].strip()) == 4:
                    id_client = str(line[0].strip())
                    name = str(line[1].strip())
                    cnp = str(line[2].strip())
                    client = Client(id_client, name, cnp)
                    self.store(client)

    def store(self, client: Client):
        """
        Stores the client in memory, and checks for double used ID's.
        """
        if self.find(client.get_id_client()):
            raise ValueError(Fore.CYAN + "The ID had already been used." + Style.RESET_ALL)
        self.__client_list.append(client)
        self.__save_to_file()

    def __save_to_file(self):
        with open(self.__filename, "w") as file:
            for client in self.__client_list:
                str_client = str(client.get_id_client()) + "," + str(client.get_name()) + "," + str(
                    client.get_cnp()) + "\n"
                file.write(str_client)

    def delete(self, id):
        """
        Deletes the client with the given ID.
        If the ID doesn't exist, an errror appears.
        The deleted client is returned.
        """
        client_to_delete = self.find(id)
        if client_to_delete is None:
            raise ValueError(Fore.CYAN + "The client you want to delete doesn't exist." + Style.RESET_ALL)
        self.__client_list.remove(client_to_delete)
        self.__save_to_file()
        return client_to_delete

    def update(self, id, name, cnp):
        client_to_update = self.find(id)
        if client_to_update is None:
            raise ValueError(Fore.CYAN + "The client you want to update doesn't exist." + Style.RESET_ALL)
        client_to_update.set_name(name)
        client_to_update.set_cnp(cnp)
        self.__save_to_file()
        return client_to_update

    def find(self, id):
        """
        Searches in the element list whether the ID does already exist.
        If it does exist, it returns the element, else, it returns 'None'
        """
        for client in self.__client_list:
            if client.get_id_client() == id:
                return client
        return None

    def get_all(self):
        """
        Returns the entire list of elements.
        """
        return self.__client_list

    def delete_all(self):
        self.__client_list = []
        self.__save_to_file()

    def size(self):
        return len(self.__client_list)