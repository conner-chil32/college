"""
  Distinguish .triggered  VS ok  < -- ALL apsects of the event, incl callbacks were completed

simpySynch_demo.py  WJM, CSUS/ECS Demo several simpy process interactions
Ver4: Edit the function docStrings, 11/19
Ver3: Improve readability of displayed messages, 4/19
Ver2: Add a function process being suspended until a shared event is triggered, 5/18
Ver1:  Original, WJM, 1/17:
Every model execution, for any user-input mode, always schedules do_fcn1 instance of fcn1 first, which in turn,
  ASYNCHRONOUSLY schedules do_fcn2 instance of fcn2, and fcn1 resumes (fcn1 does not wait for fcn2 to finish)
For user input c:: an asynchronous return to fcn1 results in (model) concurrent execution of fcn1 & fcn2
For user input w:: fcn1 calls & waits for fcn2 to complete. then fcn1 resumes (i.e.,  "serialized execution")
For user input i:: fcn1 & fcn2 start concurrently, but fcn1 interrupts fcn2, then fcn1 resumes & finishes
For user input s:: fcn fcn1 posts a "shared event" (simpy terminology) and fcn1 & fcn4 are suspended
                    until fcn3 signals for fcn1 to continue
    Terminology:
    pf - simpy process function (can be scheduled by simpy scheduling operations)
    shared event - a named entity representing an event and its life cycle: created/happens/has-happened
"""
import random
random.seed(27)
import simpy
import pdb

def getUserInput(*,p_mode):
    """ Get user input that specifies the demo funcions to call """
    promptToUser = \
    """Input 'w' = fcn1 waits for fcn2 finish  OR  'c' = fcn1 & fcn2 concurrent exec  OR
      'i' = timeoutInterrupt  OR  's' = wait for shared event to occur 
    """
    print("Choose mode for which fcn2 executes when called by fcn1")
    p_mode = input(promptToUser) # Ask user for demo execution mode; mode specifies demo execution path
    assert p_mode in ('c','w','i','s') # Fatal assert error - runMode user input out of range
    return p_mode

def fcn4(*,env,p_se):
    """ A dummy process function that becomes a second waiter on the shared event """
    print("Starting fcn4 to wait on the shared event at time ", env.now)
    print("Current contents of FEL is ",env._queue)
    yield p_se # Wait on the shared event
    print("fcn4 finished waiting on the shared event at time ", env.now)

def fcn3(*,env,p_se):
    """ fcn3Signal is a call argument; fcn3 cannot refer to it by fcn3Signal or do_fcn1.fcn3Signal; it is local """
    print("Starting fcn3 ... at time ",env.now," fcn3 simply completes (i.e., finishes) the shared event")
    #Note - this use of fcn3 does NOT require generator objects; it is an ordinary python function
    p_se.succeed() # Make the shared event happen now; all shared event waiter(s) now automatically resume
    print("fcn3 processed (marked with success) the shared event at time ", env.now)

def fcn2(*,env,duration):
    """ A pf whos interrupt handler is executed when mode is 'i' """
    try:
        print("Starting fcn2 at time ", env.now)
        yield env.timeout(duration) 
        print("Finished fcn2 at time ", env.now)
    except simpy.Interrupt as interrupt:
        print("The pf that interrupted me, me being: fcn2, is ",interrupt.cause[:4])
        pdb.set_trace()
        print("fcn2 terminates at time ",env.now," because it was interrupted (and it is not a loop)")

def fcn1(*,env):
    """ The function that determines which other demo functions to call based on user input """
    fcn1RunDuration = 7 # fcn1's run duration in simulation time
    fcn1StartTime = env.now # Save fcn1's start time
    print("Starting fcn1 execution at time ", fcn1StartTime)
    print("Whether do_fcn1 has been triggered: ", do_fcn1.triggered)
    timeOfInterrupt = 0
    # Create occurrence (is NOT a call) of generator fcn2, then execute as specified by user input
    # type(do_fcn2) displays: <class 'simpy.events.Process'>
    do_fcn2 = env.process(fcn2(env=env,duration=2)) # Schedule execution of instance of fcn2 named do_fcn2
    if runMode == 'w':
        print("Wait sychronously for pf fcn2 to finish via: yield an instance of fcn2")
        yield do_fcn2
        print("Time after fcn1 waited for fcn2 to finish ", env.now)
    elif runMode == 'c':
        # Illustrate that fcn1 and fcn2 are running concurrently
        print("Concurrent fcn1/fcn2 execution; fcn1 resumes, NOT waiting for fcn2 to finish at time ",env.now)
        print("Current FEL contents is ",env._queue)
    elif runMode == 'i':
        print("Wait synchronously for fcn2 to finish OR interrupt fcn2 before fcn2 finishes")
        results = yield do_fcn2 | env.timeout(1.5) # Wait for fcn2 to finish OR interrupt fcn2 in 1.5 t.u.s
        if do_fcn2 not in results:
            timeOfInterrupt = env.now  # We will interrupt fcn2
            do_fcn2.interrupt('fcn1 interrupted fcn2 at time ' + str(timeOfInterrupt))
    elif runMode == 's':
        print("fcn1 creating shared event, and then both fcn1 & fcn4 wait for it to be triggered")
        fcn3Signal = env.event() # Create shared event for which to wait
        pdb.set_trace()
        print("Created shared event named fcn3Signal")
        # A dummy pf that does nothing but wait on shared event to happen 
        do_fcn4 = env.process(fcn4(env=env,p_se=fcn3Signal))  #Schedule instance of fcn4 and asynchronously resume do_fcn1
        fcn3(env=env,p_se=fcn3Signal) # Call fcn3; fcn3 will complete the shared event
        print("Upon return fr. fcn3 call, has shared event happened? T/F ", fcn3Signal.triggered, " at time ",env.now)
        yield fcn3Signal # Wait for the shared event to be triggered by another agent/process
        """
        IF fcn3 does NOT do .success() call, fcn1 is at: yieldfcn3signal (forever) and simulation aborts
            because run will NOT terminate (look at the env.run() argument) UNTIL do_fcn1 finishes
        """
        print("fcn1 finished waiting on the shared event at time ", env.now)
        
    # Adjust fcn1 finish time if fcn2 was interrupted (because time increased before fcn2 was interrupted) 
    do_fcn1_timeLeft = fcn1RunDuration-timeOfInterrupt # timeOfInterrupt is 0 when do_fcn2 was not interrupted
    print("Run time left in do_fcn1 is: ", do_fcn1_timeLeft)
    yield env.timeout(do_fcn1_timeLeft)
    print("Finished fcn1 at time ", env.now)

env = simpy.Environment()
print("... Doing simpySynch_demos script's configuration ...")
runMode ='' # Init user's run mode input value
runMode = getUserInput(p_mode=runMode) # Ask user for demo choice to execute
do_fcn1 = env.process(fcn1(env=env)) # Schedule pf named fcn1 instance do_fcn1 to start first
env.run(until = do_fcn1) # Run terminates when pf instance do_fcn1 finishes
print("Finished simpySynch_demos at time: ", env.now)
