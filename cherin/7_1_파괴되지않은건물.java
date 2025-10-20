class Solution {
    public int solution(int[][] board, int[][] skill) {
        int N = board.length;
        int M = board[0].length;

        // 보조 배열: N+1 × M+1
        long[][] delta = new long[N + 1][M + 1];

        // 1) skill 명령마다 마킹
        for (int[] s : skill) {
            int type = s[0];
            int r1 = s[1];
            int c1 = s[2];
            int r2 = s[3];
            int c2 = s[4];
            int degree = s[5];

            if (type == 1) {
                degree = -degree;
            }

            delta[r1][c1] += degree;
            if (c2 + 1 < M) delta[r1][c2 + 1] -= degree;
            if (r2 + 1 < N) delta[r2 + 1][c1] -= degree;
            if (r2 + 1 < N && c2 + 1 < M) delta[r2 + 1][c2 + 1] += degree;
        }

        // 2) 행 방향 누적합
        for (int i = 0; i < N; i++) {
            long sum = 0;
            for (int j = 0; j < M; j++) {
                sum += delta[i][j];
                delta[i][j] = sum;
            }
        }

        // 3) 열 방향 누적합
        for (int j = 0; j < M; j++) {
            long sum = 0;
            for (int i = 0; i < N; i++) {
                sum += delta[i][j];
                delta[i][j] = sum;
            }
        }

        // 4) 최종 건물 상태 계산
        int answer = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] + delta[i][j] > 0) {
                    answer++;
                }
            }
        }

        return answer;
    }
}
