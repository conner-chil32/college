# Problem 1
# Define a lambda expression named f that accepts two string parameters representing a first and last name and 
# concatenates them together to return a string in "Last, First" format. For example, if passed "Cynthia" and "Lee", 
# it would return "Lee, Cynthia". Do not write an entire function; just write a statement of the form:

# f = lambda parameters: expression

a = lambda first, last : last + ", " + first

# Problem 2
# Define a lambda expression named f that accepts two integer parameters and returns the larger of the two; 
# for example, if passed 4 and 11, it would return 11. Do not write an entire function; just write a statement of the form:

# f = lambda parameters: expression

b = lambda num1, num2 : max(num1, num2)

# Problem 3
# Define a lambda expression named f that accepts an integer parameter and converts it into the square of that integer; 
# for example, if 4 were passed, your lambda would return 16. Do not write an entire function; just write a statement of the form:

# f = lambda parameters: expression

c = lambda num : pow(num, 2)

# Problem 4
# What is the output of the following code? 

# numbers = [10, -28, 33, 28, -49, 56, 49]
# numbers = list(map(lambda n: abs(n), numbers))
# print(numbers)    

# Problem 5
# What is the output of the following code? 

# numbers = [10, -28, 33, 28, -49, 56, 49]
# numbers2 = [n for n in numbers if n % 2 == 0]
# print(numbers2) 

# Problem 6
# Write a function named abs_sum that takes a list of integers as a parameter and returns the sum of the absolute values of 
# each element in the list. For example, the absolute sum of [-1, 2, -4, 6, -9] is 22. If the list is empty, return 0.

# Use Python's functional programming constructs, such as list comprehensions, map, filter, reduce, to implement your 
# function. Do not use any loops or recursion in your solution. 

def abs_sum(int_list):
    d = sum([abs(n) for n in int_list])
    return d

# Problem 7
# Write a function named count_negatives that takes a list of integers as a parameter and returns how many numbers in the 
# list are negative. For example, if the list is [5, -1, -3, 20, 47, -10, -8, -4, 0, -6, -6], you should return 7.

# Use Python's functional programming constructs, such as list comprehensions, map, filter, reduce, to implement your 
# function. Do not use any loops or recursion in your solution. 

def count_negatives(int_list):
    e = [n for n in int_list if n < 0]
    return len(e)

# Problem 8
# Write a function named double_list that takes a list as a parameter and returns a list of integers that contains 
# double the elements in the initial list. For example, if the initial list is [2, -1, 4, 16], you should return [4, -2, 8, 32].

# Use Python's functional programming constructs, such as list comprehensions, map, filter, reduce, to 
# implement your function. Do not use any loops or recursion in your solution. 

def double_list(int_list):
    f = [n*2 for n in int_list]
    return f

# Problem 9
# Write a function called four_letter_words accepts a string representing a file name as a parameter and returns a count 
# of the number of words in the file that are exactly four letters long. Words are separated by whitespace. Do not worry 
# about punctuation; look for any four-character token.

# Use Python's functional programming constructs, such as list comprehensions, map, filter, reduce, to implement your 
# function. Do not use any loops or recursion in your solution. 

def four_letter_words(file_name):
    file = open(file_name, "r")
    text = file.read().replace("\n"," ").split(" ")
    g = len([n for n in text if len(n) == 4])
    return g

# Problem 10
# Write a function called glue_reverse that accepts a list of strings as its parameter and returns a single string consisting 
# of the list's elements concatenated together in reverse order. For example, if the list stores ["the", "quick", "brown", "fox"], 
# you should return "foxbrownquickthe". If the list is empty, return an empty string, "".

# Use Python's functional programming constructs, such as list comprehensions, map, filter, reduce, to implement your function. 
# Do not use any loops or recursion in your solution. 

def glue_reverse(string_list):
    h = "".join(list(string_list[::-1]))
    return h

# Problem 11
# Write a function named largest_even that takes a list of integers as a parameter and returns the largest even number from a 
# list of integers. An even integer is one that is divisible by 2. For example, if the list is [5, -1, 12, 10, 2, 8], your 
# function should return 12. You may assume that the list contains at least one even integer.

# Use Python's functional programming constructs, such as list comprehensions, map, filter, reduce, to implement your function. 
# Do not use any loops or recursion in your solution. 

def largest_even(int_list):
    i = max(filter(lambda x : x % 2 == 0, int_list))
    return i

# Problem 12
# Suppose you have a list of strings declared as follows. Write functional code to produce a new list named words2 containing all 
# of the three- or four-letter words in the list.

words = ["four", "score", "and", "seven", "years", "ago"]

def three_four_letter_words(string_list):
    j = list(filter(lambda x : len(x) == 4 or len(x) == 3, string_list))
    return j

words2 = three_four_letter_words(words)

# Problem 13
# Write a function named total_circle_area that accepts a list of real numbers representing the radii of the circles as a parameter 
# and returns the sum of the areas of a group of circles, rounded to the nearest integer. Recall that the area of a circle of radius r 
# is π r2. For example, if the list is [3.0, 1.0, 7.2, 5.5], your function should return 289. If the list is empty, return 0.

# Use Python's functional programming constructs, such as list comprehensions, map, filter, reduce, to implement your function. 
# Do not use any loops or recursion in your solution. 

import math
def total_circle_area(radii):
    k = sum(list(map(lambda x : math.pi * pow(x,2), radii)))
    return round(k)










