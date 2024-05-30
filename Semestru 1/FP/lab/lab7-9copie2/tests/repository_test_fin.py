import unittest

from entities.client import Client
from entities.movie import Movie
from entities.rental import RentalSystem

from repository.client_repository import ClientMemoryRepository
from repository.movie_repository import MovieMemoryRepository
from repository.rental_repository import RentalMemoryRepository

input_test_client = r'C:\Alex Zdroba\python projects\fp\lab7-9copie2\tests\input_test_client.txt'
input_test_movie = r'C:\Alex Zdroba\python projects\fp\lab7-9copie2\tests\input_test_movie.txt'
input_test_rental = r'C:\Alex Zdroba\python projects\fp\lab7-9copie2\tests\input_test_rental.txt'

class ClientTest(unittest.TestCase):

    def test_store_client(self):
        client2 = Client('1234', 'Zdroba', '5041110125555')
        client3 = Client('2341', 'Zdroba', '5041110125555')
        client4 = Client('3412', 'Zdroba', '5041110125555')

        repo = ClientMemoryRepository(input_test_client)
        repo.delete_all()

        self.assertEqual(repo.size(), 0)
        self.assertNotEqual(repo.size(), 1)

        repo.store(client2)
        self.assertEqual(repo.size(), 1)
        self.assertNotEqual(repo.size(), 2)

        repo.store(client3)
        self.assertEqual(repo.size(), 2)
        self.assertNotEqual(repo.size(), 3)

        repo.store(client4)
        self.assertEqual(repo.size(), 3)
        self.assertNotEqual(repo.size(), 4)

        repo.delete_all()
        self.assertEqual(repo.size(), 0)
        self.assertNotEqual(repo.size(), 1)

    def test_delete_client(self):
        client2 = Client('1234', 'Zdroba', '5041110125555')
        client3 = Client('2341', 'Zdroba', '5041110125555')
        client4 = Client('3412', 'Zdroba', '5041110125555')

        repo = ClientMemoryRepository(input_test_client)
        repo.delete_all()

        repo.store(client2)
        self.assertEqual(repo.size(), 1)
        self.assertNotEqual(repo.size(), 0)

        repo.delete(client2.get_id_client())
        self.assertEqual(repo.size(), 0)
        self.assertNotEqual(repo.size(), 1)

        repo.store(client3)
        self.assertEqual(repo.size(), 1)
        self.assertNotEqual(repo.size(), 0)

        repo.delete(client3.get_id_client())
        self.assertEqual(repo.size(), 0)
        self.assertNotEqual(repo.size(), 1)

        repo.store(client4)
        self.assertEqual(repo.size(), 1)
        self.assertNotEqual(repo.size(), 0)

        repo.delete(client4.get_id_client())
        self.assertEqual(repo.size(), 0)
        self.assertNotEqual(repo.size(), 1)

        repo.delete_all()

    def test_update_client(self):
        client2 = Client('1234', 'Zdroba', '5041110125555')
        client3 = Client('2341', 'ASKJKSJ', '5041110125555')

        repo = ClientMemoryRepository(input_test_client)
        repo.delete_all()

        repo.store(client2)
        self.assertEqual(repo.size(), 1)
        self.assertNotEqual(repo.size(), 0)

        repo.store(client3)

        repo.update(client2.get_id_client(), "NewName", "123456789101")

        self.assertEqual(client2.get_cnp(), "123456789101")
        self.assertEqual(client2.get_name(), "NewName")
        self.assertNotEqual(client2.get_cnp(), "notCNP")

        repo.update(client3.get_id_client(), "KKKKKKKKKK", "123456789101")

        self.assertEqual(client3.get_cnp(), "123456789101")
        self.assertEqual(client3.get_name(), "KKKKKKKKKK")
        self.assertNotEqual(client3.get_cnp(), "notCNP")

        with self.assertRaises(ValueError):
            repo.update('xxxx', 'NonExistentClient', '123456789101')

        repo.delete_all()

    def test_find_client(self):
        client2 = Client('1234', 'Zdroba', '5041110125555')
        client3 = Client('xyxx', 'ASKJKSJ', '5041110125555')

        repo = ClientMemoryRepository(input_test_client)
        repo.delete_all()

        repo.store(client2)
        repo.store(client3)

        self.assertEqual(repo.find("1234"), client2)
        self.assertEqual(repo.find("xyxx"), client3)
        self.assertNotEqual(repo.find("1234"), client3)
        self.assertNotEqual(repo.find("xyxx"), client2)

        self.assertEqual(repo.find('none'), None)
        self.assertEqual(repo.find('xpop'), None)

        repo.delete_all()

