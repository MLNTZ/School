/*********************************************************
 * Student Name and UnityID
 * CSC230, Summer 2019
 * Project2
 *
 * This program will read floating point numbers using
 * getchar() function
 *********************************************************/
// Include our own header first
#include "read.h"

// Then, anything else.
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <ctype.h>

#define EXIT_VALID 0
#define EXIT_BADINPUT 1
#define EXIT_DISCRIMINATE_NEGATIVE 2
#define EXIT_DISCRIMINATE_ZERO 3


double myreaddouble();


/*
 * Using only getchar() for input, this function creates a 'double' and
 * returns whatever floating point number it read from the keyboard.
 * It determines that it is ready return the read number ONLY when a
 * \n (newline) character is encountered.
 *
 * Should be able to handle the following inputs: (not exclusive list, but gives the general idea)
 *  1 12 12. 12.34 .34 .3 0 -0
 *  -. (this is valid and will be zero)
 *  (an empty newline - that implies zero)
 *  -1 -12 -12. -12.34 -.34 -.3
 *  0.0, 00.00 -0.0 -00.00 -0001.0000
 *  ...and any variation on those type of examples.
 *
 *  On error, it prints "Invalid input.\n" and terminates using exit(EXIT_BADINPUT);
 *  Errors include:
 *    Using the negative '-' anywhere but as the first character
 *    Using the '.' decimal more than once
 *    Using any other characters except the newline, 0-9, '-', and '.';
 */
double myreaddouble()
{
  double num = 0.0;
	double decimalPlace = 1.0;
	char c;
	_Bool isNeg = false;
	int pos = 0;
	while (1) {
		c = getchar();
		if (!isdigit(c) && c != '\n' && c != '.' && c != '-'){
		  printf( "Invalid input.\n");
			exit(EXIT_BADINPUT); //letter or special char
		} else if (c == '-' && pos != 0) {
      printf( "Invalid input.\n");
			exit(EXIT_BADINPUT); //negative in wrong place
		} else if (c == '.' && decimalPlace != 1) {
			printf( "Invalid input.\n");
			exit(EXIT_BADINPUT); //second decimal
		} else if (c == '-') {
			isNeg = true;
		} else if (c == '.') {
			decimalPlace = 0.1;
		} else if (isdigit(c)) {
			if (decimalPlace == 1) {
				num = num * 10;
				num = num + (c - '0');
			} else {
				num += (c - '0') * decimalPlace;
				decimalPlace /= 10;
			}
		} else if(c == '\n'){
			break;
		}

		pos ++;
	}
	if (isNeg) {
		num = 0 - num;
	}
	return num;
}
