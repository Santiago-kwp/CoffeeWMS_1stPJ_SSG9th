package controller.user;

import constant.user.UserPage;
import constant.user.validation.UserManagementValidCheck;
import exception.user.InvalidUserDataException;
import exception.user.UnableToReadUserException;
import exception.user.UserDeleteFailedException;
import exception.user.FailedToUserUpdateException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import view.user.ConsoleView;

public interface UserManageMenu {

    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    UserManagementValidCheck validCheck = new UserManagementValidCheck();
    ConsoleView consoleView = new ConsoleView(input);

    default boolean run() {
        boolean quitMenu = false;
        boolean hasLogout = false;

        while (!quitMenu && !hasLogout) {
            try {
                printMenu();
                String menuNum = input.readLine();
                validCheck.checkMenuNumber("^[1-4]", menuNum);
                switch (menuNum) {
                    case "1" -> read();
                    case "2" -> update();
                    case "3" -> hasLogout = delete();
                    case "4" -> quitMenu = exitMenu();
                }
            } catch (IOException
                     | IllegalArgumentException
                     | InvalidUserDataException
                     | UnableToReadUserException
                     | FailedToUserUpdateException
                     | UserDeleteFailedException e) {
                System.out.println(e.getMessage());
            }
        }
        return hasLogout;
    }

    default boolean exitMenu() {
        boolean quitMenu = true;
        System.out.println(UserPage.TO_PREVIOUS_MENU);
        return quitMenu;
    }

    boolean delete() throws IOException;

    void update() throws IOException;

    void read() throws IOException;

    void printMenu();
}
