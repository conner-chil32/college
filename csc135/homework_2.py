# Problem 1
# Write a recursive function named star_string that accepts an integer parameter n and returns a string of stars (asterisks) 
# 2n long (i.e., 2 to the nth power). For example:

#     Call 	                 Returns                 Reason
# star_string(0) 	    "*" 	                     20 = 1
# star_string(1) 	    "**" 	                     21 = 2
# star_string(2) 	    "****" 	                     22 = 4
# star_string(3) 	    "********" 	                 23 = 8
# star_string(4) 	    "****************" 	         24 = 16

# You should throw a ValueError if passed a value less than 0.

def star_string(n):
    if n < 0:
        raise ValueError("Value cannot be less than 0")
    
    if n == 0:
        res = "*"
        return res
    
    res = 2 * star_string(n-1)
    return res
        
# Problem 2
# Write a recursive function named digit_sum that accepts an integer as a parameter and returns the sum of its digits. 
# For example, calling digit_sum(1729) should return 1 + 7 + 2 + 9, which is 19. If the number is negative, return the negation 
# of the value. For example, calling digit_sum(-1729) should return -19.

# Constraints: Do not declare any global variables. Do not use any loops you must use recursion. Do not use any auxiliary data 
# structures like list, dict, set, etc. Also do not solve this problem using a string. You can declare as many primitive variables 
# like ints as you like. You are allowed to define other "helper" functions if you like they are subject to these same constraints.

def digit_sum(n):
    if n == 0:
        return 0
    else:
        if n < 0:
            return -(-n % 10 + digit_sum(int(-n/10)))
        return n % 10 + digit_sum(int(n/10))
    
# Problem 3
# Write a recursive function named stutter_list that accepts a list of integers as a parameter and replaces every value in the 
# list with two occurrences of that value. For example, suppose a list named s stores these values, from bottom => top:

# [13, 27, 1, -4, 0, 9]

# Then the call of stutter_list(s) should change the list to store the following values:

# [13, 13, 27, 27, 1, 1, -4, -4, 0, 0, 9, 9]

# Notice that you must preserve the original order. In the original list the 9 was at the top and would have been popped first. 
# In the new list the two 9s would be the first values popped from the list. If the original list is empty, the result should be 
# empty as well.

# Constraints: Your solution must be recursive. Do not use any loops. Do not use any auxiliary collections or data structures to 
# solve this problem.

def stutter_list(int_list,index=0):
    if index >= len(int_list):
        return int_list
    else:
        int_list.insert(index,int_list[index])
        return stutter_list(int_list,index+2)

# Problem 4
# Write a recursive function named reverse that accepts a string parameter and returns that string with its characters in the 
# opposite order. For example, the call of reverse("Hi you!") should return "!uoy i_h" .

# Constraints: Do not use any loops you must use recursion. Do not declare any global variables or any auxiliary data structures.

def reverse(string):
    if string == "":
        return ""
    return reverse(string[1:]) + string[0]

# Problem 5
# Write a class named BankAccount where each object remembers information about a user's account at a bank. You must include the 
# following public members:

#   member name 	        type 	                                description
# BankAccount(name) 	 constructor 	constructs a new account for the person with the given name, with $0.00 balance
# ba.name 	               property 	                the account name as a string (read-only)
# ba.balance 	           property 	            the account balance as a real number (read-only)
# ba.deposit(amount) 	    method 	    adds the given amount of money, as a real number, to the account balance; if the amount is negative, does nothing
# ba.withdraw(amount) 	    method 	    subtracts the given amount of money, as a real number, from the account balance; if the amount is negative or exceeds the account's balance, does nothing

# You should define the entire class including the class heading, the private instance variables, and the declarations and 
# definitions of all the public member functions and constructor.

class BankAccount:
    def __init__(self, name, balance=0.00):
        self.name = name
        self.balance = balance
    
    def deposit(self, amount):
        if amount < 0:
            return
        else:
            self.balance += amount
    
    def withdraw(self, amount):
        if amount < 0 or amount > self.balance:
            return
        else:
            self.balance -= amount
