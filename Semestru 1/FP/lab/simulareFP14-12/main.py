from service.service import LocatieController
from repo.repository import LocatieRepository
from ui.console import Console

input = r'C:\Alex Zdroba\simulareFP14-12\input\input.txt'

repo = LocatieRepository(input)
locatie_controller = LocatieController(repo)

console = Console(locatie_controller)
console.run()

