import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {
    static int[][] gear = new int[4][8];
    // 회전 방향: 1 = 시계, -1 = 반시계
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 톱니바퀴 상태 입력
        for (int i = 0; i < 4; i++) {
            String s = br.readLine().trim();
            for (int j = 0; j < 8; j++) {
                gear[i][j] = s.charAt(j) - '0';
            }
        }
        
        int K = Integer.parseInt(br.readLine().trim());
        for (int k = 0; k < K; k++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken()) - 1;  // 0-based index
            int dir = Integer.parseInt(st.nextToken());
            // dir이 1이면 시계, -1이면 반시계
            
            // 회전 여부 결정하기 위한 배열: 각 톱니바퀴가 회전할지, 그리고 방향
            int[] rotateDir = new int[4];
            rotateDir[num] = dir;
            
            // 왼쪽으로 전달
            for (int i = num; i > 0; i--) {
                if (gear[i][6] != gear[i-1][2]) {
                    rotateDir[i-1] = -rotateDir[i];
                } else {
                    break;
                }
            }
            // 오른쪽으로 전달
            for (int i = num; i < 3; i++) {
                if (gear[i][2] != gear[i+1][6]) {
                    rotateDir[i+1] = -rotateDir[i];
                } else {
                    break;
                }
            }
            
            // 실제 회전 수행
            for (int i = 0; i < 4; i++) {
                if (rotateDir[i] == 1) {
                    rotateClockwise(i);
                } else if (rotateDir[i] == -1) {
                    rotateCounterClockwise(i);
                }
            }
        }
        
        // 점수 계산
        int score = 0;
        if (gear[0][0] == 1) score += 1;
        if (gear[1][0] == 1) score += 2;
        if (gear[2][0] == 1) score += 4;
        if (gear[3][0] == 1) score += 8;
        
        System.out.println(score);
    }
    
    static void rotateClockwise(int idx) {
        int tmp = gear[idx][7];
        for (int j = 7; j >= 1; j--) {
            gear[idx][j] = gear[idx][j-1];
        }
        gear[idx][0] = tmp;
    }
    
    static void rotateCounterClockwise(int idx) {
        int tmp = gear[idx][0];
        for (int j = 0; j < 7; j++) {
            gear[idx][j] = gear[idx][j+1];
        }
        gear[idx][7] = tmp;
    }
}
