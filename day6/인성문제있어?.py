import sys
from collections import deque

input = sys.stdin.readline

def solve_one():
    H, W, O, F, Xs, Ys, Xe, Ye = map(int, input().split())

    # 높이 맵 (1-indexed)
    height = [[0]*(W+1) for _ in range(H+1)]
    for _ in range(O):
        x, y, l = map(int, input().split())
        height[x][y] = l

    # 시작이 곧 목표면 바로 성공
    if Xs == Xe and Ys == Ye:
        print("잘했어!!")
        return

    # BFS with dominance pruning (각 칸 최대 남은 힘만 유지)
    best = [[-1]*(W+1) for _ in range(H+1)]
    dq = deque()
    dq.append((Xs, Ys, F))
    best[Xs][Ys] = F

    # 4방향
    dirs = [(-1,0),(1,0),(0,-1),(0,1)]

    while dq:
        r, c, f = dq.popleft()
        if r == Xe and c == Ye:
            print("잘했어!!")
            return
        if f <= 0:  # 더 이동 못함
            continue

        ch = height[r][c]
        for dr, dc in dirs:
            nr, nc = r+dr, c+dc
            if not (1 <= nr <= H and 1 <= nc <= W):
                continue
            nh = height[nr][nc]
            need = nh - ch  # 양수면 점프 필요
            if need > 0 and f < need:
                continue  # 점프 불가
            nf = f - 1      # 이동 후 힘 감소
            if nf > best[nr][nc]:
                best[nr][nc] = nf
                dq.append((nr, nc, nf))

    print("인성 문제있어??")

def main():
    T = int(input().strip())
    for _ in range(T):
        solve_one()

if __name__ == "__main__":
    main()
