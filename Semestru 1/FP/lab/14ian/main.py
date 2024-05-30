from ui.console import Console

from repository.artist_repository import ArtistMemoryRepository
from repository.song_repository import SongMemoryRepository
from repository.write_repository import WriteMemoryRepository

from service.artist_service import ArtistController
from service.song_service import SongController
from service.write_service import WriteController

from entity.validator import ArtistValidator, SongValidator

inputArtist = r'C:\Alex Zdroba\python projects\14ian\input\inputArtist.txt'
inputSong = r'C:\Alex Zdroba\python projects\14ian\input\inputSongs.txt'
inputWrite = r'C:\Alex Zdroba\python projects\14ian\input\inputWrite.txt'

artist_repo = ArtistMemoryRepository(inputArtist)
song_repo = SongMemoryRepository(inputSong)
write_repo = WriteMemoryRepository(inputWrite)

artist_validator = ArtistValidator()
song_validator = SongValidator()

artist_controller = ArtistController(artist_repo, artist_validator)
song_controller = SongController(song_repo, song_validator)
write_controller = WriteController(write_repo)

console = Console(artist_controller, song_controller, write_controller)
console.run()
