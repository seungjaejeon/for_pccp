import java.util.*;

/*
Week 4 Question 1 - 봉인된 주문
https://school.programmers.co.kr/learn/courses/30/lessons/389481

Time Complexity: O(nlogn)
제출 시 최대 535ms

문자열 순서는 어차피 고정 (a~z 11글자 이하로 쓸 수 있는 모든 문자열)
삭제된 주문의 순번을 확인해서 n보다 작으면 그만큼 n에서 더해서
새로운 (실제 풀배열의) n번째 문자열을 찾으면 됨
*/

class Solution {
    public String solution(long n, String[] bans) {
        long newN = n;
        
        // 사전순으로 bans 정렬 (길이 비교 중요!! 그냥 하면 'aa' < 'b')
        Arrays.sort(bans, (a,b) -> {
            if(a.length() != b.length()) return a.length() - b.length();
            return a.compareTo(b);
        });
        
        // 영향이 있는 금지된 주문만 확인
        for (String s : bans) {
            long curOrd = getOrder(s);
            if (curOrd <= newN) newN++;
            else break;
        }
        
        return getSpell(newN);
    }
    
    // 순서로 주문 찾기
    public String getSpell(long n) {
        
        // 주문 길이 계산
        int length = 1;
        long count = 26;
        long sum = 0;

        while (n > sum + count) {
            sum += count;
            length++;
            count *= 26;
        }

        // 같은 길이 문자열 안에서의 순서 계산
        long order = n - sum - 1;  

        // 알파벳으로 변환
        // 26진법 뺄셈이랑 비슷한 개념
        char[] result = new char[length];
        for (int i = length - 1; i >= 0; i--) {
            int remainder = (int)(order % 26);
            result[i] = (char)('a' + remainder);
            order /= 26;
        }

        return new String(result);
    }
    
    // 주문의 순서 계산 
    public long getOrder(String spell) {

        // Math.pow는 double 기반이라 오버플로우 될수도
        long shortCnt = 0;
        long mul = 26;
        for(int i = 1; i < spell.length(); i++) {
            shortCnt += mul;  // 글자수가 짧은 모든 경우의 수 더하기
            mul *= 26;
        }

        // 같은 길이 내에서 사전순 계산
        long curN = 0;
        for(int i = 0; i < spell.length(); i++) {
            int curOrd = (spell.charAt(i) - 'a');  // 0-based
            curN = curN * 26 + curOrd;
        }

        return shortCnt + curN + 1;
    }
}
