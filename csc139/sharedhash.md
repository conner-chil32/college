# Sharedhash results
## By: Conner Childers

### Correctness test

```
./sharedhash test2.bin -m
Final signature: 355889017
```
```
./sharedhash test2.bin -t
Final signature: 355889017
```
```
./sharedhash test2.bin
Final signature: 355889017
```

### Explanation

Changes made between `run_multi()` and `run_threads()` are the main approach of using fork based method to assign each block of data to shared memory with `umalloc()`. Another major change is that `run_multi()` uses pipes to collect and restructe the results in order, which has to wait for each child to be finished in `waitpid()`. This requires a lot more overhead than `run_threads()` which doesn't a process-based shared memory approach.

Process-based shared memory (used in sharedhash with the -m flag) requires explicit setup of shared memory regions using mmap() or similar system calls. After the fork(), a child processes receive copies of variables, so to enable memory sharing, critical data structures like the free_list pointer must be placed in shared memory regions. As apposed to Thread-based memory sharing (used in sharedhash with the -t flag) which is simpler because threads within the same process naturally share the same address space. All global variables, heap memory, and the free list are automatically visible to all threads without requiring special setup. 



