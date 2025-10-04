import sys
n = int(sys.stdin.readline().strip())
result = 0
for i in range(1, n + 1):
    if len(str(i)) == 1:
        result += 1
        continue
    str_ = str(i)
    start = int(str_[1]) - int(str_[0])
    tmp = int(str_[-1])
    flag = 0
    for idx, li in enumerate(str_[::-1]):
        if(idx == 0 or idx + 1 == len(str_)): continue
        if int(str_[idx + 1]) - int(li) != start:
            flag = 1
            break
    if flag == 0:
        result += 1
print(result)