package view.transaction;
/*
BufferedReader`를 사용하여 사용자 입력을 받는 역할을 전담합니다.

 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputHandler {
    // BufferedReader는 한 번만 생성하여 재사용하는 것이 효율적입니다.
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * 사용자에게 메시지를 출력하고 한 줄의 입력을 받아 문자열로 반환합니다.
     * @param prompt 사용자에게 보여줄 안내 메시지
     * @return 사용자가 입력한 문자열
     * @throws IOException 입력 중 오류 발생 시
     */
    public static String readInput(String prompt) throws IOException {
        System.out.print(prompt);
        return reader.readLine();
    }
}