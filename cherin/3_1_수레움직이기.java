import java.util.*;

/*
Week 3 Question 1 - [PCCP 기출문제] 4번/수레 움직이기
https://school.programmers.co.kr/learn/courses/30/lessons/250134

Time Complexity: O((n*m)^2) > n,m 이 작아서 상관없음
제출 시 최대 1823ms
*/
class Solution {
    
    int[] y = {1, -1, 0, 0};
    int[] x = {0, 0, -1, 1};
    
    int[] rTarget;
    int[] bTarget;
    int[][] rVisited;
    int[][] bVisited;
    int[][] maze;
    int n;
    int m;

    public int solution(int[][] maze) {
        
        this.maze = maze;
        n = maze.length;
        m = maze[0].length;
        
        rVisited = new int[n][m];
        bVisited = new int[n][m];
        
        int[] rCur = null;
        int[] bCur = null;
        
        // 시작지점, 도착지점 확인
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (maze[i][j] == 1) {
                    rCur = new int[]{i, j};
                    rVisited[i][j] = 1;
                } else if (maze[i][j] == 2) {
                    bCur = new int[]{i, j};
                    bVisited[i][j] = 1;
                } else if (maze[i][j] == 3) {
                    rTarget = new int[]{i, j};
                } else if (maze[i][j] == 4) {
                    bTarget = new int[]{i, j};
                }
            }
        }
        
        int turn = move(0, rCur, bCur);
        
        // 한번도 도착지점에 도달한 적 없음
        if (turn == Integer.MAX_VALUE) return 0;
        
        return turn;
    }
    
    public int move(int turn, int[] rCur, int[] bCur){
        // 수레가 둘 다 도착한 경우
        if (rCur[0] == rTarget[0] && rCur[1] == rTarget[1]
            && bCur[0] == bTarget[0] && bCur[1] == bTarget[1]){
            return turn;
        }
        
        int minTurn = Integer.MAX_VALUE;
        
        for (int rDir = 0; rDir < 4; rDir++) {
            int rX = rCur[0];
            int rY = rCur[1];
            
            // 빨간 수레가 도착지점에 도착한경우 움직이지 않음
            if (!(rX == rTarget[0] && rY == rTarget[1])) {
                rX += x[rDir];
                rY += y[rDir];
                if (!isValid(rX, rY, rVisited)) continue;
            }

            for (int bDir = 0; bDir < 4; bDir++) {
                int bX = bCur[0];
                int bY = bCur[1];
                
                // 파란색 수레가 도착지점에 도착한경우 움직이지 않음
                if (!(bX == bTarget[0] && bY == bTarget[1])) {
                    bX += x[bDir];
                    bY += y[bDir];
                    if (!isValid(bX, bY, bVisited)) continue;
                }

                // 동시에 두 수레를 같은 칸으로 움직일 수 없음
                if (rX == bX && rY == bY) continue;
                // 수레끼리 자리를 바꾸며 움직일 수 없음
                if (rX == bCur[0] && rY == bCur[1] && bX == rCur[0] && bY == rCur[1]) continue;

                rVisited[rX][rY] = 1;
                bVisited[bX][bY] = 1;
                int newTurn = move(turn + 1, new int[]{rX, rY}, new int[]{bX, bY});
                rVisited[rX][rY] = 0;
                bVisited[bX][bY] = 0;

                minTurn = Math.min(minTurn, newTurn);
            }
        }
        
        return minTurn;
        
    }
    
    // 벽이나 격자판 밖인지, 이미 방문한 칸인지 확인
    public boolean isValid(int i, int j, int[][] visited){
        return i >= 0 && i < n
               && j >= 0 && j < m
               && visited[i][j] == 0 
               && maze[i][j] != 5;
    }

}
