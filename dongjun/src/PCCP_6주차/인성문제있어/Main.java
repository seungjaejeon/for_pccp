package PCCP_6주차.인성문제있어;

import java.io.*;
import java.util.*;

public class Main {

    /*
        인성이는 인싸가 되고 싶다
        험난한 미로에서 목적지에 도달해야 하는 훈련을 받고 있다
        제한 시간 안에 미로를 통과하지 못하면 명기 교관님에게 욕을 듣는 인성이는 최선을 다해 미로를 통과하려고 한다

        미로는 가로 길이 W, 세로 길이 H의 격자 형태를 가지며, 인성이는 한 번에 격자 상의 상,하,좌,우로 한칸씩 움직일 수 있다.
        이동이 완료될 시에 인성이의 남은 힘은 1씩 감소하고, 남은 힘이 0 이하인경우에는 더이상 움직이지 못한다.

        미로의 각 격자에는 장애물이 있는데, 각각의 장애물은 높이 정보를 가지고 있다.
        인성이가 이동할 때, 현재 위치보다 이동할 위치의 높이가 더 낮으면 아무런 제약을 갖지 않고 이동할 수 있다.
        더 높은 곳으로 이동할 때는 점프를 할 수 있는데 점프해야 하는 높이 = 이동할 곳의 높이 - 현재 위치한 곳의 높이 이다.
        이때 남아있는 힘이 점프해야 하는 높이보다 크거나 같으면 이동 가능


     */

    static int  T;
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};



    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/sindongjun/Desktop/러닝클럽/dongjun/src/PCCP_6주차/인성문제있어/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        T = Integer.parseInt(st.nextToken());

        int H, W, O, F;
        int startX, startY, endX, endY;
        int[][] map;


        loop1:
        for (int t = 0; t < T; t++) {
            st = new StringTokenizer(br.readLine());

            H = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            O = Integer.parseInt(st.nextToken());
            F = Integer.parseInt(st.nextToken());
            startX = Integer.parseInt(st.nextToken());
            startY = Integer.parseInt(st.nextToken());
            endX = Integer.parseInt(st.nextToken());
            endY = Integer.parseInt(st.nextToken());

            map = new int[H + 1][W + 1];


            for (int o = 0; o < O; o++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int f = Integer.parseInt(st.nextToken());

                map[x][y] = f;
            }

            int[][] maxForce = new int[H + 1][W + 1];
            for (int i = 0; i <= H; i++) {
                Arrays.fill(maxForce[i], -1); // -1로 초기화하여 방문하지 않았음을 표시
            }


            Queue<Person> queue = new LinkedList<>();

            queue.add(new Person(startX, startY, F));

            maxForce[startX][startY] = F;

            while (!queue.isEmpty()) {
                Person now = queue.poll();

                if (now.x == endX && now.y == endY) {
                    sb.append("잘했어!!\n");
                    continue loop1;
                }

                if(now.f < maxForce[now.x][now.y]){
                    continue;
                }

                for (int i = 0; i < 4; i++) {
                    int nx = now.x + dx[i];
                    int ny = now.y + dy[i];
                    int nf = now.f - 1;;

                    if (nx < 1 || nx > H || ny < 1 || ny > W || nf < 0) {
                        continue;
                    }

                    // 내가 있는 곳 보다 가야할 곳이 더 높을 때
                    if (map[now.x][now.y] < map[nx][ny]) {
                        // 내가 가지고 있는 힘으로 오를 수 있나?
                        if (now.f - (map[nx][ny] - map[now.x][now.y]) < 0) {
                            continue;
                        }
                    }

                    if (nf > maxForce[nx][ny]) {
                        maxForce[nx][ny] = nf;
                        queue.add(new Person(nx, ny, nf));
                    }

                }
            }

            sb.append("인성 문제있어??\n");

        }

        System.out.print(sb.toString());
    }

    static class Person {
        int x;
        int y;
        int f;

        public Person(int x, int y, int f) {
            this.x = x;
            this.y = y;
            this.f = f;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "x=" + x +
                    ", y=" + y +
                    ", f=" + f +
                    '}';
        }
    }

    static void printMap(int[][] map) {
        for (int i = 0; i < map.length; i++) {
            System.out.println(Arrays.toString(map[i]));
        }

    }

}
