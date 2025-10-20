import java.io.*;
import java.util.*;

public class Main {
    static class FastScanner {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) {
                String line = br.readLine();
                if (line == null) return null;      // EOF
                if (line.isEmpty()) continue;        // 빈 줄 스킵
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }
        int nextInt() throws IOException { return Integer.parseInt(next()); }
    }

    static class Node {
        int r, c, f;
        Node(int r, int c, int f) { this.r = r; this.c = c; this.f = f; }
    }

    static final int[] dr = {-1, 1, 0, 0};
    static final int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        String tTok = fs.next();
        if (tTok == null) return;               // 방어
        int T = Integer.parseInt(tTok);

        StringBuilder out = new StringBuilder();

        while (T-- > 0) {
            int H = fs.nextInt();
            int W = fs.nextInt();
            int O = fs.nextInt();
            int F = fs.nextInt();

            int sr = fs.nextInt();
            int sc = fs.nextInt();
            int er = fs.nextInt();
            int ec = fs.nextInt();

            // 1-indexed로 쓰면 실수 줄이기 쉽습니다.
            int[][] height = new int[H + 1][W + 1];

            for (int i = 0; i < O; i++) {
                int r = fs.nextInt();
                int c = fs.nextInt();
                int h = fs.nextInt();
                height[r][c] = h;
            }

            // best[r][c] = 해당 칸 도달 시 남길 수 있었던 최대 체력
            int[][] best = new int[H + 1][W + 1];
            for (int i = 1; i <= H; i++) Arrays.fill(best[i], -1);

            ArrayDeque<Node> q = new ArrayDeque<>();
            q.add(new Node(sr, sc, F));
            best[sr][sc] = F;

            boolean ok = false;

            while (!q.isEmpty()) {
                Node cur = q.poll();
                // 도착지 도달 판단은 "꺼냈을 때" 바로 체크
                if (cur.r == er && cur.c == ec) { ok = true; break; }
                if (cur.f == 0) continue; // 더 이동 불가

                for (int d = 0; d < 4; d++) {
                    int nr = cur.r + dr[d];
                    int nc = cur.c + dc[d];
                    if (nr < 1 || nr > H || nc < 1 || nc > W) continue;

                    int diff = height[nr][nc] - height[cur.r][cur.c];

                    // 다음 칸이 더 높으면 diff <= cur.f 이어야만 '오를 수' 있음
                    if (diff > cur.f) continue;

                    int nf = cur.f - 1; // 이동 후 체력 1 감소
                    if (nf <= best[nr][nc]) continue; // 더 좋은 상태로 방문한 적 있음

                    best[nr][nc] = nf;
                    q.add(new Node(nr, nc, nf));
                }
            }

            out.append(ok ? "잘했어!!" : "인성 문제있어??").append('\n');
        }

        System.out.print(out.toString());
    }
}
