import sys
sys.path.append('C:\\Alex Zdroba\\python projects\\fp\\incercare2_7-9 - Copy\\entities')

from entities.validator import ClientValidator, MovieValidator

from repository.client_repository import ClientMemoryRepository
from repository.movie_repository import MovieMemoryRepository
from repository.rental_repository import RentalMemoryRepository

from service.client_service import ClientController
from service.movie_service import MovieController
from service.rental_service import RentalController

#inputClient = r'Q:\info\python\lab7-9copie2\tests\input_test_client.txt'
#inputMovie= r'Q:\info\python\lab7-9copie2\tests\input_test_movie.txt'
#inputRental = r'Q:\info\python\lab7-9copie2\tests\input_test_rental.txt'

inputClient = r'C:\\Alex Zdroba\\python projects\\fp\\incercare2_7-9 - Copy\\tests\\input_test_client.txt'
inputMovie= r'C:\\Alex Zdroba\\python projects\\fp\\incercare2_7-9 - Copy\\tests\\input_test_movie.txt'
inputRental = r'C:\\Alex Zdroba\\python projects\\fp\\incercare2_7-9 - Copy\\tests\\input_test_rental.txt'

#CLIENT TESTS:
def empty_file(filename):
    with open(filename, 'w'):
        pass

def test_add_client_service():
    empty_file(inputClient)
    repo = ClientMemoryRepository(inputClient)
    validator = ClientValidator()
    client_service = ClientController(repo, validator)
    
    client_service.add_client('xxyx', 'Stefanescu', '1780304125076')
    test_client_list = client_service.get_all_client()
    assert(len(test_client_list) == 1)
    
    try:
        client_service.add_client('xxyx', 'ClientDoubleID', '1780323415076')
        assert False
    except ValueError:
        assert True

    try:
        client_service.add_client('11xyx', 'ClientInvalidID', '17803234150765')
        assert False
    except ValueError:
        assert True

    try:
        client_service.add_client('1234', 'ClientInvalidCNP', 'CNP1234')
        assert False
    except ValueError:
        assert True

def test_delete_client_service():
    empty_file(inputClient)
    repo = ClientMemoryRepository(inputClient)
    validator = ClientValidator()
    client_service = ClientController(repo, validator)
    
    client_service.add_client('alex', 'Zdroba', '5041110125802')
    client_service.add_client('wgk0', 'Ionescu', '1740111827215')
    client_service.add_client('k72o', 'Popovic', '2750719126697')

    client_service.delete_client('alex')
    assert(len(client_service.get_all_client()) == 2)

    client_service.delete_client('wgk0')
    assert(len(client_service.get_all_client()) == 1)

    client_service.delete_client('k72o')
    assert(len(client_service.get_all_client()) == 0)
    
def test_update_client_service():
    empty_file(inputClient)
    repo = ClientMemoryRepository(inputClient)
    validator = ClientValidator()
    client_service = ClientController(repo, validator)
    
    client_service.add_client('alex', 'Zdroba', '5041110125802')
    client_service.add_client('wgk0', 'Ionescu', '1740111827215')
    client_service.add_client('k72o', 'Popovic', '2750719126697')

    client_service.update_client('alex', 'Taurinius', '5041110125802')
    client_service.update_client('wgk0', 'Floreanuvici', '1740111827215')
    client_service.update_client('k72o', 'Comarnic', '2750719126697')
    #TO BE FINISHED

def test_find_client_service():
    empty_file(inputClient)
    repo = ClientMemoryRepository(inputClient)
    validator = ClientValidator()
    client_service = ClientController(repo, validator)
    
    client_service.add_client('alex', 'Zdroba', '5041110125802')
    client_service.add_client('wgk0', 'Ionescu', '1740111827215')
    client_service.add_client('k72o', 'Popovic', '2750719126697')

    assert(client_service.find_client('alex') is not None)
    assert(client_service.find_client('wgk0') is not None)
    assert(client_service.find_client('k72o') is not None)

    assert(client_service.find_client('1232') is None)
    assert(client_service.find_client('asdf') is None)
    assert(client_service.find_client('XXX1') is None)

