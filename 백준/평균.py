import sys
n = int(input())
list_ = list(map(int, sys.stdin.readline().split()))

print(sum(list_) / max(list_) * 100 / len(list_))