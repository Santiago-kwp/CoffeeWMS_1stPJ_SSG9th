package controller.user;

import constant.user.UserPage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public interface UserManageMenu {

    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    default boolean run() {
        boolean quitMenu = false;
        boolean hasLogout = false;

        while (!quitMenu && !hasLogout) {
            try {
                printMenu();
                String menuNum = input.readLine();
                switch (menuNum) {
                    case "1" -> read();
                    case "2" -> update();
                    case "3" -> hasLogout = delete();
                    case "4" -> quitMenu = exitMenu();
                }
            } catch (IOException e) {
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
