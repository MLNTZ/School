import math;
import csv;
import unicodedata;


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
        if (int(i[0]) > splitVal):
            left.append(i)
        else:
            right.append(i)

    # print(left)
    # print(right)
    return left, right;


def contE(v5, splitVal):
    left, right = split(v5, splitVal);
    leftTrue = 0
    leftFalse = 0;

    rightTrue = 0
    rightFalse = 0

    for i in left:
        if i[1] == "T":
            leftTrue = leftTrue + 1
        else:
            leftFalse = leftFalse + 1

    for i in right:
        if i[1] == "T":
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
            if type(val) != int :
                if col == val:
                    ret.append(row)
            else :
                try:
                    if int(col) < val:
                        ret.append(row)
                except(ValueError):
                    pass
    return ret;

def count(data, val):
    i = 0;
    for row in data:
        for col in row:
            if col == val:
                i + 1
    return i;


def getMult(data, **vals):
    cpy = data.copy()
    for val in vals.items():
        cpy = getBranch(cpy, val[1])

    cpy =sorted(cpy, key=sortdata)

    return cpy;


def sortdata(x):
    return x[5]

def printtbl(data):
    print("_______________________________________________________________________")
    i = 1;
    print("","%-3s" % "ROW", "%10s" % "V1", "%10s" % "V2", "%10s" % "V3", "%10s" % "V4", "%10s" % "V5", "%10s" % "Class", "",
          sep='|')
    for row in data:
      print("","%-3s" % str(i), "%10s"%row[0], "%10s"%row[1], "%10s"%row[2], "%10s"%row[3], "%10s"%row[4],"%10s"%row[5], "", sep='|')
      i = i + 1
    print("_______________________________________________________________________")





v5 = [[1, False], [9, False], [11, True], [3, True], [12, True], [2, False], [19, True], [4, False],
 [18, False], [27, True], [8, True], [6, True], [21, False], [17, False], [22, True], [13, True]]

mild = [[1, False], [2, False], [4, True], [21, True], [17, True], [19, False], [8, True], [13, True]]


data = read('hw2q1.csv');



data = getMult(data, vals1=22, vals2="Strong")

classEnt = E(3, 5)
print("Class Ent= ", classEnt)

printtbl(data)

a,b =  1,1

c,d = 4,2
e,f = 1,1

size = 8.0;

print("E(", a, ",", b, ") = ", E(a,b))

print("E(", c, ",", d, ") = ", E(c,d))

print("E(", e, ",", f, ") = ", E(e,f))

Entropy = (((a + b)/size * E(a, b))  + ((c + d)/size * E(c, d)) )
#+ ((e + f)/size * E(e,f));
total = classEnt - Entropy

print("E(X)= " , Entropy)
print("G(T, X) = ", total)












result = 1;
num = 0;

for i in v5:
    temp = contE(mild, i[0])
    if (i[0] > 1 and i[0] < 19):
        if (temp < result):
            result = temp;
            num = i[0]
        if (temp == result and i[0] > num):
            result = temp;
            num = i[0]

left, right = split(mild, num)
print(left)
print(right)
#
#
print("Best V4 Ent = ", result)
print("Best Split = ", num)


result  = 1;
num = 0
result = contE(v5, 3)

print(result)











