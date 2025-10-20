def solution(input_string):
    answer = ''
    dict_ = dict()
    for idx, s in enumerate(input_string):
        if s not in dict_:
            dict_[s] = {idx}
        else:
            if idx - 1 in dict_[s]:
                dict_[s].discard(idx - 1)
            dict_[s].add(idx)
    
    for key, val in dict_.items():
        if len(val) > 1:
            answer += key
    answer = "".join(sorted(answer))
    if answer == "" :
        answer = "N"
    return answer