import sys
n = int(input())
li = []
for i in range(n):
    a,b = map(int, sys.stdin.readline().split())
    li.append([a, b])
for a, b in li:
    sum_ = 1
    for x, y in li:
        if a == x and b == y:
            continue
        if a < x and b < y:
            sum_ += 1
    print(sum_ , end =" ")
