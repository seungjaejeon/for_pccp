package PCCP_3주차.수식_복원하기;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(new String[]{"14 + 3 = 17", "13 - 6 = X", "51 - 5 = 44"})));
    }

    static public String[] solution(String[] expressions) {

        /*
        덧셈 혹은 빽셈 수식이 여러 개 적힌 고대 문명의 유물을 찾았다
        이 수식들을 관찰하던 당신은 이 문명이 사용하던 진법 체계가 10진법이 아니라는 것을 알아냈다
        (2~9)진법 중 하나
        수식들 중 몇 개의 수식은 결괏값이 지워져 있으며, 당신은 이 문명이 사용하던 진법에 맞도록 지워진 결괏값을
        채워넣으려고 한다
         */


        int minBase = 2;
        for (String expr : expressions) {
            for (char c : expr.toCharArray()) {
                if (Character.isDigit(c)) {
                    minBase = Math.max(minBase, Character.getNumericValue(c) + 1);
                }
            }
        }

        Set<Integer> possibleBases = new HashSet<>();
        for (int base = minBase; base <= 9; base++) {
            if (isBaseValidForAllKnownExpressions(expressions, base)) {
                possibleBases.add(base);
            }
        }

        List<String> answer = new ArrayList<>();
        for (String expr : expressions) {
            if (expr.contains("X")) {
                answer.add(solveUnknownExpression(expr, possibleBases));
            }
        }

        return answer.toArray(new String[0]);
    }

    static private String solveUnknownExpression(String expr, Set<Integer> possibleBases) {
        String[] parts = expr.split(" ");
        String exprWithoutResult = parts[0] + " " + parts[1] + " " + parts[2] + " = ";

        Set<String> resultSet = new HashSet<>();
        for (int base : possibleBases) {
            long a = Long.parseLong(parts[0], base);
            long b = Long.parseLong(parts[2], base);
            long decimalResult;

            if (parts[1].equals("+")) {
                decimalResult = a + b;
            } else {
                decimalResult = a - b;
            }
            resultSet.add(Long.toString(decimalResult, base));
        }

        if (resultSet.size() == 1) {
            return exprWithoutResult + resultSet.iterator().next();
        } else {
            return exprWithoutResult + "?";
        }
    }

    private static boolean isBaseValidForAllKnownExpressions(String[] expressions, int base) {
        for (String expr : expressions) {
            if (expr.contains("X")) {
                continue;
            }
            try {
                String[] parts = expr.split(" ");
                long a = Long.parseLong(parts[0], base);
                long b = Long.parseLong(parts[2], base);
                long c = Long.parseLong(parts[4], base);

                if (parts[1].equals("+")) {
                    if (a + b != c) return false;
                } else {
                    if (a - b != c) return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }
}
