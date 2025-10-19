import java.util.*;

class Solution {
  public int solution(int[][] board, int[][] skill) {
    int n = board.length;
    int m = board[0].length;

    // 누적합용 차분배열 (경계 처리를 위해 +1 확장)
    long[][] acc = new long[n + 1][m + 1];

    // 1) 차분 찍기
    for (int[] s : skill) {
      int type = s[0];
      int r1 = s[1], c1 = s[2], r2 = s[3], c2 = s[4];
      int degree = s[5];

      long v = (type == 1) ? -degree : degree;

      acc[r1][c1] += v;
      if (c2 + 1 <= m - 1) acc[r1][c2 + 1] -= v;
      if (r2 + 1 <= n - 1) acc[r2 + 1][c1] -= v;
      if (r2 + 1 <= n - 1 && c2 + 1 <= m - 1) acc[r2 + 1][c2 + 1] += v;

    }

    // 2) 행 방향 누적합
    for (int i = 0; i < n; i++) {
      for (int j = 1; j < m; j++) {
        acc[i][j] += acc[i][j - 1];
      }
    }
    // 3) 열 방향 누적합
    for (int j = 0; j < m; j++) {
      for (int i = 1; i < n; i++) {
        acc[i][j] += acc[i - 1][j];
      }
    }

    // 4) 최종 내구도 계산 및 카운트
    int alive = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        long val = board[i][j] + acc[i][j];
        if (val > 0) alive++;
      }
    }
    return alive;
  }
}
