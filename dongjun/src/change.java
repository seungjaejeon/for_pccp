public class change {


    /**
     * 입력된 문자열을 Java 2차원 배열 선언 형식으로 변환합니다.
     * 예: "[[1,2],[3,4]]" -> "new int[][] {{1,2},{3,4}};"
     *
     * @param arrayString 변환할 배열 형태의 문자열
     * @return Java 배열 선언 형식의 문자열
     */
    public static String convertToJavaArrayDeclaration(String arrayString) {
        if (arrayString == null ) {
            return "잘못된 형식의 문자열입니다.";
        }

        // 1. '[' 문자를 '{' 문자로 모두 변경합니다.
        String replacedString = arrayString.replace('[', '{');

        // 2. ']' 문자를 '}' 문자로 모두 변경합니다.
        replacedString = replacedString.replace(']', '}');

        // 3. "new int[][]"와 세미콜론(;)을 붙여 최종 결과물을 만듭니다.
        return "new int[][] " + replacedString;
    }

    public static void main(String[] args) {
        // 사용자가 제공한 예제 문자열들
        String input1 = "[[\"ICN\", \"JFK\"], [\"HND\", \"IAD\"], [\"JFK\", \"HND\"]]";
        String input2 = "[\"ICN\", \"JFK\", \"HND\", \"IAD\"]";
        String input3 = "[[\"ICN\", \"SFO\"], [\"ICN\", \"ATL\"], [\"SFO\", \"ATL\"], [\"ATL\", \"ICN\"], [\"ATL\",\"SFO\"]]";
        String input4 = "[\"ICN\", \"ATL\", \"ICN\", \"SFO\", \"ATL\", \"SFO\"]";

        // 변환 실행 및 결과 출력
        System.out.println("--- 변환 결과 ---");
        System.out.println(convertToJavaArrayDeclaration(input1));
        System.out.println(convertToJavaArrayDeclaration(input2));
        System.out.println(convertToJavaArrayDeclaration(input3));
        System.out.println(convertToJavaArrayDeclaration(input4));

        // 변수에 바로 할당하는 예제
        int[][] diceResult = new int[][] {{1, 2, 3, 4, 5, 6}, {3, 3, 3, 3, 4, 4}, {1, 3, 3, 4, 4, 4}, {1, 1, 4, 4, 5, 5}};
        System.out.println("\n--- 실제 변수 할당 예제 ---");
        System.out.println("위 코드는 아래와 같이 변수에 할당할 수 있습니다.");
        System.out.println("int[][] diceResult = " + convertToJavaArrayDeclaration(input1));
    }

}
