package 카카오준비.기출.가장많이받은선물;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println(solution(new String[] {"muzi", "ryan", "frodo", "neo"}, new String[] {"muzi frodo", "muzi frodo", "ryan muzi", "ryan muzi", "ryan muzi", "frodo muzi", "frodo ryan", "neo muzi"}));
        System.out.println(solution(new String[] {"muzi", "ryan", "frodo", "neo"}, new String[] {"muzi frodo", "muzi frodo", "ryan muzi", "ryan muzi", "ryan muzi", "frodo muzi", "frodo ryan", "neo muzi"}));
        System.out.println(solution(new String[] {"muzi", "ryan", "frodo", "neo"}, new String[] {"muzi frodo", "muzi frodo", "ryan muzi", "ryan muzi", "ryan muzi", "frodo muzi", "frodo ryan", "neo muzi"}));
    }


    public static int solution(String[] friends, String[] gifts) {

        /*
            선물을 직접 전하기 힘들 때 카카오톡 선물하기 기능을 이용해 축하 선물을 보낼 수 있다.
            당신의 친구들이 이번 달까지 선물을 주고 받은 기록을 바탕으로 다음달에 누가 선물을 많이 받을지 예측 하려고 한다
            두 사람이 선물을 주고 받은 기록이 있다면, 이번 달 까지 두 사람 사이에 더 많은 선물을 준 사람이 다음 달에 선물을 하나 받습니다.
                예를들어  A가 B에게 선물을 5번 줬고, B가 A에게 선물을 3번 줬다면, 다음달엔 A가 B에게 선물을 하나 받는다.
            두 사람이 주고받은 기록이 하나도 없거나 주고 받은 수가 같다면, 선물 지수가 더 큰 사람이 선물 지수가 더 작은 사람에게 선물을 받는다.
                선물 지수는 이번 달까지 자신이 친구들에게 준 선물의 수에서 선물의 수를 뺀 값이다.
                예를 들어 A가 친구들에게 준 선물이 3개가 받은 선물이 10개라면 A의 선물 지수는 -7이다.
                B가 친구들에게 준 선물이 3개고 받은 선물이 2개라면 B의 선물 지수는 1이다.
                만약 A와 B가 선물을 주고받은 적이 없거나 정확히 같은 수로 선물을 주고 받았다면, 다음 달엔 B가 A에게 선물을 하나 받는다.
                만약 두 사람의 선물 지수도 같다면 다음 달에 선물을 주고 받지 않는다.
            선물을 가장 많이 받을 친구가 받을 선물의 수를 알고 싶다.

            친구들의 이름을 담은 1차원 문자열 배열 friends, 이번달까지친구들이 주고 받은 선물 기록을 담은 1 차원 문자열 배열 gifts가 매개변수로 주어진다.
            이때, 다음달에 가장 많은 선물을 받는 친구가 받을 선물의 수를 return 하도록 solution 함수를 완성해라
         */

        HashMap<String, Integer> giftScore = new HashMap<>();


        for (String name : friends) {
            giftScore.put(name, 0);
        }

        for (String gift : gifts) {
            String[] giveAndTake = gift.split(" ");

            String give = giveAndTake[0];
            String take = giveAndTake[1];

            giftScore.put(give, giftScore.get(give) + 1);
            giftScore.put(take, giftScore.get(take) - 1);
        }

        HashMap<String, Integer> nameMapping = new HashMap<>();
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < friends.length; i++) {
            graph.add(new ArrayList<>());
            nameMapping.put(friends[i], i);
        }


        for (String gift : gifts) {
            String[] giveAndTake = gift.split(" ");

            String give = giveAndTake[0];
            String take = giveAndTake[1];

            graph.get(nameMapping.get(give)).add(nameMapping.get(take));
        }

        int[] giftCnt = new int[friends.length];

        for (int i = 0; i < giftCnt.length; i++) {
            for (int j = i + 1; j < giftCnt.length; j++) {
                int giveCnt = countGive(graph, i, j);
                int takeCnt = countGive(graph, j, i);

                // 두 사람이 선물을 주고 받은 기록이 있다면, 이번 달 까지 두 사람 사이에 더 많은 선물을 준 사람이 다음 달에 선물을 하나 받습니다.
                // 예를들어  A가 B에게 선물을 5번 줬고, B가 A에게 선물을 3번 줬다면, 다음달엔 A가 B에게 선물을 하나 받는다.
                if (giveCnt > takeCnt) {
                    giftCnt[i]++;
                } else if (giveCnt < takeCnt) {
                    giftCnt[j]++;
                } else {
                    int giveGiftScore = giftScore.get(friends[i]);
                    int takeGiftScore = giftScore.get(friends[j]);

                    // 두 사람이 주고받은 기록이 하나도 없거나 주고 받은 수가 같다면, 선물 지수가 더 큰 사람이 선물 지수가 더 작은 사람에게 선물을 받는다.
                    if(giveGiftScore > takeGiftScore) {
                        giftCnt[i]++;
                    } else if (giveGiftScore < takeGiftScore) {
                        giftCnt[j]++;
                    }
                }


            }
        }


        int answer = 0;

        for (int i = 0; i < giftCnt.length; i++) {
            answer = Math.max(answer, giftCnt[i]);
        }


        return answer;
    }

    public static int countGive(ArrayList<ArrayList<Integer>> graph, int index, int targetIndex) {
        int cnt = 0;

        for (Integer who : graph.get(index)) {
            if (who == targetIndex) {
                cnt++;
            }
        }

        return cnt;
    }
}
