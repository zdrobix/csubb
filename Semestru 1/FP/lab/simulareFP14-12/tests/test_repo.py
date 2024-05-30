import unittest
from repo.repository import LocatieRepository
from domain.entity import Locatie

filename = r"C:\Alex Zdroba\simulareFP14-12\tests\inputTest.txt"

class repoTest:
    def __init__(self):
        self.testFind()
        self.testGetAll()

    def testFind(self):
        repo = LocatieRepository(filename)
        loc1 = Locatie(1,'Mamaia','mare',100)
        self.assertEqual(repo.find(1), loc1)
        self.assertEqual(repo.find(1234), None)

    def testGetAll(self):
        repo = LocatieRepository(filename)
        loc1 = Locatie(1, 'Mamaia', 'mare', 100)
        loc2 = Locatie(2,'Muntenegru','munte',300)
        loc3 = Locatie(3,'Sovata','hotel',80)
        loc4 = Locatie(4,'OcnaMures','ocna',20)

        list = [loc1, loc2, loc3, loc4]
        self.assertEqual(repo.get_all(), list)
