package 카카오준비.이진탐색.나무_자르기;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int M;
    static int[] trees;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/sindongjun/Desktop/러닝클럽/dongjun/src/카카오준비/이진탐색/나무_자르기/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        /*
            높이 H를 지정
            높이를 지정하면 톱날이 땅으로부터 H미터 위로 올라간다
            그 다음, 한 줄에 연속해있는 나무를 모두 절단해버린다.
            따라서 높이가 H보다 큰 나무는 H위의 부분이 잘릴 것이고, 낮은 나무는 잘리지 않을 것이다.

            첫째줄에 나무의 수 N과 상근이가 집으로 가져가려고 하는 나무의 길이 M

         */

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        trees = new int[N];


        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            trees[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(binarySearch(0, Long.MAX_VALUE));

    }

    public static long binarySearch(long start, long end) {
        long result = 0; // 최종 결과를 저장할 변수

        while (start <= end) {
            long mid = (start + end) / 2;
            long totalCut = cutTrees(mid);

            // 잘라낸 나무의 양이 목표(M)보다 크거나 같으면,
            // 현재 높이(mid)는 가능한 답이므로 저장하고 더 높은 범위 탐색
            if (totalCut >= M) {
                result = mid;
                start = mid + 1;
            }
            // 잘라낸 나무의 양이 목표(M)보다 작으면, 높이를 낮춰야 함
            else {
                end = mid - 1;
            }
        }
        return result;
    }

    public static long cutTrees(long height) {
        long sum = 0;

        for (int i = 0; i < N; i++) {
            if (trees[i] - height > 0) sum += trees[i] - height;
        }

        return sum;
    }
}
