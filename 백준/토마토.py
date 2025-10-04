import sys
from collections import deque
# 가로, 세로 높이
M, N, H = map(int, sys.stdin.readline().split())
list_ = []
tomato = deque()
X = [-1,0,0,1,0,0]
Y = [0,1,-1,0,H,-H]
zero = 0
for i in range(N*H):
    li = list(map(int, sys.stdin.readline().split()))
    for j, val in enumerate(li):
        if val == 1:
            tomato.append([i, j, 0])
        if val == 0:
            zero += 1
    list_.append(li)
def in_range(y, x, N, M):
    if 0<=x<M and N*(y//N)<=y<N*(y//N+1) and 0<=y<N*H:
        return True
ans = 0

while tomato:
    y, x, day = tomato.popleft()
    for _ in range(6):
        dy = y + Y[_]
        dx = x + X[_]
        if in_range(dy, dx, N, M) and list_[dy][dx] == 0:
            tomato.append([dy, dx, day + 1])
            list_[dy][dx] = day + 1
            ans = max(ans, day + 1)

flag = False
for i in range(N*H):
    if flag : break
    for j in range(M):
        if list_[i][j] == 0:
            print(-1)
            flag = True
            break
if not flag:
    print(ans)
        
