import java.util.*;

/*
Week 5 Question 1 - 유전법
https://school.programmers.co.kr/learn/courses/15009/lessons/121685

Time Complexity: O(nm)
제출 시 최대 0.03ms
*/
class Solution {
    
    String[] children = new String[]{"RR", "Rr", "Rr", "rr"};
    
    public String[] solution(int[][] queries) {
        String[] answer = new String[queries.length];
        for(int i = 0; i< answer.length; i++){
            answer[i] = dnq(queries[i][0], queries[i][1]);
        }
        return answer;
    }
    
    public String dnq(int n, int p){
        if (n == 1) return "Rr";  
        
        // 부모의 위치 찾기
        int parentIndex = (p - 1) / 4 + 1;
        String parent = dnq(n - 1, parentIndex);
        
        if (parent.equals("RR")) return "RR";
        if (parent.equals("rr")) return "rr";
        
        // 부모가 Rr인 경우
        return children[(p - 1) % 4];
    }
}
