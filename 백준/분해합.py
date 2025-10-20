n = int(input())
flag = True
for i in range(n):
    str_ = str(i)
    sum_ = i
    for s in str_:
        sum_ += int(s)
    if sum_ == n:
        flag = False
        print(i)
        break
if flag == True:
    print(0)
