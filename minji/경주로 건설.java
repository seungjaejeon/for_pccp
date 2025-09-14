import java.util.*;

class Solution {
    static final int INF = 1_000_000_000;

    int n;
    int[][] board;
    int[][][] best; // best[r][c][dir]: dir 방향으로 (r,c)에 도착했을 때의 최소 비용
    // 0:상, 1:우, 2:하, 3:좌
    final int[] dr = {-1, 0, 1, 0};
    final int[] dc = {0, 1, 0, -1};

    int ans = INF;

    public int solution(int[][] boardInput) {
        board = boardInput;
        n = board.length;
        best = new int[n][n][4];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) Arrays.fill(best[i][j], INF);
        }

        // (0,0)에서 시작, 방향 미정(-1), 비용 0
        dfs(0, 0, -1, 0);

        return ans == INF ? 0 : ans;
    }

    private void dfs(int r, int c, int dir, int cost) {
        // 현재 최적解보다 비용이 크거나 같으면 컷
        if (cost >= ans) return;

        // 목표 도달
        if (r == n - 1 && c == n - 1) {
            ans = Math.min(ans, cost);
            return;
        }

        for (int nd = 0; nd < 4; nd++) {
            int nr = r + dr[nd];
            int nc = c + dc[nd];
            if (!inBounds(nr, nc) || board[nr][nc] == 1) continue;

            // 첫 이동(방향 없음) 또는 직진: +100, 코너: +600
            int add = (dir == -1 || dir == nd) ? 100 : 600;
            int ncost = cost + add;

            // 같은 (nr,nc,nd)에 더 싼 비용으로 온 적 있으면 가지치기
            if (ncost >= best[nr][nc][nd]) continue;
            best[nr][nc][nd] = ncost;

            dfs(nr, nc, nd, ncost);
        }
    }

    private boolean inBounds(int r, int c) {
        return 0 <= r && r < n && 0 <= c && c < n;
    }
}
