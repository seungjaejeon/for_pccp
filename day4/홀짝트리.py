from collections import defaultdict, deque
import sys
sys.setrecursionlimit(1_000_000)

def solution(nodes, edges):
    """
    nodes: 노드 번호 배열 (예: [1,2,3,...])
    edges: 간선 리스트 (예: [[u,v], ...]) — 무방향, 전체가 포레스트일 수 있음
    return: [홀짝_트리_개수, 역홀짝_트리_개수]
    """

    # 인접 리스트 & 차수
    adj = defaultdict(list)
    
    for u, v in edges:
        adj[u].append(v)
        adj[v].append(u)

    # 혹시 단독 노드가 있어도 처리되도록 미리 키 생성
    for x in nodes:
        adj[x]  # touch

    visited = set()
    ans_odd_even = 0       # 홀짝 트리로 만들 수 있는 컴포넌트 개수
    ans_rev_odd_even = 0   # 역홀짝 트리로 만들 수 있는 컴포넌트 개수

    for start in nodes:
        if start in visited:
            continue

        # 한 컴포넌트 순회
        q = deque([start])
        visited.add(start)

        cnt_odd = 0          # 홀수 노드
        cnt_even = 0         # 짝수 노드
        cnt_rev_odd = 0      # 역홀수 노드
        cnt_rev_even = 0     # 역짝수 노드

        while q:
            u = q.popleft()
            deg_parity = len(adj[u]) % 2
            u_parity = u % 2

            # 분류 규칙 (차수 짝/홀 × 노드번호 짝/홀)
            if deg_parity == 1 and u_parity == 1:
                cnt_odd += 1
            elif deg_parity == 0 and u_parity == 0:
                cnt_even += 1
            elif deg_parity == 0 and u_parity == 1:
                cnt_rev_odd += 1
            else:  # deg_parity == 1 and u_parity == 0
                cnt_rev_even += 1

            for v in adj[u]:
                if v not in visited:
                    visited.add(v)
                    q.append(v)

        # 이 컴포넌트가 "홀짝 트리"가 될 수 있는가?
        if (cnt_rev_odd == 1 and cnt_rev_even == 0) or (cnt_rev_odd == 0 and cnt_rev_even == 1):
            ans_odd_even += 1

        # 이 컴포넌트가 "역홀짝 트리"가 될 수 있는가?
        if (cnt_odd == 1 and cnt_even == 0) or (cnt_odd == 0 and cnt_even == 1):
            ans_rev_odd_even += 1

    return [ans_rev_odd_even, ans_odd_even]
