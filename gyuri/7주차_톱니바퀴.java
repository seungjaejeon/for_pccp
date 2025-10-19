import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 11:20
 *
 * 반시계 방향으로 돌릴시 -> 톱니(6) + 톱니(2)
 * 반대편이랑 서로 같으면 -> 회전x / 서로 같으면 -> 회전o
 *
 * [해결방법]
 * 1) 리스트에 톱니바퀴 4개 (큐로 만들기) 넣기
 * 2)
 */
class Main{
  static int[][] arr = new int[4][8];
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    //톱니바퀴 입력받기
    for(int i=0; i<4; i++){
      Deque<Integer> q = new LinkedList<>();
      String tmp = br.readLine();
      for(int j=0; j<8; j++) arr[i][j] = tmp.charAt(j)-'0';
    }

    int K = Integer.parseInt(br.readLine());
    while(K-->0){
      StringTokenizer st = new StringTokenizer(br.readLine());
      int idx = Integer.parseInt(st.nextToken()) - 1;
      int dir = Integer.parseInt(st.nextToken());
      operation(idx, dir);
    }
    int result = 0;
    for(int i=0; i<4; i++){
      result += Math.pow(2, i) * arr[i][0];
    }
    System.out.println(result);
  }
  //톱니바퀴 연산을 동작
  static void operation(int idx, int dir) {
    //왼쪽 (idx-1, -dir)
    leftSide(idx-1, -dir);
    //오른쪽 (idx+1, -dir)
    rightSide(idx+1, -dir);
    //자기자신 회전
    rotation(idx, dir);
  }
  static void rotation(int idx, int dir) {
    if (dir==1) { //시계방향
      int tmp = arr[idx][7];
      for(int i=7; i>0; i--) arr[idx][i] = arr[idx][i-1];
      arr[idx][0] = tmp;
    } else { //반시계방향
      int tmp = arr[idx][0];
      for(int i=0; i<7; i++) arr[idx][i] = arr[idx][i+1];
      arr[idx][7] = tmp;
    }
  }

  static void leftSide(int idx, int dir){
    if(idx < 0) return; //범위를 지나간 것은 패스
    if(arr[idx][2] == arr[idx+1][6]) return; //같은 극이면 패스

    leftSide(idx-1, -dir); //더 왼쪽이 있는지 체크
    rotation(idx, dir); //자기 자신 돌리기
  }
  static void rightSide(int idx, int dir){
    if(idx > 3) return;
    if(arr[idx][6] == arr[idx-1][2]) return;

    rightSide(idx+1, -dir);
    rotation(idx, dir);
  }
}