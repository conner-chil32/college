/*---------------------------------------------------------------*/
/* your name                                                     */
/* LAB 7   1-dimensional arrays                                  */


#include "lab7.h"

const char *FILENAME[] = {"lab7a.dat", "lab7b.dat", NULL};
    /* array of the data file names */

/*---------------------------------------------------------------*/
int main(void)
{
    int file_count = 0;         /* keeps track of which file we are on */
    int check_overflow = 0;     /* counter to prevent array overflow   */
    int real_filesize = 0;      /* actual count of the values read     */
    int number_list[MAX_SIZE];  /* the array                           */
    int range_count;            /* count of numbers within the range   */


    /* loop through each file, reading it, and getting stats on it */

    for (file_count=0; FILENAME[file_count] != NULL; file_count++)
    {
       real_filesize = get_data( FILENAME[file_count], number_list );
	    
       get_range_count(number_list, real_filesize, &range_count);

       print_all(FILENAME[file_count], real_filesize, &range_count);

    }
    return EXIT_SUCCESS;
}
/*-----------------------------------------------------------------*/
