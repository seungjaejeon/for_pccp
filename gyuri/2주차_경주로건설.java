import java.util.*;

class Solution {
    // 상, 우, 하, 좌 (시계방향)
    private static final int[] dr = {-1, 0, 1, 0};
    private static final int[] dc = {0, 1, 0, -1};
    private static final int STRAIGHT = 100;
    private static final int CORNER = 600;
    private static final int INF = 1_000_000_000;

    static class State {
        int r, c, dir, cost;
        State(int r, int c, int dir, int cost) {
            this.r = r;
            this.c = c;
            this.dir = dir;   // 이 칸에 "dir 방향으로" 들어왔다 (-1은 시작 전 상태)
            this.cost = cost;
        }
    }

    public int solution(int[][] board) {
        int n = board.length;

        // dist[r][c][dir] = r,c에 dir 방향으로 도달했을 때 최소 비용
        int[][][] dist = new int[n][n][4];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(dist[i][j], INF);
            }
        }

        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(s -> s.cost));

        // 시작점: 아직 방향이 없음
        pq.offer(new State(0, 0, -1, 0));

        // 벽(1)은 진입 불가
        while (!pq.isEmpty()) {
            State cur = pq.poll();

            for (int nd = 0; nd < 4; nd++) {
                int nr = cur.r + dr[nd];
                int nc = cur.c + dc[nd];

                // 경계 & 벽 체크
                if (nr < 0 || nr >= n || nc < 0 || nc >= n) continue;
                if (board[nr][nc] == 1) continue;

                // 새로 드는 비용 계산
                int add;
                if (cur.dir == -1 || cur.dir == nd) {
                    add = STRAIGHT; // 첫 이동이거나 같은 방향 유지 → 직선
                } else {
                    add = CORNER;   // 방향 전환 → 코너 비용
                }
                int ncost = cur.cost + add;

                // 갱신 조건: 더 싸게 들어갈 수 있으면 업데이트
                if (ncost < dist[nr][nc][nd]) {
                    dist[nr][nc][nd] = ncost;
                    pq.offer(new State(nr, nc, nd, ncost));
                }
            }
        }

        // 도착지 (n-1, n-1)에 대한 4방향 중 최소 비용
        int answer = INF;
        for (int d = 0; d < 4; d++) {
            answer = Math.min(answer, dist[n - 1][n - 1][d]);
        }
        // 시작점과 도착점이 같은 특수 케이스(n==1)에서는 0이 맞음
        return answer == INF ? 0 : answer;
    }
}
