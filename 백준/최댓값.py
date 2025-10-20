import sys
list_ = []
for i in range(9):
    list_.append(int(input()))
new_list = list_
n = sorted(list_).pop()
print(n)
print(new_list.index(n) + 1)