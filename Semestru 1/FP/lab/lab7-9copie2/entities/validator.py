from colorama import Fore, Back, Style, init
init()

class MovieValidator:
    def __init__(self):
        pass

    def validate_movie(self, movie):
        errors = []
        ok = 1
        if len(movie.get_id_movie()) != 6:
            errors.append(Fore.CYAN + "Invalid ID. "+ Style.RESET_ALL)
        else:
            for index in range (0, 5):
                if movie.get_id_movie()[index] not in ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', \
                         '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '@', '!', '#', '$', '%', '&', '*', '+']:
                    ok = 0

        if ok == 0:
            errors.append(Fore.CYAN + "Invalid character in ID. " + Style.RESET_ALL)

        if movie.get_title() == "":
            errors.append(Fore.CYAN + "Invalid name, cannot have blank name. " + Style.RESET_ALL)

        if movie.get_duration() < 2:
            errors.append(Fore.CYAN + "There are no movies shorter than 2 mins. "+ Style.RESET_ALL)
            
        if movie.get_type() not in ['action', 'horror', 'comedy', 'drama', 'family', 'romance', 'musical', 'war']:
            errors.append(Fore.CYAN + "Invalid movie type. "+ Style.RESET_ALL)

        if len(errors) > 0:
            error_string = '\n'.join(errors)
            raise ValueError(error_string)
        

class ClientValidator:
    def __init__(self):
        pass

    def validate_client(self, client):
        errors = []
        ok = 1
        if len(client.get_id_client()) != 4:
            errors.append(Fore.CYAN + "Invalid ID. "+ Style.RESET_ALL)
        else:
            for index in range (0, 3):
                if client.get_id_client()[index] not in ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', \
                             '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '@', '!', '#', '$', '%', '&', '*', '+']:
                    ok = 0

        if ok == 0:
            errors.append(Fore.CYAN + "Invalid character in ID. " + Style.RESET_ALL)

        if client.get_name() == '':
            errors.append(Fore.CYAN + "Invalid name. "+ Style.RESET_ALL)
        
        if len(client.get_cnp()) != 13:
            errors.append(Fore.CYAN + "Invalid CNP. "+ Style.RESET_ALL)

        if len(errors) > 0:
            error_string = '\n'.join(errors)
            raise ValueError(error_string)
