package handler.support.inputHandler;

import constant.support.BoardErrorCode;
import constant.support.ValidCheck;
import exception.support.InputException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputHandlerImpl implements InputHandler {
    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    public String twoMenuChoice() {
        try {
            String choice = input.readLine();
            ValidCheck.isTwoMenuValid(choice);
            return choice;
        } catch (InputException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
        }
        return null;
    }

    public String threeMenuChoice() {
        try {
            String choice = input.readLine();
            ValidCheck.isThreeMenuValid(choice);
            return choice;
        } catch (InputException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
        }
        return null;
    }


    public String fourMenuChoice() {
        try {
            String choice = input.readLine();
            ValidCheck.isFourMenuValid(choice);
            return choice;
        } catch (InputException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
        }
        return null;
    }

    public int choiceBoard() {
        try {
            int readChoice = Integer.parseInt(input.readLine());
            return readChoice;
        } catch (IOException e) {
            System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
        } catch (NumberFormatException e) {
            System.out.println(BoardErrorCode.NOT_INPUT_NUMBER.getMessage());
        }
        return 0;
    }
}
