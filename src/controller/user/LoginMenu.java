package controller.user;

import constant.user.InputMessage;
import constant.user.LoginPage;
import constant.user.validation.InputValidCheck;
import constant.user.validation.MenuNumberValidCheck;
import domain.user.User;
import exception.user.InvalidUserDataException;
import exception.user.LoginException;
import exception.user.UserNotFoundException;
import exception.user.FailedToUserRegisterException;
import exception.user.FailedToUserUpdateException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import model.user.LoginDAO;
import service.user.LoginService;
import service.user.LoginServiceImpl;
import view.user.ConsoleView;

public class LoginMenu {

    private static class LazyHolder {
        private static final LoginMenu LOGIN_CONTROLLER = new LoginMenu();
    }
    private static boolean quitLogin;

    private final ConsoleView consoleView = new ConsoleView(new BufferedReader(new InputStreamReader(System.in)));
    private final LoginService loginService;
    private final MenuNumberValidCheck menuNumberCheck;

    private LoginMenu() {
        this.loginService = new LoginServiceImpl(new LoginDAO());
        this.menuNumberCheck = new MenuNumberValidCheck();
    }

    // 컨트롤러에 싱글톤 패턴 적용
    public static LoginMenu getInstance() {
        return LazyHolder.LOGIN_CONTROLLER;
    }

    public void loginMenu() {
        while (!quitLogin) {
            try {
                String menuNum = consoleView.promptAndRead(LoginPage.LOGIN_MENU_TITLE.toString());
                menuNumberCheck.checkMenuNumber("^[1-5]", menuNum);
                switch (menuNum) {
                    case "1" -> login();
                    case "2" -> register();
                    case "3" -> searchID();
                    case "4" -> updatePassword();
                    case "5" -> exitLoginMenu();
                }
            } catch (IllegalArgumentException
                     | IOException | LoginException
                     | InvalidUserDataException | FailedToUserRegisterException
                     | UserNotFoundException | FailedToUserUpdateException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void login() throws IOException, LoginException {
        String userID = consoleView.promptAndRead(InputMessage.INPUT_ID.toString());
        String userPwd = consoleView.promptAndRead(InputMessage.INPUT_PWD.toString());

        User loginUser = loginService.login(userID, userPwd);

        WMSMenu wmsMenu = new WMSMenu(loginUser, (LoginServiceImpl)loginService);
        wmsMenu.run();
    }

    public void register() throws IOException, InvalidUserDataException, FailedToUserRegisterException {
        LoginPage.print(LoginPage.SIGN_UP);
        if (consoleView.checkCancel(LoginPage.REGISTER_OR_NOT.toString(), LoginPage.TO_PREVIOUS_MENU.toString())) {
            return;
        }
        String typeOption = consoleView.promptAndRead(LoginPage.INPUT_REGISTER_TYPE.toString());
        menuNumberCheck.checkMenuNumber("^[1-2]", typeOption);
        User userToRegister = null;
        switch (typeOption) {
            case "1" -> userToRegister = consoleView.inputMemberInfo(false);
            case "2" -> userToRegister = consoleView.inputManagerInfo(false);
        }

        loginService.register(userToRegister);

        System.out.println(LoginPage.REGISTER_SUCCESS);
    }

    public void searchID() throws IOException {
        LoginPage.print(LoginPage.FIND_ID);
        String userEmail = consoleView.promptAndRead(InputMessage.INPUT_EMAIL.toString());

        String foundID = loginService.findIdByEmail(userEmail);

        System.out.printf(LoginPage.FOUND_ID.toString(), foundID);
    }

    public void updatePassword() throws IOException {
        LoginPage.print(LoginPage.FIND_PWD);
        String userID = consoleView.promptAndRead(InputMessage.INPUT_ID.toString());
        String newPassword = consoleView.promptAndRead(InputMessage.INPUT_NEW_PASSWORD.toString());
        InputValidCheck.checkPwd(newPassword);

        loginService.updatePassword(userID, newPassword);

        System.out.println(LoginPage.UPDATE_PASSWORD);
    }

    public void exitLoginMenu() {
        quitLogin = true;
        System.out.println(LoginPage.EXIT_LOGIN_MENU);
    }
}
