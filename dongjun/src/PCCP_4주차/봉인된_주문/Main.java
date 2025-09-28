package PCCP_4주차.봉인된_주문;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println(solution(30,new String[] {"d", "e", "bb", "aa", "ae"}));
        System.out.println(solution(7388,new String[] {"gqk", "kdn", "jxj", "jxi", "fug", "jxg", "ewq", "len", "bhc"}));
    }

    // 길이 우선, 그 다음 사전 순
    public static final Comparator<String> STRING_COMPARATOR = (s1, s2) -> {
        if (s1.length() != s2.length()) {
            return Integer.compare(s1.length(), s2.length());
        }
        return s1.compareTo(s2);
    };

    public static String solution(long n, String[] bans) {

        /*
        주문은 알파벳 소문자 11글자 이하로 구성
        주문서에는 실제로 마법적효과를 지니지 않는 의미 없는 주문들, 즉 알파벳 소문자 11글자 이하로 쓸 수 있는 모든
        문자열이 고대의 규칙에 따라 아래와 같이 정렬되어 있다.
            1. 글자 수가 적은 주문부터 먼저 기록
            2. 글자 수가 같다면, 사전 순서대로 기록
        오래전 봉인된 저주받은 주문들이 숨겨져 있고, 이를 악용하려는 자들을 막기 위해 마법사들이 몇몇 주문을 주무서에서 삭제
        삭제가 완료된 주문서에서 N번째 주문을 찾아내야 한다

        Log N ->binary search
         */

        Arrays.sort(bans, STRING_COMPARATOR);

        long low = 1;
        long high = n + bans.length;
        long targetRank = high;

        while (low <= high) {
            long mid = low + (high - low) / 2;
            if (mid <= 0) {
                low = 1;
                continue;
            }

            String midString = findStringByRank(mid);
            long bannedCount = countBannedBefore(midString, bans);
            long validRank = mid - bannedCount;

            if (validRank < n) {
                low = mid + 1;
            } else {
                targetRank = mid;
                high = mid - 1;
            }

        }

        while (Arrays.binarySearch(bans, findStringByRank(targetRank), STRING_COMPARATOR) >= 0) {
            targetRank++;
        }

        return findStringByRank(targetRank);
    }

    public static long countBannedBefore(String s, String[] bans) {
        int index = Arrays.binarySearch(bans, s, STRING_COMPARATOR);

        if (index < 0) {
            return -(index + 1);
        } else {
            return index + 1;
        }
    }


    // 원래 순서 k에 해당하는 문자열
    public static String findStringByRank(long k) {
        int length = 1;
        long countForLength = 26;
        while (k > countForLength) {
            k -= countForLength;
            length++;
            countForLength *= 26;
        }

        k--;

        long divisor = 1;
        for (int i = 0; i < length - 1; i++) {
            divisor *= 26;
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            long charIndex = k / divisor;
            result.append((char) ('a' + charIndex));

            k %= divisor;

            if (divisor > 1) {
                divisor /= 26;
            }
        }

        return result.toString();
    }


}