class MovieTest(unittest.TestCase):

    def test_store_movie(self):
        movie1 = Movie("12xo99", "The Man", "action", 120)
        movie2 = Movie("haha$$", "Buburuze", "horror", 93)

        repo = MovieMemoryRepository(input_test_movie)
        repo.delete_all()
        self.assertEqual(repo.size(), 0)
        self.assertNotEqual(repo.size(), 1)

        repo.store(movie1)
        self.assertEqual(repo.size(), 1)
        self.assertNotEqual(repo.size(), 0)

        repo.store(movie2)
        self.assertEqual(repo.size(), 2)
        self.assertNotEqual(repo.size(), 1)

        repo.delete_all()

    def test_delete_movie(self):
        movie1 = Movie("12xo99", "The Man", "action", 120)
        movie2 = Movie("haha$$", "Buburuze", "horror", 93)

        repo = MovieMemoryRepository(input_test_movie)
        repo.delete_all()
        self.assertEqual(repo.size(), 0)
        self.assertNotEqual(repo.size(), 1)

        repo.store(movie1)
        repo.store(movie2)

        repo.delete(movie1.get_id_movie())
        self.assertEqual(repo.size(), 1)
        self.assertNotEqual(repo.size(), 2)

        repo.delete(movie2.get_id_movie())
        self.assertEqual(repo.size(), 0)
        self.assertNotEqual(repo.size(), 1)

    def test_update_movie(self):
        movie1 = Movie("12xo99", "The Man", "action", 120)

        repo = MovieMemoryRepository(input_test_movie)
        repo.delete_all()
        self.assertEqual(repo.size(), 0)
        self.assertNotEqual(repo.size(), 1)

        repo.store(movie1)
        repo.update("12xo99", "NewNameOkOKOk", "family", 999)

        self.assertEqual(movie1.get_id_movie(), "12xo99")
        self.assertEqual(movie1.get_type(), "family")
        self.assertEqual(movie1.get_duration(), 999)
        self.assertEqual(movie1.get_title(), "NewNameOkOKOk")

        self.assertNotEqual(movie1.get_id_movie(), "JJJJJJ")
        self.assertNotEqual(movie1.get_type(), "horror")
        self.assertNotEqual(movie1.get_duration(), 10293)
        self.assertNotEqual(movie1.get_title(), "AnotherName")

    def test_find_movie(self):
        movie1 = Movie("123456", "The Man", "action", 120)
        movie2 = Movie("aaaaaa", "The Man", "action", 120)
        movie3 = Movie("bbbbbb", "The Man", "action", 120)

        repo = MovieMemoryRepository(input_test_movie)
        repo.delete_all()
        self.assertEqual(repo.size(), 0)
        self.assertNotEqual(repo.size(), 1)

        repo.store(movie1)
        repo.store(movie2)
        repo.store(movie3)

        self.assertEqual(repo.find('123456'), movie1)
        self.assertEqual(repo.find('aaaaaa'), movie2)
        self.assertEqual(repo.find('bbbbbb'), movie3)

        self.assertNotEqual(repo.find('123456'), movie2)
        self.assertNotEqual(repo.find('123456'), movie3)

class RentalTest(unittest.TestCase):

    def test_store_rental(self):
        id_movie1 = '1234xx'
        id_client1 = '1234'

        repo = RentalMemoryRepository(input_test_rental)
        repo.delete_all()

        self.assertEqual(repo.size(), 0)
        self.assertNotEqual(repo.size(), 1)

        repo.store(id_client1, id_movie1)
        self.assertEqual(repo.size(), 1)
        self.assertNotEqual(repo.size(), 0)

        repo.store(id_client1, id_movie1)
        self.assertEqual(repo.size(), 2)
        self.assertNotEqual(repo.size(), 1)

        repo.delete_all()







