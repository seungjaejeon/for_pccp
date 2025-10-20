import sys
n = int(input())
list_ = []
for i in range(n):
    ox = sys.stdin.readline()
    answer = 0
    idx = 1
    for j in ox:
        if j == 'O':
            answer += 1 * idx
            idx += 1
        else:
            idx = 1
    list_.append(answer)
for i in list_:
    print(i)