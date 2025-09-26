package controller.user;

import constant.user.LoginPage;
import domain.user.User;
import exception.user.IDNotFoundException;
import exception.user.LoginException;
import exception.user.NotRegisteredUserException;
import exception.user.NotUpdatedPassword;
import model.user.LoginDAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginMenu {

    private static class LazyHolder {
        private static final LoginMenu LOGIN_CONTROLLER = new LoginMenu();
    }
    private static final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private static boolean quitLogin;

    private final LoginDAO dao;

    private LoginMenu() {
        dao = new LoginDAO();
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
                switch (menuNum) {
                    case "1" -> login();
                    case "2" -> register();
                    case "3" -> findID();
                    case "4" -> updatePassword();
                    case "5" -> exitLoginMenu();
                }
            } catch (IOException | LoginException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void login() throws IOException {
        System.out.println(LoginPage.INPUT_ID);
        String userID = input.readLine();
        System.out.println(LoginPage.INPUT_PWD);
        String userPwd = input.readLine();

        User loginUser = dao.login(userID, userPwd);
        if (loginUser == null) {
            throw new LoginException(LoginPage.CANNOT_LOGIN.toString());
        }
        WMSMenu wmsMenu = new WMSMenu(loginUser);
        wmsMenu.run();
    }

    public void register() throws IOException {
        LoginPage.print(LoginPage.SIGN_UP);
        System.out.print(LoginPage.INPUT_MEMBERSHIP_TYPE);
        String type = input.readLine();

        boolean ack = false;
        switch (type) {
            case "1":
                User newMember = inputMemberInfo();
                ack = dao.register(newMember);
                break;
            case "2":
                User newManager = inputManagerInfo();
                ack = dao.register(newManager);
                break;
        }

        if (!ack) {
            throw new NotRegisteredUserException(LoginPage.REGISTER_FAILED.toString());
        }
        System.out.println(LoginPage.REGISTER_SUCCESS);
    }

    public User inputMemberInfo() throws IOException {
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
        return newUser;
    }

    public User inputManagerInfo() throws IOException {
        LoginPage.print(LoginPage.MEMBER_REGISTER);
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
        String position = input.readLine();

        return new User(userID, userPwd, name, phone, email, position);
    }

    public void findID() throws IOException {
        LoginPage.print(LoginPage.FIND_ID);
        System.out.println(LoginPage.INPUT_EMAIL);
        String userEmail = input.readLine();
        String foundID = dao.findID(userEmail);

        if (foundID == null) {
            throw new IDNotFoundException(LoginPage.NOT_FOUND_ID.toString());
        }
        System.out.printf(LoginPage.FOUND_ID.toString(), foundID);
    }

    public void updatePassword() throws IOException {
        LoginPage.print(LoginPage.FIND_PWD);
        System.out.println(LoginPage.INPUT_ID);
        String userID = input.readLine();

        if (!dao.isExistID(userID)) {
            throw new IDNotFoundException(LoginPage.USER_NOT_EXIST.toString());
        }
        System.out.println(LoginPage.NEW_PASSWORD);
        String newPassword = input.readLine();

        boolean ack = dao.updatePassword(userID, newPassword);
        if (!ack) {
            throw new NotUpdatedPassword(LoginPage.NOT_UPDATE_PASSWORD.toString());
        }
        System.out.println(LoginPage.UPDATE_PASSWORD);
    }

    public void exitLoginMenu() {
        quitLogin = true;
        System.out.println(LoginPage.EXIT_LOGIN_MENU);
    }
}
