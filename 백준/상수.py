import sys
A, B = map(int, sys.stdin.readline().split())

A = str(A)[::-1]
B = str(B)[::-1]
print(max(int(A), int(B)))
