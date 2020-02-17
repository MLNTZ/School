import math;
import csv;


def E(a, b):
    if (a == 0 or b == 0):
        return 0
    count = a + b
    valA = a / count

    valB = b / count
    log1 = -(math.log(valA, 2) * valA)
    log2 = (math.log(valB, 2) * valB)

    ent = log1 - log2

    return ent

def split(v5, splitVal):
    left = [];
    right = [];
    for i in v5:
        if (i[0] < splitVal):
            left.append(i)
        else:
            right.append(i)

    print(left)
    print(right)
    return left, right;


def contE(v5, splitVal):
    left, right = split(v5, splitVal);
    leftTrue = 0
    leftFalse = 0;

    rightTrue = 0
    rightFalse = 0

    for i in left:
        if i[1] == True:
            leftTrue = leftTrue + 1
        else:
            leftFalse = leftFalse + 1

    for i in right:
        if i[1] == True:
            rightTrue = rightTrue + 1
        else:
            rightFalse = rightFalse + 1



    return (len(left) / len(v5) * E(leftTrue, leftFalse)) + (len(right) / len(v5) * E(rightTrue, rightFalse))


def read(file):
   f = open(file)
   r = csv.reader(f);
   ret = []
   for i in r:
       ret.append(i)

   ret.remove(ret[0])
   return ret;

def getBranch(data, val):
    ret = []
    for row in data:
        for col in row:
            if col == val:
                ret.append(row)
    return ret;

def getMult(data, **vals):
    cpy = data.copy()
    for val in vals.items():
        cpy = getBranch(cpy, val[1])

    return cpy;


def println(data):
    print("__________________________________________________________")
    for row in data:
      print("", "%10s"%row[0], "%10s"%row[1], "%10s"%row[2], "%10s"%row[3], "%10s"%row[4], "", sep='|')
    print("__________________________________________________________")







data = read('hw2q1.csv');
# data = getMult(data,  vals1 ='Sunny', vals2 = 'T', val3 = 'Mild')

classEnt = E(5, 9)

println(data)


# v5 = [[1, False], [9, False], [11, True], [3, True], [12, True], [2, False], [19, True], [4, False],
# [18, False], [27, True], [8, True], [6, True], [21, False], [17, False], [22, True], [13, True]]
#
#
# result = 1;
# num = 0;
#
# for i in v5:
#     temp = contE(v5, i[0])
#     if (temp < result):
#         result = temp;
#         num = i[0]
#
# print(result)
# print(num)
#
#
# result  = 1;
# num = 0
# result = contE(v5, 3)
#
# print(result)











