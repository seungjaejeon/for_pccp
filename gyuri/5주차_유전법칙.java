import java.util.*;

class Solution {
  static int n, p;

  public String[] solution(int[][] queries) {
    String[] answer = new String[queries.length];

    for(int t=0; t<queries.length; t++) {
      n = queries[t][0];
      p = queries[t][1] - 1;

      String parent = "Rr";
      Stack<Integer> stack = new Stack<>();

      for(int i=0; i<n-1; i++) {
        stack.add(p % 4);
        p /= 4;
      }

      for(int i=0; i<n-1; i++) {
        int num = stack.pop();

        if(parent.equals("Rr")) {
          if(num == 0) parent = "RR";
          else if (num == 3) parent = "rr";
          else parent = "Rr";
        }
        else break; //부모가 Rr이 아니면 자식들은 다 부모를 따름
      }
      answer[t] = parent;
    }
    return answer;
  }
}