import java.util.*;

/**
 * 2024 KAKAO WINTER INTERNSHIP - 주사위 고르기
 * - N개의 주사위 중 N/2개(A)를 골라 승리 확률(A합 > B합)을 최대화
 * - 동률이면 사전순(오름차순 인덱스 배열)으로 가장 앞선 조합 반환
 */
class Solution {
    public int[] solution(int[][] dice) {
        int n = dice.length;
        int k = n / 2;

        // 모든 조합 생성
        List<int[]> combs = new ArrayList<>();
        makeCombinations(n, k, 0, new int[k], 0, combs);

        long bestWins = -1;
        int[] best = null;

        // 미리 전체 경우의 수(분모)는 동일하므로 따로 저장할 필요 X
        for (int[] pick : combs) {
            // A = pick, B = 나머지
            boolean[] used = new boolean[n];
            for (int idx : pick) used[idx] = true;
            int[] other = new int[k];
            for (int i = 0, p = 0; i < n; i++) if (!used[i]) other[p++] = i;

            // 두 집합의 합 분포 계산
            int[] distA = buildSumDistribution(dice, pick);   // cntA[sum] = 경우의 수
            int[] distB = buildSumDistribution(dice, other);  // cntB[sum]

            // B의 누적분포 prefB[s] = sum_{x <= s} cntB[x]
            int[] prefB = new int[distB.length];
            long csum = 0;
            for (int s = 0; s < distB.length; s++) {
                csum += distB[s];
                // 누적이 int 범위를 넘을 수 있으므로 long으로 더한 뒤 잘라 넣음(아래 곱에서 long 사용 예정)
                prefB[s] = (int) Math.min(Integer.MAX_VALUE, csum);
            }

            // 승리 경우의 수: sum_a cntA[a] * prefB[a-1]
            long wins = 0;
            for (int a = 0; a < distA.length; a++) {
                int cntA = distA[a];
                if (cntA == 0) continue;
                int bMax = a - 1;
                if (bMax < 0) continue;
                if (bMax >= prefB.length) bMax = prefB.length - 1;
                wins += (long) cntA * (long) prefB[bMax];
            }

            // 더 좋은 조합인지 판단 (동률이면 사전순 최소)
            if (wins > bestWins || (wins == bestWins && lexicographicallySmaller(pick, best))) {
                bestWins = wins;
                best = pick.clone();
            }
        }

        for (int i = 0; i < best.length; i++) best[i] += 1;
        return best;
    }

    /** 주사위 인덱스 집합(indices)을 써서 가능한 합의 분포(경우의 수)를 구함. */
    private int[] buildSumDistribution(int[][] dice, int[] indices) {
        // 해시맵 기반 컨볼루션 -> 마지막에 압축 배열로 변환
        Map<Integer, Long> cur = new HashMap<>();
        cur.put(0, 1L); // 합 0, 경우의 수 1

        for (int idx : indices) {
            Map<Integer, Long> next = new HashMap<>();
            for (Map.Entry<Integer, Long> e : cur.entrySet()) {
                int base = e.getKey();
                long ways = e.getValue();
                for (int face : dice[idx]) {
                    int sum = base + face;
                    next.put(sum, next.getOrDefault(sum, 0L) + ways);
                }
            }
            cur = next;
        }

        // 합의 최소/최대 파악 후, 배열로 변환
        int minSum = Integer.MAX_VALUE, maxSum = Integer.MIN_VALUE;
        for (int s : cur.keySet()) {
            if (s < minSum) minSum = s;
            if (s > maxSum) maxSum = s;
        }
        if (cur.isEmpty()) return new int[]{1}; // 합 0 하나

        int offset = minSum;               // 배열의 0번째 인덱스가 실제 합 'offset'에 대응
        int[] dist = new int[maxSum - minSum + 1];
        for (Map.Entry<Integer, Long> e : cur.entrySet()) {
            int s = e.getKey();
            long ways = e.getValue();
            dist[s - offset] = (int) ways;
        }

        if (offset != 0) {
            int[] arr = new int[maxSum + 1];
            Arrays.fill(arr, 0);
            for (int i = 0; i < dist.length; i++) {
                arr[i + offset] = dist[i];
            }
            return arr;
        }
        return dist;
    }

    /** n개 중 r개 조합을 사전순으로 생성 */
    private void makeCombinations(int n, int r, int start, int[] buf, int depth, List<int[]> out) {
        if (depth == r) {
            out.add(buf.clone());
            return;
        }
        for (int i = start; i <= n - (r - depth); i++) {
            buf[depth] = i;
            makeCombinations(n, r, i + 1, buf, depth + 1, out);
        }
    }

    /** 사전순 비교: a가 b보다 앞서면 true. b가 null이면 a가 더 작다고 간주. */
    private boolean lexicographicallySmaller(int[] a, int[] b) {
        if (b == null) return true;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) return a[i] < b[i];
        }
        return false; // 완전히 동일
    }
}
