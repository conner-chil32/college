#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int debug = 0;

struct input {
    int flag;
    char* value;
    char* flag_type;
};
struct job {
    struct job* next;
    int jobnum;
    int runtime;
};

struct job* create_job(int jobnum, int runtime) {
    struct job* newNode = (struct job*)malloc(sizeof(struct job));

    if (newNode == NULL) {
        printf("Memory allocation failed!\n");
        exit(1);
    }

    newNode->jobnum = jobnum;
    newNode->runtime = runtime;
    newNode->next = NULL;

    return newNode;
}

void push_job(struct job** head, int jobnum, int runtime) {
    struct job* newNode = create_job(jobnum, runtime);
    if (*head == NULL) {
        *head = newNode;
        return;
    }
    struct job* temp = *head;
    while (temp->next != NULL) {
        temp = temp->next;
    }
    temp->next = newNode;
}

struct job* pop_job(struct job** head) {
    if (*head == NULL) {
        printf("List is empty, nothing to pop.\n");
    }

    struct job* temp = *head;
    *head = (*head)->next;   // move head to the next node
    temp->next = NULL;       // detach popped node from the list
    return temp;        
}

void sort_jobs(struct job* head) {
    if (head == NULL) return;

    struct job* current;
    struct job* index;
    int temp;

    for (current = head; current->next != NULL; current = current->next) {
        for (index = current->next; index != NULL; index = index->next) {
            if (current->runtime > index->runtime) {
                // Swap the runtime
                temp = current->runtime;
                current->runtime = index->runtime;
                index->runtime = temp;

                // Swap the jobnum
                temp = current->jobnum;
                current->jobnum = index->jobnum;
                index->jobnum = temp;
            }
        }
    }
}

void print_jobs(struct job* head) {
    struct job* temp = head;
    while (temp != NULL) {
        printf("Job %d ( length = %d)\n", temp->jobnum, temp->runtime);
        temp = temp->next;
    }
    printf("\n");
}

int count_jobs(struct job* head) {
    int count = 0; 
    struct job* current = head; 

    while (current != NULL) {
        count++;
        current = current->next; 
    }
    return count;
}

static char** parse_delimiter(char* arg, char* delimiter) {
    char** buffer = (char**)malloc(sizeof(char*) * 24);
    char* token = strtok(arg, delimiter);
    int index = 0;

    while (token != NULL) {
        buffer[index] = token;
        token = strtok(NULL, delimiter);
        index++;
    }

    return buffer;
}

static void parse_inputs(struct input* args, int argc, char* argv[]) {
    // setting default values
    args[0].flag = 0;
    args[0].flag_type = "seed";
    args[0].value = "0";

    args[1].flag = 0;
    args[1].flag_type = "job count";
    args[1].value = "3";

    args[2].flag = 0;
    args[2].flag_type = "jobs list";
    args[2].value = "";
    
    args[3].flag = 0;
    args[3].flag_type = "max length";
    args[3].value = "10";

    args[4].flag = 0;
    args[4].flag_type = "policy flag";
    args[4].value = "FIFO";

    args[5].flag = 0;
    args[5].flag_type = "quantum";
    args[5].value = "1";

    args[6].flag = 0;
    args[6].flag_type = "compute";
    args[6].value = "false";

    args[7].flag = 0;
    args[7].flag_type = "help";
    args[7].value = "false";

    args[8].flag = 0;
    args[8].flag_type = "input_validation";
    args[8].value = "null";

    if(argc == 1) {
        args[8].flag = 1;
    }

    for (int i = 1; i < argc; i++) {

        if(strcmp(argv[i], "-s") == 0) {                                        // seed
            args[0].flag = 1;
            args[8].flag = 1;
            args[0].value = argv[i+1];                                          // <int> seed value
        } else if(strstr(argv[i], "--seed=") != NULL) {
            args[0].flag = 1;
            args[8].flag = 1;
            char** res = parse_delimiter(argv[i], "=");
            args[0].value = res[1];
            free(res);
        }
        
        if(strcmp(argv[i], "-j") == 0) {                                        // jobs
            args[1].flag = 1;
            args[8].flag = 1;
            args[1].value = argv[i+1];                                          // <int> jobs value
        } else if(strstr(argv[i], "--jobs=") != NULL) {
            args[1].flag = 1;
            args[8].flag = 1;
            char** res = parse_delimiter(argv[i], "=");
            args[1].value = res[1];
            free(res);
        }
        

        if(strcmp(argv[i], "-l") == 0) {                                        // list of jobs
            args[2].flag = 1;
            args[8].flag = 1;
            args[2].value = argv[i+1];                                          // <int[]> jobs value"
        } else if(strstr(argv[i], "--jlist=") != NULL) {
            args[2].flag = 1;
            args[8].flag = 1;
            char** res = parse_delimiter(argv[i], "=");
            args[2].value = res[1];
            free(res);
        }

        if(strcmp(argv[i], "-m") == 0) {                                        // max length of job
            args[3].flag = 1;
            args[8].flag = 1;
            args[3].value = argv[i+1];                                          // <int> max length
        } else if(strstr(argv[i], "--maxlen=") != NULL) {
            args[3].flag = 1;
            args[8].flag = 1;
            char** res = parse_delimiter(argv[i], "=");
            args[3].value = res[1];
            free(res);
        }

        if(strcmp(argv[i], "-p") == 0) {                                        // policy flag
            args[4].flag = 1;
            args[8].flag = 1;
            args[4].value = argv[i+1];                                          // <char[]> FIFO, SJF, RR
        } else if(strstr(argv[i], "--policy=") != NULL) {
            args[4].flag = 1;
            args[8].flag = 1;
            char** res = parse_delimiter(argv[i], "=");
            args[4].value = res[1];
            free(res);
        }

        if(strcmp(argv[i], "-q") == 0 || strcmp(argv[i], "--quantum") == 0) {   // quantum
            args[5].flag = 1;
            args[8].flag = 1;
            args[5].value = argv[i+1];      // <int> quantum
        } else if(strstr(argv[i], "--quantum=") != NULL) {
            args[5].flag = 1;
            args[8].flag = 1;
            char** res = parse_delimiter(argv[i], "=");
            args[5].value = res[1];
            free(res);
        }

        if(strcmp(argv[i], "-c") == 0) {                                        // compute
            args[6].flag = 1;
            args[8].flag = 1;
            args[6].value = "true";         // <bool>
        }

        if(strcmp(argv[i], "-h") == 0 || strcmp(argv[i], "--help") == 0) {      // help
            args[7].flag = 1;
            args[8].flag = 1;
            args[7].value = "true";         // <bool>
        }
    }
}

