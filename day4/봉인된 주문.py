import math
def solution(n, bans):
    dict_ = {}
    for idx, val in enumerate(list('abcdefghijklmnopqrstuvwxyz'), 1):
        dict_[val]= idx
    
    bans = sorted(bans, key=lambda x:(len(x), x))
    
    actual_number = n
    ## 더 큰수인지 계산
    for string in bans:
        string_list = list(reversed(string))
        number = 0
        for idx in range(len(string)):
            number += (math.pow(26, idx) * dict_[string_list[idx]])
        if number > actual_number:
            break
        actual_number += 1
        
    n_26 = []
    while actual_number > 0:
        share = actual_number // 26
        remainer = actual_number % 26
        if remainer == 0:
            remainer = 26
            share -= 1
        n_26.append(remainer)
        actual_number = share
        
    n_26.reverse()
    alphabets = list('abcdefghijklmnopqrstuvwxyz')
    answer = []
    for val in n_26:
        answer.append(alphabets[val-1])
        
    return "".join(answer)