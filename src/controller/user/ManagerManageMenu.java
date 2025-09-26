package controller.user;

import constant.user.LoginPage;
import constant.user.UserPage;
import domain.user.Manager;
import domain.user.Member;
import domain.user.User;
import exception.user.DeleteException;
import exception.user.NotAvailableDeleteChiefManagerException;
import exception.user.NotHavePermissionException;
import exception.user.NotRegisteredUserException;
import model.user.ManagerDAO;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class ManagerManageMenu implements UserManageMenu {

    private final ManagerDAO dao;
    private final Manager currentManager;

    public ManagerManageMenu(Manager manager) {
        this.currentManager = manager;
        this.dao = new ManagerDAO(manager);
    }

    @Override
    public void printMenu() {
        System.out.print(UserPage.MANAGER_MEMBER_MENU_TITLE);
    }

    @Override
    public void read() throws IOException {
        boolean quitRead = false;
        while (!quitRead) {
            try {
                System.out.print(UserPage.MANAGER_SELECT_TITLE);
                String menuNum = input.readLine();
                switch (menuNum) {
                    case "1":
                        readOneUserDetail();
                        break;
                    case "2":
                        readAllUser();
                        break;
                    case "3":
                        readUsersByRole();
                        break;
                    case "4":
                        quitRead = quit();
                        break;
                }
            } catch (NotHavePermissionException | NotRegisteredUserException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void readOneUserDetail() throws IOException {
        boolean quitRead = false;
        while (!quitRead) {
            System.out.print(UserPage.MANAGER_DETAIL_INFO_TITLE);
            String menuNum = input.readLine();
            switch (menuNum) {
                case "1":
                    readCurrentUser();
                    break;
                case "2":
                    readOtherUser();
                    break;
                case "3":
                    quitRead = quit();
                    break;
            }
        }
    }
    private void readCurrentUser() {
        System.out.println(UserPage.CURRENT_USER_SELECT);
        Manager manager = dao.searchUserDetails();
        // 현재 관리자 정보 조회
        UserPage.managerDetails(manager);
    }

    private void readOtherUser() throws IOException {
        System.out.print(UserPage.INPUT_ID_FOR_SEARCH);
        String targetID = input.readLine();

        String userType = dao.searchUserTypeBy(targetID);
        User found = dao.searchUser(targetID, userType);

        if (userType == null || found == null) {
            throw new NotRegisteredUserException("해당하는 회원을 찾을 수 없습니다.");
        }
        switch (currentManager.getPosition()) {
            case "창고관리자":
                if (found instanceof Manager) {
                    // 창고관리자는 다른 창고관리자의 인적사항을 열람할 수 없다.
                    throw new NotHavePermissionException(UserPage.NOT_HAVE_PERMISSION.toString());
                }
                UserPage.memberDetails((Member)found);
                break;
            case "총관리자":
                if (found instanceof Manager manager) {
                    UserPage.managerDetails(manager);
                } else {
                    UserPage.memberDetails((Member)found);
                }
                break;
        }
    }

    // 권한에 관계없이 전체 회원 조회(승인된 회원의 공통 정보만 조회)
    public void readAllUser() {
        System.out.print(UserPage.MANAGER_SEARCH_ALL);
        if (!currentManager.getPosition().equals("총관리자")) {
            throw new NotHavePermissionException(UserPage.NOT_HAVE_PERMISSION.toString());
        }
        List<User> allApprovedUser = dao.searchAllUser();

        UserPage.userCommonInfoTitle();
        allApprovedUser.stream()
                .sorted(Comparator.comparing(User::getType).reversed().thenComparing(User::getId))
                .forEach(UserPage::userCommonInfo); // 변경 예정
    }

    // 회원이 보유한 권한에 따라 회원 조회를 진행
    public void readUsersByRole() throws IOException {
        System.out.print(UserPage.MANAGER_SEARCH_BY_ROLE_TITLE);
        String menuNum = input.readLine();

        switch (menuNum) {
            case "1":
                readMemberList();
                break;
            case "2":
                readManagerList(currentManager.getPosition());
                break;
        }
    }

    public void readMemberList() {
        List<User> searchResult = dao.searchByRole("members");
        searchResult.stream()
                .map(user -> (Member)user)
                .forEach(member -> System.out.println(member));
    }

    public void readManagerList(String position) {
        if (!position.equals("총관리자")) {
            throw new NotHavePermissionException(UserPage.NOT_HAVE_PERMISSION.toString());
        }
        List<User> searchResult = dao.searchByRole("managers");
        searchResult.stream()
                .map(user -> (Manager)user)
                .sorted(Comparator.comparing(Manager::getPosition).reversed().thenComparing(Manager::getId))
                .forEach(manager -> System.out.println(manager));
    }

    @Override
    public void update() throws IOException {
        boolean quitUpdate = false;
        while (!quitUpdate) {
            System.out.print(UserPage.MANAGER_UPDATE_TITLE);
            String menuNum = input.readLine();
            switch (menuNum) {
                case "1":
                    updateCurrentMember();
                    break;
                case "2":
                    updateManagerRole();
                    break;
                case "3":
                    quitUpdate = quit();
                    break;
            }
        }
    }

    public void updateCurrentMember() throws IOException {
        User newUserInfo = inputNewManager();
        boolean ack = dao.updateUserInfo(newUserInfo);
        System.out.println(ack ? UserPage.MEMBER_UPDATE : UserPage.MEMBER_UPDATE_FAILED);
    }

    public void updateManagerRole() {
        String position = currentManager.getPosition();
        if (!position.equals("총관리자")) {
            // 예외 처리 필요
            throw new NotHavePermissionException(UserPage.NOT_HAVE_PERMISSION.toString());
        }

    }

    public User inputNewManager() throws IOException {
        System.out.println(UserPage.MANAGER_UPDATE_SUBTITLE);
        System.out.println(LoginPage.INPUT_PWD);
        String userPwd = input.readLine();
        System.out.println(LoginPage.INPUT_NAME);
        String name = input.readLine();
        System.out.println(LoginPage.INPUT_PHONE);
        String phone = input.readLine();
        System.out.println(LoginPage.INPUT_EMAIL);
        String email = input.readLine();

        User newInfo = new User();
        newInfo.setPwd(userPwd);
        newInfo.setName(name);
        newInfo.setPhone(phone);
        newInfo.setEmail(email);
        return newInfo;
    }

    @Override
    public boolean delete() throws IOException {
        boolean quitDelete = false;
        boolean isDeleted = false;
        while (!quitDelete && !isDeleted) {
            System.out.println(UserPage.MANAGER_DELETE_TITLE);
            String menuNum = input.readLine();
            switch (menuNum) {
                case "1":
                    isDeleted = deleteCurrentUser();
                    break;
                case "2":
                    deleteManagerRole();
                    break;
                case "3":
                    quitDelete = quit();
                    break;
            }
        }
        return isDeleted;
    }

    public boolean deleteCurrentUser() throws IOException {
        System.out.print(UserPage.USER_DELETE_TITLE);
        if (currentManager.getPosition().equals("총관리자")) {  // 예외처리
            throw new NotAvailableDeleteChiefManagerException(UserPage.CHIEF_MANAGER_CANNOT_DELETE.toString());
        }
        String yesOrNo = input.readLine();
        if (!yesOrNo.equalsIgnoreCase("Y")) {
            System.out.println(UserPage.USER_NOT_DELETE);
            return false;
        }

        boolean ack = dao.deleteUserInfo();
        if (!ack) {
            // 예외처리
            throw new DeleteException(UserPage.USER_DELETE_FAILED.toString());
        }
        System.out.println(UserPage.USER_DELETE);
        return true;
    }

    public void deleteManagerRole() {
        String position = currentManager.getPosition();
        if (!position.equals("총관리자")) {
            throw new NotHavePermissionException(UserPage.NOT_HAVE_PERMISSION.toString());
        }
    }

    private boolean quit() {
        System.out.println(UserPage.USER_MENU_PREVIOUS);
        return true;
    }
}
