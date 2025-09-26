package view;

import controller.MainMenu;

public class Main {

    // 총관리자 wmsAdmin | admin1234
    // 창고관리자 manager2256 | manager1342
    // 일반회원 usera003 | userpass03
    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.mainMenu();
    }
}
