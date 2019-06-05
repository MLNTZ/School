CC = gcc
CFLAGS = -Wall -std=c99 -g
SRC_FILES = read.c roots.c
OBJ_FILES = read.o roots.o
roots: read.o
roots.o: read.h

read.o: read.c read.h

clean:
	rm -f $(OBJ_FILES)
	rm -f roots.exe
