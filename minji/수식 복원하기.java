import java.util.*;

class Solution {

    // s가 base 진법에서 유효한 숫자인지 확인 (모든 자리 < base)
    private boolean isValidInBase(String s, int base) {
        for (int i = 0; i < s.length(); i++) {
            int d = s.charAt(i) - '0';
            if (d < 0 || d >= base) return false;
        }
        return true;
    }

    // n진수 문자열 s -> 10진수 정수
    private int toDec(String s, int base) {
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            val = val * base + (s.charAt(i) - '0');
        }
        return val;
    }

    // 10진수 val -> n진수 문자열(0 포함)
    private String fromDec(int val, int base) {
        if (val == 0) return "0";
        StringBuilder sb = new StringBuilder();
        while (val > 0) {
            sb.append(val % base);
            val /= base;
        }
        return sb.reverse().toString();
    }

    // 완성된 식이 주어졌을 때, 해당 base에서 성립하는지 확인
    private boolean equationHolds(String A, String op, String B, String C, int base) {
        if (!isValidInBase(A, base) || !isValidInBase(B, base) || !isValidInBase(C, base)) return false;
        int a = toDec(A, base);
        int b = toDec(B, base);
        int c = toDec(C, base);
        int res = op.equals("+") ? a + b : a - b;
        return res == c;
    }

    public String[] solution(String[] expressions) {
        // 초기 후보: 2 ~ 9
        boolean[] cand = new boolean[10];
        Arrays.fill(cand, false);
        for (int b = 2; b <= 9; b++) cand[b] = true;

        // 등장한 최대 숫자 기반 하한(base > maxDigit)
        int maxDigit = 0;

        for (String exp : expressions) {
            String[] tok = exp.split(" "); // A, +/-, B, =, C|X
            String A = tok[0], op = tok[1], B = tok[2], C = tok[4];

            // 숫자 자리 최댓값 갱신 (X는 제외)
            for (String s : new String[]{A, B}) {
                for (int i = 0; i < s.length(); i++) maxDigit = Math.max(maxDigit, s.charAt(i) - '0');
            }
            if (!C.equals("X")) {
                for (int i = 0; i < C.length(); i++) maxDigit = Math.max(maxDigit, C.charAt(i) - '0');
            }

            // 완성된 식이면 후보 진법 필터링
            if (!C.equals("X")) {
                for (int base = 2; base <= 9; base++) {
                    if (cand[base]) {
                        if (!equationHolds(A, op, B, C, base)) cand[base] = false;
                    }
                }
            }
        }

        // maxDigit보다 작거나 같은 진법은 불가 → (maxDigit + 1) ~ 9만 남김
        for (int base = 2; base <= 9; base++) {
            if (base <= maxDigit) cand[base] = false;
        }

        // 후보 목록 구성
        List<Integer> bases = new ArrayList<>();
        for (int b = 2; b <= 9; b++) if (cand[b]) bases.add(b);

        // 이제 X가 있는 식들을 채워서 정답 배열 구성
        List<String> ans = new ArrayList<>();
        for (String exp : expressions) {
            String[] tok = exp.split(" ");
            String A = tok[0], op = tok[1], B = tok[2], C = tok[4];

            if (!C.equals("X")) continue; // X가 아닌 식은 출력 대상 아님

            // 각 후보 진법에서의 결과값들을 계산
            Integer oneResult = null;
            boolean ambiguous = false;

            for (int base : bases) {
                // 피연산자가 해당 base에서 유효해야 계산 가능
                if (!isValidInBase(A, base) || !isValidInBase(B, base)) continue;

                int a = toDec(A, base);
                int b = toDec(B, base);
                int r = op.equals("+") ? a + b : a - b;
                if (r < 0) continue; // 문제 조건상 음수 결과는 주어지지 않지만 안전장치

                String rBase = fromDec(r, base);

                int asIntLikeDigits = 0;
                for (int i = 0; i < rBase.length(); i++) {
                    asIntLikeDigits = asIntLikeDigits * 10 + (rBase.charAt(i) - '0');
                }

                if (oneResult == null) oneResult = asIntLikeDigits;
                else if (!oneResult.equals(asIntLikeDigits)) { ambiguous = true; break; }
            }

            if (oneResult == null) {
                ans.add(A + " " + op + " " + B + " = ?");
            } else if (ambiguous || bases.size() > 1) {
                if (!ambiguous) {
                    ans.add(A + " " + op + " " + B + " = " + oneResult);
                } else {
                    ans.add(A + " " + op + " " + B + " = ?");
                }
            } else {
                // 후보가 1개(base 확정) → 그 진법의 표기로 채움
                int base = bases.get(0);
                int a = toDec(A, base);
                int b = toDec(B, base);
                int r = op.equals("+") ? a + b : a - b;
                ans.add(A + " " + op + " " + B + " = " + fromDec(r, base));
            }
        }

        return ans.toArray(new String[0]);
    }
}
