## 실시간 인덱스를 파악해서 뒤로 앞으로를 판단하기
# N*N -> 2500 충분함
#
import sys
from collections import deque
N, M = map(int, sys.stdin.readline().split())
pop = list(map(int, sys.stdin.readline().split()))
li = deque(i for i in range(1, N+1))

def move_front(li):
    n = li.popleft()
    li.append(n)

def move_back(li):
    n = li.pop()
    li.appendleft(n)
    
ret = 0
for p in pop:
    idx = li.index(p)
    if idx == 0:
        li.popleft()
    elif idx < len(li) - idx - 1:
        for i in range(idx):
            move_front(li)
        li.popleft()
        ret += idx
    else:
        move = len(li) - idx
        for i in range(move):
            move_back(li)
        li.popleft()
        ret += (move)
print(ret)

