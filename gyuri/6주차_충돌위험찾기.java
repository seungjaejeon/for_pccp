import java.util.*;

class Solution {

  // 각 초(t)마다 좌표(r,c)에 몇 대가 모였는지 카운트해서
  // t,r,c가 동일한 “이벤트”의 개수 중 2대 이상이면 위험 1회로 센다.
  public int solution(int[][] points, int[][] routes) {
    // 포인트 번호(1-based) -> 좌표 매핑
    // points[i] = {r, c}, routes의 각 값은 1..points.length
    int nPoint = points.length;

    // (time, r, c) -> count
    // 키는 "t|r|c" 문자열로 단순하게 구성 (문제 한도에서 충분히 빠름)
    Map<String, Integer> cnt = new HashMap<>();

    // 각 로봇의 경로를 “최단경로: r 먼저, 그다음 c”로 시뮬레이션
    for (int[] route : routes) {
      int t = 0;
      int[] start = points[route[0] - 1];
      int r = start[0], c = start[1];

      // 출발 시각 t=0의 위치 기록
      add(cnt, t, r, c);

      // 경로의 각 구간을 잇대어 이동
      for (int i = 1; i < route.length; i++) {
        int[] target = points[route[i] - 1];
        int tr = target[0], tc = target[1];

        // 1) 행(r) 방향으로 먼저 이동
        while (r != tr) {
          r += (tr > r ? 1 : -1);
          t += 1;
          add(cnt, t, r, c);
        }
        // 2) 열(c) 방향으로 이동
        while (c != tc) {
          c += (tc > c ? 1 : -1);
          t += 1;
          add(cnt, t, r, c);
        }
      }
      // 마지막 지점 도착 이후에는 더 이상 카운트하지 않음
    }

    // 같은 시각 같은 좌표에 2대 이상 모인 경우만 센다
    int answer = 0;
    for (int v : cnt.values()) {
      if (v >= 2) answer++;
    }
    return answer;
  }

  private void add(Map<String, Integer> cnt, int t, int r, int c) {
    String key = t + "|" + r + "|" + c;
    cnt.put(key, cnt.getOrDefault(key, 0) + 1);
  }
}
