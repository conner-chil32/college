# poisson_eval.py

import math     # Required external modules are accessed via the import statement

def peval(mean,x):
    return( math.exp(-mean) * math.pow(mean,x) ) / math.factorial(x)

meanVal = 1     # Define the mean of the pmf to be evaluated


# Display the first j values of the pmf
# Note: range(n) iterates over the ints 0, 1, ..., (n-1)
for j in range(6):
    print(peval(meanVal,j))
