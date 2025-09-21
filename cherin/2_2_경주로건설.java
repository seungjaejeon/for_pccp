import java.util.*;

class Solution {
    
    int[] dx = {-1, 1, 0, 0}; // 상하좌우
    int[] dy = {0, 0, -1, 1};
    
    /*
    Week 2 Question 2 - 경주로 건설
    https://school.programmers.co.kr/learn/courses/30/lessons/67259

    Time Complexity: n^2 (bfs, dp)
                     제출 시 최대 7.20ms
    */
    public int solution(int[][] board) {
        int n = board.length;
        int[][][] costs = new int[n][n][2];
        
        for (int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                Arrays.fill(costs[i][j], Integer.MAX_VALUE);
            }
        }
        
        Queue<Box> q = new LinkedList<>();
        q.add(new Box(0,0,0,0));
        q.add(new Box(0,0,0,1));
        costs[0][0][0] = 0;
        costs[0][0][1] = 0;
        
        while(!q.isEmpty()){
            Box cur = q.poll();
            
            // 상하좌우 건설
            for (int i=0;i<4;i++){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                int ndir = (i<2) ? 1 : 0; 
                
                // 부지 밖이거나 벽일 경우 건설 불가
                if (nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
                if (board[nx][ny] == 1) continue;
                
                int newCost = cur.cost + 100;
                // 코너일 경우 비용 500 추가
                if (cur.dir != -1 && cur.dir != ndir) newCost += 500; 
                
                if (newCost < costs[nx][ny][ndir]){
                    costs[nx][ny][ndir] = newCost;
                    q.add(new Box(nx, ny, newCost, ndir));
                }
            }
            
        }
        
        return Math.min(costs[n-1][n-1][0], costs[n-1][n-1][1]);
    }
    

    
}

class Box {
    public int x;
    public int y;
    public int cost;
    
    // 0 = horizontal, 1 vertical
    public int dir;
    
    public Box(int x, int y, int cost, int dir){
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.dir = dir;
    }
}
