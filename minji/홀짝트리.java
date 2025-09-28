import java.util.*;

class Solution {
    static List<Integer>[] g;
    static boolean[] vis;
    static int[] label, deg;

    public int[] solution(int[] nodes, int[][] edges) {
        int n = nodes.length;

        // 라벨 -> 인덱스 매핑
        Map<Integer,Integer> idx = new HashMap<>(n*2);
        for (int i=0;i<n;i++) idx.put(nodes[i], i);

        // 그래프 구성 + 차수
        g = new ArrayList[n];
        for (int i=0;i<n;i++) g[i] = new ArrayList<>();
        deg = new int[n];
        for (int[] e: edges){
            int u = idx.get(e[0]), v = idx.get(e[1]);
            g[u].add(v); g[v].add(u);
            deg[u]++; deg[v]++;
        }
        label = Arrays.copyOf(nodes, n);

        vis = new boolean[n];
        long oddAns = 0, revAns = 0;

        for (int s=0; s<n; s++){
            if (vis[s]) continue;

            // 컴포넌트 수집
            List<Integer> comp = new ArrayList<>();
            Deque<Integer> st = new ArrayDeque<>();
            st.push(s); vis[s]=true;
            while(!st.isEmpty()){
                int u = st.pop();
                comp.add(u);
                for(int v: g[u]) if(!vis[v]){ vis[v]=true; st.push(v); }
            }

            int size = comp.size();

            if (size == 1) {
                int p = label[comp.get(0)] & 1;
                // 루트 자신만 존재 → 자식수=0
                oddAns += (p==0 ? 1 : 0); // 짝수면 홀짝 트리 OK
                revAns += (p==1 ? 1 : 0); // 홀수면 역홀짝 트리 OK
                continue;
            }

            // 비루트 가정: 자식수 parity = (deg - 1) % 2
            int revCnt = 0;   // '역(값%2 != (deg-1)%2)' 개수
            int normCnt = 0;  // '일반(값%2 == (deg-1)%2)' 개수
            for (int u: comp){
                int valParity = label[u] & 1;
                int childParity = (deg[u]-1) & 1;
                if (valParity != childParity) revCnt++;  // 역
                else normCnt++;                           // 일반
            }

            // ★ 수정 포인트: ‘정확히 1개’ 규칙
            oddAns += (revCnt == 1 ? 1 : 0);
            revAns += (normCnt == 1 ? 1 : 0);
        }
        return new int[]{(int)oddAns, (int)revAns};
    }
}
