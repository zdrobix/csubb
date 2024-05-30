from ui.console import Console

from service.service import TruckController
from repository.repository import TruckMemoryRepository

input_file = r'C:\Alex Zdroba\python projects\cautari_sortari_vrajeli\practic_tractoare\input\input_tractoare.txt'

repository = TruckMemoryRepository(input_file)
controller = TruckController(repository)
console = Console(controller)

console.run()