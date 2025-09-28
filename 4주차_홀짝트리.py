import sys
sys.setrecursionlimit(2_000_000)

def solution(nodes, edges_):
    visited = [-1 for _ in range(1_000_001)]
    edges = [[] for _ in range(1_000_001)]

    for a,b in edges_ :
        edges[a].append(b)
        edges[b].append(a)

    def dfs(cur, prev, arr) :
        cnt = 0
        for nxt in edges[cur] :
            if nxt == prev : continue
            dfs(nxt, cur, arr)
            cnt += 1
        visited[cur] = (cur % 2 + cnt % 2) % 2 
        arr[visited[cur]] += 1

    ans = [0,0]
    for node in nodes :
        if visited[node] != -1 : continue
        arr = [0,0]
        dfs(node, -1, arr)
        if (arr[0] == 0 and arr[1] == 2) or (arr[0] == 2 and arr[1] == 0) :
            ans[0] += 1
            ans[1] += 1
        elif arr[0] == 0 : ans[1] += 1
        elif arr[1] == 0 : ans[0] += 1
        elif arr[0] == 2 and visited[node] == 0 : ans[1] += 1
        elif arr[1] == 2 and visited[node] == 1 : ans[0] += 1
    return ans
