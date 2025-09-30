"""
timeout_2.py - Demo  script about timeout objects & How To schedule them - WJM, CSUS, 2021
                Also illustrate debug use
            As a demo, a time unit (t.u.) is undefined
"""
import pdb # The default debugger
import simpy # The simpy DES
env=simpy.Environment() # Reference to  simpy environment

"""

# First attempt to simulate a (non-programmable) timer; 2 concurrent timeouts
x = env.timeout(2) # Create timeout objects, with delay = argument value
y = env.timeout(3)
env._queue # Display scheduled events
# The timeout() objects do not have expected effect; model ends at time = max(3,2), not 3+2 
#  time; that is, after model execution, model time is 3, not 5
print("Timeout object x ", x," created at time ", env.now)
print("Timeout object y ", y," created at time ", env.now)
env.run()   # Execute the simulation until no more events are scheduled
print("Simulation ended at time ", env.now) # x and y still exist here


# Second attempt to build a simulation that processes a delay event
# Error "yield outside a function" thrown
yield env.timeout(5) # Execute a timeout object
env.run()   # Execute the simulation until no more events are scheduled
print("Simulation ends at time ", env.now)

"""

# Third attempt - schedule and execute 2 successive timeout delays
def scheduleMe(delay):
    # delay is the timeout parameter
    #pdb.set_trace() # Left here to illustrate effects when a scheduled pf starts
    print("In scheduleMe: A chain of 2 cumulative model delays started at time ", env.now)
    for j in range(2):
        print("scheduleMe ",(j+1)," delay started at time ", env.now)
        print("This delay duration is ", delay+(2*j)," time units")
        yield env.timeout(delay+(2*j))
        print("scheduleMe ",(j+1)," delay finished at time ", env.now)
    return

def monitorAllEvents():
    for j in range(runDuration):
        yield env.timeout(3) # Every 3 t.u., dump the event queue
        print("Event queue contents at time ",env.now," is: ",env._queue)

# Schedule the pfs Note - a pf can be scheduled at any desired model time
runDuration = 13 # Define model run duration
# When a pf b (anonymous or not) is scheuled at model time t, then b is initialized at time t
#  (as with any generator) AND b executes until encountering & executing the first yield.
#  Unlike a Python generator, a next function is not needed tostart and execute code up to a yield.
# However, in simpy, a yield with env.timeout() pauses/suspends b until the timeout expires
env.process(scheduleMe(5))  # Asynchronously schedule (run in parallel) some pfs
env.process(monitorAllEvents())
# Note  Above pfs do not execute until env.run is executed
print("The timeouts were scheduled at time ", env.now)
env.run(until = runDuration)
# Here after all scheduled events have occurred; display PostRun info
print("Simulation ends at time ", env.now)
print("The env object is ", env)

