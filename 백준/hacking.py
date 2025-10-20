import sys
from collections import deque
N, M = map(int, sys.stdin.readline().split())
dict_ = [[] for _ in range (N + 1)]
for i in range(M):
    a, b = map(int, sys.stdin.readline().split())
    dict_[b].append(a)


num = [0] * (N + 1)
visited = [False] * (N + 1)

def dfs(node):
    if num[node]:
       return num[node]
    visited[node] = True
    cnt = 0
    for no in dict_[node]:
        if not visited[no]:
            cnt += 1 + dfs(no)
    visited[node] = False
    num[node] = cnt
    return num[node]

for i in range(1, N+1):
    dfs(i)
max_ = max(num)
for idx, val in enumerate(num):
    if val == max_:
        print(idx, end=" ")