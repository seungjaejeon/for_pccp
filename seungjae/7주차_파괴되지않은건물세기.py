# 누적합이 도대체 뭔데,,,, N*M 누적합
# r1, c1 ~ r2, c2의 경우 r2+1, c2+1에 degree 공격
def solution(board, skill):
    N = len(board)
    M = len(board[0])
    diff = [[0] * (M + 1) for _ in range(N + 1)]

    # 모든 스킬을 차분 배열에 O(1)로 반영
    for typ, r1, c1, r2, c2, degree in skill:
        if typ == 1:
            effect = -degree     
        else:
            effect = degree
            diff[r1][c1] += effect
            diff[r1][c2 + 1] -= effect
            diff[r2 + 1][c1] -= effect
            diff[r2 + 1][c2 + 1] += effect

    # 행 방향 누적합
    for i in range(N):
        for j in range(1, M):
            diff[i][j] += diff[i][j - 1]

    # 열 방향 누적합
    for j in range(M):
        for i in range(1, N):
            diff[i][j] += diff[i - 1][j]

    # 0 이상인 놈들 세기
    answer = 0
    for i in range(N):
        for j in range(M):
            if board[i][j] + diff[i][j] > 0:
                answer += 1

    return answer
