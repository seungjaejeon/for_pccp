package PCCP_6주차.충돌위험찾기;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println(solution(new int[][] {{3, 2}, {6, 4}, {4, 7}, {1, 4}}, new int[][] {{4, 2}, {1, 3}, {2, 4}}));
        System.out.println(solution(new int[][] {{3, 2}, {6, 4}, {4, 7}, {1, 4}}, new int[][] {{4, 2}, {1, 3}, {4, 2}, {4, 3}}));
        System.out.println(solution(new int[][] {{2, 2}, {2, 3}, {2, 7}, {6, 6}, {5, 2}}, new int[][] {{2, 3, 4, 5}, {1, 3, 4, 5}}));
    }

    static int[][] map = new int[101][101];

    public static class Robot {
        int x;
        int y;
        int[] nextPoints;
        int nextPointIndex;
        boolean finish;

        @Override
        public String toString() {
            return "Robot{" +
                    "x=" + x +
                    ", y=" + y +
                    ", nextPoints=" + Arrays.toString(nextPoints) +
                    ", nextPointIndex=" + nextPointIndex +
                    ", finish=" + finish +
                    '}';
        }

        public Robot(int x, int y, int[] nextPoints) {
            this.x = x;
            this.y = y;
            this.nextPoints = nextPoints;
            nextPointIndex = 1;
            finish = false;
        }

    }

    public static int solution(int[][] points, int[][] routes) {
        int answer = 0;

        /*
            물류센터에는 (r,c) 2차원 좌쵸로 나타낼수있는 n개의 포인트
            각 포인트는 1 ~ n 까지 번호 매겨짐
            로봇마다 정해진 운송 경로가 존재. m개 포인트로 구성되고 로봇은 첫 포인트에서 시작해 할당된 포인트를 순서대로 방문
            운송 시스템에 사용되는 로봇은 x대이고, 모든 로봇은 0초에 동시 출발
            로봇은 1초마다 r 좌표와 c 좌표와 c좌표 중 하나가 1만큼 감소하거나 증가한 좌표로 이동
            다음포인트로 이동할 때는 항상 최단 경로로 이동하며, 최단 경로가 여러 가지일 경우 r좌표가 변하는 이동을 c 좌표가 변하는 이동보다 먼저
            마지막 포인트에 도착한 로봇은 그 즉시 운송을 종료
            같은 좌표에 로봇이 2대 이상 모인다면 충돌 가능성있다
            로봇이 움직일 때 위험한 상황이 총 몇 번 일어나는지 알고 싶다
            만약 어떤 시간에 여러 좌표에서 위험 상황이 발생한다면 그 횟수를 모두 더한다
         */

        for (int i = 0; i < points.length; i++) {
            map[points[i][0]][points[i][1]] = i + 1;
        }

        ArrayList<Robot> robots = new ArrayList<>();

        for (int[] route : routes) {
            int startPoint = route[0] - 1;

            int startX = points[startPoint][0];
            int startY = points[startPoint][1];

            robots.add(new Robot(startX, startY, route));
        }

        // 맨 처음 같이 있는 부분 체크
        int[][] mapCheckTemp = new int[101][101];

        for (Robot robot : robots) {
            if(!robot.finish){
                mapCheckTemp[robot.x][robot.y]++;

                if (robot.nextPointIndex >= robot.nextPoints.length) {
                    robot.finish = true;
                }
            }


        }

        for (int i = 0; i < 101; i++) {
            for (int j = 0; j < 101; j++) {
                if (mapCheckTemp[i][j] > 1) {
                    answer++;
                }
            }
        }

        while (!finishCheck(robots)) {
            //System.out.println();
            for (Robot robot : robots) {
                //System.out.println(robot);
                if(!robot.finish) {
                    int nextPoint = robot.nextPoints[robot.nextPointIndex];
                    int nx = points[nextPoint - 1][0];
                    int ny = points[nextPoint - 1][1];


                    // x 부터 움직이기
                    if (robot.x > nx) {
                        robot.x = robot.x - 1;
                    } else if (robot.x < nx) {
                        robot.x = robot.x + 1;
                    } else {
                        // x가 맞춰지면 y 움직이기
                        if (robot.y > ny) {
                            robot.y = robot.y - 1;
                        } else if (robot.y < ny) {
                            robot.y = robot.y + 1;
                        }
                    }

                    // x,y 움직인 후 도착 지점 체크
                    if (robot.x == nx && robot.y == ny) {
                        robot.nextPointIndex = robot.nextPointIndex + 1;
                    }
                }
            }

            int[][] mapCheck = new int[101][101];

            for (Robot robot : robots) {
                if(!robot.finish){
                    mapCheck[robot.x][robot.y]++;

                    if (robot.nextPointIndex >= robot.nextPoints.length) {
                        robot.finish = true;
                    }
                }


            }

            for (int i = 0; i < 101; i++) {
                for (int j = 0; j < 101; j++) {
                    if (mapCheck[i][j] > 1) {
                        answer++;
                    }
                }
            }
        }







        return answer;
    }

    public static boolean finishCheck(ArrayList<Robot> robots) {
        for (Robot robot : robots) {
            if (robot.finish == false) {
                return false;
            }
        }
        return true;
    }
}
