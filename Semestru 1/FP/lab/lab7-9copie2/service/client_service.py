from entities.client import Client
from repository.client_repository import ClientMemoryRepository
from entities.validator import ClientValidator
import random

class ClientController:
    def __init__(self, repository: ClientMemoryRepository, client_validator: ClientValidator):
        self.__client_validator = client_validator
        self.__repo = repository

    def add_client(self, id: str, name: str, cnp: str):
        client = Client(id, name, cnp)
        self.__client_validator.validate_client(client)
        self.__repo.store(client)

    def delete_client(self, id: str):
        return self.__repo.delete(id)

    def update_client(self, id: str, new_name: str, new_cnp: str):
        self.__client_validator.validate_client(Client(id, new_name, new_cnp))
        return self.__repo.update(id, new_name, new_cnp)

    def find_client(self, id: str):
        return self.__repo.find(id)

    def get_all_client(self):
        return self.__repo.get_all()

    def rand_client(self, nr):
        added = []
        for i in range(nr):
            name_lenght = random.randint(4, 10)
            id = ""
            j = 1
            while j <= 4:
                id += random.choice(
                ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', \
                     '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '@', '!', '#', '$', '%', '&', '*', '+'])
                j = j + 1
                if j == 5 and self.__repo.find(id) is not None:
                    id = ""
                    j = 0


            name = ""
            for j in range(name_lenght):
                if j == 0:
                    name += random.choice(
                    ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'P', 'R', 'Z', 'S'])
                elif j % 2 == 0 and j != 0:
                    name += random.choice(['a', 'e', 'i', 'o', 'u'])
                else:
                    name += random.choice(
                    ['b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y',
                     'z'])
            name += ' '
            for j in range(12 - name_lenght):
                if j == 0:
                    name += random.choice(
                    ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'P', 'R', 'Z', 'S'])
                elif j % 2 == 0 and j != 0:
                    name += random.choice(['a', 'e', 'i', 'o', 'u'])
                else:
                    name += random.choice(
                    ['b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y',
                     'z'])
            name += random.choice(['anu', 'escu', 'covsky', 'ean', 'aus', 'van', 'son', '-Yong', 'etcher', 'kins', 'Xi', 'taka', 'scu', 'ham', 'tar', 'gor', 'ret', 'asa', 'oba'])

            cnp = ""
            cnp_first = random.choice(['1', '2', '5', '6'])
            cnp += cnp_first
            if cnp_first in ['1', '2']:
                cnp_year = str(random.randint(0, 99))
                if int(cnp_year) < 10:
                    cnp += '0'
                    cnp += cnp_year
                else:
                    cnp += cnp_year
            else:
                cnp_year = str(random.randint(0, 23))
                if int(cnp_year) < 10:
                    cnp += '0'
                    cnp += cnp_year
                else:
                    cnp += cnp_year
            cnp_day = str(random.randint(1, 31))
            cnp_month = str(random.randint(1, 12))
            if cnp_month == '2' and cnp_day == '29':
                if int(cnp_year) % 4 != 0:
                    cnp_day = '28'
            if cnp_month in ['2', '4', '6', '9', '11']:
                if int(cnp_day) > 30:
                    cnp_day = str(int(cnp_day) - 4)
            if int(cnp_month) < 10:
                cnp += '0'
                cnp += cnp_month
            else:
                cnp += cnp_month
            if int(cnp_day) < 10:
                cnp += '0'
                cnp += cnp_day
            else:
                cnp += cnp_day
            cnp_location = str(random.randint(1, 48))
            if int(cnp_location) < 10:
                cnp += '0'
                cnp += cnp_location
            else:
                cnp += cnp_location
            cnp_nnn = str(random.randint(500, 999))
            cnp += cnp_nnn
            cnp += str(random.randint(0, 9))

            client = Client(id, name, cnp)
            added.append(client)
            self.__client_validator.validate_client(client)
            self.__repo.store(client)
        return added


    def delete_all(self):
        return self.__repo.delete_all()