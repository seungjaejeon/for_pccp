n = int(input())
for i in range(n):
    str_ = str(i)
    sum_ = i
    for s in str_:
        sum_ += int(s)
    if sum_ == n:
        print(i)
        break