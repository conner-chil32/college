/*-------------------------------------------------------------------*/
/* Conner Childers                                                   */ 
/* Ruthann Biel                                                      */
/* get_stats_me is a function to do figure the best throw for each   */
/* thrower, compute the all-over average of the best throws, and     */
/* find the longest throw  on the track and each thrower's deviation */
/* from the winning throw                                            */

#include "lab8.h"

void get_stats(thrower_t throw_list[NCOMPETITORS], /* in & out */
               stats_t *throw_stats)      	   /* output   */
{
    throw_stats->average_of_best_throws = 0;
    throw_stats->winning_throw = 0;
    double sum = 0;
    
    for(int r=0; r < NCOMPETITORS; r++) {
        throw_list[r].best_throw = throw_list[r].tries[0];
        for(int c=0; c < N_TRIES; c++) {
            if(throw_list[r].tries[c] > throw_list[r].best_throw) {
                throw_list[r].best_throw = throw_list[r].tries[c];
            }
            if(throw_list[r].tries[c] > throw_stats->winning_throw) {
                throw_stats->winning_throw = throw_list[r].tries[c];
            }
        }
        sum += throw_list[r].best_throw;
    }
    throw_stats->average_of_best_throws = sum/NCOMPETITORS;
    

    for(int r=0; r < NCOMPETITORS; r++) {
        throw_list[r].deviation = throw_stats->winning_throw - throw_list[r].best_throw;
    }
    return;
}

/*--------------------------------------------------------*/
