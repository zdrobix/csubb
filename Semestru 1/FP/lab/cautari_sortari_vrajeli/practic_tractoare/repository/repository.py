from practic_tractoare.domain.trucks import Truck
class TruckMemoryRepository:
    def __init__(self, filename):
        self.__filename = filename
        self.__truck_list = []
        self.__load_from_file()


    def __load_from_file(self):
        file = self.__filename
        with open(file, mode = 'r') as input_file:
            lines = input_file.readlines()
            for line in lines:
                line = line.strip()
                line = line.split('/')
                if line != '':
                    id = line[0]
                    denumire = line[1]
                    pret = line[2]
                    model = line[3]
                    data = line[4]
                    truck = Truck(id, denumire, pret, model, data)
                    self.add(truck)

    def __save_to_file(self):
        file = self.__filename
        with open(file, mode='w') as input_file:
            for truck in self.__truck_list:
                id = str(truck.get_id())
                denumire = str(truck.get_denumire())
                pret = str(truck.get_pret())
                model = str(truck.get_model())
                data = str(truck.get_data())
                input_file.write(f'{id}/{denumire}/{pret}/{model}/{data}\n')


    def add(self, truck):
        self.__truck_list.append(truck)
        self.__save_to_file()

    def delete(self, truck):
        self.__truck_list.remove(truck)
        self.__save_to_file()

    def filter(self):
        pass

    def get_all(self):
        return self.__truck_list

    def delete_all(self):
        self.__truck_list = []
        self.__save_to_file()