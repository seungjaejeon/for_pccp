import java.util.*;

class Solution {
    public int solution(int[] bandage, int health, int[][] attacks) {
        int answer = -1;
        int con = 0;
        int attCnt = 0;
        int nowH = health;
        
        for(int i=1; i<=attacks[attacks.length-1][0];i++){
            if(i == attacks[attCnt][0]){
                nowH -= attacks[attCnt++][1];
                con = 0;
                if(nowH < 1) return -1;
            }
            else {
                nowH += bandage[1];
                
                if(++con == bandage[0]){
                    nowH += bandage[2];
                    con = 0;
                }
                
                if(nowH > health) nowH = health;
            }
            
            System.out.println(i + " " + nowH);
        }
        
        answer = nowH;
        return answer;
    }
}
