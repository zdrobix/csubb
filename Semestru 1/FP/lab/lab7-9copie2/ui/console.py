import sys
from ui.stopwatch import StopWatch

sys.path.append('C:\\Alex Zdroba\\python projects\\fp\\incercare2_7-9 - Copy\\entities')
    #pt pc de firma
#sys.path.append(r'Q:\info\python\lab7-9copie2')
    #pt pc personal

import configparser

config = configparser.ConfigParser()
config.read('config.ini')
password = config.get('Credentials', 'DATABASE_PASSWORD')

from service.client_service import *
from service.movie_service import *
from service.rental_service import *
from colorama import Fore, Style, init
init()

class Console:
    def __init__(self, client_controller: ClientController, movie_controller: MovieController, rental_controller: RentalController):
        self.__movie_service = movie_controller
        self.__client_service = client_controller
        self.__rental_service = rental_controller

    def __print_menu(self):
        print('1. Add')
        print('2. Delete')
        print('3. Update')
        print('4. Find')
        print('5. Report')
        print('\n6. Rentals')
        print('7. Show rented movies')
        print('8. Random :>')
        print('\nZ. Print all movies')
        print('D. Print all clients')
        print('R. Exit')
        print('\nX. Delete all')
        print('Y. Admin view')

    def add_element_ui(self):
        id = input("\nEnter the ID: ")
        if len(id) == 4:
            try:
                name = input("Enter the name: ")
                cnp = input("Enter the CNP: ")
                self.__client_service.add_client(id, name, cnp)
            except ValueError as e:
                print('ERROR: ' + str(e))
        elif len(id) == 6:
            try:
                title = input("Enter the title: ")
                type = input("Enter the type: ")
                type = type.lower()
                duration = int(input("Enter the duration: "))
                self.__movie_service.add_movie(id, title, type, duration)
            except ValueError as e:
                print('ERROR: ' + str(e))
        else: print(Fore.RED + "The ID must be either 4, either 6 characters long" + Style.RESET_ALL)
          
    def delete_element_ui(self):
        print("Remember, the ID you enter must be either 4, either 6 characters long!")
        id = input("\nEnter the ID of the element you want to delete: ")
        if len(id) == 4:
            try: 
                self.__client_service.delete_client(id)
                print("The client has been deleted.") 
            except ValueError as e:
                print('ERROR: ' + str(e))
        elif len(id) == 6:
            try:
                self.__movie_service.delete_movie(id)
                print("The movie has been deleted.")
            except ValueError as e:
                print('ERROR: ' + str(e))
        else: print(Fore.RED + "The ID must be either 4, either 6 characters long" + Style.RESET_ALL)
         
    def update_element_ui(self):
        id = input("\nEnter the ID of the element you want to update: ")
        if len(id) == 4:
            try:
                new_name = input("Name: ")
                new_cnp = input("CNP: ")
                self.__client_service.update_client(id, new_name, new_cnp)
                print("The client has been updated.")
            except ValueError as e:
                print('ERROR: ' + str(e))
        elif len(id) == 6:
            try:
                new_title = input("Title: ")
                new_type = input("Type: ")
                new_duration = int(input("Duration: "))
                self.__movie_service.update_movie(id, new_title, new_type, new_duration)
                print("The movie has been updated.")
            except ValueError as e:
                print('ERROR: ' + str(e))
        else: print(Fore.RED + "The ID must be either 4, either 6 characters long" + Style.RESET_ALL)
    
    def find_element_ui(self):
        id = input("\nEnter the ID: ")
        if len(id) == 4:
            client = self.__client_service.find_client(id)
            if client is not None:
                print(
                Fore.GREEN + "Name:" + Style.RESET_ALL + ' ' + f"{client.get_name():<22}" + Fore.GREEN + "CNP:" + Style.RESET_ALL + ' ' + f"{client.get_cnp():<15}", end='\n')
            else:
                print("The client you are looking for doesn't exist!")
        elif len(id) == 6:
            movie = self.__movie_service.find_movie(id)
            if movie is not None:
                print(
                Fore.GREEN + "Title:" + Style.RESET_ALL + ' ' + f"{movie.get_title():<30}" + Fore.GREEN + "Type:" + Style.RESET_ALL + ' ' + f"{movie.get_type():<12}",
                end='\n')
            else:
                print("The movie you are looking for doesn't exist!")
        else: print(Fore.RED + "The ID must be either 4, either 6 characters long" + Style.RESET_ALL)

    def rent_element_ui(self):
        id_client = input("\nEnter the ID of the client: ")
        if len(id_client) != 4:
            print(Fore.RED + "The ID must be 4 characters long" + Style.RESET_ALL)
        else:
            id_movie = input("Enter the ID of the movie: ")
            if len(id_movie) != 6:
                print(Fore.RED + "The ID must be 6 characters long" + Style.RESET_ALL)
            else:
                try:
                    self.__rental_service.rent_movie(id_client, id_movie)
                    print("The movie has been rented succesfully.")
                except ValueError as e:
                    print('ERROR: ' + str(e))

    def display_rented_ui(self):
        id = input("\nEnter the ID of a client or movie: ")
        if len(id) == 4:
            if self.__client_service.find_client(id) is not None:
                movies_ids = self.__rental_service.get_all_movies_for_client(id)
                client = self.__client_service.find_client(id)
                if movies_ids != []:
                    print(f"The movies {client.get_name()} has rented are:")
                    for id_movie in movies_ids:
                        movie = self.__movie_service.find_movie(id_movie.strip())
                        if movie is not None:
                            print(
                                Fore.GREEN + "Title:" + Style.RESET_ALL + ' ' + f"{movie.get_title():<30}" + Fore.GREEN + "Type:" + Style.RESET_ALL + ' ' + f"{movie.get_type():<12}", end='\n')
                        else:
                            print(f"Movie with ID {id_movie} not found.", end='\n')
                else:
                    print(f"{client.get_name()} has not rented any movies.")
            else:
                print("The client does not exist.")
        elif len(id) == 6:
            if self.__movie_service.find_movie(id) is not None:
                client_ids = self.__rental_service.get_all_clients_for_movie(id)
                movie = self.__movie_service.find_movie(id)
                if client_ids != []:
                    print(f"The clients who rented {movie.get_title()}:")
                    for id_client in client_ids:
                        client = self.__client_service.find_client(id_client.strip())
                        if client is not None:
                            print( Fore.GREEN + "Name:" + Style.RESET_ALL + ' '+ f"{client.get_name():<22}" + Fore.GREEN + "CNP:" + Style.RESET_ALL + ' ' + f"{client.get_cnp():<15}")
                            print('\n')
                        else:
                            print(f"Client with ID {id_client} not found.", end='\n')
                else:
                    print(f"{movie.get_title()} has not been rented anyone.")

            else:
                print("The movie does not exist.")
        else: print(Fore.RED + "The ID must be either 4, either 6 characters long" + Style.RESET_ALL)

    def report_menu_ui(self):
        print("1. Display the clients in alphabetical order, sorted by number of rented movies")
        print("2. Display the top 10 most rented movies")
        print("3. Display the top 30 % clients with the most rented movies")
        print("4. Display the top 10 least rented movies")
        option_report = input('Enter the option: ')
        option_report = option_report.strip()
        match option_report:
            case '1':
                self.clients_sorted_by_rented_movies()
            case '2':
                self.most_rented_ui()
            case '3':
                self.clients_with_most_movies_30()
            case '4':
                self.least_rented_movies()

    def clients_sorted_by_rented_movies(self):
        sorted_client_list = self.__rental_service.create_tuple_client_alph()
        client_number = len(self.__client_service.get_all_client())
        for index in range(client_number - 1):
            if index < len(sorted_client_list):
                id_client = sorted_client_list[index][0]
                client = self.__client_service.find_client(id_client)
                rented_copies = sorted_client_list[index][1]
                if client is not None:
                    print(
                    Fore.GREEN + "Name:" + Style.RESET_ALL + ' ' + f"{client.get_name():<22}" + Fore.GREEN + "CNP:" + Style.RESET_ALL + ' ' + f"{client.get_cnp():<14}" + Fore.GREEN + "Copies:" + Style.RESET_ALL + ' ' + f"{rented_copies:<3}")

    def clients_with_most_movies_30(self):
        sorted_client_list = self.__rental_service.create_tuple_client()
        client_number = len(self.__client_service.get_all_client())
        for index in range(client_number//3):
            id_client = sorted_client_list[index][0]
            client = self.__client_service.find_client(id_client)
            rented_copies = sorted_client_list[index][1]
            if client is not None:
                print(
                    Fore.GREEN + "Name:" + Style.RESET_ALL + ' ' + f"{client.get_name():<22}" + Fore.GREEN + "CNP:" + Style.RESET_ALL + ' ' + f"{client.get_cnp():<14}" + Fore.GREEN + "Copies:" + Style.RESET_ALL + ' ' + f"{rented_copies:<3}")

    def least_rented_movies(self):
        sorted_movie_list = self.__rental_service.create_tuple_list_least()
        for index in range(10):
            id_movie = sorted_movie_list[index][0]
            movie = self.__movie_service.find_movie(id_movie)
            rented_copies = sorted_movie_list[index][1]
            if movie is not None:
                print(
                    Fore.GREEN + "Title:" + Style.RESET_ALL + ' ' + f"{movie.get_title():<30}" + Fore.GREEN + "Type:" + Style.RESET_ALL + ' ' + f"{movie.get_type():<12}" + "Copies:" + Style.RESET_ALL + ' ' + f"{rented_copies:<3}",
                    end='\n')

    def most_rented_ui(self):
        sorted_movie_list = self.__rental_service.create_tuple_list()
        for index in range(10):
            id_movie = sorted_movie_list[index][0]
            movie = self.__movie_service.find_movie(id_movie)
            rented_copies = sorted_movie_list[index][1]
            if movie is not None:
                print(
                    Fore.GREEN + "Title:" + Style.RESET_ALL + ' ' + f"{movie.get_title():<30}" + Fore.GREEN + "Type:" + Style.RESET_ALL + ' ' + f"{movie.get_type():<12}"  + Fore.GREEN + "Copies:" + Style.RESET_ALL + ' ' + f"{rented_copies:<3}",
                    end='\n')

    def random_clients_ui(self):
        nr = input ("\nHow many random clients do you want to add? ")
        nr = int(nr.strip())
        self.__client_service.rand_client(nr)
    def random_movies_ui(self):
        nr = input ("\nHow many random movies do you want to add? ")
        nr = int(nr.strip())
        self.__movie_service.rand_movie(nr)
    def random_rental_ui(self):
        nr = input("\nHow many random rentals do you want to add? ")
        nr = int(nr.strip())
        client_list = self.__client_service.get_all_client()
        movie_list = self.__movie_service.get_all_movie()
        self.__rental_service.rand_rent(client_list, movie_list, nr)
    def random_menu_id(self):
        print("1. Random clients")
        print("2. Random movies")
        print("3. Random rentals")
        opt = input("Enter option : ")
        opt = opt.strip()
        match opt:
            case '1':
                self.random_clients_ui()
            case '2':
                self.random_movies_ui()
            case '3':
                self.random_rental_ui()

    def print_movies_ui(self):
        stopwatch = StopWatch()
        stopwatch.start()
        for movie in self.__movie_service.get_all_movie():
            print( Fore.GREEN + "Title:" + Style.RESET_ALL + ' '+ f"{movie.get_title():<30}" \
                   + Fore.GREEN + "Type:" + Style.RESET_ALL + ' ' + f"{movie.get_type():<12}")
        print(Fore.GREEN + "Movies: " + Style.RESET_ALL + str(len(self.__movie_service.get_all_movie())))
        stopwatch.stop()

    def print_clients_ui(self):
        for client in self.__client_service.get_all_client():
            print( Fore.GREEN + "Name:" + Style.RESET_ALL + ' '+ f"{client.get_name():<22}" \
                   + Fore.GREEN + "CNP:" + Style.RESET_ALL + ' ' + f"{client.get_cnp():<15}")

    def delete_all_ui(self):
        password_user = input("\nEnter the password : ")
        if password_user == password:
            self.__movie_service.delete_all()
            self.__client_service.delete_all()
            self.__rental_service.delete_all()
            self.__client_service.add_client('gabi', 'Gabriela Serdean', '6050405120000')
            self.__client_service.add_client('alex', 'Alex Zdroba', '5041110125000')
            print("All data has been removed. Except Gabi and Alex.")
        else:
            print("Incorrect password")

    def admin_view_ui(self):
        password_user = input("\nEnter the password : ")
        if password_user == password:
            for client in self.__client_service.get_all_client():
                print(f'{client.get_id_client()}    {client.get_name():<30}  {client.get_cnp():<15}')
            print('\n')
            for movie in self.__movie_service.get_all_movie():
                print(f'{movie.get_id_movie()}  {movie.get_title():<30}  {movie.get_type():<12}\
                  {movie.get_duration():<4}')
            for client in self.__client_service.get_all_client():
                id = client.get_id_client()
                print(f'{id} : {self.__rental_service.get_all_movies_for_client(id)}')
            print('\n')
            for movie in self.__movie_service.get_all_movie():
                id = movie.get_id_movie()
                print(f'{id} : {self.__rental_service.get_all_clients_for_movie(id)}')

        else:
            print("Incorrect password")

    def run(self):
        while True:
            self.__print_menu()
            option = input('\nEnter the option: ')
            match option:
                case '1':
                    self.add_element_ui()
                case '2':
                    self.delete_element_ui()
                case '3':
                    self.update_element_ui()
                case '4':
                    self.find_element_ui()
                case '5':
                    self.report_menu_ui()
                case '6':
                    self.rent_element_ui()
                case '7':
                    self.display_rented_ui()
                case '8':
                    self.random_menu_id()
                case 'Z':
                    self.print_movies_ui()
                case 'D':
                    self.print_clients_ui()
                case 'R':
                    break
                case 'X':
                    self.delete_all_ui()
                case 'Y':
                    self.admin_view_ui()