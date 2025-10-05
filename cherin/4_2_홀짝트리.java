import java.util.*;

/*
Week 4 Question 2- 홀짝트리
https://school.programmers.co.kr/learn/courses/30/lessons/388354

Time Complexity: O(2n + m)
제출 시 최대 683.82ms

처음에는 엣지 매핑 > Node 클래스 만듦 > 트리 구조 확인 > 모든 경우의수로 쿨래스 활용해서 트리 빌드해서 타입 확인
구현하다가 너무 복잡해서 다시 고민해보니 사이클이 없는 포레스트 구조만 주어지므로 
트리 구조로 빌드 필요 X 연결된 노드 갯수만 확인하고 루트가 아닌 경우에만 childCnt 하나 빼주면 됨
이렇게 계산하다가 iteration 줄일 수 있을 것 같아서 코드 줄이다보니 하나만 타입이 다를때만 홀짝트리/역홀짝트리임
루트는 타입이 그대로고 나머지는 다 타입이 뒤집히니까....... 
알고나니 쉬운데 빠른 방법을 생각해내는게 너무 오래걸림...ㅠ
*/
class Solution {
    
    public int[] solution(int[] nodes, int[][] edges) {

        Map<Integer, List<Integer>> edgeMap = new HashMap<>();
        Map<Integer, Boolean> visited = new HashMap<>();

        // 포레스트 구조 매핑
        for (int n : nodes) {
            edgeMap.put(n, new ArrayList<>());
            visited.put(n, false);
        }

        for (int[] e : edges) {
            int a = e[0];
            int b = e[1];
            edgeMap.get(a).add(b);
            edgeMap.get(b).add(a);
        }
        
        // 각 트리 구조 확인하며 홀짝노드, 역홀짝노드 개수 세어서 트리 개수 계산
        int treeCnt = 0;
        int revTreeCnt = 0;
        for (int n : nodes) {
            if (visited.get(n)) continue;
            Queue<Integer> q = new LinkedList<>();
            q.add(n);
            visited.put(n, true);
            
            int nodeCnt = 0;
            int revNodeCnt = 0;

            while (!q.isEmpty()) {
                int cur = q.poll();
                
                boolean nodeEven = cur % 2 == 0;
                boolean childEven = edgeMap.get(cur).size() % 2 == 0;
                if (nodeEven == childEven) nodeCnt++;       // 홀짝
                else revNodeCnt++;    // 역홀짝
                
                for (int next : edgeMap.get(cur)) {
                    if (!visited.get(next)) {
                        visited.put(next, true);
                        q.add(next);
                    }
                }
            }
            if(nodeCnt == 1) treeCnt++;
            if(revNodeCnt == 1) revTreeCnt++;
        }

        return new int[]{treeCnt, revTreeCnt};
    }

}
