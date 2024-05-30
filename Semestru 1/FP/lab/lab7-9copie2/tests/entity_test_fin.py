import unittest

from entities.client import Client
from entities.movie import Movie
from entities.rental import RentalSystem
from entities.validator import *

class ClientTest(unittest.TestCase):

    def test_create_client(self):
        client = Client('1234', 'Zdroba', '5041110125555')
        self.assertEqual(client.get_id_client(),'1234')
        self.assertEqual(client.get_name(), 'Zdroba')
        self.assertEqual(client.get_cnp(), '5041110125555')

        client1 = Client('xyza', 'Popantiu', '1889900124000')
        self.assertEqual(client1.get_id_client(), 'xyza')
        self.assertEqual(client1.get_name(), 'Popantiu')
        self.assertEqual(client1.get_cnp(), '1889900124000')

        client.set_name('Zdrobix')
        client.set_cnp('5041110125666')
        self.assertEqual(client.get_name(), 'Zdrobix')
        self.assertEqual(client.get_cnp(), '5041110125666')

        client1.set_name('Gombauer')
        client1.set_cnp('5041110125123')
        self.assertEqual(client1.get_name(), 'Gombauer')
        self.assertEqual(client1.get_cnp(), '5041110125123')

    def test_equal_client(self):
        client1 = Client("fff1", "Man", 1890981264789)
        client2 = Client("fff1", "Man", 1890981264789)

        client3 = Client('1234', 'Zdroba', '5041110125555')
        client4 = Client('1234', 'Zdroba', '5041110125555')

        self.assertEqual(client1, client2)
        self.assertEqual(client3, client4)
        self.assertNotEqual(client1, client3)
        self.assertNotEqual(client2, client4)

    def test_validate_client(self):
        validator = ClientValidator()

        client1 = Client('((((', 'InvalidID', '5041110125555')
        client2 = Client('name', '', '5021212125121')
        client3 = Client('dani', 'InvalidCNP', '504111')
        client4 = Client('', 'InvalidID', '5041110125555')
        client5 = Client('okay', 'ValidClient', '5041110125555')

        with self.assertRaises(ValueError):
            validator.validate_client(client2)
        with self.assertRaises(ValueError):
            validator.validate_client(client3)
        with self.assertRaises(ValueError):
            validator.validate_client(client4)

class MovieTest(unittest.TestCase):

    def test_create_movie(self):
        movie1 = Movie("12xo99", "The Man", "action", 120)
        self.assertEqual(movie1.get_id_movie(), '12xo99')
        self.assertEqual(movie1.get_title(), "The Man")
        self.assertEqual(movie1.get_duration(), 120)
        self.assertEqual(movie1.get_type(), "action")

        movie2 = Movie("haha$$", "Buburuze", "horror", 93)
        self.assertEqual(movie2.get_id_movie(), 'haha$$')
        self.assertEqual(movie2.get_title(), "Buburuze")
        self.assertEqual(movie2.get_duration(), 93)
        self.assertEqual(movie2.get_type(), "horror")

        movie1.set_duration(100)
        movie1.set_type("family")
        self.assertEqual(movie1.get_duration(), 100)
        self.assertEqual(movie1.get_type(), "family")

        movie2.set_title("Chuck Norris")
        movie2.set_type("romance")
        self.assertEqual(movie2.get_title(), "Chuck Norris")
        self.assertEqual(movie2.get_type(), "romance")

    def test_equal_movie(self):
        movie1 = Movie("12xo99", "The Man", "action", 120)
        movie2 = Movie("12xo99", "The Man", "action", 120)
        movie3 = Movie("haha$$", "Buburuze", "horror", 93)
        movie4 = Movie("haha$$", "Buburuze", "horror", 93)

        self.assertEqual(movie1, movie2)
        self.assertEqual(movie3, movie4)

        self.assertNotEqual(movie1, movie3)
        self.assertNotEqual(movie2, movie4)

    def test_validate_movie(self):
        validator = MovieValidator()

        movie1 = Movie("123", "InvalidID", "action", 120)
        movie2 = Movie("noname", "", "musical", 135)
        movie3 = Movie("xxxrrr", "InvalidType", "coolmovie", 135)
        movie4 = Movie("asd123", "InvalidLenght", "action", 0)
        movie5 = Movie("kykyky", "ValidMovie", "family", 135)

        with self.assertRaises(ValueError):
            validator.validate_movie(movie1)
        with self.assertRaises(ValueError):
            validator.validate_movie(movie2)
        with self.assertRaises(ValueError):
            validator.validate_movie(movie3)
        with self.assertRaises(ValueError):
            validator.validate_movie(movie4)



class RentalTest(unittest.TestCase):

    def test_create_rental(self):
        client1 = Client("fff1", "Pop", '1890981264789')
        client2 = Client('1234', 'Zdroba', '5041110125555')
        movie1 = Movie("12xo99", "The Man", "action", 120)
        movie2 = Movie("haha$$", "Buburuze", "horror", 93)

        rental11 = RentalSystem("fff1", "12xo99")
        rental12 = RentalSystem("fff1", "haha$$")
        rental21 = RentalSystem('1234', "12xo99")


        self.assertEqual(rental11.get_id_client_rental(), "fff1")
        self.assertEqual(rental11.get_id_movie_rental(), "12xo99")
        self.assertEqual(rental12.get_id_client_rental(), "fff1")
        self.assertEqual(rental12.get_id_movie_rental(), "haha$$")
        self.assertEqual(rental21.get_id_client_rental(), "1234")
        self.assertEqual(rental21.get_id_movie_rental(), "12xo99")
