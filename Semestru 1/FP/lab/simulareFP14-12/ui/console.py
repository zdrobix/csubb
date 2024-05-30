from service.service import LocatieController

class Console:
    def __init__(self, locatie_controller: LocatieController):
        self.__locatie_service = locatie_controller


    def print_choose_opt(self):
        print("1. Cautare pe baza tipului")
        print("2. Informatii privind rezervarea. Incadrarea in buget")

    def cautare_tip_ui(self):
        tip = input("Introduceti tipul dorit : ")

        lista_locatii = self.__locatie_service.locatie_acelasi_tip(tip)
        if lista_locatii != []:
            for locatie in lista_locatii:
                print(f"{locatie.get_nume():<15}{locatie.get_pret():<4}")
        else:
            print("Nicio locatie nu corespunde cu tipul introdus.")

    def informatii_buget_ui(self):
        id = str(input("Introduceti id-ul locatiei dorite : ").strip())
        buget = int(input("Introduceti bugetul dumneavoastra : ").strip())
        locatie = self.__locatie_service.find_locatie(id)
        if locatie is None:
            print("Locatie inexistenta.")
        else:
            #zile_permise = self.__booking_service.get_number_of_days(locatie, buget)
            zile_permise = buget // int(locatie.get_pret())
            print(f"Pentru locatia {locatie.get_nume()}: {locatie.get_tip()}, va puteti permite {zile_permise} zile de vacanta.")

    def run(self):
        while True:
            self.print_choose_opt()
            option = input("Introduceti optiunea : ")
            if option == '1':
                self.cautare_tip_ui()
            elif option == '2':
                self.informatii_buget_ui()
            else:
                break

