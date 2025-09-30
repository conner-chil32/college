"""
chisquareCalcs_f22.py  - CSC148 Poisson pmf & Chi-square Ei calculations
Variable names uee terminolgy related to the Chi-square distribution fit
"""
import math  # Built-in math module

numberOfClasses = int(input("Number of combined (if needed) Classes: "))  # Number of classes is type int
OiFrequencyMean = float(input("Mean of the class frequencies: "))  # Mean derived from raw data
sumOfxiFrequencies = int(input("sum of the class observations (= number of intervals measured): ")) # Observation count

# The expected Poisson pmf pi values for user-input parameters
print("Chi-square class values calculations tool \n")
print("The pmf  values for pi = prob(X=xi), xi=0,1,2, ..., assuming NON-combined classes")
for xi in range (numberOfClasses):
    print("p"+str(xi), " is: ",(math.exp(-OiFrequencyMean)*OiFrequencyMean**xi )/math.factorial(xi) ) 
print("")
# The expected Ei values
print(" \nThe Ei  values for classes E1, E2, etc.")
for xi in range (numberOfClasses):
    print("E"+str(xi), " is: ",sumOfxiFrequencies*(math.exp(-OiFrequencyMean)*OiFrequencyMean**xi )/math.factorial(xi)) 
