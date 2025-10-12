import java.util.*;

class Solution {
    public int solution(int[][] points, int[][] routes) {
        int n = points.length;
        int x = routes.length;

        // 포인트 번호 1~n 에 맞춰 좌표 저장
        int[][] pos = new int[n + 1][2];
        for (int i = 0; i < n; i++) {
            pos[i + 1][0] = points[i][0];
            pos[i + 1][1] = points[i][1];
        }

        // 각 로봇의 시간별 이동 경로
        List<List<int[]>> robotPaths = new ArrayList<>();
        int maxTime = 0;

        for (int i = 0; i < x; i++) {
            int[] route = routes[i];
            List<int[]> path = new ArrayList<>();

            // 시작 지점
            int r = pos[route[0]][0];
            int c = pos[route[0]][1];
            path.add(new int[]{r, c});

            // 경로 이동
            for (int j = 1; j < route.length; j++) {
                int tr = pos[route[j]][0];
                int tc = pos[route[j]][1];

                // r 먼저 이동
                while (r != tr) {
                    r += (tr > r) ? 1 : -1;
                    path.add(new int[]{r, c});
                }
                // c 이동
                while (c != tc) {
                    c += (tc > c) ? 1 : -1;
                    path.add(new int[]{r, c});
                }
            }

            robotPaths.add(path);
            maxTime = Math.max(maxTime, path.size());
        }

        int answer = 0;

        // t초마다 위치 비교
        for (int t = 0; t < maxTime; t++) {
            Map<String, Integer> count = new HashMap<>();

            for (int i = 0; i < x; i++) {
                List<int[]> path = robotPaths.get(i);

                // 경로 끝난 로봇은 제외
                if (t >= path.size()) continue;

                int[] p = path.get(t);
                String key = p[0] + "," + p[1];
                count.put(key, count.getOrDefault(key, 0) + 1);
            }

            for (int val : count.values()) {
                if (val >= 2) {
                    answer++;
                }
            }
        }

        return answer;
    }
}