static void print_help() {
    printf("Usage: ./scheduler [options]\n");
    printf("    Options:\n");     
    printf("    -h, --help                      show this help message and exit\n");
    printf("    -s SEED, --seed=SEED            the random seed\n");
    printf("    -j JOBS, --jobs=JOBS            number of jobs in the system\n");
    printf("    -l JLIST, --jlist=JLIST         instead of random jobs, provide a comma-separated list of run times\n");
    printf("    -m MAXLEN, --maxlen=MAXLEN      max length of job\n");
    printf("    -p POLICY, --policy=POLICY      sched policy to use: SJF, FIFO, RR\n");                                
    printf("    -q QUANTUM, --quantum=QUANTUM   length of time slice for RR policy\n");
    printf("    -c                              compute answers for me\n");
}

void fifo(struct job* head) {
    if(debug == 1) printf("FIFO\n");

    struct job* tmp1 = head;
    struct job* tmp2 = head;
    int time = 0;

    printf("Execution trace:\n");
    while(tmp1 != NULL) {
        printf("  [ time %3d ] Run job %d for %.2d secs ( DONE at %.2d )\n", time, tmp1->jobnum, tmp1->runtime, time + tmp1->runtime);
        time += tmp1->runtime;
        tmp1 = tmp1->next;
    }

    printf("\nFinal statistics:\n");
    double t = 0.0;
    int count = 0;
    double turnaroundSum = 0.0;
    double responseSum = 0.0;
    double waitSum = 0.0;

    while(tmp2 != NULL) {
        int jobnum  = tmp2->jobnum;
        int runtime = tmp2->runtime;

        double response = (double)t;
        double turnaround = (double)t + (double)runtime;
        double wait = (double)t;

        printf("    Job %d -- Response: %.2f  Turnaround %.2f  Wait %.2f\n", jobnum, response, turnaround, wait);

        responseSum += response;
        turnaroundSum += turnaround;
        waitSum += wait;

        t += runtime;
        count += 1;

        tmp2 = tmp2->next;
    }
    printf("\n  Average -- Response: %.2f  Turnaround %.2f  Wait %.2f\n", responseSum/count, turnaroundSum/count, waitSum/count);
}

void sjf(struct job* head) {
    if(debug == 1) printf("SJF\n");

    sort_jobs(head);
    fifo(head);
}

void round_robin(struct job* head, double quantum) {
    if(debug == 1) printf("ROUND ROBIN\n");

    struct job* tmp1 = head;

    printf("Execution trace:\n");
    double turnaround[256];
    int response[256];
    double lastran[256];
    double wait[256];
    int jobcount = count_jobs(head);

    for(int i=0; i < jobcount; i++) {
        lastran[i] = 0.0;
        wait[i] = 0.0;
        turnaround[i] = 0.0;
        response[i] = -1;
    }

    struct job* runlist = NULL;
    while(tmp1 != NULL) {
        push_job(&runlist, tmp1->jobnum, tmp1->runtime);
        tmp1 = tmp1->next;
    }

    int time = 0;
    while(jobcount > 0) {
        struct job* job = pop_job(&runlist);
        int jobnum = job->jobnum;
        double runtime = (double)job->runtime;
        if(response[jobnum] == -1) {
            response[jobnum] = time;
        }
        double currwait = time-lastran[jobnum];
        wait[jobnum] += currwait;

        double ranfor;
        if(runtime > quantum) {
            runtime -= quantum;
            ranfor = quantum;
            printf("  [ time %3d ] Run job %3d for %.2f secs\n", time, jobnum, ranfor);
            push_job(&runlist,jobnum,runtime);
        } else {
            ranfor = runtime;
            printf("  [ time %3d ] Run job %3d for %.2f secs ( DONE at %.2f )\n", time, jobnum, (double)time, ranfor);
            turnaround[jobnum] = time + ranfor;
            jobcount -= 1;
        }
        time += ranfor;
        lastran[jobnum] = time;
    }
    printf("\nFinal statistics:\n");
    double turnaroundSum = 0.0;
    double waitSum = 0.0;
    double responseSum = 0.0;
    for(int i=0; i < count_jobs(head); i++) {
        turnaroundSum += turnaround[i];
        responseSum += response[i];
        waitSum += wait[i];
        printf("  Job %3d -- Response: %3.2f  Turnaround %3.2f  Wait %3.2f\n", i, (double)response[i], turnaround[i], wait[i]);
    }
    int count = count_jobs(head);
    printf("\n  Average -- Response: %3.2f  Turnaround %3.2f  Wait %3.2f\n", responseSum/count, turnaroundSum/count, waitSum/count);
}

