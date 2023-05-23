# Ruthann Biel Lab 8 Structs
# You need to add the code here for get_stats.c

lab8: lab8.o get_data.o open_out_file.o print_all.o get_stats.o  lab8.h
	gcc lab8.o get_data.o open_out_file.o print_all.o get_stats.o  -o lab8 
   
lab8.o: lab8.c lab8.h
	gcc -c lab8.c 
	
get_data.o: get_data.c lab8.h	
	gcc -c get_data.c
	
open_out_file.o: open_out_file.c lab8.h
	gcc -c open_out_file.c
	
print_all.o: print_all.c lab8.h
	gcc -c print_all.c

get_stats.o: get_stats.c lab8.h
	gcc -c get_stats.c	
