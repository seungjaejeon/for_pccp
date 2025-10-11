package PCCP_2주차.경주로_건설;


import java.util.*;

public class Main2 {
    public static void main(String[] args) {

        System.out.println(solution(new int[][] {{0,0,0,0,0,0},{0,1,1,1,1,0},{0,0,1,0,0,0},{1,0,0,1,0,1},{0,1,0,0,0,1},{0,0,0,0,0,0}}));


    }

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    static int N;

    static class Node implements Comparable<Node> {

        int x;
        int y;
        int dir;
        int cost;

        public Node(int x, int y, int dir, int cost) {
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node other) {
            return this.cost - other.cost;
        }
    }

    public static int solution(int[][] board) {
        int N = board.length;
        int[][][] cost = new int[N][N][4];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < 4; k++) {
                    cost[i][j][k] = Integer.MAX_VALUE;
                }
            }
        }

        Node start = new Node(0, 0, -1, 0);

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(start);

        while (!pq.isEmpty()) {
            Node now = pq.poll();

            for (int i = 0; i < 4; i++) {
                int nx = now.x + dx[i];
                int ny = now.y + dy[i];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N || board[nx][ny] == 1) {
                    continue;
                }

                int nowCost = now.cost;

                if (now.dir == -1 || now.dir == i) { // 직진
                    nowCost += 100;
                } else { //코너
                    nowCost += 600;
                }

                if (nowCost < cost[nx][ny][i]) {
                    cost[nx][ny][i] = nowCost;
                    pq.add(new Node(nx, ny, i, nowCost));
                }
            }
        }

        int answer = Integer.MAX_VALUE;

        for (int i = 0; i < 4; i++) {
            answer = Math.min(answer, cost[N - 1][N - 1][i]);
        }

        return answer;
    }
}
