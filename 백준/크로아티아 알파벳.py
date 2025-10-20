import sys
str_ = sys.stdin.readline().strip()
str_ = str_.replace("c=", "+").replace("c-", "+").replace("dz=", "+").replace("d-", "+").replace("lj", "+").replace("nj", "+").replace("s=", "+").replace("z=", "+")
print(len(str_))