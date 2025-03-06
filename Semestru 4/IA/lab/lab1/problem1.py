"""
Să se determine ultimul (din punct de vedere alfabetic) cuvânt care poate apărea într-un text care conține mai multe cuvinte separate prin ” ” (spațiu).
De ex. ultimul (dpdv alfabetic) cuvânt din ”Ana are mere rosii si galbene” este cuvântul "si".
"""


# me theta(n) -> n = numarul de cuvinte
def get_max_word(input_str):
    return max(input_str.split())


# chatGPT
def last_word(text):
    words = text.split()
    return sorted(words)[-1]


def tests():
    str1 = "ana are mere si zrezultat"
    str2 = "zrezultat"
    str3 = "aa aa ab ac"
    assert (get_max_word(str1) == "zrezultat")
    assert (last_word(str1) == "zrezultat")
    assert (get_max_word(str2) == "zrezultat")
    assert (last_word(str2) == "zrezultat")
    assert (get_max_word(str3) == "ac")
    assert (last_word(str3) == "ac")


tests()
