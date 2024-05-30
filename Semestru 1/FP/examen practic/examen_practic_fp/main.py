from service.service import MelodieController
from repository.repository import MelodieRepository
from ui.console import Console

filename = r'C:\Alex Zdroba\python projects\examen_practic_fp\input\input_melodii.txt'

repository = MelodieRepository(filename)
controller = MelodieController(repository)

console = Console(controller)

console.run()