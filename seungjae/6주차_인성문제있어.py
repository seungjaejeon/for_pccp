from collections import deque

T = int(input().strip())
results = []
for _ in range(T):
    H, W, O, F, Xs, Ys, Xe, Ye = map(int, input().split())
    heights = [[0] * (W + 1) for _ in range(H + 1)]

    for _ in range(O):
        x, y, l = map(int, input().split())
        heights[x][y] = l

    visited = [[-1] * (W + 1) for _ in range(H + 1)]
    q = deque([(Xs, Ys, F)])
    visited[Xs][Ys] = F

    dirs = [(-1, 0), (1, 0), (0, -1), (0, 1)]
    success = False

    while q:
        x, y, f = q.popleft()
        if (x, y) == (Xe, Ye):
            success = True
            break
        if f <= 0:
            continue

        for dx, dy in dirs:
            nx, ny = x + dx, y + dy
            if not (1 <= nx <= H and 1 <= ny <= W):
                continue

            nh, ch = heights[nx][ny], heights[x][y]
            # 이동 가능한지 판단
            if nh > ch and f < (nh - ch):
                continue

            nf = f - 1
            if nf > visited[nx][ny]:
                visited[nx][ny] = nf
                q.append((nx, ny, nf))

    if success:
        results.append("잘했어!!")
    else:
        results.append("인성 문제있어??")

print("\n".join(results))
