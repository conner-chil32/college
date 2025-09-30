#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int debug = 1;

struct input {
    int flag;
    char* value;
    char* flag_type;
};

static void parse_inputs(struct input* args, int argc, char* argv[]) {
    // setting default values
    args[0].flag_type = "seed";
    args[0].value = "0";

    args[1].flag_type = "job count";
    args[1].value = "3";

    args[2].flag_type = "jobs list";
    args[2].value = "";
    
    args[3].flag_type = "max length";
    args[3].value = "10";

    args[4].flag_type = "policy flag";
    args[4].value = "FIFO";

    args[5].flag_type = "quantum";
    args[5].value = "1";

    args[6].flag_type = "compute";
    args[6].value = "false";

    args[7].flag_type = "help";
    args[7].value = "false";

    for (int i = 1; i < argc; i++) {

        if(strcmp(argv[i], "-s") == 0 || strcmp(argv[i], "--seed") == 0) {      // seed
            args[0].flag = 1;
            args[0].value = argv[i+1];      // <int> seed value
        }
        
        if(strcmp(argv[i], "-j") == 0 || strcmp(argv[i], "--jobs") == 0) {      // jobs
            args[1].flag = 1;
            args[1].value = argv[i+1];      // <int> jobs value
        }

        if(strcmp(argv[i], "-l") == 0 || strcmp(argv[i], "--jlist") == 0) {     // list of jobs
            args[2].flag = 1;
            args[2].value = argv[i+1];      // <int[]> jobs value"
        }

        if(strcmp(argv[i], "-m") == 0 || strcmp(argv[i], "--maxlen") == 0) {    // max length of job
            args[3].flag = 1;
            args[3].value = argv[i+1];      // <int> max length
        }

        if(strcmp(argv[i], "-p") == 0 || strcmp(argv[i], "--policy") == 0) {    // policy flag
            args[4].flag = 1;
            args[4].value = argv[i+1];      // <char[]> FIFO, SJF, RR
        }

        if(strcmp(argv[i], "-q") == 0 || strcmp(argv[i], "--quantum") == 0) {   // quantum
            args[5].flag = 1;
            args[5].value = argv[i+1];      // <int> quantum
        }

        if(strcmp(argv[i], "-c") == 0) {                                        // compute
            args[6].flag = 1;
            args[6].value = "true";         // <bool>
        }

        if(strcmp(argv[i], "-h") == 0) {                                        // help
            args[7].flag = 1;
            args[7].value = "true";         // <bool>
        }
    }
}

int main(int argc, char* argv[]) {

    struct input arguments[8];
    for(int i=0; i<8; i++) {
        arguments->flag = 0;
    }
    
    parse_inputs(arguments, argc, argv);

    if(debug == 1) {
        printf("DEBUG: \n");
        for(int i=0; i<8; i++) {
            printf("Flag %d: %d | ", i, arguments[i].flag);
            printf("Value: %s | ", arguments[i].value);
            printf("Flag Type: %s\n", arguments[i].flag_type);
        }
    }
    printf("\n");

    // MAIN LOGIC START
    srand(atoi(arguments[0].value));    // seed
    printf("ARG policy %s\n", arguments[4].value);

    if(arguments[2].value == "") {
        printf("ARG jobs %s\n", arguments[1].value);
        printf("ARG maxlen %s\n", arguments[3].value);
        printf("ARG seed %s\n", arguments[0].value);
    } else {
        printf("ARG jlist %s\n", arguments[2].value);
    }
    printf("\nHere is the job list, with the run time of each job: \n");

    
    
}