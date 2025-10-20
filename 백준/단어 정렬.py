import sys
N = int(input())
set_ = set()
for i in range(N):
    str_ = sys.stdin.readline().strip()
    set_.add(str_)
list_ = list(set_)
list_ = sorted(list_, key=lambda x: (len(x), x))
print(*list_, sep="\n")