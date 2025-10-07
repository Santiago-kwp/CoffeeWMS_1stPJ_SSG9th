package controller.user;

import constant.user.WMSPage;
import controller.inventory.InventoryController;
import controller.support.CSMenu;
import domain.inventory.UserVO;
import controller.cargo.CargoController;
import constant.user.validation.MenuNumberValidCheck;
import controller.transaction.InboundMenu;
import controller.transaction.OutboundMenu;
import domain.user.Manager;
import domain.user.Member;
import domain.user.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import model.user.ManagerDAO;
import model.user.MemberDAO;
import service.inventory.UserService;
import java.sql.SQLException;
import service.user.LogoutService;
import service.user.ManagerServiceImpl;
import service.user.MemberServiceImpl;

public class WMSMenu {

    private static final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    private final User currentLoginUser;
    private final MenuNumberValidCheck validCheck;
    private final LogoutService logoutService;

    private boolean quitWMS;
    private UserManageMenu userManageMenu;
    private CSMenu csMenu = new CSMenu();
    private InboundMenu inboundMenu = new InboundMenu();
    private OutboundMenu outboundMenu = new OutboundMenu();
    private CargoController cargoController;

    WMSMenu(User loginUser, LogoutService logoutService) {
        this.currentLoginUser = loginUser;
        this.validCheck = new MenuNumberValidCheck();
        this.logoutService = logoutService;
    }

    void run() {
        quitWMS = false;
        while (!quitWMS) {
            try {
                if (currentLoginUser instanceof Member member) {
                    memberMenuList(member);
                } else if (currentLoginUser instanceof Manager manager) {
                    managerMenuList(manager);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    void memberMenuList(Member member) throws IOException {
        System.out.print(WMSPage.MEMBER_MENU_TITLE);
        String menuNum = input.readLine();
        validCheck.checkMenuNumber("^[1-6]", menuNum);
        switch (menuNum) {
            case "1":   // 회원관리
                userManagement(member);
                break;
            case "2":   // 고객센터
                csMenu.memberCSMenu(member);
                break;
            case "3":   // 재고관리
                UserService userService = new UserService();
                UserVO loggedInUser = userService.login(member.getId());
                InventoryController.getInstance().inventoryMainMenu(loggedInUser);
                break;
            case "4":   // 입고
                inboundMenu.menuMember(member.getId());
                break;
            case "5":   // 출고
                outboundMenu.menuMember(member.getId());
                break;
            case "6":   // 로그아웃
                logout(member.getId());
                break;
        }
    }

    void managerMenuList(Manager manager) throws IOException {
        // 창고관리 기능은 관리자 전용 기능이므로, memberMenu(), managerMenu()를 구분
        System.out.print(WMSPage.MANAGER_MENU_TITLE);
        String menuNum = input.readLine();
        validCheck.checkMenuNumber("^[1-7]", menuNum);
        switch (menuNum) {
            case "1":   // 회원관리
                userManagement(manager);
                break;
            case "2":   // 고객센터
                csMenu.managerCSMenu(manager);
                break;
            case "3":   // 창고관리
                cargoConnect(manager);
                break;
            case "4":   // 재고관리
                UserService userService = new UserService();
                UserVO loggedInUser = userService.login(manager.getId());
                InventoryController.getInstance().inventoryMainMenu(loggedInUser);
                break;
            case "5":   // 입고
                inboundMenu.menuManager(manager.getId());
                break;
            case "6":   // 출고
                outboundMenu.menuManager(manager.getId());
                break;
            case "7":   // 로그아웃
                logout(manager.getId());
                break;
        }
    }

    private void userManagement(User user) {
        // 회원 탈퇴 시 자동 종료
        if (user instanceof Manager manager) {
            userManageMenu = new ManagerManageMenu(manager, new ManagerServiceImpl(ManagerDAO.getInstance()));
            quitWMS = userManageMenu.run();
        } else if (user instanceof Member member) {
            userManageMenu = new MemberManageMenu(member, new MemberServiceImpl(MemberDAO.getInstance()));
            quitWMS = userManageMenu.run();
        }
    }

    // 각자 담당하신 기능에 관한 컨트롤러를 실행하는 메서드를 여기서부터 작성해주시면 됩니다.

    private void cargoConnect(Manager manager) {
       try {
           cargoController = new CargoController(manager);
           cargoController.start();
       }catch (SQLException e){
           System.out.println(e.getMessage());
       }
    }

    private void logout(String userID) {
        quitWMS = true;
        String logoutSuccess = logoutService.logout(userID);
        if (logoutSuccess != null) {
            System.out.println(logoutSuccess);
        }
    }
}
