"""
ntuple.py - A simple demo of the named tupe construct
"""

from collections import namedtuple
meal = namedtuple("meal",["food","beverage","dessert"])
print("Meal's type is ",type(meal))

def f():
    """ Create and return instance breakfast of named tuple meal"""
    breakfast = meal("breakfast burrito","black coffee","cinnamon role")
    return  breakfast

"""
    Understanding returned tuple items much clearer w. 'dot' notation VS typical
    indexing access i.e., ([] in which index position must be looked up)
"""
print(f().beverage)
