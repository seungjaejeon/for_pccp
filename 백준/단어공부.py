import sys
str = sys.stdin.readline().strip()
dict_ = dict()
for s in str:
    if s.upper() in dict_:
        dict_[s.upper()] += 1
    else:
        dict_[s.upper()] = 1
sorted_dict = sorted(dict_.items(), key=lambda x: x[1], reverse=True)
print(sorted_dict)
if sorted_dict[0][1] != sorted_dict[1][1] :
    print(sorted_dict[0][0])
else:
    print("?")