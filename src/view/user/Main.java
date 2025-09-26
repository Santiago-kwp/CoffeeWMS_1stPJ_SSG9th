package view.user;

import controller.user.LoginMenu;

public class Main {

    public static void main(String[] args) {
        LoginMenu loginMenu = LoginMenu.getInstance();
        loginMenu.loginMenu();
    }
}
