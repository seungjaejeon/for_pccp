package KAKAO_WINTER_INTERNSHIP_주사위고르기;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        System.out.println(Arrays.toString(solution(new int[][] {{1, 2, 3, 4, 5, 6}, {3, 3, 3, 3, 4, 4}, {1, 3, 3, 4, 4, 4}, {1, 1, 4, 4, 5, 5}})));
        System.out.println(Arrays.toString(solution(new int[][] {{1, 2, 3, 4, 5, 6}, {2, 2, 4, 4, 6, 6}})));
        System.out.println(Arrays.toString(solution(new int[][] {{40, 41, 42, 43, 44, 45}, {43, 43, 42, 42, 41, 41}, {1, 1, 80, 80, 80, 80}, {70, 70, 1, 1, 70, 70}})));


    }


    static int[][] staticDice;
    static int n;
    static int maxWins = -1; // 최대 승리 횟수를 저장
    static int[] answer;     // 최종 정답 조합을 저장

    public static int[] solution(int[][] dice) {
        n = dice.length;
        staticDice = dice;
        answer = new int[n / 2];

        int[] diceIndices = new int[n];
        for (int i = 0; i < n; i++) {
            diceIndices[i] = i;
        }

        combination(diceIndices, new boolean[n], 0, n, n / 2);

        Arrays.sort(answer);
        return answer;
    }

    static void combination(int[] arr, boolean[] visited, int start, int n, int r) {
        if (r == 0) {
            ArrayList<Integer> combA = new ArrayList<>();
            ArrayList<Integer> combB = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                if (visited[i]) {
                    combA.add(i);
                } else {
                    combB.add(i);
                }
            }

            List<Integer> sumsA = new ArrayList<>();
            List<Integer> sumsB = new ArrayList<>();
            calculateAllSums(0, 0, combA, sumsA);
            calculateAllSums(0, 0, combB, sumsB);

            Collections.sort(sumsB);

            int currentWins = 0;
            for (int sumA : sumsA) {
                currentWins += lowerBound(sumsB, sumA);
            }

            if (currentWins > maxWins) {
                maxWins = currentWins;
                for (int i = 0; i < combA.size(); i++) {
                    answer[i] = combA.get(i) + 1;
                }
            }
            return;
        }

        for (int i = start; i < n; i++) {
            visited[i] = true;
            combination(arr, visited, i + 1, n, r - 1);
            visited[i] = false;
        }
    }

    static void calculateAllSums(int depth, int currentSum, ArrayList<Integer> comb, List<Integer> sumList) {
        // 모든 주사위를 다 굴렸으면, 현재 합을 결과 리스트에 추가
        if (depth == comb.size()) {
            sumList.add(currentSum);
            return;
        }

        int diceIndex = comb.get(depth);
        for (int faceValue : staticDice[diceIndex]) {
            calculateAllSums(depth + 1, currentSum + faceValue, comb, sumList);
        }
    }

    static int lowerBound(List<Integer> list, int target) {
        int left = 0, right = list.size();
        while (left < right) {
            int mid = (left + right) / 2;
            if (list.get(mid) >= target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }


}