#MOVIE TESTS:
def test_add_movie_service():
    empty_file(inputMovie)
    repo = MovieMemoryRepository(inputMovie)
    validator = MovieValidator()
    movie_service = MovieController(repo, validator)
    movie_service.add_movie('xyz123', 'Spiderman', 'action', 123 )
    test_moviet_list = movie_service.get_all_movie()
    assert(len(test_moviet_list) == 1)
    
    try:
        movie_service.add_movie('xyz123', 'DuplicateID', 'action', 123 )
        assert False
    except ValueError:
        assert True

    try:
        movie_service.add_movie('123456', 'InvalidType', 'coolmovie', 123 )
        assert False
    except ValueError:
        assert True

    try:
        movie_service.add_movie('power3', 'NoMovieLenght', 'action', 0 )
        assert False
    except ValueError:
        assert True

def test_delete_movie_service():
    empty_file(inputMovie)
    repo = MovieMemoryRepository(inputMovie)
    validator = MovieValidator()
    movie_service = MovieController(repo, validator)

    movie_service.add_movie('abcdef', 'Cocaine Bear', 'horror', '95')
    movie_service.add_movie('1234qp', '2012', 'action', '158')
    movie_service.add_movie('qwerty', 'Censor', 'drama', '84')

    movie_service.delete_movie('abcdef')
    assert(len(movie_service.get_all_movie()) == 2)

    movie_service.delete_movie('1234qp')
    assert(len(movie_service.get_all_movie()) == 1)

    movie_service.delete_movie('qwerty')
    assert(len(movie_service.get_all_movie()) == 0)


def test_find_movie_servic():
    empty_file(inputMovie)
    repo = MovieMemoryRepository(inputMovie)
    validator = MovieValidator()
    movie_service = MovieController(repo, validator)
    
    movie_service.add_movie('abcdef', 'Cocaine Bear', 'horror', '95')
    movie_service.add_movie('1234qp', '2012', 'action', '158')
    movie_service.add_movie('qwerty', 'Censor', 'drama', '84')

    assert(movie_service.find_movie('abcdef') is not None)
    assert(movie_service.find_movie('1234qp') is not None)
    assert(movie_service.find_movie('qwerty') is not None)

    assert(movie_service.find_movie('121232') is None)
    assert(movie_service.find_movie('asd23f') is None)
    assert(movie_service.find_movie('X12XX1') is None)

#RENTAL TESTS
def test_rental_functions():
    empty_file(inputRental)
    repo = RentalMemoryRepository(inputRental)
    rental_service = RentalController(repo)

    id_client1 = '1234'
    id_client2 = 'alex'
    id_client3 = 'gaby'
    id_movie1 = 'abcdef'
    id_movie2 = 'random'
    id_movie3 = 'nuprea'

    rental_service.rent_movie(id_client1, id_movie1)
    rental_service.rent_movie(id_client2, id_movie2)
    rental_service.rent_movie(id_client3, id_movie3)
    rental_service.rent_movie(id_client2, id_movie1)

    assert(rental_service.get_client_number(id_client2) == 2)
    assert(rental_service.get_movie_number(id_movie1) == 2)
    assert(rental_service.get_all_clients_for_movie(id_movie1) == ['1234', 'alex'])
    assert(rental_service.get_all_clients_for_movie(id_movie2) ==['alex'])
    assert(rental_service.get_all_clients_for_movie(id_movie3) ==['gaby'])

    assert(rental_service.create_tuple_client() == [('alex', 2),('gaby', 1),('1234', 1)])
    assert(rental_service.create_tuple_list() == [('abcdef', 2), ('nuprea', 1),('random', 1)])
    assert (rental_service.create_tuple_list_least() == [('random', 1), ('nuprea', 1), ('abcdef', 2)])


test_rental_functions()
test_add_movie_service()
test_add_client_service()
test_find_movie_servic()
test_delete_client_service()
test_find_client_service()
test_update_client_service()