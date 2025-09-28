import sys
T = int(input())
list_ = []
for i in range(T):
    R, S = sys.stdin.readline().split()
    new_word = ''
    for s in S:
        for j in range(int(R)):
            new_word += s
    list_.append(new_word)
print(*list_, sep="\n")