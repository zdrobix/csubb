import unittest
from practic_tractoare.repository.repository import TruckMemoryRepository
from practic_tractoare.domain.trucks import Truck
filename = r'C:\Alex Zdroba\python projects\cautari_sortari_vrajeli\practic_tractoare\tests\input_test.txt'

class RepositoryTest(unittest.TestCase):

    def test_add(self):
        repo = TruckMemoryRepository(filename)
        repo.delete_all()
        self.assertEqual(repo.get_all(), [])

        tractor1 = Truck(1, 'OKAY', 10000, 'Volvo Runner', '2024-10-10')
        tractor2 = Truck(123, 'sss', 9999, 'Volvo Simplu', '1999-11-10')

        repo.add(tractor1)
        self.assertEqual(len(repo.get_all()), 1)

        repo.add(tractor2)
        self.assertEqual(len(repo.get_all()), 2)

        repo.delete_all()
        self.assertEqual(repo.get_all(), [])

    def test_delete(self):
        repo = TruckMemoryRepository(filename)
        repo.delete_all()
        self.assertEqual(repo.get_all(), [])

        tractor1 = Truck(1, 'OKAY', 10000, 'Volvo Runner', '2024-10-10')
        tractor2 = Truck(123, 'sss', 9999, 'Volvo Simplu', '1999-11-10')

        repo.add(tractor1)
        self.assertEqual(len(repo.get_all()), 1)

        repo.add(tractor2)
        self.assertEqual(len(repo.get_all()), 2)

        repo.delete(tractor1)
        self.assertEqual(len(repo.get_all()), 1)

        repo.delete(tractor2)
        self.assertEqual(len(repo.get_all()), 0)

        repo.delete_all()
        self.assertEqual(repo.get_all(), [])

