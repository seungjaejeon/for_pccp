import java.io.*;
import java.util.*;

public class Main {
    static class Node {
        int x, y, f;

        Node(int x, int y, int f) {
            this.x = x;
            this.y = y;
            this.f = f;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int H = Integer.parseInt(st.nextToken());
            int W = Integer.parseInt(st.nextToken());
            int O = Integer.parseInt(st.nextToken());
            int F = Integer.parseInt(st.nextToken());
            int Xs = Integer.parseInt(st.nextToken()) - 1;
            int Ys = Integer.parseInt(st.nextToken()) - 1;
            int Xe = Integer.parseInt(st.nextToken()) - 1;
            int Ye = Integer.parseInt(st.nextToken()) - 1;

            int[][] map = new int[H][W];
            for (int i = 0; i < O; i++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken()) - 1;
                int y = Integer.parseInt(st.nextToken()) - 1;
                int L = Integer.parseInt(st.nextToken());
                map[x][y] = L;
            }

            // BFS with remaining energy
            boolean[][] visited = new boolean[H][W];
            // visited 배열은 남은 힘 때문에 단순 boolean이면 부족해 보일 수 있으나,
            // 갈수 있는 모든 경로를 힘이 줄어드는 방향으로만 진행하므로 boolean으로도 충분.

            Queue<Node> q = new LinkedList<>();
            q.offer(new Node(Xs, Ys, F));
            visited[Xs][Ys] = true;

            boolean canReach = false;

            while (!q.isEmpty()) {
                Node cur = q.poll();
                if (cur.x == Xe && cur.y == Ye) {
                    canReach = true;
                    break;
                }
                if (cur.f <= 0) continue;

                for (int i = 0; i < 4; i++) {
                    int nx = cur.x + dx[i];
                    int ny = cur.y + dy[i];

                    if (nx < 0 || nx >= H || ny < 0 || ny >= W) continue;

                    int currH = map[cur.x][cur.y];
                    int nextH = map[nx][ny];
                    int remain = cur.f;

                    // 이동 1칸으로 힘 감소
                    // 단, 점프가 가능한지 먼저 확인해야 함
                    if (nextH > currH) {
                        int needJump = nextH - currH;
                        if (remain < needJump) continue;
                    }

                    int nf = remain - 1;
                    if (nf < 0) continue;  // 이동 불가

                    if (!visited[nx][ny]) {
                        visited[nx][ny] = true;
                        q.offer(new Node(nx, ny, nf));
                    }
                }
            }

            if (canReach) sb.append("잘했어!!\n");
            else sb.append("인성 문제있어??\n");
        }

        System.out.print(sb.toString());
    }
}
