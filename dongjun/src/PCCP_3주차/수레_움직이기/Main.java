package PCCP_3주차.수레_움직이기;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) {
        System.out.println(solution(new int[][] {{1, 4}, {0, 0}, {2, 3}}));
        System.out.println(solution(new int[][] {{1, 0, 2}, {0, 0, 0}, {5, 0 ,5}, {4, 0, 3}}));
        System.out.println(solution(new int[][] {{1, 5}, {2, 5}, {4, 5}, {3, 5}}));


    }

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    static int n, m;
    static int rEndX, rEndY, bEndX, bEndY;
    static int rStartX = 0, rStartY = 0, bStartX = 0, bStartY = 0;

    static class Node implements Comparable<Node> {

        int rx;
        int ry;
        int bx;
        int by;
        int turn;
        boolean[][] rVisited;
        boolean[][] bVisited;

        public Node(int rx, int ry, int bx, int by, int turn, boolean[][] rVisited, boolean[][] bVisited) {
            this.rx = rx;
            this.ry = ry;
            this.bx = bx;
            this.by = by;
            this.turn = turn;
            this.rVisited = rVisited;
            this.bVisited = bVisited;
        }
        @Override
        public int compareTo(Node o) {
            return this.turn - o.turn;
        }
    }

    static public int solution(int[][] maze) {
        int answer = 0;
        /*
        n x m 격자 모양의 퍼즐판
        빨간색 수레와 파란색 수레 하나씩 존재
        각 수레들은 자신의 시작 칸에서부터 자신의 도착 칸까지 이동해야 한다
        모든 수레들은 각자 도착 칸으로 이동시키면 퍼즐을 풀 수 있다

        각 턴마다 반드시 모든 수레를 상화좌우로 인접한 칸 중 한 칸으로 움직여야 한다
        규칙.
            - 수레는 벽이나 격자 판 밖으로 움직일 수 없다
            - 수레는 자신이 방문했던 칸으로 움직일 수 없다
            - 자신의 도착 칸에 위치한 수레는 움직이지 않는다. 계속 해당 칸에 고정
            - 동시에 두 수레를 같은 칸으로 움직일 수 없다
            - 수레끼리 자리를 바꾸며 움직일 수 없다.


         */

        n = maze.length;
        m = maze[0].length;

        
        
        boolean[][][][] turn = new boolean[n][m][n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (maze[i][j] == 1) {
                    rStartX = i;
                    rStartY = j;
                } else if (maze[i][j] == 2) {
                    bStartX = i;
                    bStartY = j;
                } else if (maze[i][j] == 3) {
                    rEndX = i;
                    rEndY = j;
                } else if (maze[i][j] == 4) {
                    bEndX = i;
                    bEndY = j;
                }
            }
        }

        boolean[][] initialRVisited = new boolean[n][m];
        boolean[][] initialBVisited = new boolean[n][m];
        initialRVisited[rStartX][rStartY] = true;
        initialBVisited[bStartX][bStartY] = true;


        PriorityQueue<Node> pq = new PriorityQueue<>();

        pq.add(new Node(rStartX, rStartY, bStartX, bStartY, 0, initialRVisited, initialBVisited));
        turn[rStartX][rStartY][bStartX][bStartY] = true;

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (current.rx == rEndX && current.ry == rEndY && current.bx == bEndX && current.by == bEndY) {
                return current.turn;
            }

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    int nrx = current.rx, nry = current.ry;
                    int nbx = current.bx, nby = current.by;

                    boolean rArrived = (current.rx == rEndX && current.ry == rEndY);
                    boolean bArrived = (current.bx == bEndX && current.by == bEndY);

                    if (!rArrived) { nrx += dx[i]; nry += dy[i]; }
                    if (!bArrived) { nbx += dx[j]; nby += dy[j]; }

                    if (nrx >= n || nrx < 0 || nry >= m || nry < 0 || maze[nrx][nry] == 5 || nbx >= n || nbx < 0 || nby >= m || nby < 0 || maze[nbx][nby] == 5 ) {
                        continue;
                    }

                    if (!rArrived && current.rVisited[nrx][nry]) continue;
                    if (!bArrived && current.bVisited[nbx][nby]) continue;

                    if (nrx == nbx && nry == nby) {
                        continue;
                    }
                    if (nrx == current.bx && nry == current.by && nbx == current.rx && nby == current.ry) {
                        continue;
                    }
                    if (turn[nrx][nry][nbx][nby]) {
                        continue;
                    }

                    turn[nrx][nry][nbx][nby] = true;

                    boolean[][] nextRVisited = Arrays.stream(current.rVisited).map(boolean[]::clone).toArray(boolean[][]::new);
                    boolean[][] nextBVisited = Arrays.stream(current.bVisited).map(boolean[]::clone).toArray(boolean[][]::new);
                    nextRVisited[nrx][nry] = true;
                    nextBVisited[nbx][nby] = true;

                    pq.add(new Node(nrx, nry, nbx, nby, current.turn + 1, nextRVisited, nextBVisited));

                }
            }
        }



        return answer;
    }
}
