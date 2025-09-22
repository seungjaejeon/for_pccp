import java.util.*;

class Solution {
    // 상, 하, 좌, 우
    static final int[] DR = {-1, 1, 0, 0};
    static final int[] DC = {0, 0, -1, 1};

    int R, C;
    int answer = Integer.MAX_VALUE;

    // 각 수레의 방문표 (자기 자신이 방문한 칸만 금지)
    boolean[][] visitedRed;
    boolean[][] visitedBlue;

    public int solution(int[][] maze) {
        R = maze.length;
        C = maze[0].length;

        int sr=0, sc=0, br=0, bc=0; // 시작 위치
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (maze[i][j] == 1) { sr = i; sc = j; }
                else if (maze[i][j] == 2) { br = i; bc = j; }
            }
        }

        visitedRed  = new boolean[R][C];
        visitedBlue = new boolean[R][C];
        visitedRed[sr][sc] = true;
        visitedBlue[br][bc] = true;

        dfs(maze, sr, sc, br, bc, false, false, 0);

        return (answer == Integer.MAX_VALUE) ? 0 : answer;
    }

    // 한 턴에 두 수레를 동시에 한 칸씩 이동(도착한 수레는 고정)
    void dfs(int[][] maze, int rr, int rc, int br, int bc,
             boolean redDone, boolean blueDone, int turns) {

        // 둘 다 도착 → 최솟값 갱신
        if (redDone && blueDone) {
            answer = Math.min(answer, turns);
            return;
        }
        // 가지치기
        if (turns >= answer) return;

        // 빨강의 다음 후보들
        int[] redDirs = redDone ? new int[]{-1} : new int[]{0,1,2,3}; // -1이면 고정
        // 파랑의 다음 후보들
        int[] blueDirs = blueDone ? new int[]{-1} : new int[]{0,1,2,3};

        for (int rd : redDirs) {
            int nr = rr, nc = rc; // 빨강 다음 위치 (기본: 고정)
            if (!redDone) {
                nr = rr + DR[rd];
                nc = rc + DC[rd];
                // 범위/벽/자기 재방문 금지
                if (!in(nr, nc) || maze[nr][nc] == 5) continue;
                if (visitedRed[nr][nc]) continue;
            }

            for (int bd : blueDirs) {
                int nbr = br, nbc = bc; // 파랑 다음 위치 (기본: 고정)
                if (!blueDone) {
                    nbr = br + DR[bd];
                    nbc = bc + DC[bd];
                    // 범위/벽/자기 재방문 금지
                    if (!in(nbr, nbc) || maze[nbr][nbc] == 5) continue;
                    if (visitedBlue[nbr][nbc]) continue;
                }

                // 같은 칸으로 이동 금지
                if (nr == nbr && nc == nbc) continue;
                // 자리바꿈 금지
                if (nr == br && nc == bc && nbr == rr && nbc == rc) continue;

                // 방문 마킹
                boolean markedRed = false, markedBlue = false;
                if (!redDone)  { visitedRed[nr][nc]   = true; markedRed  = true; }
                if (!blueDone) { visitedBlue[nbr][nbc] = true; markedBlue = true; }

                // 도착 판정
                boolean nextRedDone  = redDone  || (maze[nr][nc]   == 3);
                boolean nextBlueDone = blueDone || (maze[nbr][nbc] == 4);

                dfs(maze, nr, nc, nbr, nbc, nextRedDone, nextBlueDone, turns + 1);

                // 되돌리기
                if (markedRed)  visitedRed[nr][nc]   = false;
                if (markedBlue) visitedBlue[nbr][nbc] = false;
            }
        }
    }

    boolean in(int r, int c) {
        return 0 <= r && r < R && 0 <= c && c < C;
    }
}
