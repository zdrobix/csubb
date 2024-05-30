from practic_tractoare.domain.trucks import Truck
class TruckController:
    def __init__(self, repository):
        self.__repo = repository

    def add_truck(self, id, denumire, pret, model, data):
        truck = Truck(id, denumire, pret, model, data)
        self.__repo.add(truck)
        return truck

    def delete(self, number):
        deleted_trucks = []
        for truck in self.__repo.get_all():
            if str(number) in str(truck.get_pret()):
                deleted_trucks.append(truck)
                self.__repo.delete(truck)
        return deleted_trucks

    def undo_add(self, truck):
        self.__repo.delete(truck)

    def undo_delete(self, truck_list):
        for truck in truck_list:
            self.__repo.add(truck)


    def filter(self, search, price):
        truck_list = []
        for truck in self.__repo.get_all():
            if search != '' and price == -1:
                if str(search) in str(truck.get_denumire()):
                    truck_list.append(truck)
            if search != '' and price != -1:
                if str(search) in str(truck.get_denumire()):
                    if int(truck.get_pret()) <= price:
                        truck_list.append(truck)
            if search == '' and price != -1:
                if int(truck.get_pret()) <= price:
                    truck_list.append(truck)

        return truck_list




