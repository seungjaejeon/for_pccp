import java.util.*;

/*
Week 3 Question 2- [PCCP 기출문제] 4번 / 수식 복원하기
https://school.programmers.co.kr/learn/courses/30/lessons/340210

Time Complexity: O(18n)?
제출 시 최대 1.61ms

로직은 쉬웠는데 진법대로 계산하는 함수 제대로 만드는게 제일 헷갈렸다..ㅋㅋㅎ
*/
class Solution {
    
    boolean[] base = new boolean[9];
    
    public String[] solution(String[] expressions) {
        List<String> unknowns = new ArrayList<>();
        
        // 2~9 진법중 하나
        Arrays.fill(base, true);
        base[0] = false;
        
        int maxFirstDig = 0;
        
        // 계산해야 하는 수식 찾고 몇진법이 가능한지 확인
        for(int i = 0; i<expressions.length; i++){
            String[] values = expressions[i].split(" ");
            int a = Integer.parseInt(values[0]);
            int b = Integer.parseInt(values[2]);
            boolean add = values[1].equals("+") ? true : false;
            String c = values[4];
            
            // 가장 큰 자릿수 확인
            maxFirstDig = Math.max(maxFirstDig, a - (a / 10) * 10);
            maxFirstDig = Math.max(maxFirstDig, b - (b / 10) * 10);
            maxFirstDig = Math.max(maxFirstDig, (a / 10));
            maxFirstDig = Math.max(maxFirstDig, (b / 10));
            
            if (c.equals("X")){
                unknowns.add(expressions[i]);
            } else {
                int cInt = Integer.parseInt(c);
                checkBase(a, b, cInt, add);
            }
            
        }
        
        // 각 자리수가 진법보다 클 수 없음
        for(int i = 0; i<maxFirstDig; i++){
            base[i] = false;
        }
        
        String[] answer = new String[unknowns.size()];
        
        // 계산해야 하는 수식에 대해서 가능한 경우 계산
        for(int i = 0; i<answer.length; i++){
            String[] values = unknowns.get(i).split(" ");
            int a = Integer.parseInt(values[0]);
            int b = Integer.parseInt(values[2]);
            boolean add = values[1].equals("+") ? true : false;
            
            String ans = calcAns(a, b, add);
            answer[i] = unknowns.get(i).replace("X", ans);
        }
        
        return answer;
    }
    
    // 주어진 수식에 따라 몇진법이 가능한지 확인
    public void checkBase(int a, int b, int c, boolean add){
        for(int i = 0; i<9; i++){
            if(!base[i]) continue;
            int res = calcBase(a, b, add, i+1);
            if (res != c) base[i] = false;
         }
    }
    
    // 주어진 수식과 진법에 따라 결과 계산, 불확실할 경우 ? 리턴
    public String calcAns(int a, int b, boolean add){
        int res = -1;
        
        for(int i = 0; i<9; i++){
            if(!base[i]) continue;
            int curRes = calcBase(a, b, add, i+1);
            if(res<0) res = curRes;
            if(res != curRes) return "?";
        }
        
        return Integer.toString(res);
    }
    
    // 주어진 진법에 따라 계산
    public int calcBase(int a, int b, boolean add, int base) {
        int res = 0;
        int carry = 0;
        int digit = 1;

        // 1의 자리수부터 계산
        while (a > 0 || b > 0 || carry != 0) {
            // 현재 자리수 
            int digitA = a % 10;
            int digitB = b % 10;

            if (add) {
                int sum = digitA + digitB + carry;
                carry = sum / base;
                sum = sum % base;
                res += sum * digit;
            } else {
                int diff = digitA - digitB - carry;
                if (diff < 0) {
                    diff += base;
                    carry = 1;
                } else {
                    carry = 0;
                }
                res += diff * digit;
            }

            // 다음 자리수로 변환
            digit *= 10;
            a /= 10;
            b /= 10;
        }

        return res;
    }
}
