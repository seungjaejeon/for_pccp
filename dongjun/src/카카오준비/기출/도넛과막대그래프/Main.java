package 카카오준비.기출.도넛과막대그래프;

import java.util.*;

public class Main {
    public static void main(String[] args) {

    }

    public static int[] solution(int[][] edges) {

        /*
            도넛 모양 그래프, 막대 모양 그래프, 8자 모양 그래프 들이 있다
            이 그래프들은 1개 이상의 정점과 정점들을 연결하는 단방향 간선으로 이루어져 있다.
            - 크기가 n인 도넛 모양 그래프는 n개의 정점과 n개의 간선이 있다.
            - 크기가 n인 막대 모양 그래프는 n개의 정점과 n-1개의 간선
            - 크기가 n인 8자 모양 그래프는 2n + 1 개의 정점과 2n + 2개의 간선이 있다.
                - 크기가 동일한 2개의 도넛 모양 그래프에서 정점을 하나씩 골라 결합시킨 형태

            정점을 하나 생성한 뒤, 각 도넛 모양 그래프, 막대 모양 그래프, 8자 모양 그래프의 임의의 정점 하나로 향하는 간선들을 연결
            그 후 각 정점에 서로 다른 번호를 매긴다.
            이때 그래프의 간선 정보가 주어지면 생성한 정점의 번호와 정점을 생성하기 전 도넛 모양 그래프의 수, 막대모양 그래프의 수, 8자 모양 그래프의 수를 구해야 한다.



         */

        // 각 정점의 [진출 차수, 진입 차수]를 저장할 맵
        Map<Integer, int[]> degreeMap = new HashMap<>();

        // 1. 모든 간선을 순회하며 각 정점의 진입/진출 차수 계산
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];

            // from 정점의 진출 차수 증가
            degreeMap.putIfAbsent(from, new int[2]);
            degreeMap.get(from)[0]++;

            // to 정점의 진입 차수 증가
            degreeMap.putIfAbsent(to, new int[2]);
            degreeMap.get(to)[1]++;
        }


        int[] answer = new int[4]; // [생성된 정점, 도넛, 막대, 8자]
        int createdVertex = -1;
        int donutCount = 0;
        int barCount = 0;
        int eightCount = 0;

        // 2. degreeMap을 순회하며 정점 유형 분석
        for (Map.Entry<Integer, int[]> entry : degreeMap.entrySet()) {
            int vertex = entry.getKey();
            int[] degrees = entry.getValue();
            int outDegree = degrees[0];
            int inDegree = degrees[1];

            // 2-1. 생성된 정점 찾기 (진입 0, 진출 2 이상)
            if (outDegree >= 2 && inDegree == 0) {
                createdVertex = vertex;
            }
            // 2-2. 막대 그래프의 끝점 찾기 (진출 0)
            else if (outDegree == 0) {
                barCount++;
            }
            // 2-3. 8자 모양 그래프의 중심점 찾기 (진입 2 이상, 진출 2)
            else if (outDegree == 2 && inDegree >= 2) {
                eightCount++;
            }
        }

        // 3. 생성된 정점의 진출 차수는 전체 그래프의 수와 동일
        int totalGraphs = degreeMap.get(createdVertex)[0];

        // 4. 도넛 그래프의 수는 전체 그래프 수에서 막대와 8자 모양 그래프 수를 뺀 값
        donutCount = totalGraphs - barCount - eightCount;

        answer[0] = createdVertex;
        answer[1] = donutCount;
        answer[2] = barCount;
        answer[3] = eightCount;


        return answer;
    }

}
