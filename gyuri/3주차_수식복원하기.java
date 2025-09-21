import java.util.*;

class Solution {

    public String[] solution(String[] expressions) {
        // 1) 파싱
        List<Expression> exprs = new ArrayList<>();
        for (String line : expressions) exprs.add(parse(line));

        // 2) 가능한 최소 진법(등장한 최대 자리 + 1, 단 최소 2)
        int minBase = 2;
        for (Expression e : exprs) {
            minBase = Math.max(minBase, minRequiredBase(e.n1));
            minBase = Math.max(minBase, minRequiredBase(e.n2));
            if (!e.isUnknownC) minBase = Math.max(minBase, minRequiredBase(e.n3));
        }

        // 3) 모든 후보 진법 수집 (minBase ~ 9) — 모든 완성식이 성립하는 진법만
        List<Integer> candidates = new ArrayList<>();
        for (int base = Math.max(2, minBase); base <= 9; base++) {
            if (isConsistent(exprs, base)) candidates.add(base);
        }

        // 4) X였던 식만 결과를 만들어 반환 (표기가 후보 진법들에서 모두 같으면 그 문자열, 아니면 "?")
        List<String> outs = new ArrayList<>();
        for (Expression e : exprs) {
            if (!e.isUnknownC) continue;

            String unique = null;
            boolean ambiguous = false;

            for (int base : candidates) {
                int a = parseInBase(e.n1, base);
                int b = parseInBase(e.n2, base);
                int val = (e.op == '+') ? (a + b) : (a - b); // 제약상 음수 없음
                String rep = toBase(val, base);             // 결과를 해당 진법의 "문자열"로

                if (unique == null) unique = rep;
                else if (!unique.equals(rep)) { ambiguous = true; break; }
            }

            String rhs = (ambiguous || unique == null) ? "?" : unique;
            outs.add(e.n1 + " " + e.op + " " + e.n2 + " = " + rhs);
        }

        return outs.toArray(new String[0]);
    }

    // ===== 내부 구조체 =====
    static class Expression {
        String n1, n2, n3;  // A, B, C
        char op;            // '+' 또는 '-'
        boolean isUnknownC; // C == "X"
    }

    // "A op B = C" 파싱 (형식 보장)
    private Expression parse(String s) {
        StringTokenizer st = new StringTokenizer(s);
        Expression e = new Expression();
        e.n1 = st.nextToken();
        e.op = st.nextToken().charAt(0);
        e.n2 = st.nextToken();
        st.nextToken();            // "=" 소비
        e.n3 = st.nextToken();
        e.isUnknownC = e.n3.equals("X");
        return e;
    }

    // 숫자 문자열이 요구하는 최소 진법(= 최대 자리 + 1). "X"는 2로 취급
    private int minRequiredBase(String num) {
        if (num.equals("X")) return 2;
        int maxDigit = 0;
        for (int i = 0; i < num.length(); i++) {
            int d = num.charAt(i) - '0';
            if (d > maxDigit) maxDigit = d;
        }
        return Math.max(2, maxDigit + 1);
    }

    // 주어진 base에서 모든 완성식이 성립하는지 (자리 유효성 + 값 동일)
    private boolean isConsistent(List<Expression> exprs, int base) {
        for (Expression e : exprs) {
            if (!isValidInBase(e.n1, base)) return false;
            if (!isValidInBase(e.n2, base)) return false;
            if (!e.isUnknownC && !isValidInBase(e.n3, base)) return false;

            if (!e.isUnknownC) {
                int a = parseInBase(e.n1, base);
                int b = parseInBase(e.n2, base);
                int c = parseInBase(e.n3, base);
                if (e.op == '+') {
                    if (a + b != c) return false;
                } else {
                    if (a - b != c) return false;
                }
            }
        }
        return true;
    }

    // base 진법에서 숫자 유효성(모든 자리 < base). "X"는 true
    private boolean isValidInBase(String num, int base) {
        if (num.equals("X")) return true;
        for (int i = 0; i < num.length(); i++) {
            int d = num.charAt(i) - '0';
            if (d >= base) return false;
        }
        return true;
    }

    // base 진법 문자열 -> 십진수 int
    private int parseInBase(String num, int base) {
        int val = 0;
        for (int i = 0; i < num.length(); i++) {
            val = val * base + (num.charAt(i) - '0');
        }
        return val;
    }

    // 십진수 -> base 진법 문자열
    private String toBase(int n, int base) {
        if (n == 0) return "0";
        StringBuilder sb = new StringBuilder();
        int x = n;
        while (x > 0) {
            int r = x % base;
            sb.append((char)('0' + r));
            x /= base;
        }
        return sb.reverse().toString();
    }
}
