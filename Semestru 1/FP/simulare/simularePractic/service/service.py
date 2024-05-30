from repository.repository import CoffeeRepository
from domain.validator import CoffeeValidator

class CoffeeController:

    def __init__(self, repository: CoffeeRepository, validator: CoffeeValidator):
        self.__repo = repository
        self.validator = validator