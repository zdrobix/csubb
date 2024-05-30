import unittest
from domain.entity import Locatie, BookingInquiry

class entityTest:
    def __init__(self):
        self.createLocation()
        self.getNumberBookingInq()

    def createLocation(self):
        locatie1 = Locatie('1', 'Costinesti', 'mare', 30)
        locatie2 = Locatie('2', 'Norocel', 'munte', 199)

        self.assertEqual(locatie1.get_id(),'1')
        self.assertEqual(locatie2.get_id(), '2')
        self.assertEqual(locatie1.get_nume(), 'Costinesti')
        self.assertEqual(locatie2.get_nume(), 'Norocel')
        self.assertEqual(locatie1.get_tip(), 'mare')
        self.assertEqual(locatie2.get_tip(), 'munte')
        self.assertEqual(locatie1.get_pret(), 30)
        self.assertEqual(locatie2.get_pret(), 199)

    def getNumberBookingInq(self):
        locatie1 = Locatie('1', 'Costinesti', 'mare', 30)
        book1 = BookingInquiry(locatie1, 300)

        self.assertEqual(book1.get_number_of_days(), 10)
        self.assertNotEqual(book1.get_number_of_days(), 1111)

        locatie2 = Locatie('2', 'Norocel', 'munte', 200)
        book2 = BookingInquiry(locatie2, 1250)

        self.assertEqual(book2.get_number_of_days(), 6)
        self.assertNotEqual(book2.get_number_of_days(), 1111)

