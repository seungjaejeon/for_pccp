import java.util.*;

class Solution {

  // Disjoint Set Union (Union-Find)
  static class DSU {
    int[] parent, rank;
    DSU(int n) {
      parent = new int[n];
      rank = new int[n];
      for (int i = 0; i < n; i++) parent[i] = i;
    }
    int find(int x) {
      if (parent[x] != x) parent[x] = find(parent[x]);
      return parent[x];
    }
    void union(int a, int b) {
      int pa = find(a), pb = find(b);
      if (pa == pb) return;
      if (rank[pa] < rank[pb]) parent[pa] = pb;
      else if (rank[pa] > rank[pb]) parent[pb] = pa;
      else { parent[pb] = pa; rank[pa]++; }
    }
  }

  public int[] solution(int[] nodes, int[][] edges) {
    int n = nodes.length;

    // 값 -> 인덱스 매핑 (간선이 노드 "값"으로 주어지므로)
    Map<Integer, Integer> idx = new HashMap<>(n * 2);
    for (int i = 0; i < n; i++) idx.put(nodes[i], i);

    DSU dsu = new DSU(n);
    int[] deg = new int[n];

    // 차수 집계 + 컴포넌트 병합
    for (int[] e : edges) {
      int u = idx.get(e[0]);
      int v = idx.get(e[1]);
      deg[u]++; deg[v]++;
      dsu.union(u, v);
    }

    // 각 컴포넌트별 "루트로 가정했을 때" Good/Bad 개수
    int[] compGood = new int[n]; // parity(node) == parity(deg) 인 노드 수
    int[] compBad  = new int[n]; // parity(node) != parity(deg) 인 노드 수
    for (int i = 0; i < n; i++) {
      int r = dsu.find(i);
      boolean nodeOdd = (nodes[i] & 1) == 1;
      boolean degOdd  = (deg[i] & 1) == 1;
      if (nodeOdd == degOdd) compGood[r]++; else compBad[r]++;
    }

    // 트리마다: Good이 1이면 홀짝 트리 가능, Bad가 1이면 역홀짝 트리 가능
    int evenOddTrees = 0;
    int reverseTrees = 0;
    for (int i = 0; i < n; i++) {
      if (dsu.find(i) != i) continue; // 컴포넌트 대표만
      if (compGood[i] == 1) evenOddTrees++;
      if (compBad[i]  == 1) reverseTrees++;
    }

    return new int[]{evenOddTrees, reverseTrees};
  }
}
