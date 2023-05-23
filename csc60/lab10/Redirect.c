/* Author(s): Conner Childers */

/* ----------------------------------------------------------------- */
/*                  Redirect, part of a child process                */
/* ----------------------------------------------------------------- */

#include "lab9_10.h"

void Redirect(int argc, char *argv[])
{
    int i;	     // loop counter
    int out = 0;  // track position of location Out redirection, >
    int in = 0;   // track position of location In redirection, <
    int fd;

    for(i=0; i < argc; i++) {
        if(strcmp(argv[i], ">") == 0) {
            if(out != 0) {
                fprintf(stderr, "Cannot Output to more than one file\n");
                _exit(EXIT_FAILURE); //failure
            }
            else if(i == 0) {
                fprintf(stderr, "No Command Entered\n");
                _exit(EXIT_FAILURE); //failure
            }
            out = i;
        }
        else if(strcmp(argv[i], "<") == 0) {
            if(in != 0) {
                fprintf(stderr, "Cannot input from more than one file\n");
                _exit(EXIT_FAILURE);
            } else if (i == 0) {
                fprintf(stderr, "No command entered\n");
                _exit(EXIT_FAILURE);
            }
            in = i;
        }
    }
    
    if(out != 0) {
        if(argv[out+1] == NULL) {
            fprintf(stderr, "There is no file\n");
            _exit(EXIT_FAILURE); //failure
        }
        fd = open(argv[out+1],O_RDWR | O_CREAT | O_TRUNC, S_IRUSR | S_IWUSR);
        if(fd == -1) {
            perror("Error opening file\n");
            _exit(EXIT_FAILURE); //failure
        }
        dup2(fd, 1);
        int returnvalue = close(fd);
        if (returnvalue == -1) {
            perror("Error Closing File\n");
            _exit(EXIT_FAILURE); //failure
        }
        argv[out] = NULL;
    }

    if(in != 0) {
        if(argv[in+1] == NULL) {
            fprintf(stderr, "There is no file\n");
            _exit(EXIT_FAILURE); //failure
        }
        fd = open(argv[in+1], O_RDONLY);
        if(fd == -1) {
            perror("Error Opening File\n");
            _exit(EXIT_FAILURE); //failure
        }
        dup2(fd, 0);
        int returnvalue = close(fd);
        if(returnvalue == -1) {
            perror("Error Closing File\n");
            _exit(EXIT_FAILURE); //failure
        }
        argv[in] = NULL;
    }

    
}  /*end of function*/

