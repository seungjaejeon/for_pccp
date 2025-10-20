n = 1
def func(n):
    str_ = str(n)
    ret = n
    for s in str_:
        ret += int(s)
    return ret
set_ = set(i for i in range(1, 10001))
while set_:
    n = sorted(list(set_)).pop(0)
    set_.discard(n)
    print(n)
    while n < 10000:
        n = func(n)
        #print('func' + str(n))
        set_.discard(n)
