import java.util.*;
import java.lang.Math.*;

/*
Week 1 Question 2 - [PCCP 모의고사 #1] 2번 - 체육대회 
https://school.programmers.co.kr/learn/courses/15008/lessons/121684

Time Complexity: 학생수가 최대 10이므로 constant로 계산할 시 O(11^n)?
memoization으로 최적화 해야할 것 같은데 이대로도 제출시 빨라서,,
*/
class Solution {
    public int solution(int[][] ability) {
        
        // i 학생 뽑힘 여부
        boolean[] students = new boolean[ability.length];
        
        return dfs(ability, students, 0, 0);
    }
    
    public int dfs(int[][] ability, boolean[] students, int j, int curScore){
        
        if(j >= ability[0].length) return curScore;
        
        int max = 0;
        for(int i = 0; i < ability.length; i++){
            // 이미 뽑힌 학생은 제외
            if(students[i]) continue;

            students[i] = true;
            // 해당 종목에 i 학생 뽑았을 때 가능한 경우의 수 중 가장 점수가 높은 경우 
            max = Math.max(max, dfs(ability, students, j+1, curScore+ability[i][j]));
            students[i] = false;
        }

        // 해당 종목에 미출전
        max = Math.max(max, dfs(ability, students, j+1, curScore));

        return max;
    }
}