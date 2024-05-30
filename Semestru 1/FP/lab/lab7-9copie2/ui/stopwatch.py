import time

class StopWatch:

    def __init__(self):
        self.startt = None
        self.end = None
        self.run = False

    def start(self):
        if not self.run:
            self.startt = time.time()
            self.run = True

    def stop(self):
        if self.run:
            self.end = time.time()
            self.run = False
            self.display_time()

    def display_time(self):
        if self.startt is not None and self.end is not None:
            print (self.end - self.startt)
        else: print('x')