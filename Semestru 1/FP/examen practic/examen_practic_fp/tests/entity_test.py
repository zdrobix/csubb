from domain.entity import Melodie

def melodie_test():
    melodie = Melodie('Acasa', 'Smiley', 'Pop', '11.05.2011')
    melodie2 = Melodie('AltaPiesa', 'Smiley', 'Rock', '11.05.2019')
    melodie3 = Melodie('AltaPiesaX', 'SmileyX', 'Pop', '11.05.2020')

    assert(melodie.get_data() == '11.05.2011')
    assert(melodie.get_gen() == 'Pop')
    assert(melodie.get_titlu() == 'Acasa')
    assert(melodie.get_artist() == 'Smiley')

    assert(melodie2.get_gen() == 'Rock')
    assert (melodie3.get_gen() == 'Pop')
    assert (melodie2.get_gen() != 'Jazz')

    assert (melodie2.get_data() == '11.05.2019')
    assert (melodie3.get_data() == '11.05.2020')
    assert (melodie2.get_data() != '11.05.2020')

    melodie.set_gen('Rock')

    assert(melodie.get_gen() == 'Rock')
    assert(melodie.get_gen() != 'HeavyMetal')

    melodie.set_data('11.05.2014')

    assert(melodie.get_data() == '11.05.2014')
    assert(melodie.get_data() != '10.10.1999')


melodie_test()
