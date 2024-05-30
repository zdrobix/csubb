def solution(x):
 pass

def select_most(x):
   pass

def is_ok(x):
   pass

def greedy(c):
    b = []
    while not solution(b) and c!=[]:
        candidate = select_most(c)
        c.remove(candidate)
        if is_ok(b + [candidate]):
            b.append(candidate)
    if solution(b):
       return b
    return None

greedy([1, 2, 3])