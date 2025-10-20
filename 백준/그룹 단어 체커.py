import sys
n = int(input())
answer = 0
for i in range(n):
    str_ = sys.stdin.readline().strip()
    set_ = set()
    flag = False
    for idx, val in enumerate(str_):
        if val in set_:
            if str_[idx-1] != str_[idx]:
                flag = True
                break
        else:
            set_.add(val)
    if flag == False:
        answer += 1
print(answer)