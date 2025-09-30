import math
import random  # Random number & seeding module Note: Python automatically re-seeds, per run

# User prompted for an int or float rate value 
rate = float(input("Please enter a numerical exponential distribution rate: "))
assert (rate > 0 and isinstance(rate,float))  # Distribution mean must be positive and type float
print("User-input exponential distribution rate: ", rate)

def exprand(lambdr,rNum):
    """
    Return an exponentially-distributed random variate with RATE lambdr
    Each variate value is generated using the Inverse-transform method
    """
    return -math.log(rNum) / lambdr

# Display 20 random values with exponential distribution and distribution parameter  rate
for j in range(20):
    rNum = random.random() # Get a random number in (0,1)
    print ("1-(nextRandom number): ",(1-rNum)," Exp-distr. variate: ",exprand(rate,1-rNum))
