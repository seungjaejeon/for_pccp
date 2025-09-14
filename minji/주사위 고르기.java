import java.util.*;

class Solution {
    boolean[] visited;
    int N;

    // 최적 결과 저장
    int maxWin = -1;
    int[] bestPick; // 0-based 인덱스 저장

    public int[] solution(int[][] dice) {
        N = dice.length;
        visited = new boolean[N];

        // 조합 생성: A가 고를 N/2개
        combination(0, 0, dice);

        // 1-based로 변환해서 반환
        return Arrays.stream(bestPick).map(x -> x + 1).toArray();
    }

    // start: 지금까지 고른 개수, idx: 다음으로 탐색 시작할 주사위 인덱스
    public void combination(int start, int idx, int[][] dice) {
        if (start == N / 2) {
            int[] a = new int[N / 2];
            int[] b = new int[N / 2];
            int ai = 0, bi = 0;
            for (int i = 0; i < N; i++) {
                if (visited[i]) a[ai++] = i;
                else b[bi++] = i;
            }
            int wins = getCase(dice, a); // A가 이기는 경우의 수
            // 최적 갱신(동률 시 사전순 더 빠른 조합 선택)
            if (wins > maxWin || (wins == maxWin && lexicographicallyBetter(a, bestPick))) {
                maxWin = wins;
                bestPick = a.clone();
                Arrays.sort(bestPick);
            }
            return;
        }

        // 조합 백트래킹
        for (int i = idx; i < N; i++) {
            visited[i] = true;
            combination(start + 1, i + 1, dice);
            visited[i] = false;
        }
    }

    // pick(=A의 주사위 인덱스) 기준으로 A의 승리 경우의 수 계산
    public int getCase(int[][] dice, int[] pick) {
        // B(상대) 인덱스 구하기
        boolean[] sel = new boolean[N];
        for (int p : pick) sel[p] = true;
        int[] other = new int[N / 2];
        for (int i = 0, j = 0; i < N; i++) if (!sel[i]) other[j++] = i;

        // A/B의 가능한 합 목록 생성(컨볼루션)
        ArrayList<Integer> sumsA = buildSums(dice, pick);
        ArrayList<Integer> sumsB = buildSums(dice, other);

        // B 합 정렬 후, 각 A합에 대해 B합 < A합 개수(lower bound)
        Collections.sort(sumsB);
        int wins = 0;
        for (int a : sumsA) {
            wins += lowerBound(sumsB, a); // B합이 a보다 작은 개수
        }
        return wins;
    }

    // 선택한 주사위들의 모든 합(중복 포함) 리스트
    private ArrayList<Integer> buildSums(int[][] dice, int[] idxs) {
        ArrayList<Integer> cur = new ArrayList<>();
        cur.add(0);
        for (int di : idxs) {
            ArrayList<Integer> next = new ArrayList<>(cur.size() * 6);
            for (int s : cur) {
                for (int f = 0; f < 6; f++) {
                    next.add(s + dice[di][f]);
                }
            }
            cur = next;
        }
        return cur;
    }

    // 정렬된 리스트에서 target보다 작은 원소 개수
    private int lowerBound(List<Integer> arr, int target) {
        int l = 0, r = arr.size();
        while (l < r) {
            int m = (l + r) >>> 1;
            if (arr.get(m) < target) l = m + 1;
            else r = m;
        }
        return l;
    }

    // 동률일 때 사전순(오름차순)으로 더 빠른 조합인지 판단
    private boolean lexicographicallyBetter(int[] cand, int[] best) {
        if (best == null) return true;
        int[] a = cand.clone(); Arrays.sort(a);
        int[] b = best.clone(); Arrays.sort(b);
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) return a[i] < b[i];
        }
        return false;
    }
}
