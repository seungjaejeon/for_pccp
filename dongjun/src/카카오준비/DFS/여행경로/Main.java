package 카카오준비.DFS.여행경로;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(new String[][]{{"ICN", "JFK"}, {"HND", "IAD"}, {"JFK", "HND"}})));
        System.out.println(Arrays.toString(solution(new String[][] {{"ICN", "SFO"}, {"ICN", "ATL"}, {"SFO", "ATL"}, {"ATL", "ICN"}, {"ATL","SFO"}})));

    }

    static ArrayList<String> allRoute;
    static boolean[] visited;

    public static String[] solution(String[][] tickets) {
        /*
            [a,b]는 a->b
            주어진 항공권은 모두 사용해야 한다.
            만일 가능한 경로가 2개 이상일 경우 알파벳 순서가 앞서는 경로로
         */
        visited = new boolean[tickets.length];
        allRoute = new ArrayList<>();

        for (int i = 0; i < tickets.length; i++) {
            if (tickets[i][0].equals("ICN")) {
                dfs(tickets, tickets[i][1], "ICN " + tickets[i][1], i, 1);
            }
        }

        Collections.sort(allRoute);


        return allRoute.getFirst().split(" ");
    }

    public static void dfs(String[][] tickets, String start, String route, int index, int depth) {
        // 1. 체크인
        visited[index] = true;

        // 2. 목적지인가?
        if (depth == tickets.length) {
            allRoute.add(route);
        } else {
            // 3. 연결된 곳 순회
            for (int i = 0; i < tickets.length; i++) {
                // 4. 갈 수 있는가?
                if (start.equals(tickets[i][0]) && !visited[i]) {
                    // 5. 간다.
                    dfs(tickets, tickets[i][1], route + " " + tickets[i][1], i, depth + 1);
                }
            }
        }

        // 6. 체크아웃
        visited[index] = false;
    }
}
