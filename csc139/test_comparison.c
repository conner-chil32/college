/* =========================================================================
 * Performance Comparison Test: esharedhash vs sharedhash
 * =========================================================================
 * 
 * This test demonstrates the dramatic performance difference between:
 *   1. sharedhash.c   - Global mutex on every allocation (severe contention)
 *   2. esharedhash.c  - Thread-local memory pools (minimal contention)
 * 
 * COMPILE INSTRUCTIONS:
 *   gcc -o test_sharedhash test_comparison.c sharedhash.c -pthread
 *   gcc -o test_esharedhash test_comparison.c esharedhash.c -pthread
 * 
 * RUN INSTRUCTIONS:
 *   # Test with different thread counts to see scalability
 *   ./test_sharedhash -t 1    # Single thread baseline
 *   ./test_sharedhash -t 4    # 4 threads (shows contention)
 *   ./test_sharedhash -t 8    # 8 threads (severe contention)
 *   
 *   ./test_esharedhash -t 1   # Single thread baseline
 *   ./test_esharedhash -t 4   # 4 threads (scales well!)
 *   ./test_esharedhash -t 8   # 8 threads (scales great!)
 * 
 * EXPECTED RESULTS:
 *   sharedhash:   Minimal speedup (1.1-1.3x) due to lock contention
 *   esharedhash:  Near-linear speedup (3.5-7x) with independent allocations
 * 
 * WHY THIS MATTERS:
 *   When threads have independent allocation patterns (like processing
 *   separate data blocks), thread-local pools eliminate contention and
 *   unlock true parallel performance. sharedhash forces serialization
 *   at the allocator, wasting most of your CPU cores.
 * ========================================================================= */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>
#include <time.h>
#include <unistd.h>
#include <sys/time.h>

#define BLOCK_SIZE 1024
#define SYMBOLS 256
#define LARGE_PRIME 2147483647
#define UMEM_SIZE (2 * 1024 * 1024)

/* External functions - we'll compile sharedhash/esharedhash separately */
void *umalloc(size_t size);
void ufree(void *ptr);
unsigned long process_block(const unsigned char *buf, size_t len);
void *init_umem(void);
int use_multiprocess = 0;

/* Thread-local pool initialization (only for esharedhash) */
void __attribute__((weak)) thread_pool_init(int tid, int num_threads) {
    /* This will only be called if esharedhash is linked */
    (void)tid;
    (void)num_threads;
}

/* =========================================================================
 * TEST WORKLOAD GENERATOR
 * =========================================================================
 * Creates realistic test data similar to the Huffman tree workload.
 * Each thread processes multiple independent blocks, exercising the
 * allocator heavily with many small allocations (tree nodes, heap arrays).
 * ========================================================================= */

typedef struct {
    int thread_id;
    int num_threads;
    int blocks_per_thread;
    unsigned long *result_hash;
    double *thread_time;
} thread_args_t;

/* Generate pseudo-random test data for a block */
void generate_test_block(unsigned char *buf, size_t len, unsigned int seed) {
    srand(seed);
    for (size_t i = 0; i < len; i++) {
        /* Create varied frequency distribution */
        int r = rand() % 100;
        if (r < 50) {
            buf[i] = 'a' + (rand() % 26);  /* Common: lowercase letters */
        } else if (r < 80) {
            buf[i] = 'A' + (rand() % 26);  /* Less common: uppercase */
        } else if (r < 95) {
            buf[i] = '0' + (rand() % 10);  /* Rare: digits */
        } else {
            buf[i] = rand() % 256;          /* Very rare: other symbols */
        }
    }
}

/* =========================================================================
 * WORKER THREAD: Independent Allocation Pattern
 * =========================================================================
 * Each thread:
 *   1. Gets its own thread-local memory pool (esharedhash only)
 *   2. Processes multiple blocks independently
 *   3. Each block builds a Huffman tree (100+ allocations)
 *   4. Trees are freed after hashing
 * 
 * KEY INSIGHT: No shared data structures between threads!
 *              Perfect scenario for thread-local pools.
 * ========================================================================= */

void *worker_thread(void *arg) {
    thread_args_t *args = (thread_args_t *)arg;
    
    /* Initialize thread-local pool (esharedhash only) */
    if (thread_pool_init) {
        thread_pool_init(args->thread_id, args->num_threads);
    }
    
    struct timeval start, end;
    gettimeofday(&start, NULL);
    
    unsigned long thread_hash = 0;
    unsigned char *block_buf = malloc(BLOCK_SIZE);
    
    /* Process multiple blocks independently */
    for (int block = 0; block < args->blocks_per_thread; block++) {
        /* Generate unique test data for this thread/block combo */
        unsigned int seed = args->thread_id * 10000 + block;
        generate_test_block(block_buf, BLOCK_SIZE, seed);
        
        /* Process block: builds Huffman tree with many allocations */
        unsigned long hash = process_block(block_buf, BLOCK_SIZE);
        thread_hash = (thread_hash + hash) % LARGE_PRIME;
    }
    
    free(block_buf);
    
    gettimeofday(&end, NULL);
    double elapsed = (end.tv_sec - start.tv_sec) + 
                     (end.tv_usec - start.tv_usec) / 1000000.0;
    
    *args->result_hash = thread_hash;
    *args->thread_time = elapsed;
    
    return NULL;
}

