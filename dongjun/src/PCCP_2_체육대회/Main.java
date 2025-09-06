package PCCP_2_체육대회;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        System.out.println(solution(new int[][] {{40, 10, 10}, {20, 5, 0}, {30, 30, 30}, {70, 0, 70}, {100, 100, 100}}));

    }

    static private int answer = 0;
    static private int numStudents;
    static private int numSports;


    public static int solution(int[][] ability) {

        numStudents = ability.length;
        numSports = ability[0].length;

        boolean[] visited = new boolean[numStudents];

        dfs(0, 0, visited, ability);

        return answer;
    }

    private static void dfs(int sportIndex, int currentSum, boolean[] visited, int[][] ability) {
        if (sportIndex == numSports) {

            answer = Math.max(answer, currentSum);
            return;
        }

        for (int i = 0; i < numStudents; i++) {
            if (!visited[i]) {
                visited[i] = true;
                dfs(sportIndex + 1, currentSum + ability[i][sportIndex], visited, ability);

                visited[i] = false;
            }
        }

    }
}
