package PCCP_7주차.파괴되지않은건물;

public class Main {

    public static void main(String[] args) {
        System.out.println(solution(new int[][] {{5,5,5,5,5},{5,5,5,5,5},{5,5,5,5,5},{5,5,5,5,5}}, new int[][] {{1,0,0,3,4,4},{1,2,0,2,3,2},{2,1,0,3,1,2},{1,0,1,3,3,1}}));
        System.out.println(solution(new int[][] {{1,2,3},{4,5,6},{7,8,9}}, new int[][] {{1,1,1,2,2,4},{1,0,0,1,1,2},{2,2,0,2,0,100}}));
    }

    public static int solution(int[][] board, int[][] skill) {
        int N = board.length;
        int M = board[0].length;
        int answer = 0;

        // 1. 변화량(delta)을 기록할 (N+1) x (M+1) 크기의 배열 생성
        // (N, M) 인덱스 접근 시 런타임 에러 방지를 위해 +1 크기로 생성
        int[][] sumBoard = new int[N + 1][M + 1];

        // 2. skill을 순회하며 변화량의 경계 표시 (O(K))
        for (int[] s : skill) {
            int type = s[0];
            int r1 = s[1];
            int c1 = s[2];
            int r2 = s[3];
            int c2 = s[4];

            // type이 1(공격)이면 음수, 2(회복)이면 양수로 변환
            int degree = (type == 1) ? -s[5] : s[5];

            // 2차원 누적 합의 원리를 이용한 경계 표시
            sumBoard[r1][c1] += degree;           // 시작 지점
            sumBoard[r1][c2 + 1] -= degree;     // 가로 종료 지점
            sumBoard[r2 + 1][c1] -= degree;     // 세로 종료 지점
            sumBoard[r2 + 1][c2 + 1] += degree; // 중복 제거 지점
        }

        // 3. 누적 합 계산 (O(N*M))

        // 3-1. 가로(행) 방향 누적 합 (왼쪽 -> 오른쪽)
        for (int i = 0; i <= N; i++) { // 행 기준
            for (int j = 1; j <= M; j++) { // 0열은 기준, 1열부터 누적
                sumBoard[i][j] += sumBoard[i][j - 1];
            }
        }

        // 3-2. 세로(열) 방향 누적 합 (위 -> 아래)
        for (int j = 0; j <= M; j++) { // 열 기준
            for (int i = 1; i <= N; i++) { // 0행은 기준, 1행부터 누적
                sumBoard[i][j] += sumBoard[i - 1][j];
            }
        }

        // 4. 최종 내구도 계산 및 파괴되지 않은 건물 카운트 (O(N*M))
        // sumBoard[0..N-1][0..M-1]에 최종 변화량이 저장됨
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                // 원본 내구도(board)와 최종 변화량(sumBoard)을 합산
                if (board[i][j] + sumBoard[i][j] > 0) {
                    answer++;
                }
            }
        }

        return answer;
    }
}
