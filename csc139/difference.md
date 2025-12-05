# Results from esharedhash.c vs sharedhash.c
## By: Conner Childers

### What Implementation did I use?:
I used Option A, Thread Based Memory Pools. The reason I chose to do this implementation is two fold:

1. It was the first one I saw, and by the time I realized we didn't have to do all of them it was too late to turn back.
2. I was interested to see just how big the difference would be, since the description said that it would be very fast for workloads with independent allocations.

### Testing Results:

The test file was a simple 1mb binary file named test2.bin. Here is the timing result for the two files.

#### sharedhash results:
```
time ./sharedhash test2.bin -t
Final signature: 355889017

real    0m3.060s
user    0m1.915s
sys     0m4.967s
```

#### esharedhash results:
 
```
time ./esharedhash test2.bin -t
Final signature: 355889017
Total allocator lock acquisitions: 2048

real    0m0.167s
user    0m0.034s
sys     0m0.061s
```

### Discussion:

Now the results shown here might not show much of an improvement, however the big improvements come in the field of lock contention.

Because sharedhash is using this global lock:
```
void *umalloc(size_t size) {
    if (use_multiprocess) pthread_mutex_lock(&mLock);
    void* p = _umalloc(size);
    if (use_multiprocess) pthread_mutex_unlock(&mLock);
    return p;
}
```
It falls victim to a Lock Contention since each thread needs it own lock. With esharedhash there are no locks, instead it uses a reusable thread pool to allocate resources. The current design I have for both of them is that they uses 8 total threads when in the `-t` flag.

The difference is how they use them!

Since each thread needs its own lock in sharedhash, the other 7 threads are put on hold while the first thread finishes and releases the lock. This causes a massive delay with larger files as there are more cycles needed, compounding the locking issues for each thread. With the sharedhash approach, the size of file directly affects the length of time needed to finish the operation.

To put into perspective, the problem isn't as severe with smaller fill sizes, the following was tested on a 5kb file:

#### sharedhash
```
time ./sharedhash sample.bin -t
Final signature: 1237462697

real    0m0.016s
user    0m0.011s
sys     0m0.000s
```

#### esharedhash
```
time ./esharedhash sample.bin -t
Final signature: 1237462697
Total allocator lock acquisitions: 10

real    0m0.009s
user    0m0.002s
sys     0m0.000s
```

With this small of a file, the difference between the two is minor at best, however the time jump from 0.016s to the +3s time for sharedhash with the 1mb file is indictive of the disadvantages with thread-lock when it comes to efficiency. 

Esharedhash achieves much tighter efficency targets by properly using concerrency to allocate each thread its own block to work on, 8 at a time each cycle. You can still theoretically run into the problem of thread-lock with significantly large files as the 8 thread limit with the current implementation means that each time it cycles 8 threads are working at the same time. Sure it is a theoretical force multiplier of 8 as apposed sharedhash. However with files over the 1mb, both sharedhash and esharedhash will fail due to the amount of blocks needed to process a file of that size exceeding 1024 blocks that it can process. But if we were to adjust the block size for both we would like see the same trend since esharedhash actually utilizes its threads in parallel, unlike sharedhash which effectively mimic a FIFO scheduler by hold all threads until the lock is free.

Another reason why we are also seeing such a high result from my testing is likely because my CPU has 8 cores. Because of that, it can truly handle 8 threads simultaneously (1 thread per core).

I am on a [AMD Ryzen 9 7940HS](https://www.cpubenchmark.net/cpu.php?cpu=AMD+Ryzen+9+7940HS&id=5454).

After further research into this (as I was not expecting such a large deviation originally and you will likely not given your testing on different hardware).

I found that because I have a 8 core machine, each of the 8 threads gets its own core to work on truly independently. On top of that, it appears the greater than 8x bonus comes from cache locality, which I don't full understand, but after some research, I found that each thread in the pool (128kb pool) fits entirely within the L1 cache of memory on my system, which means the program does not need to access RAM, which makes its siginificantly faster.

With all that said, sharedhash creates a concurrency bottleneck where the global lock serializes all allocation operations. With heavy allocation workloads like Huffman tree construction, threads spend >90% of their time waiting for locks instead of doing useful work. esharedhash effectively achieved parallelism by having each thread work inside its own lock free memory pool. This also each thread to work without contention, allowing them to utilize each core more effectively than sharedhash does.


