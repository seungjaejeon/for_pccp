import java.util.*;

/*
Week 5 Question 2 - 외톨이 알파벳
https://school.programmers.co.kr/learn/courses/15009/lessons/121683

Time Complexity: O(n)
제출 시 최대 10.42ms
*/
class Solution {
    public String solution(String input_string) {
        String answer = "";
        
        int[] parts = new int[26];
        int last = -1;

        for(int i = 0; i<input_string.length(); i++){
            char curChar = input_string.charAt(i);
            int cur = curChar - 'a' ; // 0-based
            if (cur != last){
                parts[cur]++;
            }
            last = cur;
        }
        
        for(int i = 0; i<26; i++){
            if(parts[i] > 1) answer += (char) ('a' + i);
        }
        
        return answer.equals("") ? "N" : answer;
    }
}