/* =========================================================================
 * BENCHMARK RUNNER
 * =========================================================================
 * Runs the workload with specified number of threads and measures:
 *   - Total wall-clock time
 *   - Per-thread execution time
 *   - Speedup vs single-threaded baseline
 * ========================================================================= */

void run_benchmark(int num_threads, int blocks_per_thread) {
    printf("\n");
    printf("====================================================================\n");
    printf("BENCHMARK: %d threads, %d blocks each (%d total blocks)\n",
           num_threads, blocks_per_thread, num_threads * blocks_per_thread);
    printf("====================================================================\n");
    
    pthread_t *threads = malloc(sizeof(pthread_t) * num_threads);
    thread_args_t *args = malloc(sizeof(thread_args_t) * num_threads);
    unsigned long *results = malloc(sizeof(unsigned long) * num_threads);
    double *thread_times = malloc(sizeof(double) * num_threads);
    
    /* Initialize allocator */
    init_umem();
    use_multiprocess = 1;  /* Enable locking for multi-threaded mode */
    
    struct timeval start, end;
    gettimeofday(&start, NULL);
    
    /* Launch all threads */
    for (int i = 0; i < num_threads; i++) {
        args[i].thread_id = i;
        args[i].num_threads = num_threads;
        args[i].blocks_per_thread = blocks_per_thread;
        args[i].result_hash = &results[i];
        args[i].thread_time = &thread_times[i];
        
        pthread_create(&threads[i], NULL, worker_thread, &args[i]);
    }
    
    /* Wait for all threads */
    for (int i = 0; i < num_threads; i++) {
        pthread_join(threads[i], NULL);
    }
    
    gettimeofday(&end, NULL);
    double total_time = (end.tv_sec - start.tv_sec) + 
                        (end.tv_usec - start.tv_usec) / 1000000.0;
    
    /* Compute final hash (verification) */
    unsigned long final_hash = 0;
    for (int i = 0; i < num_threads; i++) {
        final_hash = (final_hash + results[i]) % LARGE_PRIME;
    }
    
    /* Calculate statistics */
    double max_thread_time = 0;
    double min_thread_time = thread_times[0];
    double avg_thread_time = 0;
    
    for (int i = 0; i < num_threads; i++) {
        if (thread_times[i] > max_thread_time) max_thread_time = thread_times[i];
        if (thread_times[i] < min_thread_time) min_thread_time = thread_times[i];
        avg_thread_time += thread_times[i];
    }
    avg_thread_time /= num_threads;
    
    /* Print results */
    printf("\nRESULTS:\n");
    printf("  Total wall time:        %.3f seconds\n", total_time);
    printf("  Thread times (min/avg/max): %.3f / %.3f / %.3f seconds\n",
           min_thread_time, avg_thread_time, max_thread_time);
    printf("  Verification hash:      %lu\n", final_hash);
    printf("\n");
    
    /* Per-thread breakdown */
    printf("PER-THREAD BREAKDOWN:\n");
    for (int i = 0; i < num_threads; i++) {
        printf("  Thread %d: %.3f seconds (hash: %lu)\n", 
               i, thread_times[i], results[i]);
    }
    
    /* Cleanup */
    free(threads);
    free(args);
    free(results);
    free(thread_times);
}

/* =========================================================================
 * LOCK CONTENTION VISUALIZATION TEST
 * =========================================================================
 * This test specifically highlights contention by having all threads
 * allocate/free in tight loops simultaneously.
 * ========================================================================= */

typedef struct {
    int thread_id;
    int num_threads;
    int iterations;
    long allocations_completed;
} contention_args_t;

void *contention_thread(void *arg) {
    contention_args_t *args = (contention_args_t *)arg;
    
    /* Initialize thread-local pool (esharedhash only) */
    if (thread_pool_init) {
        thread_pool_init(args->thread_id, args->num_threads);
    }
    
    /* Allocate/free small blocks repeatedly */
    for (int i = 0; i < args->iterations; i++) {
        /* Simulate Huffman tree node allocations */
        void *ptrs[50];
        for (int j = 0; j < 50; j++) {
            ptrs[j] = umalloc(32);  /* Small allocation (tree node size) */
        }
        
        /* Free in reverse order */
        for (int j = 49; j >= 0; j--) {
            ufree(ptrs[j]);
        }
        
        args->allocations_completed += 50;
    }
    
    return NULL;
}

