import sys
N = int(input())
list_ = []
for i in range(N):
    a,b = map(int, sys.stdin.readline().split())
    list_.append([a,b])
list_ = sorted(list_, key=lambda x: (x[0], x[1]))
for li in list_:
    print(str(li[0]) + " " + str(li[1]))