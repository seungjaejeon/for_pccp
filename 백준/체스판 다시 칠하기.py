import sys
import copy
N, M = map(int, sys.stdin.readline().split())
case1 = [['W', 'B', 'W', 'B', 'W', 'B', 'W', 'B'], ['B', 'W', 'B', 'W', 'B', 'W', 'B', 'W'], ['W', 'B', 'W', 'B', 'W', 'B', 'W', 'B'], ['B', 'W', 'B', 'W', 'B', 'W', 'B', 'W'], ['W', 'B', 'W', 'B', 'W', 'B', 'W', 'B'], ['B', 'W', 'B', 'W', 'B', 'W', 'B', 'W'], ['W', 'B', 'W', 'B', 'W', 'B', 'W', 'B'], ['B', 'W', 'B', 'W', 'B', 'W', 'B', 'W']]
case2 = [['B', 'W', 'B', 'W', 'B', 'W', 'B', 'W'], ['W', 'B', 'W', 'B', 'W', 'B', 'W', 'B'], ['B', 'W', 'B', 'W', 'B', 'W', 'B', 'W'], ['W', 'B', 'W', 'B', 'W', 'B', 'W', 'B'], ['B', 'W', 'B', 'W', 'B', 'W', 'B', 'W'], ['W', 'B', 'W', 'B', 'W', 'B', 'W', 'B'], ['B', 'W', 'B', 'W', 'B', 'W', 'B', 'W'], ['W', 'B', 'W', 'B', 'W', 'B', 'W', 'B']]
def check(list_):
    ret = 10**9
    cnt = 0
    for i in range(8):
        for j in range(8):
            if list_[i][j] != case1[i][j]:
                cnt += 1

    ret = min(ret, cnt)

    cnt = 0

    for i in range(8):
        for j in range(8):
            if list_[i][j] != case2[i][j]:
                cnt += 1
            
    ret = min(ret, cnt)

    return ret
                

list_ = []
for i in range(N):
    list_.append(list(sys.stdin.readline().strip()))
print(list_[0:8][1:9])
min_ = 10**10
for i in range(N):
    for j in range(M):
        if not (i + 7 < N and j + 7 < M) : continue
        min_ = min(min_, check(list_[i:i+8][j:j+8]))
print('min_', min_)