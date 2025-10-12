import java.util.*;

class Solution {
  static final int MAX_LEN = 11;   // 문자열 길이는 1~11
  static final int ALPHA = 26;     // a~z
  static long[] pow26 = new long[MAX_LEN + 1];
  static long[] pref;              // pref[L] = sum_{i=1..L} 26^i (전체 개수 누적)

  public String solution(long n, String[] bans) {
    precompute();

    // 1) bans 중복 제거 후 "원래 순번(rank)"로 변환해 정렬
    long[] banRanks = compressBansToRanks(bans);

    // 2) 이분 탐색: f(r) = r - (#(ban <= r)) >= n 인 최소 r
    long r = findOriginalRank(n, banRanks);

    // 3) r 을 문자열로 복원 (길이 블록 찾고, 26진수 변환)
    return rankToString(r);
  }

  // --- 사전 계산 ---
  private void precompute() {
    pow26[0] = 1;
    for (int i = 1; i <= MAX_LEN; i++) pow26[i] = pow26[i - 1] * ALPHA;

    pref = new long[MAX_LEN + 1];
    for (int i = 1; i <= MAX_LEN; i++) pref[i] = pref[i - 1] + pow26[i];
  }

  // --- bans -> rank 배열 (중복 제거 + 정렬) ---
  private long[] compressBansToRanks(String[] bans) {
    // 중복 제거
    HashSet<String> set = new HashSet<>(bans.length * 2);
    for (String s : bans) set.add(s);

    long[] arr = new long[set.size()];
    int p = 0;
    for (String s : set) {
      arr[p++] = stringToRank(s);
    }
    Arrays.sort(arr);
    return arr;
  }

  // --- s 의 "원래 순번(1-based)" ---
  private long stringToRank(String s) {
    int L = s.length();
    long base = pref[L - 1];            // 길이 < L 인 모든 개수
    long val = 0;                       // L자리 26진수 값 (a=0..z=25)
    for (int i = 0; i < L; i++) {
      val = val * ALPHA + (s.charAt(i) - 'a');
    }
    return base + val + 1;              // 전체 1-based 순번
  }

  // --- 순번 r(1-based) -> 문자열 ---
  private String rankToString(long r) {
    // 길이 블록 찾기: pref[L-1] < r <= pref[L]
    int L = 1;
    while (L <= MAX_LEN && pref[L] < r) L++;
    long offset = r - pref[L - 1] - 1;  // 0-based within length-L block

    // offset 을 26진수 L자리로
    char[] buf = new char[L];
    for (int i = L - 1; i >= 0; i--) {
      int d = (int)(offset % ALPHA);
      buf[i] = (char)('a' + d);
      offset /= ALPHA;
    }
    return new String(buf);
  }

  // --- #bans <= x (이분 탐색 upper_bound) ---
  private int countBansLE(long[] banRanks, long x) {
    int lo = 0, hi = banRanks.length; // [lo, hi)
    while (lo < hi) {
      int mid = (lo + hi) >>> 1;
      if (banRanks[mid] <= x) lo = mid + 1;
      else hi = mid;
    }
    return lo; // 개수
  }

  // --- f(r) = r - countBansLE(r) >= n 을 만족하는 최소 r ---
  private long findOriginalRank(long n, long[] banRanks) {
    long lo = 1, hi = pref[MAX_LEN]; // 전체 우주 크기 상한
    // 이분 탐색
    while (lo < hi) {
      long mid = (lo + hi) >>> 1;
      long kept = mid - countBansLE(banRanks, mid); // 삭제 후 남는 개수
      if (kept >= n) hi = mid;
      else lo = mid + 1;
    }
    return lo;
  }
}
