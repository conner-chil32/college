/* Conner Childers  */
/* Lab 7            */

#include <stdio.h>
#include <stdlib.h>

void get_range_count(int number_list[], int real_filesize, int *range_count)
{
    range_count = 0;
    for(int i=0; i < real_filesize; i++) {
        if(number_list[i] > 90 && number_list[i] < 99) {
            range_count += 1;
        }
    }        
}
