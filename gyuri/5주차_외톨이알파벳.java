import java.util.*;
import java.io.*;
class Solution {
  public String solution(String input_string) {
    //외톨이 알파벳 : 두개로 나누어져있으면 외톨이
    //
    //선형리스트에 담았다가 하나씩 뺄 때 다른게 나타나면 리스트에 저장
    //넣으려고 봤는데 이미 존재한다면 넣지말고 sb에 저장
    StringBuilder sb = new StringBuilder();
    ArrayList<Character> list = new ArrayList<>();
    TreeSet<Character> set = new TreeSet<>();

    char start = input_string.charAt(0);
    for(int i=1; i<input_string.length(); i++) {
      char ch = input_string.charAt(i);

      if (start == ch) continue;

      if (!list.contains(start)) list.add(start);
      else set.add(start);

      start = ch;
    }
    if(list.contains(start)) set.add(start);

    if(set.size() == 0) return "N";

    for(char ch : set) sb.append(ch);
    return sb.toString();
  }
}