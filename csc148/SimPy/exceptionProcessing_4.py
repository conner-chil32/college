"""
    exceptionProcessing_4.py - WJM, Nov 2023   Demo Python/SimPy exception processing
    Re-wrote this script docString of the original source code version, NOv 2021
    All exception types in CSC148 are handled by ONE  try: .. except yyy :  else: .. finally: ..  statements 
    In a Python instance methods,  each first formal parameter is, by convention, named "self", regardless of whether
    source code is using keyword-only parameters or not.
    By contrast each first parameter of a class method is named "cls", and again regardless of whether
    source code is using keyword-only parameters or not.
"""

import sys  # Verify we are running a Python 3 version
import simpy # The simpy DES environment
import random # Randomness calculations
import numpy as np # General numerical processing, used here for well-known probability pmf 
import pdb # Optional, only if needed for debugging
import classDict as cls_d # Retrieves class dict info

# Python version check BEFORE executing the rest of this script
try:
    print("Python version & info:: ",sys.version,"\n")
    assert (sys.version_info[0] == 3), "Python version must be 3 - Abort execution if not"
except Exception as e:
    print(e)

# RNG seeding for NumPy use
numpy_seed = 18334247  # Changing seed generates a different random nums stream, else randomness is repeated
np.random.seed(numpy_seed) # Seed random variate generation

class A(object):
    """Class with a pf that handles exceptions"""
    # Class variables
    interruptCount = 0 # Running total of interrupts that occurred
    exceptionCount = 0 # Running total of exceptions that occurred
    
    def __init__(self,*,env,className,instanceName):
        self.env = env
        self.className = className
        self.instanceName = instanceName
        print("Running class A __init__ fcn at time ",self.env.now," for instance ",self.instanceName,\
                " // NumPy seed is ",numpy_seed)
        # Schedule exceptDispatch to wait for and respond to B instance interrupts
        self.do_exceptDispatch = self.env.process(self.exceptDispatch(env=self.env))

    def parseFirstSubstring(self,*,p_string):
        """ Return leading slice of string p_string until ':' """
        return p_string[0:p_string.find(":")]

    def exceptDispatch(self,*,env):
        """ Event-wait Loop is interrupted by exceptions, try auto. re-executes after ea. exception handled """
        self.env = env
        while True:
            try:
                yield self.env.timeout(runDuration) # Indefinite exceptions wait will be interrupted by B instance genExceptions
            except simpy.Interrupt as myInterrupt:   #  < == Interrupt info stored in identifier myInterrupt
                # Handle interrupts
                print("TRACE:: exceptDisplatch interrupted at time ", env.now)
                A.interruptCount += 1 # Increment number of exceptions thrown
                interrupter = self.parseFirstSubstring(p_string=myInterrupt.cause) # Determine reason for interrupt
                print("Interrupting instance name is: ", interrupter," Interrupt number ",A.interruptCount)
                continue
            finally:
                print("exceptDispatch try/except/finally Loop body finishing at time ",env.now)
        print("\n        ... Terminating  exceptDispatch execution using (loop) break ... at time ",env.now)

class B(object):
    """ exceptions generator """

    def __init__(self,*,env,className,instanceName):
        self.env = env
        self.className = className
        self.instanceName = instanceName
        print("Executing class B's __init__ function for instance: ",self.instanceName," at time ",env.now)

    def genExceptions(self,*,env,instanceName):
        self.instanceName = instanceName
        # First digits until ":"  encountered is the A instance number in e.cause first chcaracters
        a1.do_exceptDispatch.interrupt(str(self.instanceName) + ": is INTERRUPTING a1's exceptDispatch pf") # arg = Cause
        yield env.timeout(0) # No lag/delay between thrown interrupts
        print("!! Class B instance ",self.instanceName," is interrupting A instance at time ",env.now) 

def zeroDiv(*,env,delay):
    """Generate a  built-in ZeroDivide exception at model time  delay, and then an interrupt"""
    while True:
        try:
            yield env.timeout(delay)
            if delay == env.now:
                x = 3/0
            else:
                env.process(b1.genExceptions(env=env,instanceName='b1'))  # b1 throws a second interrupt
                yield env.timeout(1) # Suspend for 1 t.u.
                non_pf_fcn() # See if b1 can throw a third interrupt
                break
        except ZeroDivisionError:
            print("Division by zero exception at time ", env.now,  \
                  "Continue the simulation")
            continue

def non_pf_fcn():
    """See if a non-pf can throw a simpy interrupt"""
    env.process(b1.genExceptions(env=env,instanceName='b1'))  # b1 throws a third interrupt
       
env = simpy.Environment()
runDuration = 10 # Specify model runtime upper bound

a1 = A(env=env,className='A',instanceName='a1') # Create class A and B instances
b1 = B(env=env,className='B',instanceName='b1')
b2 = B(env=env,className='B',instanceName='b2')

env.process(a1.exceptDispatch(env=env)) # Start A's instance exception dispatcher pf to listen for interrupts
env.process(b1.genExceptions(env=env,instanceName='b1')) # Throw 2 interrupts at same model time (just to show it's legal)
env.process(b2.genExceptions(env=env,instanceName='b2'))
env.process(zeroDiv(env=env,delay=4))

env.run(until=runDuration)
print("Finished run at model time ", env.now)
print("\n    Review of Python variables scope by example")
print("Is A.interruptCount a global? ",eval("'A.interruptCount' in globals()"))
print("A's dictionary contents: \n", sorted(cls_d.dict_from_class(A).items())) # Class A's dictionary contents
print("Demoing display of class A's __doc__ dunder variable: ", A.__doc__)

