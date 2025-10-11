package 카카오준비.DFS.단어_변환;

public class Main {
    public static void main(String[] args) {
        System.out.println(solution("hit", "cog", new String[]{"hot", "dot", "dog", "lot", "log", "cog"}));
        System.out.println(solution("hit", "cog", new String[]{"hot", "dot", "dog", "lot", "log"}));
    }

    static boolean[] visited;
    static int answer;

    public static int solution(String begin, String target, String[] words) {
        /*
            begin, target과 단어의 집합 words가 있다.
                1. 한 번에 한 개의 알파백만 바꿀 수 있다.
                2. words에 있는 단어로만 변환할 수 있다

         */



        visited = new boolean[words.length];
        answer = Integer.MAX_VALUE;

        for (int i = 0; i < words.length; i++) {
            if (canChange(begin, words[i])) {
                dfs(words[i], target, words, i, 1);
            }
        }


        return answer == Integer.MAX_VALUE ? 0 : answer;
    }

    public static void dfs(String str , String target, String[] words, int index, int depth) {
        // 1. 체크인
        visited[index] = true;

        // 2. 목적지인가?
        if (str.equals(target)) {
            answer = Math.min(answer, depth);
        } else {
            // 3. 연결된 곳 순회
            for (int i = 0; i < words.length; i++) {
                // 4. 갈 수 있는가?
                if (!visited[i] && canChange(str, words[i])) {
                    // 5. 간다
                    dfs(words[i], target, words, i, depth + 1);
                }
            }
        }

        // 6. 체크아웃
        visited[index] = false;
    }

    public static boolean canChange(String begin, String target) {

        int cnt = 0;

        for (int i = 0; i < begin.length(); i++) {
            if (begin.charAt(i) != target.charAt(i)) {
                cnt++;
            }
        }

        if (cnt == 1) {
            return true;
        } else {
            return false;
        }
    }


}
