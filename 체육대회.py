from itertools import permutations
def solution(ability):
    answer = 0
    arr = [_ for _ in range(len(ability))]
    comb = permutations(arr, len(ability[0]))
    max_ = -1
    for comb_ in comb:
        tmp = 0
        for idx, c in enumerate(comb_):
            tmp += ability[c][idx]
        max_ = max(max_, tmp)
    return max_
