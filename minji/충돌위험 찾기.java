import java.util.*;

class Solution {
    public int solution(int[][] points, int[][] routes) {
        int n = points.length;
        int[][] P = new int[n + 1][2];
        for (int i = 0; i < n; i++) {
            P[i + 1][0] = points[i][0];
            P[i + 1][1] = points[i][1];
        }

        List<Deque<long[]>> robotPaths = new ArrayList<>();
        for (int[] route : routes) {
            Deque<long[]> path = new ArrayDeque<>();
            int cr = points[route[0] - 1][0];
            int cc = points[route[0] - 1][1];
            path.add(new long[]{cr, cc});

            for (int i = 1; i < route.length; i++) {
                int tr = points[route[i] - 1][0];
                int tc = points[route[i] - 1][1];

                while (cr != tr) {
                    cr += (tr > cr) ? 1 : -1; path.add(new long[]{cr, cc}); 
                }
                while (cc != tc) {
                    cc += (tc > cc) ? 1 : -1; path.add(new long[]{cr, cc}); 
                }
            }
            robotPaths.add(path);
        }

        int answer = 0;
        while (true) {
            boolean allEmpty = true;
            Map<String, Integer> posCount = new HashMap<>();

            for (Deque<long[]> path : robotPaths) {
                if (path.isEmpty()) continue;
                allEmpty = false;
                long[] cur = path.pollFirst(); // 이번 시간의 위치 꺼냄
                String key = cur[0] + "," + cur[1];
                posCount.put(key, posCount.getOrDefault(key, 0) + 1);
            }

            if (allEmpty) break; // 전부 도착했다면 종료

            for (int cnt : posCount.values()) {
                if (cnt >= 2) answer++;
            }
        }

        return answer;
    }
}
