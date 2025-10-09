package PCCP_5주차.유전법칙;

public class Main {
    public static void main(String[] args) {
        System.out.println(solution(new int[][]{{1, 1}, {2, 2}, {3, 3}, {4, 4}})); // ["Rr", "RR", "Rr", "rr"]
        System.out.println(solution(new int[][]{{1, 2}, {2, 2}, {3, 3}})); // ["Rr", "RR", "Rr"]
        System.out.println(solution(new int[][]{{4, 26}, {5, 17}}));
    }

    public static String[] solution(int[][] queries) {
        String[] answer = new String[queries.length];

        /*
            둥근 완두 순종(RR)을 자가 수분, 즉 같은 유전자끼리 교배할 경우, 다음 세대에 둥근 완두 순종 형질만 나타난다
            주름진 완두 순종(rr)을 자가 수분할 경우, 다음 세대에 주름진 완두 순종 형질만 나타난다
            두 순종을 교배한 잡종(Rr)을 자가 수분할 경우, 다음 세대에 둥근 완두 순종(RR), 잡종(Rr), 주름진 완두 순종(rr)이 1:2:1의 비율로 나타난다

           진송이는 직접 완두콩의 자가 수분 실험 진행
           진송이의 실험에서 완두콩 한 개를 자가 수분한 결과는
            1. 각 완두콩은 자가 수분해서 정확히 4개의 완두콩 후손을 남긴다
            2. 잡종 완두콩(Rr)은 자가 수분해서 첫째는 RR, 둘째와 셋째는 Rr, 넷째는 rr의 형질을 가진다
            3. 순종 완두콩(RR, rr)은 자가 수분해서 자신과 같은 형질이 후손을 남긴다.

            완두콩의 세대와 해당 세대에서 몇 번째 게체인지를 알면 형질을 바로 계산하는 프로그램을 만들려 한다.

            각 세대의 맨 왼쪽 개체부터 1,2,3,... 순서로 번호를 매긴다
         */

        for (int i = 0; i < queries.length; i++) {
            int n = queries[i][0]; // 세대
            int p = queries[i][1]; // 위치 (1부터 시작)

            answer[i] = findTrait(n, p - 1);
        }

        return answer;
    }


    private static String findTrait(int generation, int positionIdx) {
        if (generation == 1) {
            return "Rr";
        }

        // 부모 세대의 위치를 계산
        int parentPositionIdx = positionIdx / 4;

        // 부모의 형질
        String parentTrait = findTrait(generation - 1, parentPositionIdx);

        // 부모가 순종이면 자식도 순종
        if (parentTrait.equals("RR") || parentTrait.equals("rr")) {
            return parentTrait;
        }

        // 부모가 잡종이면 위치에 따라 결정
        int childOrder = positionIdx % 4;

        if (childOrder == 0) {
            return "RR";
        } else if (childOrder == 1 || childOrder == 2) {
            return "Rr";
        } else {
            return "rr";
        }
    }
}
