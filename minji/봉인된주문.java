import java.util.*;

class Solution {
    private static final long[] POW = new long[12];
    static {
        POW[0] = 1L;
        for (int i = 1; i < POW.length; i++) POW[i] = POW[i - 1] * 26L;
    }

    private long prefixCount(int L) {
        long s = 0;
        for (int k = 1; k <= L; k++) s += POW[k];
        return s;
    }

    // 문자열 s 의 전역 순번(1-based): 길이 우선 + 같은 길이 내 사전순
    private long rankOf(String s) {
        int L = s.length();
        long rank = 0L;
        // 길이 < L 인 전체 개수 더하기
        for (int k = 1; k < L; k++) rank += POW[k];

        // 같은 길이 내 사전순 1-based
        long within = 1L; // 'a...a' 가 1
        for (int i = 0; i < L; i++) {
            int digit = s.charAt(i) - 'a'; // 0..25
            within += digit * POW[L - 1 - i];
        }
        return rank + within;
    }

    // 전역 순번 rank(1-based) → 실제 문자열
    private String wordOf(long rank) {
        // 적절한 길이 L 찾기: sum_{k=1..L-1} 26^k < rank ≤ sum_{k=1..L} 26^k
        int L = 1;
        long acc = 0L;
        while (true) {
            long next = acc + POW[L];
            if (rank <= next) break;
            acc = next;
            L++;
        }
        // 같은 길이 구간 내 offset (0-based)
        long offset = (rank - 1) - acc; // 0..(26^L - 1)

        char[] ch = new char[L];
        for (int i = L - 1; i >= 0; i--) {
            int d = (int)(offset % 26);
            ch[i] = (char)('a' + d);
            offset /= 26;
        }
        return new String(ch);
    }

    public String solution(long n, String[] bans) {
        // 1) 삭제 목록을 전부 전역 순번으로 치환
        long[] blocked = new long[bans.length];
        for (int i = 0; i < bans.length; i++) blocked[i] = rankOf(bans[i]);
        Arrays.sort(blocked);

        // 2) 앞에서부터 스윕하며, n 이하의 차단 순번을 만날 때마다 n을 한 칸 뒤로 민다
        long target = n;
        for (long r : blocked) {
            if (r <= target) target++;   // 삭제 때문에 실제 타깃이 뒤로 한 칸 밀림
            else break;                  // 정렬되어 있으니 더 볼 필요 없음
        }

        return wordOf(target);
    }
}
