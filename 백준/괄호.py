import sys
n = int(input())
for i in range(n):
    str_ = sys.stdin.readline().strip()
    num = 0
    flag = True
    for s in str_:
        if num < 0:
            print("NO")
            flag = False
            break
        if s == '(':
            num += 1
        else :
            num -= 1
    if num == 0:
        print("YES")
    elif flag == True:
        print("NO")