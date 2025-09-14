import java.util.*;
import java.io.*;

class Solution {
    static int studentNum, gameNum;
    static boolean[] isSelected;
    static int[] student;
    static int MAX = 0;

    public int solution(int[][] ability) {
        studentNum = ability.length;
        gameNum = ability[0].length;

        isSelected = new boolean[studentNum];
        student = new int[gameNum];

        /*
        한 학생은 최대 한개의 종목 대표만 가능
        반 학생 5명, 종목 개수 3
        종목 대표의 해당 종목에 대한 능력치의 합을 최대화

        [해결방법]
        숫자가 별로 크지 않으므로 순열로 풀자 !
        */

        //테니스 - 탁구 - 수영 순서

        permutation(0, ability);

        return MAX;
    }

    public void permutation(int dep, int[][] ability) {
        if(dep == gameNum) {
            MAX = Math.max(MAX, getSum(ability));
            return;
        }

        for(int i=0; i<studentNum; i++) {
            if(isSelected[i]) continue;

            isSelected[i] = true;

            student[dep] = i;
            permutation(dep+1, ability);

            isSelected[i] = false;
        }
    }

    public int getSum(int[][] ability) {
        int sum = 0;
        int idx = 0;

        for(int s : student) {
            sum += ability[s][idx];
            idx++;
        }
        return sum;
    }
}