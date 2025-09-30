"""
generator_1.py Intro to generators Refr:: In-depth description of Python function stack:
https://towardsdatascience.com/cpython-internals-how-do-generators-work-ba1c4405b4bc
Unlike ordinary functions, a generator can be paused, resumed, and iterated
"""
import pdb  # Default Python debugging module

def gen():
  """ A very simple generator """
  print("Hello")
  yield
  print("Goodbye")

print('gen() expression value (right after def defined) is: ', gen())

# First gen use example
# Initialize the generator (several initializations are possible)
#  Initializes a stack execution frame, passes arguments, etc.
g = gen() # gen code does NOT yet run (just creates gen instance)
pdb.set_trace()  # After display, print, examine values at breakpoit, resume using  c (means continue)
next(g) # Advance g: The code prior to yield runs, suspends at yield until next executed
# Advance g again: The code following the yield executes, but then since
#  no other yield statements are encountered, the generator throws a
#  a StopIteration built-in exception. However, since the exception is handled,
#  script execution continues, and the print statement below executes.
try:
    next(g)
except StopIteration as exc:
    print('StopIteration exception handled; ... continue script execution')
  
print('End of First gen example','\n')

# Second gen use example
# A loop iterates a generator to StopIteration
# Also shows unhandled exceptons cause script termination
g = gen()  # Initialize a gen() generator again
for k in g:
    print(next(g))

print('Finished  Second gen example') # Exception not handled, so this print not executed

