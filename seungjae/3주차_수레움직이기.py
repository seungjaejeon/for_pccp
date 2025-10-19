from collections import deque

def bfs(maze, rxy, bxy, R_goal, B_goal, d):
    row, col = len(maze), len(maze[0])
    start_state = (tuple(rxy), tuple(bxy))
    visited = set([start_state])
    q = deque([(tuple(rxy), tuple(bxy), 0)])  # (빨강위치, 파랑위치, 이동횟수)

    while q:
        last_r, last_b, count = q.popleft()

        # 목표 도착
        if last_r == tuple(R_goal) and last_b == tuple(B_goal):
            return count

        for rdx, rdy, bdx, bdy in d:
            # 목표지점에 도착한 수레는 더 이상 움직이지 않음
            nr = last_r if last_r == tuple(R_goal) else (last_r[0] + rdx, last_r[1] + rdy)
            nb = last_b if last_b == tuple(B_goal) else (last_b[0] + bdx, last_b[1] + bdy)

            # 범위 체크
            if not (0 <= nr[0] < row and 0 <= nr[1] < col): 
                continue
            if not (0 <= nb[0] < row and 0 <= nb[1] < col): 
                continue

            # 벽 체크
            if maze[nr[0]][nr[1]] == 5 or maze[nb[0]][nb[1]] == 5:
                continue

            # 같은 칸 체크
            if nr == nb:
                continue

            # 자리 겹침 체크
            if nr == last_b or nb == last_r:
                continue
                
            new_state = (nr, nb)
            if new_state in visited:
                continue
            
            visited.add(new_state)
            q.append((nr, nb, count + 1))

    return 0  # 도달 못 하면 0 반환

def solution(maze):
    rd = [[1, 0], [0, 1], [-1, 0], [0, -1]]
    bd = [[1, 0], [0, 1], [-1, 0], [0, -1]]
    d = [i + j for i in rd for j in bd]

    row, col = len(maze), len(maze[0])
    for i in range(row):
        for j in range(col):
            if maze[i][j] == 1:
                mar = [i, j]
            if maze[i][j] == 2:
                mab = [i, j]
            if maze[i][j] == 3:
                R_goal = [i, j]
            if maze[i][j] == 4:
                B_goal = [i, j]

    return bfs(maze, mar, mab, R_goal, B_goal, d)
