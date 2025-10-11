package handler.support.cs.csServiceImpl;

import constant.support.BoardErrorCode;
import constant.support.BoardText;
import constant.support.ValidCheck;
import exception.support.InputException;
import handler.support.cs.CSOption;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CSOptionImpl implements CSOption {
    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    // 뒤로가기 옵션
    public void backOption () {
        while (true) {
            System.out.println(BoardText.LINE.getMessage());

            System.out.println(BoardText.BACK_OPTION.getMessage());

            try {
                String y = input.readLine();
                ValidCheck.yCheck(y);
                break;
            } catch (InputException e) {
                System.out.println(e.getMessage());;
            } catch (IOException e){
                System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
            }
        }
    }

    // 예, 아니오
    public boolean yesOrNo(Character c) {
        return switch (c) {
            case 'y', 'Y' -> true;
            case 'n', 'N' -> false;
            default -> false;
        };
    }
}
