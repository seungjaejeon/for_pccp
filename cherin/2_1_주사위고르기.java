import java.util.*;

class Solution {
    
    /*
    Week 2 Question 1 - 주사위 고르기
    https://school.programmers.co.kr/learn/courses/30/lessons/258709

    Time Complexity: 대충 최소 n * 6^(n/2)...
    프로그래머스 제출 시 563ms가 최대

    보완할점: 이기는 경우의 수, 무승부, 지는 경우의 수 한번에 계산하면 계산 반절만 해도 되는데 
               n<=10 제한이 있어서 그냥 해도 통과하긴한다...
               (현재는 1001 0110 중복 계산)
    */
    public int[] solution(int[][] dice) {
        
        int n2 = dice.length/2;
        int maxScore = 0;
        int maxCombo = 0;

        // 승률이 가장 높은 주사위 조합 찾기
        for (int i = 0; i < 1 << dice.length; i++) {
            if (Integer.bitCount(i) == n2) {
                int curScore = getScore(i, dice);
                if (curScore > maxScore){
                    maxScore = curScore;
                    maxCombo = i;
                }
            }
        }
        
        
        // 비트마스킹된 주사위 조합 배열로 변환
        int[] answer = new int[n2];
        int cur = 0;
        for (int i = 0; i <= dice.length; i++) {
            if ((maxCombo & (1 << i)) > 0) {
               answer[cur++] = i+1;
            }
        }
        
        return answer;
    }
    
    /*
        주어진 주사위 조합에 대해 승리하는 경우의 수를 구한다.
    */
    public int getScore(int combo, int[][] dice){
        
        int n2 = dice.length/2;
        int scoreLen = (int) Math.pow(6, n2);
        
        int[] myScores = new int[scoreLen];
        int[] yourScores = new int[scoreLen];
        int myDepth = 0;
        int yourDepth = 0;
        
        // 모든 주사위 경우의 수 계산
        for(int i = 0; i<dice.length; i++){
            if ( (combo & (1 << i) ) > 0) {
              // 내가 고른 주사위
               for(int j = 0; j<scoreLen; j++){
                   int faceIdx = ( (j+1) / (int) Math.pow(6, myDepth) ) % 6;
                   myScores[j] += dice[i][faceIdx];
               }
                myDepth++;
            } else {
                // 내가 고르지 않은 주사위
                for(int j = 0; j<scoreLen; j++){
                    int faceIdx = ( (j+1) / (int) Math.pow(6, yourDepth) ) % 6;
                    yourScores[j] += dice[i][faceIdx];
               }
                yourDepth++;
            }
        }
        
        Arrays.sort(myScores);
        Arrays.sort(yourScores);
        
        int win = 0;
        int i = scoreLen - 1;
        int j = scoreLen - 1;
        
        // 승률 계산
        while (i>=0 && j>=0){
            if(myScores[i] > yourScores[j]){
                // i가 j보다 클 경우 j보다 작은 수는 다 이김
                win += j+1;
                i--;
            } else {
                // 지거나 무승부
                j--;
            }
        }

        return win;
    }
}
