import List135

def createlist(start,end,ls=List135.List135()):
    if start == end:
        return ls
    else:
        ls = ls.add(start)
        return createlist(start+1,end,ls)

a = createlist(0,6)

import functools
list = [2,5,5,5,2,3]

def count(ys, y):
    a = functools.reduce(lambda x: x==y, ys)
    print(a)

count(list,2)



