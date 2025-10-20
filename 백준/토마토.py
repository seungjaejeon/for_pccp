# 1 : 익은 / 0: 익지 않은 / -1: 없는
import sys
from collections import deque
M,N,H = map(int, sys.stdin.readline().split())
list_ = []
tomato = deque()

X = [-1,0,0,1,0,0]
Y = [0,-1,1,0,N,-N]

def check_range(y, x):
  if 0<=y<N*H and 0<=x<M:
    return True
  return False
def check_done():
  for i in range(N*H):
    for j in range(M):
      if list_[i][j] == 0:
        return False
  return True
for i in range(N*H):
  tmp = list(map(int, sys.stdin.readline().split()))
  for j, li in enumerate(tmp):
    if li == 1:
      tomato.append([i, j, 0])
  list_.append(tmp)
ans = 0
while tomato:
  if check_done() : break
  y, x, day = tomato.popleft()
  for _ in range(4):
    dy = Y[_] + y
    dx = X[_] + x
    if dy // N == y // N and check_range(dy, dx) and list_[dy][dx] == 0:
      list_[dy][dx] = day + 1
      tomato.append([dy, dx, day + 1])
      ans = max(ans, day + 1)
  for _ in range(2):
    dy = Y[4+_] + y
    dx = X[4+_] + x
    if check_range(dy, dx) and list_[dy][dx] == 0:
      list_[dy][dx] = day + 1
      tomato.append([dy, dx, day + 1])
      ans = max(ans, day + 1)
flag = True
for i in range(N*H):
  if not flag : break
  for j in range(M):
    if list_[i][j] == 0:
      print(-1)
      flag = False
      break
if flag:
  print(ans)

    

s