void run_contention_test(int num_threads, int iterations_per_thread) {
    printf("\n");
    printf("====================================================================\n");
    printf("CONTENTION TEST: %d threads, %d iterations each\n",
           num_threads, iterations_per_thread);
    printf("====================================================================\n");
    printf("Each iteration: 50 allocations + 50 frees\n");
    printf("Total operations: %d\n", num_threads * iterations_per_thread * 100);
    printf("\n");
    
    pthread_t *threads = malloc(sizeof(pthread_t) * num_threads);
    contention_args_t *args = malloc(sizeof(contention_args_t) * num_threads);
    
    /* Initialize allocator */
    init_umem();
    use_multiprocess = 1;
    
    struct timeval start, end;
    gettimeofday(&start, NULL);
    
    /* Launch threads */
    for (int i = 0; i < num_threads; i++) {
        args[i].thread_id = i;
        args[i].num_threads = num_threads;
        args[i].iterations = iterations_per_thread;
        args[i].allocations_completed = 0;
        pthread_create(&threads[i], NULL, contention_thread, &args[i]);
    }
    
    /* Wait for completion */
    for (int i = 0; i < num_threads; i++) {
        pthread_join(threads[i], NULL);
    }
    
    gettimeofday(&end, NULL);
    double total_time = (end.tv_sec - start.tv_sec) + 
                        (end.tv_usec - start.tv_usec) / 1000000.0;
    
    long total_ops = 0;
    for (int i = 0; i < num_threads; i++) {
        total_ops += args[i].allocations_completed;
    }
    
    printf("RESULTS:\n");
    printf("  Total time:             %.3f seconds\n", total_time);
    printf("  Operations completed:   %ld\n", total_ops * 2);  /* alloc + free */
    printf("  Throughput:             %.0f ops/sec\n", 
           (total_ops * 2) / total_time);
    
    free(threads);
    free(args);
}

/* =========================================================================
 * MAIN: Test Suite
 * ========================================================================= */

void print_usage(const char *prog) {
    printf("Usage: %s [options]\n", prog);
    printf("Options:\n");
    printf("  -t <num>    Number of threads (default: 4)\n");
    printf("  -b <num>    Blocks per thread (default: 10)\n");
    printf("  -c          Run contention test instead\n");
    printf("  -h          Show this help\n");
    printf("\nExamples:\n");
    printf("  %s -t 1           # Single-threaded baseline\n", prog);
    printf("  %s -t 8 -b 20     # 8 threads, 20 blocks each\n", prog);
    printf("  %s -c -t 8        # Contention test with 8 threads\n", prog);
}

int main(int argc, char *argv[]) {
    int num_threads = 4;
    int blocks_per_thread = 10;
    int contention_mode = 0;
    
    /* Parse arguments */
    for (int i = 1; i < argc; i++) {
        if (strcmp(argv[i], "-t") == 0 && i + 1 < argc) {
            num_threads = atoi(argv[++i]);
        } else if (strcmp(argv[i], "-b") == 0 && i + 1 < argc) {
            blocks_per_thread = atoi(argv[++i]);
        } else if (strcmp(argv[i], "-c") == 0) {
            contention_mode = 1;
        } else if (strcmp(argv[i], "-h") == 0) {
            print_usage(argv[0]);
            return 0;
        }
    }
    
    /* Validate arguments */
    if (num_threads < 1 || num_threads > 64) {
        fprintf(stderr, "Error: Thread count must be 1-64\n");
        return 1;
    }
    if (blocks_per_thread < 1 || blocks_per_thread > 1000) {
        fprintf(stderr, "Error: Blocks per thread must be 1-1000\n");
        return 1;
    }
    
    /* Print configuration */
    printf("\n");
    printf("====================================================================\n");
    printf("ALLOCATOR PERFORMANCE TEST\n");
    printf("====================================================================\n");
    printf("Linked against: %s\n", 
           thread_pool_init ? "esharedhash (thread pools)" : 
                             "sharedhash (global lock)");
    printf("\n");
    
    if (contention_mode) {
        /* Contention test: allocate/free in tight loops */
        run_contention_test(num_threads, 100);
    } else {
        /* Realistic workload: Huffman tree construction */
        run_benchmark(num_threads, blocks_per_thread);
    }
    
    printf("\n");
    printf("====================================================================\n");
    printf("KEY TAKEAWAYS:\n");
    printf("====================================================================\n");
    if (thread_pool_init) {
        printf("✓ esharedhash uses thread-local memory pools\n");
        printf("✓ Allocations from thread pool are LOCK-FREE\n");
        printf("✓ Should see near-linear speedup with thread count\n");
        printf("✓ Perfect for independent allocation patterns\n");
    } else {
        printf("✗ sharedhash uses global mutex on EVERY allocation\n");
        printf("✗ All threads serialize at allocator\n");
        printf("✗ Limited speedup regardless of thread count\n");
        printf("✗ Thread time spread indicates contention (threads waiting)\n");
    }
    printf("\n");
    
    return 0;
}
