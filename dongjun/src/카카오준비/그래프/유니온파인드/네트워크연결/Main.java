package 카카오준비.그래프.유니온파인드.네트워크연결;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int M;
    static int[] parents;

    public static class Node implements Comparable<Node> {
        int start;
        int end;
        int cost;

        public Node(int start, int end, int cost) {
            this.start = start;
            this.end = end;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o) {
            return this.cost - o.cost;
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/sindongjun/Desktop/러닝클럽/dongjun/src/카카오준비/그래프/유니온파인드/네트워크연결/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        parents = new int[N + 1];

        PriorityQueue<Node> pq = new PriorityQueue<>();

        while (M > 0) {
            int a,b,c;

            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());

            pq.add(new Node(a,b,c));

            M--;
        }

        for (int i = 1; i <= N; i++) {
            parents[i] = i;
        }

        int totalCost = 0;

        while(!pq.isEmpty()){
            Node now = pq.poll();

            if(find(now.start) != find(now.end)){
                union(now.start, now.end);
                totalCost += now.cost;
            } else{
                continue;
            }

        }

        System.out.println(totalCost);
    }

    static void union(int a, int b){
        int rootA = find(a);
        int rootB = find(b);

        if (rootA != rootB) {
            parents[rootB] = rootA;
        }
    }

    static int find(int a){
        if(parents[a] == a) return a;
        return parents[a] = find(parents[a]);
    }
}
