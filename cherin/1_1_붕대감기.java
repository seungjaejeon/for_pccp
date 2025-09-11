import java.util.*;
import java.lang.Math.*;

/*
Week 1 Question 1 - [PCCP 기출문제] 1번/붕대 감기
https://school.programmers.co.kr/learn/courses/30/lessons/250137

Time Complexity: O(n)
*/
class Solution {
    public int solution(int[] bandage, int health, int[][] attacks) {
        
        int t = bandage[0];
        int x = bandage[1];
        int y = bandage[2];
        
        int curTime = 1;
        int curHealth = health;
        
        for(int i = 0; i < attacks.length; i++){
            
            // 체력이 0이하로 떨어지면 게임오버
            if (curHealth <= 0) break;
            
            // 지난 초당 x만큼 회복
            int timeDiff = Math.max(0, attacks[i][0] - curTime - 1);
            int recovery = timeDiff * x;
            
            // 붕대감기 t초 연속 성공하면 y만큼 추가회복
            int combo =  (int) timeDiff/t;
            recovery += combo * y;
            
            // 체력 회복 적용
            curHealth = Math.min(health, curHealth + recovery);
            
            // 공격 피해 적용
            curHealth -= attacks[i][1];
                
            // 현재시간 업데이트
            curTime = attacks[i][0];
        }
        

        return curHealth <= 0 ? -1 : curHealth;
    }
}
