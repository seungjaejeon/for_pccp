package 카카오준비.그래프.위상정렬.줄세우기;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.*;

public class Main {
    static int N, M;
    static int[] inDegree;
    static boolean[] visited;
    static ArrayList<ArrayList<Integer>> graph;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/sindongjun/Desktop/러닝클럽/dongjun/src/카카오준비/그래프/위상정렬/줄세우기/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        inDegree = new int[N + 1];
        visited = new boolean[N + 1];
        graph = new ArrayList<>();

        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());

            graph.get(A).add(B);

            inDegree[B] += 1;
        }

        // --- 큐를 이용한 위상 정렬 실행 ---
        Queue<Integer> queue = new LinkedList<>();
        StringBuilder resultBuilder = new StringBuilder();

        // 1. 초기에 진입차수가 0인 모든 정점(학생)을 큐에 추가
        for (int i = 1; i <= N; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        // 2. 큐가 빌 때까지 반복
        while (!queue.isEmpty()) {
            // 2-1. 큐에서 정점을 하나 꺼내 결과에 추가
            int currentStudent = queue.poll();
            resultBuilder.append(currentStudent).append(" ");

            // 2-2. 현재 정점과 연결된 모든 정점들의 진입차수를 1 감소
            for (int nextStudent : graph.get(currentStudent)) {
                inDegree[nextStudent]--;

                // 2-3. 진입차수가 0이 된 정점은 큐에 추가
                if (inDegree[nextStudent] == 0) {
                    queue.offer(nextStudent);
                }
            }
        }

        // --- 결과 출력 ---
        System.out.println(resultBuilder.toString().trim());
    }

}
