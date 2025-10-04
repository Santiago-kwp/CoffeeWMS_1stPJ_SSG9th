package handler.support.inputHandler;

import java.io.IOException;

public interface InputHandler {
    String twoMenuChoice();

    String threeMenuChoice() throws IOException;

    String fourMenuChoice();

    int choiceBoard();
}
