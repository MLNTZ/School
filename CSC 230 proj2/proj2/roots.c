/*********************************************************
 * Student Name and UnityID
 * CSC230, Summer 2019
 * Project2
 *
 * This program will calculate the roots of a
 * quadratic equation using the quadratic formula. The
 * user will be prompted for three number coefficients,
 * a, b, and c. It will calculate the roots.
 *********************************************************/

// Include our own header first
#include "read.h"

// Then, anything else.
#include <stdio.h>
#include <stdlib.h>

#define GUESS_THRESHOLD 0.000000001

#define EXIT_VALID 0
#define EXIT_BADINPUT 1
#define EXIT_DISCRIMINATE_NEGATIVE 2
#define EXIT_DISCRIMINATE_ZERO 3

int main();
//TODO: You must provide the function prototype for mysqrt here.
double myabs(double n);
double getroot(double a, double b, double c, int positive);



int main()
{
  double a, b, c;
  double result;


  //DEBUG CASE #1 (does the myreaddouble function work?)
  
  // while(1)
  // {
  //   double in;
  //   in = myreaddouble();
  //   printf("read %lf\n", in);
  //   if(in == 0)
  //   {
  //     printf("terminating on zero\n");
  //     return 0;
  //   }
  // }
  

  //DEBUG CASE #2 (does the myabs function work?)
  
  while(1)
  {
    double in;
    in = myreaddouble();
    printf("read %lf - the absolute value of that is %lf\n", in, myabs(in));
    if(in == 0)
    {
      printf("terminating on zero\n");
      return 0;
    }
  }
  

  //DEBUG CASE #3 (does the squareroot function work?)
  /*
  while(1)
  {
    double in;
    in = myreaddouble();
    printf("read %lf - the square root of that is %lf\n", in, mysqrt(in));
    if(in == 0)
    {
      printf("terminating on zero\n");
      return 0;
    }
  }
  */

  printf("This program solves a quadratic equation using the quadratic formula.\n");
  printf("For the form ax^2 + bx + c = 0 please enter the following:\n");

  printf("a=");
  a = myreaddouble();
  //  You are not allowed to use this, but could debug with it: scanf("%lf", &a);

  printf("b=");
  b = myreaddouble();
  //  You are not allowed to use this, but could debug with it: scanf("%lf", &b);

  printf("c=");
  c = myreaddouble();
  //  You are not allowed to use this, but could debug with it: scanf("%lf", &c);

  //You could uncomment this to debug the values or a b and c:
  //printf("a b c %lf %lf %lf\n", a, b, c);

  result = getroot(a, b, c, 1);
  printf("The positive root is %lf\n", result);

  result = getroot(a, b, c, 0);
  printf("The negative root is %lf\n", result);

  exit(EXIT_VALID);
}


//TODO: You must provide the comments for mysqrt here.
//TODO: You must provide the function definition for mysqrt here.
//TODO: You must implement the function mysqrt here.
//TODO: Remember, mysqrt takes a single parameter as a double,
//      and it also returns a double that is the square root
//      for that number.
//      The squareroot of 0 is 0.
//      The squareroot of a negative results in:
//        {
//          printf("mysqrt cannot accept negative numbers.\n");
//          exit(EXIT_BADINPUT);
//        }
//
//This is the algorithm to use for estimating the square root:
//Given input 'n':
//
//  Perform input validation on n. (Not negative)
//  If n is zero, then shortcircuit and return zero immediately.
//
//  Let guess be n.
//  Let the next movementDistance be n/2
//
//  While the absolute value of (guess^2 - n) is greater than GUESS_THRESHOLD:
//    If the guess^2 is less than n
//      Let the guess be guess + movementDistance
//    Otherwise
//      Let the guess be guess -movementDistance
//    Let the movementDistance be divided by two
//
//  Return the value of guess. (It is now within the GUESS_THRESHOLD)
//
//
//TODO: Start the function mysqrt right HERE






/*
 * This function returns the absolute value of a provided double.
 * ie, 123 returns 123
 *     -456 returns 456
 *     0 returns 0
 */
double myabs(double n)
{
  return (n < 0) ? 0 - n : n;
}


//TODO: You must provide the comments for getroot here.
//TODO: You must provide the function definition for getroot here. (go copy the prototype above!)
//TODO: You must implement the function getroot here.
//HINT: Remember that if the discriminate is negative then you'll print the message:
//        "The discriminant is negative. There are no real roots.\n"
//        and exit with EXIT_DISCRIMINATE_NEGATIVE
//      If the discriminate is zero then you'll print:
//        "The discriminant is zero. There is only one real root.\n"
//        and exit with EXIT_DISCRIMINATE_ZERO
//HINT: Remember that if the variable positive is set to zero, then
//      you return the negative case from the quadratic formula.
//      Otherwise, you return the positive case from the quadratic formula.
//HINT: To do b^2 you simply evaluate (b*b) since C doesn't let you use the '^' operator like that.
