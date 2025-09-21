import java.util.*;

class Solution {
    static class State {
        int rx, ry, bx, by;
        int rMask, bMask, dist;
        State(int rx, int ry, int bx, int by, int rMask, int bMask, int dist) {
            this.rx = rx; this.ry = ry; this.bx = bx; this.by = by;
            this.rMask = rMask; this.bMask = bMask; this.dist = dist;
        }
    }

    static final int[] dx = {-1, 1, 0, 0};
    static final int[] dy = {0, 0, -1, 1};

    public int solution(int[][] maze) {
        int n = maze.length, m = maze[0].length;

        int rsx=0, rsy=0, bsx=0, bsy=0, rgx=0, rgy=0, bgx=0, bgy=0;
        for (int i=0;i<n;i++) for (int j=0;j<m;j++) {
            if (maze[i][j]==1){ rsx=i; rsy=j; }
            else if (maze[i][j]==2){ bsx=i; bsy=j; }
            else if (maze[i][j]==3){ rgx=i; rgy=j; }
            else if (maze[i][j]==4){ bgx=i; bgy=j; }
        }

        int startR = idx(rsx, rsy, m);
        int startB = idx(bsx, bsy, m);
        int initRMask = 1 << startR;
        int initBMask = 1 << startB;

        Queue<State> q = new ArrayDeque<>();
        q.offer(new State(rsx, rsy, bsx, bsy, initRMask, initBMask, 0));

        // 방문 체크: (rPos(0..15), bPos(0..15), rMask(16bit), bMask(16bit))를 하나의 long으로 패킹
        HashSet<Long> seen = new HashSet<>();
        seen.add(pack(rsx, rsy, bsx, bsy, initRMask, initBMask, m));

        while (!q.isEmpty()) {
            State cur = q.poll();
            // 두 수레 모두 도착하면 최단
            if (cur.rx==rgx && cur.ry==rgy && cur.bx==bgx && cur.by==bgy) return cur.dist;

            // 각 수레의 다음 이동 후보 만들기 (도착했으면 그대로 고정)
            List<int[]> rNexts = new ArrayList<>();
            if (cur.rx==rgx && cur.ry==rgy) {
                rNexts.add(new int[]{cur.rx, cur.ry, -1}); // -1: 이동 안함
            } else {
                for (int d=0; d<4; d++) {
                    int nr = cur.rx + dx[d], nc = cur.ry + dy[d];
                    if (!in(nr,nc,n,m) || maze[nr][nc]==5) continue;
                    int id = idx(nr, nc, m);
                    if ((cur.rMask & (1<<id)) != 0) continue; // 자기 경로 재방문 금지
                    rNexts.add(new int[]{nr, nc, id});
                }
            }

            List<int[]> bNexts = new ArrayList<>();
            if (cur.bx==bgx && cur.by==bgy) {
                bNexts.add(new int[]{cur.bx, cur.by, -1});
            } else {
                for (int d=0; d<4; d++) {
                    int nr = cur.bx + dx[d], nc = cur.by + dy[d];
                    if (!in(nr,nc,n,m) || maze[nr][nc]==5) continue;
                    int id = idx(nr, nc, m);
                    if ((cur.bMask & (1<<id)) != 0) continue;
                    bNexts.add(new int[]{nr, nc, id});
                }
            }

            // 두 수레 동시 이동(조합) 후 제약 필터링
            for (int[] rN : rNexts) {
                for (int[] bN : bNexts) {
                    int nrx = rN[0], nry = rN[1];
                    int nbx = bN[0], nby = bN[1];

                    // 같은 칸 금지
                    if (nrx==nbx && nry==nby) continue;
                    // 자리바꿈 금지
                    if (nrx==cur.bx && nry==cur.by && nbx==cur.rx && nby==cur.ry) continue;

                    int newRMask = cur.rMask;
                    int newBMask = cur.bMask;
                    if (rN[2] != -1) newRMask |= (1<<rN[2]); // 빨강이 실제로 이동한 경우만 기록
                    if (bN[2] != -1) newBMask |= (1<<bN[2]);

                    long key = pack(nrx, nry, nbx, nby, newRMask, newBMask, m);
                    if (seen.add(key)) {
                        q.offer(new State(nrx, nry, nbx, nby, newRMask, newBMask, cur.dist+1));
                    }
                }
            }
        }
        // 풀 수 없는 경우
        return 0;
    }

    private static boolean in(int x, int y, int n, int m) {
        return 0<=x && x<n && 0<=y && y<m;
    }
    private static int idx(int x, int y, int m) { return x*m + y; }

    private static long pack(int rx,int ry,int bx,int by,int rMask,int bMask,int m){
        int rPos = idx(rx,ry,m), bPos = idx(bx,by,m);
        long key = rPos;
        key = (key<<6) | bPos;      // 각 0..15면 6비트면 충분
        key = (key<<16) | (rMask & 0xFFFF);
        key = (key<<16) | (bMask & 0xFFFF);
        return key;
    }
}
