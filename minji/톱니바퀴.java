import java.io.*;
import java.util.*;

public class Main {
    static int[][] gear = new int[4][8];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < 4; i++) {
            String s = br.readLine().trim();
            for (int j = 0; j < 8; j++) gear[i][j] = s.charAt(j) - '0';
        }

        int K = Integer.parseInt(br.readLine().trim());
        while (K-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int idx = Integer.parseInt(st.nextToken()) - 1; // 0-based
            int dir = Integer.parseInt(st.nextToken());     // 1: CW, -1: CCW

            int[] rot = new int[4];
            rot[idx] = dir;

            // 왼쪽으로 전파
            for (int g = idx - 1; g >= 0; g--) {
                // g와 g+1의 맞닿는 치아 비교: g의 오른쪽(2) vs g+1의 왼쪽(6)
                if (gear[g][2] != gear[g + 1][6]) rot[g] = -rot[g + 1];
                else break;
            }
            // 오른쪽으로 전파
            for (int g = idx + 1; g < 4; g++) {
                // g-1과 g의 맞닿는 치아 비교: g-1의 오른쪽(2) vs g의 왼쪽(6)
                if (gear[g - 1][2] != gear[g][6]) rot[g] = -rot[g - 1];
                else break;
            }

            // 실제 회전 수행
            for (int i = 0; i < 4; i++) {
                if (rot[i] == 0) continue;
                rotate(i, rot[i]);
            }
        }

        // 점수 계산
        int ans = 0;
        for (int i = 0; i < 4; i++) if (gear[i][0] == 1) ans += (1 << i);
        System.out.println(ans);
    }

    static void rotate(int g, int dir) {
        // dir = 1(CW): 맨 끝이 앞으로
        // dir = -1(CCW): 맨 앞이 뒤로
        int[] ng = new int[8];
        if (dir == 1) {
            ng[0] = gear[g][7];
            for (int i = 1; i < 8; i++) ng[i] = gear[g][i - 1];
        } else {
            for (int i = 0; i < 7; i++) ng[i] = gear[g][i + 1];
            ng[7] = gear[g][0];
        }
        gear[g] = ng;
    }
}
