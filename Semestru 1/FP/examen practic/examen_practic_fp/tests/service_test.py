from service.service import MelodieController
from repository.repository import MelodieRepository
from domain.entity import Melodie

file = r'C:\Alex Zdroba\python projects\examen_practic_fp\tests\input_test.txt'
repo = MelodieRepository(file)
service = MelodieController(repo)


def test_verificare_date():
    melodie = Melodie('GenInvalid', 'X', 'Hip-Hop', '11.05.2011')
    melodie2 = Melodie('DataInvalida', 'X', 'Pop', '34.13.2011')
    melodie3 = Melodie('MelodieValida', 'X', 'Pop', '11.05.2011')

    repo.store(melodie)
    repo.store(melodie2)
    repo.store(melodie3)

    eroare1 = 'Ati introdus un gen invalid.\n'
    eroare2 = 'Ati introdus o data invalida.\n'
    eroare3 = 'Melodia nu a fost gasita.\n'


    assert(service.verificare_date('GenInvalid', 'X', 'Hip-Hop', '11.05.2011') == eroare1 )
    assert(service.verificare_date('DataInvalida', 'X', 'Pop', '34.13.2011') == eroare2)
    assert (service.verificare_date('MelodieInexistenta', 'Y', 'Pop', '01.11.2011') == eroare3)

    assert (service.verificare_date('GenInvalidSi', 'X', 'Hip-Hop', '11.05.2011') == eroare1 + eroare3)
    assert (service.verificare_date('TotInvalid', 'X', 'XXX', '34.13.2011') == eroare1 + eroare2 + eroare3)
    assert (service.verificare_date('MelodieInexistenta', 'Y', 'Pop', '01.15.2011') == eroare2 + eroare3)


    repo.delete_all()

def test_modifica_melodie():
    melodie = Melodie('MelodieValida', 'X', 'Pop', '11.05.2011')
    repo.store(melodie)

    assert(service.verificare_date('MelodieValida', 'X', 'Pop', '11.05.2011') == '')

    service.modifica_melodie('MelodieValida' , 'X', 'Rock', '13.04.2013' )
    assert(melodie.get_gen() == 'Rock')
    assert (melodie.get_gen() != 'Pop')
    assert(melodie.get_data() == '13.04.2013')
    assert (melodie.get_data() != '13.04.2015')

    repo.delete_all()

    melodie = Melodie('AltaMElodieValida', 'Artist', 'Pop', '11.05.2009')
    repo.store(melodie)

    service.modifica_melodie('AltaMElodieValida', 'Artist', 'Jazz', '13.04.2013')
    assert (melodie.get_gen() == 'Jazz')
    assert (melodie.get_gen() != 'Pop')
    assert (melodie.get_data() == '13.04.2013')
    assert (melodie.get_data() != '13.04.2009')




def creare_aleator_melodii():
    lista_titluri = 'Titlu1,Titlu2,Titlu3,Titlu4'
    lista_artisti = 'Artist1,Artist2,Artist3,Artist4'

    lista_titluri = lista_titluri.split(',')
    lista_artisti = lista_artisti.split(',')

    lista_creata = service.creare_aleator_melodii(4, lista_titluri, lista_artisti)
    assert(len(lista_creata) == 4)

    repo.delete_all()

    lista_titluri = ['Titlu1']
    lista_artisti = ['Artist1']

    lista_creata = service.creare_aleator_melodii(1, lista_titluri, lista_artisti)
    assert (len(lista_creata) == 1)

    repo.delete_all()


test_verificare_date()
test_modifica_melodie()
creare_aleator_melodii()