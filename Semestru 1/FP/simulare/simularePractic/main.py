file = r'C:\Alex Zdroba\simularePractic\input\input.txt'

from service.service import CoffeeController
from domain.validator import CoffeeValidator
from repository.repository import CoffeeRepository

from ui.console import Console

coffee_repository = CoffeeRepository(file)

coffee_validator = CoffeeValidator()

coffee_controller = CoffeeController(coffee_repository, coffee_validator)

console = Console(coffee_controller)

console.run()