package controller.user;

import constant.user.LoginPage;
import constant.user.validation.InputValidCheck;
import constant.user.validation.LoginValidCheck;
import domain.user.User;
import exception.user.InvalidUserDataException;
import exception.user.LoginException;
import exception.user.UserNotFoundException;
import exception.user.UserNotRegisteredException;
import exception.user.UserNotUpdatedException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import model.user.LoginDAO;

public class LoginMenu {

    private static class LazyHolder {
        private static final LoginMenu LOGIN_CONTROLLER = new LoginMenu();
    }
    private static final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private static boolean quitLogin;

    private final LoginDAO dao;
    private final InputValidCheck inputValidCheck;
    private final LoginValidCheck loginValidCheck;

    private LoginMenu() {
        this.dao = new LoginDAO();
        this.inputValidCheck = new InputValidCheck();
        this.loginValidCheck = new LoginValidCheck();
    }

    // 컨트롤러에 싱글톤 패턴 적용
    public static LoginMenu getInstance() {
        return LazyHolder.LOGIN_CONTROLLER;
    }

    public void loginMenu() {
        while (!quitLogin) {
            try {
                System.out.print(LoginPage.LOGIN_MENU_TITLE);
                String menuNum = input.readLine();
                loginValidCheck.checkMenuNumber(menuNum);
                switch (menuNum) {
                    case "1" -> login();
                    case "2" -> register();
                    case "3" -> searchID();
                    case "4" -> updatePassword();
                    case "5" -> exitLoginMenu();
                }
            } catch (IllegalArgumentException
                     | IOException | LoginException
                     | InvalidUserDataException | UserNotRegisteredException
                     | UserNotFoundException | UserNotUpdatedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void login() throws IOException, LoginException {
        System.out.println(LoginPage.INPUT_ID);
        String userID = input.readLine();
        System.out.println(LoginPage.INPUT_PWD);
        String userPwd = input.readLine();

        User loginUser = dao.login(userID, userPwd);
        loginValidCheck.checkLoginSuccess(loginUser);
        WMSMenu wmsMenu = new WMSMenu(loginUser);
        wmsMenu.run();
    }

    public void register() throws IOException, InvalidUserDataException, UserNotRegisteredException {
        LoginPage.print(LoginPage.SIGN_UP);
        System.out.print(LoginPage.REGISTER_OR_NOT);
        String yesOrNo = input.readLine();
        if (!yesOrNo.equalsIgnoreCase("Y")) {
            System.out.println(LoginPage.TO_PREVIOUS_MENU);
            return;
        }

        System.out.print(LoginPage.INPUT_MEMBERSHIP_TYPE);
        String type = input.readLine();
        boolean ack = false;
        switch (type) {
            case "1" -> {
                User newMember = inputMemberInfo();
                ack = dao.register(newMember);
            }
            case "2" -> {
                User newManager = inputManagerInfo();
                ack = dao.register(newManager);
            }
        }
        loginValidCheck.checkUserRegistered(ack);
        System.out.println(LoginPage.REGISTER_SUCCESS);
    }

    public User inputMemberInfo() throws IOException, InvalidUserDataException {
        LoginPage.print(LoginPage.MEMBER_REGISTER);

        System.out.println(LoginPage.INPUT_ID);
        String userID = input.readLine();
        System.out.println(LoginPage.INPUT_PWD);
        String userPwd = input.readLine();
        System.out.println(LoginPage.INPUT_COMPANY_NAME);
        String companyName = input.readLine();
        System.out.println(LoginPage.INPUT_PHONE);
        String phone = input.readLine();
        System.out.println(LoginPage.INPUT_EMAIL);
        String email = input.readLine();
        System.out.println(LoginPage.INPUT_COMPANY_CODE);
        String companyCode = input.readLine();
        System.out.println(LoginPage.INPUT_ADDRESS);
        String address = input.readLine();

        User newUser = new User(userID, userPwd, companyName, phone, email, "일반회원");
        newUser.setCompanyCode(companyCode);
        newUser.setAddress(address);

        inputValidCheck.checkMemberData(newUser, false);
        return newUser;
    }

    public User inputManagerInfo() throws IOException, InvalidUserDataException {
        LoginPage.print(LoginPage.MANAGER_REGISTER);
        System.out.println(LoginPage.INPUT_ID);
        String userID = input.readLine();
        System.out.println(LoginPage.INPUT_PWD);
        String userPwd = input.readLine();
        System.out.println(LoginPage.INPUT_NAME);
        String name = input.readLine();
        System.out.println(LoginPage.INPUT_PHONE);
        String phone = input.readLine();
        System.out.println(LoginPage.INPUT_EMAIL);
        String email = input.readLine();
        System.out.println(LoginPage.INPUT_MANAGER_POSITION);

        String option = input.readLine();
        String position = null;
        switch (option) {
            case "1" -> position = "창고관리자";
            case "2" -> position = "총관리자";
        }

        User newUser = new User(userID, userPwd, name, phone, email, position);
        inputValidCheck.checkManagerData(newUser, false);
        return newUser;
    }

    public void searchID() throws IOException {
        LoginPage.print(LoginPage.FIND_ID);
        System.out.println(LoginPage.INPUT_EMAIL);
        String userEmail = input.readLine();

        String foundID = dao.findID(userEmail);
        loginValidCheck.checkIDFound(dao.isExistID(foundID));
        System.out.printf(LoginPage.FOUND_ID.toString(), foundID);
    }

    public void updatePassword() throws IOException {
        LoginPage.print(LoginPage.FIND_PWD);
        System.out.println(LoginPage.INPUT_ID);
        String userID = input.readLine();
        System.out.println(LoginPage.NEW_PASSWORD);
        String newPassword = input.readLine();

        boolean ack = dao.updatePassword(userID, newPassword);
        loginValidCheck.checkPwdUpdated(ack);
        System.out.println(LoginPage.UPDATE_PASSWORD);
    }

    public void exitLoginMenu() {
        quitLogin = true;
        System.out.println(LoginPage.EXIT_LOGIN_MENU);
    }
}
