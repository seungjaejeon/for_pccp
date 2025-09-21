package PCCP_2주차.경주로_건설;


import java.util.Arrays;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {

        System.out.println(Arrays.toString(solution(new int[][] {{0,0,0,0,0,0},{0,1,1,1,1,0},{0,0,1,0,0,0},{1,0,0,1,0,1},{0,1,0,0,0,1},{0,0,0,0,0,0}})));


    }

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    static int N;


    static class Node implements Comparable<Node> {
        int x, y, dir, cost;

        Node(int x, int y, int dir, int cost) {
            this.x = x;
            this.y = y;
            this.dir = dir; // 이전 노드에서 현재 노드로 온 방향
            this.cost = cost;
        }

        @Override
        public int compareTo(Node other) {
            return this.cost - other.cost; // 비용이 낮은 순으로 정렬
        }
    }

    public static int[] solution(int[][] board) {
        N = board.length;
        int[][][] cost = new int[N][N][4];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < 4; k++) {
                    cost[i][j][k] = Integer.MAX_VALUE;
                }
            }
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();

        pq.add(new Node(0, 0, -1, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            for (int i = 0; i < 4; i++) {
                int nx = current.x + dx[i];
                int ny = current.y + dy[i];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N || board[nx][ny] == 1) {
                    continue;
                }

                int newCost = current.cost;
                if (current.dir == -1 || current.dir == i) { // 시작점이거나 직진
                    newCost += 100;
                } else { // 코너
                    newCost += 600; // 직선(100) + 코너(500)
                }

                if (cost[nx][ny][i] > newCost) {
                    cost[nx][ny][i] = newCost;
                    pq.add(new Node(nx, ny, i, newCost));
                }
            }
        }

        int minCost = Integer.MAX_VALUE;
        for (int i = 0; i < 4; i++) {
            minCost = Math.min(minCost, cost[N - 1][N - 1][i]);
        }

        return new int[]{minCost};
    }

}
