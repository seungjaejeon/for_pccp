from collections import deque
def calculate(queue):
    beans = {'Rr' : ['RR', 'Rr', 'Rr', 'rr'], 'RR' : ['RR', 'RR', 'RR', 'RR'], 'rr' : ['rr','rr','rr','rr']}
    start = 'Rr'
    while queue:
        q = queue.popleft()
        start = beans[start][q]
    return start
        
            
def solution(queries):
    answer = []
    for n, p in queries:
        np = p - 1
        queue = deque()
        for i in range(n-1,0,-1):
            parent = np // 4
            me = np % 4
            queue.appendleft(me)
            np = parent
        answer.append(calculate(queue))
    return answer