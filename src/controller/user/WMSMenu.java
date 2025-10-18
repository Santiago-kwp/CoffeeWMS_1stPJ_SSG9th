package controller.user;

import constant.user.WMSPage;
import controller.inventory.InventoryController;
//import controller.support.CSMenu;
import domain.inventory.UserVO;
import controller.cargo.CargoController;
import constant.user.validation.WMSValidCheck;
import controller.transaction.InboundController;
import domain.user.Manager;
import domain.user.Member;
import domain.user.User;
import exception.support.InputException;
import model.user.LoginDAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import service.inventory.UserService;
import java.sql.SQLException;

public class WMSMenu {

    private static final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    private final User currentLoginUser;
    private final WMSValidCheck validCheck;

    private boolean quitWMS;
    private UserManageMenu userManageMenu;
//    private CSMenu csMenu = new CSMenu();

    private final InboundController inboundMenu; // final로 변경


    private CargoController cargoController;
    public WMSMenu(User loginUser) {
        this.currentLoginUser = loginUser;
        this.validCheck = new WMSValidCheck();
        // 입고 컨트롤러 생성
        this.inboundMenu = new InboundController();

    }

    public void run() {
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

    public void memberMenuList(Member member) throws IOException {
        System.out.print(WMSPage.MEMBER_MENU_TITLE);
        String menuNum = input.readLine();
        validCheck.checkMemberMenu(menuNum);
        switch (menuNum) {
            case "1":   // 회원관리
                userManagement(member);
                break;
            case "2":   // 고객센터
//                csMenu.memberCSMenu(member);
                break;
            case "3":   // 재고관리
                UserService userService = new UserService();
                UserVO loggedInUser = userService.login(member.getId());
                InventoryController.getInstance().inventoryMainMenu(loggedInUser);
                break;
            case "4":   // 입고
                inboundMenu.memberMenu(member);
                break;
            case "5":   // 출고
                break;
            case "6":   // 로그아웃
                logout(member.getId());
                break;
        }
    }



    public void managerMenuList(Manager manager) throws IOException {
        // 창고관리 기능은 관리자 전용 기능이므로, memberMenu(), managerMenu()를 구분
        System.out.print(WMSPage.MANAGER_MENU_TITLE);
        String menuNum = input.readLine();
        validCheck.checkManagerMenu(menuNum);
        switch (menuNum) {
            case "1":   // 회원관리
                userManagement(manager);
                break;
            case "2":   // 고객센터
//                csMenu.managerCSMenu(manager);
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
                inboundMenu.managerMenu(manager);
                break;
            case "6":   // 출고
                break;
            case "7":   // 로그아웃
                logout(manager.getId());
                break;
        }
    }

    public void userManagement(User user) {
        // 회원 탈퇴 시 자동 종료
        if (user instanceof Manager manager) {
            userManageMenu = new ManagerManageMenu(manager);
            quitWMS = userManageMenu.run();
        } else if (user instanceof Member member) {
            userManageMenu = new MemberManageMenu(member);
            quitWMS = userManageMenu.run();
        }
    }

    // WMS의 나머지 기능에 관한 컨트롤러를 실행할 메서드를 여기서부터 작성해주시면 됩니다.

    private void cargoConnect(Manager manager) {
       try {
           cargoController = new CargoController(manager);
           cargoController.start();
       }catch (SQLException e){
           System.out.println(e.getMessage());
       }
    }


    public void logout(String userID) {
        LoginDAO.logout(userID);
        quitWMS = true;
    }
}
