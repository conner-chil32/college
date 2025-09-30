"""
    classesInSympy_3.py
    Nov 2021 Version:
        add Selective execution of Approach# 1 or 2, depending on which code section is commented
        add pf references, but only for code section Approach#2
    Nov 2020 Version: add Demo of List of class instance references
    Classes and their instances used in simpy behave the same as in ordinary Python.
    Python simplifies the distinction between "class" and "instance" variables
    (these referred to in languages such as Java as "static" and "non-static").
    The keyword "static" is not used in Python. Instead, any variable that is assigned
      a value in a class declaration is a class variable, and a variable assigned a
      value in a class function (including __init__) is an instance variable.
      After a class variable is assigned, reference it everywhere by: className.variableName
    This module Demos: 1) creating class definitions and instances 2) using class and instance variables
      3) scheduling class pfs and 4) two ways to save/store class instance and other object references
      USAGE - user prompted to choose variables or a list for storing object references
"""
import pdb
import simpy
env=simpy.Environment()

class Myclass(object):
    """
        Demo class instances, and scheduling a class's pf
    """
    numOfInstances = 0 # Example class variable, shared and updatable among all instances

    def __init__(self,*,env,x,instanceName):
        self.x = x # After assignment, each parameter is referred to by self.parametername
        self.instanceName = instanceName
        print("Executed Myclass __init__ for instance named ", self.instanceName," at time ", env.now)
        print("Class __init__ argument x is ", x)
        Myclass.numOfInstances  += 1 # Increment count of instances created

    def pfun(self,*,env,delay):
        """
            Demo pf;  parameter delay specifies suspension duration per pfun execution
        """
        self.x = self.x.append('testString') # Add a string to end of __init__ parameter self.x
        print("pfun delay starts at time ", env.now, " for instance name ",self.instanceName)
        yield env.timeout(delay) # This pf suspends for delay t.u.s
        print("pfun delay finished at time ", env.now," for instance name ",self.instanceName)
        print("Event queue at time ",env.now, " ",env._queue)

# A stand-alone Python function
def loneRanger():
    """ Demo that env is accessible in a stand-alone function """
    print("In loneRanger function, env is ", env)
    print("Simulation not yet started => Scheduled events list is empty: ",env._queue) 

loneRanger() # Stand-alone function call; breakpoint after call shows simulation not yet started

def enumList(*,enumList,enumListName):
    """ Iterate through a list and print each list item value & its type """
    for j,k in enumerate(enumList):
        print(enumListName + '[',j,']: ', enumList[j],' has type ',type(k))

myList1 = [1,2,3] # Test objects
myList2 = [None,-4]

def objectRefsInVariables():
    """How to store class instance references in variables"""
    print("Executing function objectRefsInVariables")
    mc1 = Myclass(env=env,x=myList1,instanceName='mc1') # Create instances of Myclass
    mc2 = Myclass(env=env,x=myList2,instanceName='mc2')
    pfun1 = env.process(mc1.pfun(env=env,delay=5)) # Schedule instances of Myclass's  pf pfun now
    pfun2 = env.process(mc2.pfun(env=env,delay=9))

def objectRefsInList():
    """How to store class instance references in a list <-- Access each refr by indexing"""
    print("Executing function objectRefsInList")
    instanceList = [] # Init list of Myclass instance references
    pfList = [] # Init list of Myclass.pfun pf references
    for k in range(3):  # "3" is a Demo constant
        # Populate lists with Myclass instance & pfun references
        instanceList.append(Myclass(env=env,x=myList1,instanceName='mcList'+str(k+1))) # Create instance references container
        pfList.append( env.process(instanceList[k].pfun(env=env,delay=k+1)) ) # Schedule ea. instance run & save a reference

# User inputs choice of references storage
while True:
    refStore = input("For refs storage  Choose 'v' for variables and 'l' for lists  ")
    if refStore not in {'v','l'}:  # Re-prompt user if invalid input given
        print(" Please retry:  choose 'v' or 'l' ")
        continue
    else:  # Terminate loop after calling function per kind of references storage
        if refStore == 'v':
            objectRefsInVariables()
        else:
            objectRefsInList()
        break

print("\n ... Starting demo on storage option ",refStore," for object references")
env.run()  # Run the simulation
# Summary Report
print("Run Summary::\n Model finish time: ", env.now,"\n env is: ",env)
if 'instanceList' in globals(): # Display only for object references stored in list
    print("The value of the lists after the Approach#",Approach," run are:")
    enumList(enumList=instanceList,enumListName='instanceList')
    enumList(enumList=pfList,enumListName='pfList')
print("Class variable numOfInstances Value is ", Myclass.numOfInstances)

