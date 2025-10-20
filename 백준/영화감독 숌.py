N = int(input())
n = 665
ans = 0
while True:
    n += 1
    str_ = str(n)
    if '666' in str_:
        ans += 1
    if ans == N:
        break
print(n)

