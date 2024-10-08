import List135

def createlist(start,end,ls=List135.List135()):
    if start == end:
        return ls
    else:
        ls = ls.add(start)
        return createlist(start+1,end,ls)

a = createlist(0,6)
print(a)



