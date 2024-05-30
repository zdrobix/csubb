from service.artist_service import ArtistController
from service.song_service import SongController
from service.write_service import WriteController
from colorama import Fore, Style, init
init()

class Console:

    def __init__(self, artist_service: ArtistController, song_service: SongController, write_service: WriteController):
        self.__artist_service = artist_service
        self.__song_service = song_service
        self.__write_service = write_service


    def print_menu_ui(self):
        print('1. ADD')
        print('2. DELETE')
        print('3. FIND')
        print('\n4. REPORT')
        print('5. LIBRARY')

    def add_menu_ui(self):
        print('     X. ARTIST\n     Y. ALBUM\n     Z. SINGLE')
        option = input('     Enter option: ').strip()
        if option == 'X' or option == 'x':
            id = int(input('     Enter ID: '))
            name = input('     Enter name: ')
            try:
                self.__artist_service.add_artist(id, name)
            except ValueError as e:
                print('ERROR: ' + str(e))


        elif option == 'Y' or option == 'y':
            id = int(input('     Enter ID: '))
            title = input('     Enter title: ')
            id_artist = int(input('     Enter artist id: '))
            nb_songs = int(input('     Enter number of songs: '))

            for nb in range (1,nb_songs):
                id = int(input('          Enter ID: '))
                song_title = input('     Enter song title: ')

        elif option == 'Z' or option == 'z':
            id_song = int(input('     Enter ID: '))
            song_title = input('     Enter song title: ')
            id_artist = int(input('     Enter artist id: '))
            try:
                self.__song_service.add_song(id_song, song_title)
            except ValueError as e:
                print('ERROR: ' + str(e))
            self.__write_service.add_write(id_artist, id_song)
            if input('     Feature? Y/N: ') in ['Y', 'y']:
                for num in range (0, int(input('     How many features? '))):
                    id_feature = int(input('     Enter artist id: '))
                    self.__write_service.add_write(id_feature, id_song)

    def delete_menu_ui(self):
        print('     X. ARTIST\n     Y. ALBUM\n     Z. SINGLE')
        option = input('     Enter option: ').strip()
        if option == 'X' or option == 'x':
            id_artist = input('     Enter ID: ')
            if input('Deleting an artist will automatically delete all the songs'
                  ' written by that artist, including features. Are you sure'
                  ' about proceeding through? Y/N: ') in ['Y', 'y']:
                artist = self.__artist_service.delete_artist(id_artist)
                print(f'The artist {artist.get_name()} has been deleted.')
                id_songs = self.__write_service.delete_songs_from_artist(id_artist)
                for id in id_songs:
                    self.__song_service.delete_song(id)




        elif option == 'Y' or option == 'y':
            pass
        elif option == 'Z' or option == 'z':
            pass

    def find_menu_ui(self):
        print('     X. ARTIST\n     Y. ALBUM\n     Z. SINGLE')
        option = input('     Enter option: ')
        if option == 'X' or option == 'x':
            search = input('     Enter name: ')
            search_result = self.__artist_service.search_artist(search)
            if search_result == []:
                print('No result for your search.')
            else:
                print(Fore.GREEN + '\nResults:\n' + Style.RESET_ALL)
                for artist in search_result:
                    print(artist.get_name())
        elif option == 'Y' or option == 'y':
            pass
        elif option == 'Z' or option == 'z':
            pass

    def library_menu_ui(self):
        print('     X. ARTISTS\n     Y. SONGS')
        option = input('     Enter option: ')
        if option == 'X' or option == 'x':
            if self.__artist_service.get_all() == []:
                print('No artists in your library.')
            else:
                for artist in self.__artist_service.get_all():
                    print(f'{Fore.GREEN} ID: {Style.RESET_ALL} {artist.get_id():<3}'
                          f' {Fore.GREEN} NAME: {Style.RESET_ALL} {artist.get_name():<20}')
        elif option == 'Y' or option == 'y':
            if self.__song_service.get_all() == []:
                print('No songs in your library.')
            else:
                for song in self.__song_service.get_all():
                    id_artist = self.__write_service.get_artist_for_song(song.get_id())
                    artist_list = []
                    for id in id_artist:
                        artist = self.__artist_service.find_artist(id)
                        artist_list.append(artist.get_name())
                    artist_list = ','.join(artist_list)
                    print(f'{Fore.GREEN} ID: {Style.RESET_ALL} {song.get_id():<3}'
                          f' {Fore.GREEN} NAME: {Style.RESET_ALL}   {song.get_title()}'
                          f' \n          {Fore.GREEN} ARTIST: {Style.RESET_ALL} {artist_list}\n')

    def run(self):
        while True:
            self.print_menu_ui()
            option = int(input("Enter option: ").strip())
            if option == 1:
                self.add_menu_ui()
            if option == 2:
                self.delete_menu_ui()
            if option == 3:
                self.find_menu_ui()
            if option == 5:
                self.library_menu_ui()
            elif option == 9:
                break