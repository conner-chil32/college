"""
demoClass.py  -  Illustrate some class basics.
Also included are some built-ins such as dir that inspect internal object info.
"""

import pdb
from datetime import datetime

class DemoClass(object):
    """Demo some fundamental Python class behavior"""
    # Class attributes are shared/global across al instances
    instanceCount = 0 # Current existing number of instances

    def __init__(self,*, instanceName,currentTime):
        """Initialize instance instanceName of DemoClass at time currentTime"""
        self.instanceName = instanceName # Instance attributes
        self.currentTime = currentTime
        
        DemoClass.instanceCount += 1 # Incr instances count
        print("...Initializing instance ", self.instanceName," of DemoClass at time", self.currentTime)

# Create a class instance AND a reference named dc1 to this instance
dc1 = DemoClass(instanceName='dc1',currentTime=datetime.now())
print("DemoClass attributes:: \n", dir(DemoClass))
print("The dictionary of all attributes of class instance dc1:: \n", dc1.__dict__)

print("... Finishing demoClass.py execution with ",DemoClass.instanceCount,"  instances of DemoClass")
