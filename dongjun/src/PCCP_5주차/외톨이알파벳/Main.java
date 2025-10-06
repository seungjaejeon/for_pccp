package PCCP_5주차.외톨이알파벳;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println(solution("edeaaabbccd"));
    }

    public static String solution(String input_string) {
        String answer = "";

        /*
            알파벳 소문자로만 이루어진 문자열에서 2회 이상 나타난 알파벳이 2개 이상의 부분으로 나뉘어 있으면 외톨이 알파벳이라고 정의한다
         */

        StringBuilder sb = new StringBuilder();

        for (char c : input_string.toCharArray()) {
            if(sb.length() == 0) {
                sb.append(c);
                continue;
            }

            if(sb.charAt(sb.length() - 1) != c) {
                sb.append(c);
            }
        }

        Map<Character, Boolean> map = new java.util.HashMap<>();

        map.put('a', false);
        map.put('b', false);
        map.put('c', false);
        map.put('d', false);
        map.put('e', false);
        map.put('f', false);
        map.put('g', false);
        map.put('h', false);
        map.put('i', false);
        map.put('j', false);
        map.put('k', false);
        map.put('l', false);
        map.put('m', false);
        map.put('n', false);
        map.put('o', false);
        map.put('p', false);
        map.put('q', false);
        map.put('r', false);
        map.put('s', false);
        map.put('t', false);
        map.put('u', false);
        map.put('v', false);
        map.put('w', false);
        map.put('x', false);
        map.put('y', false);
        map.put('z', false);

        for (char c : sb.toString().toCharArray()) {
            if (count(sb, c) != 1 && map.get(c) == false) {
                map.put(c, true);
                answer += c;
            }
        }

        if(answer.length() == 0) {
            answer = "N";
        }

        char[] chars = answer.toCharArray();
        Arrays.sort(chars);
        answer = new String(chars);

        return answer;
    }

    public static int count(StringBuilder sb, char c) {
        int cnt = 0;

        for (char ch : sb.toString().toCharArray()) {
            if (ch == c) {
                cnt++;
            }
        }

        return cnt;
    }

}
