package controller.user;

import constant.user.WMSMessage;
import domain.user.Manager;
import domain.user.Member;
import domain.user.User;
import model.user.LoginDAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WMSMenu {

    private static final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private static boolean quitWMS;

    private MemberMenu memberMenu;

    private final User currentLoginUser;

    public WMSMenu(User loginUser) {
        this.currentLoginUser = loginUser;
    }

    public void run() {
        quitWMS = false;
        if (currentLoginUser instanceof Member member) {
            memberMenu(member);
        } else if (currentLoginUser instanceof Manager manager) {
            managerMenu(manager);
        }
    }

    public void memberMenu(Member member) {
        while (!quitWMS) {
            try {
                System.out.print(WMSMessage.MEMBER_MENU_TITLE);
                String menuNum = input.readLine();
                switch (menuNum) {
                    case "1":   // 회원관리
                        memberMenu = new MemberMenu(member);
                        memberMenu.run();
                        break;
                    case "2":   // 고객센터
                        break;
                    case "3":   // 재고관리
                        break;
                    case "4":   // 입고
                        break;
                    case "5":   // 출고
                        break;
                    case "6":   // 로그아웃
                        logout(member.getId());
                        break;
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void managerMenu(Manager manager) {
        // 관리자 전용 기능이 존재하여 memberMenu(), managerMenu()를 구분
        while (!quitWMS) {
            try {
                System.out.print(WMSMessage.MANAGER_MENU_TITLE);
                String menuNum = input.readLine();
                switch (menuNum) {
                    case "1":   // 회원관리
                        memberMenu = new MemberMenu(manager);
                        memberMenu.run();
                        break;
                    case "2":   // 고객센터
                        break;
                    case "3":   // 창고관리
                        break;
                    case "4":   // 재고관리
                        break;
                    case "5":   // 입고
                        break;
                    case "6":   // 출고
                        break;
                    case "7":   // 로그아웃
                        logout(manager.getId());
                        break;
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void logout(String userID) {
        LoginDAO.logout(userID);
        quitWMS = true;
    }
}
