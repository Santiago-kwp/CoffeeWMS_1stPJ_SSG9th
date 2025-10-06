package controller.user;

import constant.user.InputMessage;
import constant.user.LoginPage;
import constant.user.validation.InputValidCheck;
import constant.user.validation.LoginValidCheck;
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
import view.user.InputView;

public class LoginMenu {

    private static class LazyHolder {
        private static final LoginMenu LOGIN_CONTROLLER = new LoginMenu();
    }
    private static boolean quitLogin;

    private final InputView inputView = new InputView(new BufferedReader(new InputStreamReader(System.in)));
    private final LoginDAO dao;
    private final LoginValidCheck loginValidCheck;

    private LoginMenu() {
        this.dao = new LoginDAO();
        this.loginValidCheck = new LoginValidCheck();
    }

    // 컨트롤러에 싱글톤 패턴 적용
    public static LoginMenu getInstance() {
        return LazyHolder.LOGIN_CONTROLLER;
    }

    public void loginMenu() {
        while (!quitLogin) {
            try {
                String menuNum = inputView.promptAndRead(LoginPage.LOGIN_MENU_TITLE.toString());
                loginValidCheck.checkMenuNumber("^[1-5]", menuNum);
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
        String userID = inputView.promptAndRead(InputMessage.INPUT_ID.toString());
        String userPwd = inputView.promptAndRead(InputMessage.INPUT_PWD.toString());

        User loginUser = dao.login(userID, userPwd);
        loginValidCheck.checkLoginSuccess(loginUser);
        WMSMenu wmsMenu = new WMSMenu(loginUser);
        wmsMenu.run();
    }

    public void register() throws IOException, InvalidUserDataException, FailedToUserRegisterException {
        LoginPage.print(LoginPage.SIGN_UP);
        String yesOrNo = inputView.promptAndRead(LoginPage.REGISTER_OR_NOT.toString());
        if (!yesOrNo.equalsIgnoreCase("Y")) {
            System.out.println(LoginPage.TO_PREVIOUS_MENU);
            return;
        }

        String type = inputView.promptAndRead(LoginPage.INPUT_REGISTER_TYPE.toString());
        boolean ack = false;
        switch (type) {
            case "1" -> {
                User newMember = inputView.inputMemberInfo();
                ack = dao.register(newMember);
            }
            case "2" -> {
                User newManager = inputView.inputManagerInfo();
                ack = dao.register(newManager);
            }
        }
        loginValidCheck.checkUserRegistered(ack);
        System.out.println(LoginPage.REGISTER_SUCCESS);
    }

    public void searchID() throws IOException {
        LoginPage.print(LoginPage.FIND_ID);
        String userEmail = inputView.promptAndRead(InputMessage.INPUT_EMAIL.toString());
        String foundID = dao.findID(userEmail);

        loginValidCheck.checkIDFound(foundID);
        System.out.printf(LoginPage.FOUND_ID.toString(), foundID);
    }

    public void updatePassword() throws IOException {
        LoginPage.print(LoginPage.FIND_PWD);
        String userID = inputView.promptAndRead(InputMessage.INPUT_ID.toString());
        String newPassword = inputView.promptAndRead(InputMessage.INPUT_NEW_PASSWORD.toString());
        InputValidCheck.checkPwd(newPassword);

        boolean ack = dao.updatePassword(userID, newPassword);
        loginValidCheck.checkPwdUpdated(ack);
        System.out.println(LoginPage.UPDATE_PASSWORD);
    }

    public void exitLoginMenu() {
        quitLogin = true;
        System.out.println(LoginPage.EXIT_LOGIN_MENU);
    }
}
