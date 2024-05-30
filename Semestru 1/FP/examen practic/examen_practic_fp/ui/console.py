from service.service import MelodieController

class Console:
    def __init__(self, controller: MelodieController):
        self.__melodie_service = controller

    def print_menu(self):
        print('1. Modificare melodii')
        print('2. Creaza aleator melodii')
        print('3. Exporta melodii sortate')
        print('\n4. Iesire din aplicatie\n')

    def modificare_melodie_ui(self):
        titlu = input('Introduceti titlul: ')
        artist = input('Introduceti artistul: ')
        print('Pentru a modifica genul si data, va rog introduceti: ')
        gen = input('Noul gen: ')
        data = input('Noua data: ')
        try:
            self.__melodie_service.modifica_melodie(titlu, artist, gen, data)
        except ValueError as e:
            print(e)

    def creaza_melodii_ui(self):
        numar = int(input('Numarul de melodii a fi create: '))
        lista_titluri = input(f'Introduceti {numar} titluri: ')
        lista_artisti = input(f'Introduceti {numar} artisti: ')

        lista_titluri = lista_titluri.split(',')
        lista_artisti = lista_artisti.split(',')

        if numar != len(lista_artisti) or numar != len(lista_titluri):
            print('Va rog introduceti numarul corect de artisti!')
        else:
            self.__melodie_service.creare_aleator_melodii(numar, lista_titluri, lista_artisti)
            print(f'Au fost introduse {numar} melodii.')

    def exporta_in_fisier_ui(self):
        nume_fisier = input('Introduceti numele fisierului in care doriti sa exportati lista: ')
        self.__melodie_service.export_melodii_fisier(nume_fisier)


    def print_all_melodies(self):
        for melodie in self.__melodie_service.get_all():
            print(melodie)

    def run(self):
        while True:
            self.print_menu()
            option = int(input('Introduceti optiunea: '))
            if option == 1:
                self.modificare_melodie_ui()
            elif option == 2:
                self.creaza_melodii_ui()
            elif option == 3:
                self.exporta_in_fisier_ui()
            elif option == 4:
                break
            elif option == 5:
                self.print_all_melodies()