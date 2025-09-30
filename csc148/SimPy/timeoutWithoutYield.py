""" timeoutWithoutYield.py """
import pdb # The default debugger
import simpy # The simpy DES system
env=simpy.Environment() # simpy reference identifier

x = env.timeout(2)
print(env.now)
y = env.timeout(3)

# The timeout() objects do not have expected effect; model ends at time = max(3,2), not 3+2 
#  time; that is, after model execution, model time is 3, not 5
# x and y are simple timeout events that expire after their arg time duration
print("Timeout object x ", x," created at time ", env.now)
print("Timeout object y ", y," created at time ", env.now)
print("Future event queue at time ", env.now," is: ", env._queue)
env.run()   # Execute the simulation until no more events are scheduled
print("Simulation ended at time ", env.now,"\n") # x and y still exist here

