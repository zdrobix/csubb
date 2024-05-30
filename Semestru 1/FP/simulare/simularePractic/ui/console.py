from service.service import CoffeeController
class Console:
    def __init__(self, coffee_controller: CoffeeController):
        self.__coffee_service = coffee_controller

    

    def run(self):
        while True:
            pass
