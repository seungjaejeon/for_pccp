import java.io.*;
import java.util.*;

public class Main {
  // 상, 하, 좌, 우
  static final int[] DR = {-1, 1, 0, 0};
  static final int[] DC = {0, 0, -1, 1};

  public static void main(String[] args) throws Exception {
    FastScanner fs = new FastScanner(System.in);
    StringBuilder sb = new StringBuilder();

    int T = fs.nextInt();
    while (T-- > 0) {
      int H = fs.nextInt();      // 세로(행)
      int W = fs.nextInt();      // 가로(열)
      int O = fs.nextInt();      // 장애물 개수
      int F = fs.nextInt();      // 초기/현재 힘(시작 p)
      int Xs = fs.nextInt();     // 시작 X(행)
      int Ys = fs.nextInt();     // 시작 Y(열)
      int Xe = fs.nextInt();     // 도착 X(행)
      int Ye = fs.nextInt();     // 도착 Y(열)

      int sr = Xs - 1, sc = Ys - 1; // ⚠️ X=행, Y=열
      int er = Xe - 1, ec = Ye - 1;

      int[][] h = new int[H][W];     // 기본 높이 0
      for (int i = 0; i < O; i++) {
        int X = fs.nextInt();      // 장애물 X(행)
        int Y = fs.nextInt();      // 장애물 Y(열)
        int L = fs.nextInt();      // 높이
        int r = X - 1, c = Y - 1;  // ⚠️ X=행, Y=열
        h[r][c] = L;
      }

      boolean ok = bfs(h, H, W, sr, sc, er, ec, F);
      sb.append(ok ? "잘했어!!" : "인성 문제 있어??").append('\n');
    }
    System.out.print(sb);
  }

  // BFS: 오르막이면 높이차 <= 현재 p 여야 이동 가능. 이동할 때마다 p--.
  static boolean bfs(int[][] h, int H, int W, int sr, int sc, int er, int ec, int startP) {
    ArrayDeque<int[]> q = new ArrayDeque<>();
    int[][] best = new int[H][W];
    for (int i = 0; i < H; i++) Arrays.fill(best[i], -1);

    best[sr][sc] = startP;
    q.offer(new int[]{sr, sc, startP});

    while (!q.isEmpty()) {
      int[] cur = q.poll();
      int r = cur[0], c = cur[1], p = cur[2];

      if (r == er && c == ec) return true; // 도착
      if (p == 0) continue;                // 더 못 움직임

      for (int d = 0; d < 4; d++) {
        int nr = r + DR[d], nc = c + DC[d];
        if (nr < 0 || nr >= H || nc < 0 || nc >= W) continue;

        int diff = h[nr][nc] - h[r][c];  // 오르막 높이차
        if (diff > 0 && diff > p) continue; // 현재 남은 힘 p로 못 오르면 패스

        int np = p - 1;                  // 이동 후 p 감소
        if (best[nr][nc] >= np) continue; // 더 좋은 상태로 들른 적 있으면 패스
        best[nr][nc] = np;
        q.offer(new int[]{nr, nc, np});
      }
    }
    return false;
  }

  // 빠른 입력
  static class FastScanner {
    private final InputStream in;
    private final byte[] buf = new byte[1 << 16];
    private int ptr = 0, len = 0;
    FastScanner(InputStream is) { in = is; }
    private int read() throws IOException {
      if (ptr >= len) {
        len = in.read(buf);
        ptr = 0;
        if (len <= 0) return -1;
      }
      return buf[ptr++];
    }
    int nextInt() throws IOException {
      int c, s = 1, v = 0;
      do { c = read(); } while (c <= ' ');
      if (c == '-') { s = -1; c = read(); }
      while (c > ' ') { v = v * 10 + (c - '0'); c = read(); }
      return v * s;
    }
  }
}