int main(int argc, char* argv[]) {

    struct input arguments[9];
    
    parse_inputs(arguments, argc, argv);

    if(debug == 1) {
        printf("DEBUG: \n");
        for(int i=0; i<9; i++) {
            printf("Flag %d: %d | ", i, arguments[i].flag);
            printf("Value: %s | ", arguments[i].value);
            printf("Flag Type: %s\n", arguments[i].flag_type);
        }
    }

    // MAIN LOGIC START
    if(arguments[7].flag == 1) {    // if help is triggered
       print_help();
    } else {
        // CONFIGURATION VALIDATION
        if(arguments[8].flag != 0) {                                // checking input is valid, since any valid input configuration sets this flag to 1
            srand(atoi(arguments[0].value));    // seed             // on top of that, it should run defaults if no inputs are provided
            printf("ARG policy %s\n", arguments[4].value);

            if(arguments[2].value == "") {
                printf("ARG jobs %s\n", arguments[1].value);
                printf("ARG maxlen %s\n", arguments[3].value);
                printf("ARG seed %s\n", arguments[0].value);
            } else {
                printf("ARG jlist %s\n", arguments[2].value);
            }
            printf("\nHere is the job list, with the run time of each job: \n");
        } else {
            print_help();
        }

        struct job* head = NULL;
        
        // GENERATE JOBS
        if(arguments[2].flag != 1) { // randomly generated job lengths
            int count = atoi(arguments[1].value);
            for(int i=0; i < count; i++) {
                int runtime = (rand() & atoi(arguments[1].value)) + 1;
                push_job(&head,i,runtime);
            }
            print_jobs(head);
            printf("\n");

            if(arguments[6].flag == 1) {
                printf("** Solutions **\n");
                if(strcmp(arguments[4].value, "FIFO") == 0) {   // if the input is FIFO
                    fifo(head);
                } else if(strcmp(arguments[4].value, "SJF") == 0) {
                    sjf(head);
                } else if(strcmp(arguments[4].value, "RR") == 0) {
                    round_robin(head, (double)(atoi(arguments[5].value)));
                } else {
                    printf("Error: Policy, %s is not available.\n", arguments[4].value);
                }
            } else {
                printf("Compute the turnaround time, response time, and wait time for each job.\n");
                printf("When you are done, run this program again, with the same arguments,\n");
                printf("but with -c, which will thus provide you with the answers. You can use\n");
                printf("\n-s <somenumber> or your own job list (-l 10,15,20 for example)\n");
                printf("to generate different problems for yourself.\n");
                printf("\n");
            }
        } else {    // predefined job lengths
            char** lengths = parse_delimiter(arguments[2].value, ",");
            int count = 0;
            for(int i=0; i < sizeof(lengths); i++) {
                if(lengths[i] != NULL) {
                    count++;
                }
            }
            for(int i=0; i < count; i++) {
                int runtime = atoi(lengths[i]);
                push_job(&head,i,runtime);
            }

            print_jobs(head);
            printf("\n");

            if(arguments[6].flag == 1) {
                printf("** Solutions **\n");
                if(strcmp(arguments[4].value, "FIFO") == 0) {   // if the input is FIFO
                    fifo(head);
                    printf("\n");
                } else if(strcmp(arguments[4].value, "SJF") == 0) {
                    sjf(head);
                    printf("\n");
                } else if(strcmp(arguments[4].value, "RR") == 0) {
                    round_robin(head, (double)(atoi(arguments[5].value)));
                    printf("\n");
                } else {
                    printf("Error: Policy, %s is not available.\n", arguments[4].value);
                }
            } else {
                printf("Compute the turnaround time, response time, and wait time for each job.\n");
                printf("When you are done, run this program again, with the same arguments,\n");
                printf("but with -c, which will thus provide you with the answers. You can use\n");
                printf("\n-s <somenumber> or your own job list (-l 10,15,20 for example)\n");
                printf("to generate different problems for yourself.\n");
                printf("\n");
            }
        }
    }   
}