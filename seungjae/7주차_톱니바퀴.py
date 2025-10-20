import sys
from collections import deque
import copy
gears = []
for _ in range(4):
    gears_str = str(sys.stdin.readline().rstrip())
    gears.append(deque([int(i) for i in gears_str]))
K = int(sys.stdin.readline())
do = []
for i in range(K):
    do.append(list(map(int,sys.stdin.readline().split())))

# i = 2인게 오른쪽
# i = 6인게 왼쪽
def can_turn_left(number,l): #왼쪽 톱니바퀴가 돌아가는가?
    if number<0:
        return False
    if gears[number][2]!=l:
        return True
    return False
def can_turn_right(number,r): #오른쪽 톱니바퀴가 돌아가는가?
    if number>3:
        return False
    if r!=gears[number][6]:
        return True
    return False
def what_direction_turn(number,direction):
    if number>3 or number<0:
        return
    r, l = gears[number][2], gears[number][6]
    turn(number,direction)
    visited[number]=True
    if can_turn_right(number+1,r) and visited[number+1]==False:
        what_direction_turn(number+1,-direction)
    if can_turn_left(number-1,l) and visited[number-1]==False:
        what_direction_turn(number-1,-direction)
    return

def turn(number, direction): # number는 회전시킬 톱니바퀴
    if direction==1: #시계방향 오른쪽으로 한칸
        gears[number].rotate(1)
    else : # 반시계방향 왼쪽으로 1칸
        gears[number].rotate(-1)

for number, direction in do:
    visited = [False for _ in range(4)]
    what_direction_turn(number-1,direction)
answer = 0
if gears[0][0]==1:
    answer+=1
if gears[1][0]==1:
    answer+=2
if gears[2][0]==1:
    answer+=4
if gears[3][0]==1:
    answer+=8
print(answer)
