def solution(bandage, health, attacks):
    answer = 0
    now_health = health
    t, x, y = bandage
    now_time = 0
    for time, amount in attacks:
        tmp_time = time - now_time - 1
        tmp_health = now_health + tmp_time * x
        if tmp_time >= t :
            tmp_health += (int)(tmp_time / t) * y
        now_health = min(health, tmp_health) - amount
        
        if now_health <= 0:
            return -1
        now_time = time
    return now_health
       
    return answer
