import unittest
import datetime

from practic_tractoare.domain.trucks import Truck

class Entitytest(unittest.TestCase):

    def testCreate(self):
        tractor1 = Truck(1, 'OKAY', 10000, 'Volvo Runner', '2024-10-10')
        self.assertEqual(tractor1.get_id(), 1)
        self.assertEqual(tractor1.get_pret(), 10000)
        self.assertEqual(tractor1.get_denumire(), 'OKAY')
        self.assertEqual(tractor1.get_model(), 'Volvo Runner')
        self.assertEqual(tractor1.get_data(), datetime.date(2024,10,10))

        tractor2 = Truck(123, 'sss', 9999, 'Volvo Simplu', '1999-11-10')
        self.assertEqual(tractor2.get_id(), 123)
        self.assertEqual(tractor2.get_pret(), 9999)
        self.assertEqual(tractor2.get_denumire(), 'sss')
        self.assertEqual(tractor2.get_model(), 'Volvo Simplu')
        self.assertEqual(tractor2.get_data(), datetime.date(1999, 11, 10))

        self.assertNotEqual(tractor1.get_id(), 2)
        self.assertNotEqual(tractor1.get_pret(), 0)
        self.assertNotEqual(tractor1.get_denumire(), 'S')
        self.assertNotEqual(tractor1.get_model(), 'Volvo 124')
        self.assertNotEqual(tractor1.get_data(), datetime.date(1241, 10, 10))
        self.assertNotEqual(tractor2.get_id(), 34)
        self.assertNotEqual(tractor2.get_pret(), 90)
        self.assertNotEqual(tractor2.get_denumire(), 'Altceva')
        self.assertNotEqual(tractor2.get_model(), 'BMW')
        self.assertNotEqual(tractor2.get_data(), datetime.date(2009, 11, 10))

