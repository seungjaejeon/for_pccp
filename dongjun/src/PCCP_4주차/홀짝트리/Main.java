package PCCP_4주차.홀짝트리;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(new int[] {11, 9, 3, 2, 4, 6}, new int[][] {{9, 11}, {2, 3}, {6, 3}, {3, 4}})));
        System.out.println(Arrays.toString(solution(new int[] {9, 15, 14, 7, 6, 1, 2, 4, 5, 11, 8, 10}, new int[][] {{5, 14}, {1, 4}, {9, 11}, {2, 15}, {2, 5}, {9, 7}, {8, 1}, {6, 4}})));

    }

    public static int[] solution(int[] nodes, int[][] edges) {
        /*
        루트 노드가 설정되지 않은 1개 이상의 트리가 있다.
        모든 노드들은 서로 다른 번호를 가지고 있다
        각 노드는 홀수 노드, 짝수 노드, 역홀수 노드, 역짝수 노드 중 하나 이다

        홀수 노드 : 노드의 번호가 홀수이며 자식 노드의 개수가 홀수
        짝수 노드 : 노드의 번호가 짝수이며 자식 노드의 개수가 짝수
        역홀수 노드 : 노드의 번호가 홀수이며 자식 노드의 개수가 짝수
        역짝수 노드 : 노드의 번호가 짝수이며 자식 노드의 개수가 홀수

        각 트리에 대해 루트 노드를 설정했ㅇ르 때 홀짝 트리가 될 수 있는 트리의 개수고 역홀짝 트리가 될 ㅅ ㅜ있는
        트리의 개수를 구하려고 한다

        홀짝 트리 : 홀수 노드와 짝수 노드로만 이루어진 트리
        역홀짝 트리 : 역홀수 노드와 역짝수 노드로만 이루어진 트리

        포레스트에 존재하는 노드들의 번호를 담은 1차원 정수 배열 nodes,
        포레스트에 존재하는 간선들의 정보를 담은 2차원 정수 배열 edges가 매개변수로 주어집니다.
        이때, 홀짝 트리가 될 수 있는 트리의 개수와 역홀짝 트리가 될 수 있는 트리의 개수를 1차원 정수 배열에 순서대로 담아 return

         */

        Map<Integer, List<Integer>> adj = new HashMap<>();
        Map<Integer, Integer> degree = new HashMap<>();

        for (int node : nodes) {
            adj.put(node, new ArrayList<>());
            degree.put(node, 0);
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            adj.get(u).add(v);
            adj.get(v).add(u);
            degree.put(u, degree.get(u) + 1);
            degree.put(v, degree.get(v) + 1);
        }

        int holJJakTreeCount = 0;
        int yeokHolJJakTreeCount = 0;
        Set<Integer> visited = new HashSet<>();


        for (int startNode : nodes) {
            if (!visited.contains(startNode)) {
                List<Integer> componentNodes = new ArrayList<>();
                Queue<Integer> queue = new LinkedList<>();

                queue.add(startNode);
                visited.add(startNode);

                while (!queue.isEmpty()) {
                    int currentNode = queue.poll();
                    componentNodes.add(currentNode);

                    for (int neighbor : adj.get(currentNode)) {
                        if (!visited.contains(neighbor)) {
                            visited.add(neighbor);
                            queue.add(neighbor);
                        }
                    }
                }

                int typeACount = 0;
                int typeBCount = 0;

                for (int node : componentNodes) {
                    boolean isNodeOdd = (node % 2 != 0);
                    boolean isDegreeOdd = (degree.get(node) % 2 != 0);

                    if (isNodeOdd == isDegreeOdd) {
                        typeACount++;
                    } else {
                        typeBCount++;
                    }
                }

                if (typeACount == 1 && typeBCount == componentNodes.size() - 1) {
                    holJJakTreeCount++;
                }
                if (typeBCount == 1 && typeACount == componentNodes.size() - 1) {
                    yeokHolJJakTreeCount++;
                }
            }
        }

        return new int[]{holJJakTreeCount, yeokHolJJakTreeCount};
    }

}
