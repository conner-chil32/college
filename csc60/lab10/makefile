#Conner Childers, lab10 makefile

lab10: lab10.o ParseCmd.o ProcessExtCmd.o Redirect.o RunExtCmd.o
	gcc -g lab10.o ParseCmd.o ProcessExtCmd.o Redirect.o RunExtCmd.o -o lab10

lab10.o: lab10.c lab9_10.h
	gcc -c lab10.c

ProcessExtCmd.o: ProcessExtCmd.c lab9_10.h
	gcc -c ProcessExtCmd.c

Redirect.o: Redirect.c lab9_10.h
	gcc -c Redirect.c

RunExtCmd.o: RunExtCmd.c lab9_10.h
	gcc -c RunExtCmd.c 
