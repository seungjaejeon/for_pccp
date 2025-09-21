from itertools import combinations, product
from bisect import bisect_left

# 리스트에서 만들 수 있는 sum 조합 찾기
def make_sum(list_, dice):
    tot_sum = []
    products = product(tuple(_ for _ in range(6)), repeat=len(list_))
    
    for product_ in products:
        tmp = 0
        for idx, p in enumerate(product_):
            tmp += dice[list_[idx]][p]
        tot_sum.append(tmp)
    return tot_sum
    
def solution(dice):
    n = len(dice)
    half_length = n // 2
    # A가 뽑은 조합
    A = combinations(tuple(_ for _ in range(n)), half_length)
    wins = 0
    answer = []
    for combi in A:
        B = tuple(_ for _ in range(n) if _ not in combi)
        A_sum = make_sum(combi, dice) 
        B_sum = make_sum(B, dice)     
        B_sum.sort()
        wins_tmp = 0
        for a in A_sum:
            # A 가 B를 얼마나 이기는지
            wins_tmp += bisect_left(B_sum, a)
        if wins < wins_tmp:
            answer = combi
            wins = wins_tmp
    list(answer).sort()
    return [i+1 for i in answer]
