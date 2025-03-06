"""
Să se determine cuvintele unui text care apar exact o singură dată în acel text.
De ex. cuvintele care apar o singură dată în ”ana are ana are mere rosii ana" sunt: 'mere' și 'rosii'.
"""
from collections import Counter


# me theta(n) -> n este numarul de cuvinte
def non_occuring_words(input_string):
    words = []
    for word in input_string.split():
        if word not in words:
            words += [word]
        else:
            words.remove(word)
    return words

# chat gpt
def unique_words(text):
    words = text.split()
    word_counts = Counter(words)
    return [word for word, count in word_counts.items() if count == 1]

def tests():
    str1, r1 = "a a b b c c d d e f f e z", ['z']
    str2, r2 = "a b c d a z y z y", ['b', 'c', 'd']
    str3, r3 = "cuvant cuvant alex", ['alex']
    assert(non_occuring_words(str1) == r1)
    assert(non_occuring_words(str2) == r2)
    assert(non_occuring_words(str3) == r3)
    assert (unique_words(str1) == r1)
    assert (unique_words(str2) == r2)
    assert (unique_words(str3) == r3)


tests()
