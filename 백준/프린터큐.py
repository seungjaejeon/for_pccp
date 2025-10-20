import sys
T = int(input())
for t in range(T):
    N, M = map(int, sys.stdin.readline().split())
    li = list(map(int, sys.stdin.readline().split()))
    new_li = list([i, li[i]] for i in range(N))
    ret = 1
    while new_li:
        max_ = max(new_li, key=lambda x: x[1])[1]
        idx, n = new_li.pop(0)
        if max_ != n:
            new_li.append([idx, n])
        else:
            if idx == M:
                print(ret)
                break
            else:
                ret += 1
        
        