"""demoLists.py - Simple list procesing examples"""
lis1 = [1,2]
print("lis1's internal id ",id(lis1))
print("First element in list lis1 ",lis1[0]) # List indexing starts at 0, not 1

"""
The following assignment does NOT do what Python beginners think it does:
1) The append function adds the list argument to lis1 2) the append function returns
the value None; in other words, append has a side-effect, and 3) variable lis2 is a
reference to the result  returned by append()
"""
lis2 = lis1.append([3,4]) # A 2-element list becomes lis1's element with largest index
print("List lis1's modified contents: ",lis1)
"""
Assignment to lis2 was NOT a copy of the enlarged list lis1.
Surprisingly, lis2 was assigned None, because append() returns the value None
"""
print("lis2 type and value are ",type(lis2)," and ",lis2)
