
class Console:
    def __init__(self, controller):
        self.__truck_service = controller
    def print_menu(self):
        print('1. Add\n2.Delete\n3.Filter\n4.Undo\n\n5.Exit')

    def add_menu_ui(self):
        print('You selected the option: Add truck.')
        id = input('Enter the ID : ')
        denumire = input('Enter the name : ')
        pret = input('Enter the price: ')
        model = input('Enter the model: ')
        data = input('Enter the revision date: ')
        self.added_truck = self.__truck_service.add_truck(id, denumire, pret, model, data)
        self.add_or_delete = 'add'


    def delete_menu_ui(self):
        print('You selected the option: Delete truck.')
        print('Enter a number, and the trucks that have prices that contain that number will be deleted.')
        print('Enter X if you have changed your mind.')
        number = input('Enter a number: ')
        if number in ['x', 'X']:
            print('The deletion was succesfully canceled.')
        else:
            deleted_trucks = self.__truck_service.delete(number)
            if deleted_trucks == []:
                print('No trucks have been deleted.')
            else:
                if len(deleted_trucks) == 1:
                    print('A truck has been deleted succesfully.')
                else:
                    print(f'{len(deleted_trucks)} have been deleted succesfully.')
                self.deleted_trucks_undo = deleted_trucks
                self.add_or_delete = 'delete'

    def filter_menu_ui(self):
        print('You selected the option: Filter trucks.')
        print('Enter a text sequence if you want to filter by name and model, or leave the text empty.')
        print('Enter a number if you want to filter by price, or -1 if you do not.')
        search_filter = input('Search: ')
        price_filter = int(input('Price: '))
        if search_filter == '' and price_filter == -1:
            print('Invalid filter.')
        else:
            if search_filter != '' and price_filter != -1:
                print(f'Searching for: {search_filter}. Price under: {price_filter}.')
            if search_filter != '' and price_filter == -1:
                print(f'Searching for: {search_filter}.')
            if search_filter == '' and price_filter != -1:
                print(f'Price under: {price_filter}.')

            truck_filter_result = self.__truck_service.filter(search_filter, price_filter)
            if truck_filter_result is []:
                print('No results.')
            else:
                for truck in truck_filter_result:
                    print(truck)

    def undo_menu_ui(self):
        if self.add_or_delete is None:
            raise ValueError('Invalid use of undo operation.')
        if self.add_or_delete == 'add' and self.added_truck is None:
            raise ValueError('Cannot perform undo operation: No truck has been added.')
        if self.add_or_delete == 'delete' and self.deleted_trucks_undo == []:
            raise ValueError('Cannot perform undo operation: No trucks have been deleted.')

        if self.add_or_delete == 'add':
            self.__truck_service.undo_add(self.added_truck)
            added_truck = None
            deleted_trucks_undo = []
            add_or_delete = None
        elif self.add_or_delete == 'delete':
            self.__truck_service.undo_delete(self.deleted_trucks_undo)
            added_truck = None
            deleted_trucks_undo = []
            add_or_delete = None



    added_truck = None
    deleted_trucks_undo = []
    add_or_delete = None

    def run(self):
        while True:
            self.print_menu()
            option = int(input('Enter option : '))
            if option == 1:
                self.add_menu_ui()
            elif option == 2:
                self.delete_menu_ui()
            elif option == 3:
                self.filter_menu_ui()
            elif option == 4:
                self.undo_menu_ui()
            elif option == 5:
                break
            else: print('Invalid option')

