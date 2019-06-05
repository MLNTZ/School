#!/bin/bash
FAIL=0

# Function to run a test and check its output and exit status against expected.
runTest() {
  TESTNO=$1
  ESTATUS=$2

  rm -f output.txt

  echo "Test $TESTNO: ./roots < input-$TESTNO.txt > output.txt"
  ./roots < input-$TESTNO.txt > output.txt
  STATUS=$?

  # Make sure the program exited with the right exit status.
  if [ $STATUS -ne $ESTATUS ]; then
      echo "**** Test $TESTNO FAILED - incorrect exit status. Expected: $ESTATUS Got: $STATUS"
      FAIL=1
      return 1
  fi

  # Make sure the output matches the expected output.
  if ! diff -q expected-$TESTNO.txt output.txt >/dev/null 2>&1 ; then
      echo "**** Test $TESTNO FAILED - output didn't match the expected output"
      FAIL=1
      return 1
  fi

  echo "Test $TESTNO PASS"
  return 0
}

# Build the program.
make clean
make

# Run individual tests.
if [ -x roots ] ; then
    runTest 1 0
    runTest 2 0
    runTest 3 0
    runTest 4 0
    runTest 5 2
    runTest 6 3
    runTest 7 1
    runTest 8 1
    runTest 9 1
else
    echo "**** The program didn't compile successfully"
    FAIL=1
fi

if [ $FAIL -ne 0 ]; then
  echo "**** There were failing tests"
  exit 1
else
  echo "Tests successful"
  exit 0
fi
