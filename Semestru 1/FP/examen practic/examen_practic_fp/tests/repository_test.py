from repository.repository import MelodieRepository
from domain.entity import Melodie
file = r'C:\Alex Zdroba\python projects\examen_practic_fp\tests\input_test.txt'

def test_repo_store():
    repo = MelodieRepository(file)
    repo.delete_all()

    assert(len(repo.get_all()) == 0)

    melodie = Melodie('Acasa', 'Smiley', 'Pop', '11.05.2011')
    melodie2 = Melodie('AltaPiesa', 'Smiley', 'Pop', '11.05.2011')
    melodie3 = Melodie('Ok', 'Smiley', 'Pop', '11.05.2011')
    melodie4 = Melodie('Fara tine', 'Smiley', 'Pop', '11.05.2011')

    repo.store(melodie)
    assert (len(repo.get_all()) == 1)

    repo.store(melodie2)
    assert (len(repo.get_all()) == 2)

    repo.store(melodie3)
    assert (len(repo.get_all()) == 3)

    repo.store(melodie4)
    assert (len(repo.get_all()) == 4)

    assert(repo.find('Acasa', 'Smiley') == melodie)
    assert(repo.find('AltaPiesa', 'Smiley') == melodie2)
    assert(repo.find('NU Exista', 'NIMENI') == None)

    repo.delete_all()

def test_update():
    repo = MelodieRepository(file)
    repo.delete_all()

    melodie = Melodie('Acasa', 'Smiley', 'Pop', '11.05.2011')
    melodie2 = Melodie('AltaPiesa', 'Smiley', 'Pop', '11.05.2019')

    repo.update(melodie, 'Rock', '12.06.2011')
    repo.update(melodie2, 'Jazz', '30.07.2024')

    assert(melodie.get_gen() == 'Rock')
    assert (melodie2.get_gen() == 'Jazz')
    assert (melodie.get_data() == '12.06.2011')
    assert (melodie2.get_data() == '30.07.2024')

    melodie = Melodie('Acasa', 'Smiley', 'Pop', '11.05.2011')
    melodie2 = Melodie('AltaPiesa', 'Smiley', 'Pop', '11.05.2019')

    repo.update(melodie, 'Rock', '12.06.2011')
    repo.update(melodie2, 'Jazz', '30.07.2024')

    assert (melodie.get_gen() != 'Pop')
    assert (melodie2.get_gen() != 'Rock')
    assert (melodie.get_data() != '12.06.2014')
    assert (melodie2.get_data() != '30.07.2003')

    repo.delete_all()

def test_interclasare_si_merge():
    repo = MelodieRepository(file)
    repo.delete_all()

    melodie = Melodie('Acasa', 'Smiley', 'Pop', '11.05.2011')
    melodie2 = Melodie('AltaPiesa', 'Smiley', 'Pop', '11.05.2019')
    melodie3 = Melodie('AltaPiesaX', 'SmileyX', 'Pop', '11.05.2020')

    repo.store(melodie)
    repo.store(melodie2)
    repo.store(melodie3)

    assert(repo.merge_sort(repo.get_all()) == [melodie, melodie2, melodie3])

    repo.delete_all()

    melodie = Melodie('Acasa', 'Smiley', 'Pop', '8.05.2011')
    melodie2 = Melodie('AltaPiesa', 'Smiley', 'Pop', '9.6.2019')
    melodie3 = Melodie('AltaPiesaX', 'SmileyX', 'Pop', '10.7.2020')
    melodie5 = Melodie('Acasa', 'Smiley', 'Pop', '12.05.2022')
    melodie4 = Melodie('AltaPiesa', 'Smiley', 'Pop', '15.06.2024')
    melodie6 = Melodie('AltaPiesaX', 'SmileyX', 'Pop', '18.07.2024')

    repo.store(melodie)
    repo.store(melodie2)
    repo.store(melodie3)
    repo.store(melodie4)
    repo.store(melodie5)
    repo.store(melodie6)

    repo.delete_all()

test_repo_store()
test_update()
test_interclasare_si_merge()