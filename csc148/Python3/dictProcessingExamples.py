"""
dictProcessingExamples.py
dict processing examples, WJM
"""

inventory = {'rope': 1, 'torch': 6, 'money': '$1000'}
# Display the keys of the dict items on reverse order, no need for backwards traversal code
print( reversed(inventory.keys()) ) # Display what this reversed(xxx) object is
# Sum the integer values in the list comprehension result of extracting all dict values
print( "Sum of all int values in inventory is ", sum([ x for x in inventory.values() if isinstance(x,int) ]) )
# Even simpler code in the above sum relies on sum realizing that list comprhension crerates a list
print( "Sum of all int values in inventory is ", sum( x for x in inventory.values() if isinstance(x,int) ) )

"""
# Caution with expressions involving types
type(6) == int
True
int = 77
int
77
isinstance(6,int)
Traceback (most recent call last):
  File "<pyshell#30>", line 1, in <module>
    isinstance(6,int)
TypeError: isinstance() arg 2 must be a type, a tuple of types, or a union
isinstance(6,'int')
Traceback (most recent call last):
  File "<pyshell#31>", line 1, in <module>
    isinstance(6,'int')
TypeError: isinstance() arg 2 must be a type, a tuple of types, or a union
int = type(int)_
SyntaxError: invalid syntax
int=type(int)
int
<class 'int'>

"""
     
