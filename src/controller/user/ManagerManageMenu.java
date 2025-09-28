package controller.user;

import constant.user.LoginPage;
import constant.user.ManagerPage;
import constant.user.MemberPage;
import constant.user.UserPage;
import domain.user.Manager;
import domain.user.Member;
import domain.user.User;
import exception.user.UnableToReadUserException;
import exception.user.UserNotApprovedException;
import exception.user.UserNotDeletedException;
import exception.user.UserNotHavePermissionException;
import exception.user.UserNotRestoredException;
import exception.user.UserNotUpdatedException;
import exception.user.UserRoleNotDeletedException;
import exception.user.UserRoleNotUpdatedException;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import model.user.ManagerDAO;

public class ManagerManageMenu implements UserManageMenu {

    private final ManagerDAO dao;
    private final Manager currentManager;

    public ManagerManageMenu(Manager manager) {
        this.currentManager = manager;
        this.dao = new ManagerDAO(manager);
    }

    @Override
    public void printMenu() {
        System.out.print(ManagerPage.MANAGER_MANAGEMENT_MENU_TITLE);
    }

    @Override
    public void read() throws IOException {
        boolean quitRead = false;
        while (!quitRead) {
            try {
                System.out.print(ManagerPage.MANAGER_SELECT_TITLE);
                String menuNum = input.readLine();
                switch (menuNum) {
                    case "1" -> readOneUserDetail();
                    case "2" -> readAllUser();
                    case "3" -> readUsersByRole();
                    case "4" -> quitRead = quit();
                }
            } catch (UserNotHavePermissionException
                     | UnableToReadUserException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void readOneUserDetail() throws IOException {
        boolean quitRead = false;
        while (!quitRead) {
            try {
                System.out.print(ManagerPage.MANAGER_DETAIL_INFO_TITLE);
                String menuNum = input.readLine();
                switch (menuNum) {
                    case "1" -> readCurrentUser();
                    case "2" -> readOtherUser();
                    case "3" -> quitRead = quit();
                }
            } catch (UnableToReadUserException
                     | UserNotHavePermissionException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    private void readCurrentUser() {
        System.out.println(UserPage.CURRENT_USER_SELECT);
        Manager manager = dao.searchUserDetails();
        if (manager == null) {
            throw new UnableToReadUserException(UserPage.CANNOT_SEARCH_USER.toString());
        }
        ManagerPage.details(manager);
    }

    private void readOtherUser() throws IOException {
        System.out.print(ManagerPage.INPUT_ID_FOR_SEARCH);
        String targetID = input.readLine();

        String userType = dao.searchUserTypeBy(targetID);
        if (userType == null) {
            throw new UnableToReadUserException(UserPage.CANNOT_SEARCH_USER.toString());
        }

        User foundUser = dao.searchUser(targetID, userType);
        if (foundUser == null) {
            throw new UnableToReadUserException(UserPage.CANNOT_SEARCH_USER.toString());
        }
        switch (currentManager.getPosition()) {
            case "창고관리자":
                if (foundUser instanceof Manager) {
                    // 창고관리자는 다른 창고관리자의 인적사항을 열람할 수 없다.
                    throw new UserNotHavePermissionException(ManagerPage.NOT_HAVE_PERMISSION.toString());
                }
                MemberPage.details((Member)foundUser);
                break;
            case "총관리자":
                if (foundUser instanceof Manager) {
                    ManagerPage.details((Manager)foundUser);
                } else {
                    MemberPage.details((Member)foundUser);
                }
                break;
        }
    }

    // 권한에 관계없이 전체 회원 조회(승인된 회원의 공통 정보만 조회)
    public void readAllUser() {
        if (!currentManager.getPosition().equals("총관리자")) {
            throw new UserNotHavePermissionException(ManagerPage.NOT_HAVE_PERMISSION.toString());
        }
        List<User> allUsers = dao.searchAllUser();
        if (allUsers.isEmpty()) {
            throw new UnableToReadUserException(UserPage.CANNOT_SEARCH_USER.toString());
        }
        System.out.print(UserPage.searchAllTitle());
        allUsers.stream()
                .sorted(Comparator.comparing(User::getType).reversed().thenComparing(User::getId))
                .forEach(System.out::println);
    }

    // 회원이 보유한 권한에 따라 회원 조회를 진행
    public void readUsersByRole() throws IOException {
        System.out.print(ManagerPage.MANAGER_SEARCH_BY_ROLE_TITLE);
        String menuNum = input.readLine();
        switch (menuNum) {
            case "1":
                readMemberList();
                break;
            case "2":
                if (!currentManager.getPosition().equals("총관리자")) {
                    throw new UserNotHavePermissionException(ManagerPage.NOT_HAVE_PERMISSION.toString());
                }
                readManagerList();
                break;
        }
    }

    // 수정 필요
    public void readMemberList() {
        List<User> searchResult = dao.searchByRole("members");
        if (searchResult.isEmpty()) {
            throw new UnableToReadUserException(UserPage.CANNOT_SEARCH_USER.toString());
        }
        System.out.print(MemberPage.memberInfoTitle());
        searchResult.stream()
                .map(user -> (Member)user)
                .forEach(System.out::println);
    }

    // 수정 필요
    public void readManagerList() {
        System.out.print(ManagerPage.managerInfoTitle());
        List<User> searchResult = dao.searchByRole("managers");
        if (searchResult.isEmpty()) {
            throw new UnableToReadUserException(UserPage.CANNOT_SEARCH_USER.toString());
        }
        searchResult.stream()
                .map(user -> (Manager)user)
                .sorted(Comparator.comparing(Manager::getPosition).reversed().thenComparing(Manager::getId))
                .forEach(System.out::println);
    }

    @Override
    public void update() throws IOException {
        boolean quitUpdate = false;
        while (!quitUpdate) {
            try {
                System.out.print(ManagerPage.MANAGER_UPDATE_TITLE);
                String menuNum = input.readLine();
                switch (menuNum) {
                    case "1" -> updateCurrentManager();
                    case "2" -> approveUser();
                    case "3" -> updateUserRole();
                    case "4" -> restoreUser();
                    case "5" -> quitUpdate = quit();
                }
            } catch (UserNotUpdatedException
                     | UserRoleNotUpdatedException
                     | UserNotHavePermissionException
                     | UserNotApprovedException
                     | UserNotRestoredException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void updateCurrentManager() throws IOException {
        User newUserInfo = inputNewManagerInfo();
        boolean ack = dao.updateUserInfo(newUserInfo);
        if (!ack) {
            throw new UserNotUpdatedException(UserPage.USER_UPDATE_FAILED.toString());
        }
        currentManager.setPwd(newUserInfo.getPwd());
        currentManager.setName(newUserInfo.getName());
        currentManager.setPhone(newUserInfo.getPhone());
        currentManager.setEmail(newUserInfo.getEmail());
        System.out.println(UserPage.USER_UPDATE);
    }

    public User inputNewManagerInfo() throws IOException {
        System.out.println(ManagerPage.MANAGER_UPDATE_SUBTITLE);
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

    private void approveUser() throws IOException {
        System.out.print(ManagerPage.INPUT_ID_FOR_APPROVE);
        String targetID = input.readLine();
        boolean ack = dao.approve(targetID, false);
        if (!ack) {
            throw new UserNotApprovedException(ManagerPage.APPROVE_FAILED.toString());
        }
        System.out.println(ManagerPage.APPROVE_COMPLETE);
    }

    // 아무런 권한이 없는 회원에게 권한을 부여
    // 창고관리자는 일반회원 권한만 부여 가능
    public void updateUserRole() throws IOException {
        System.out.print(ManagerPage.INPUT_ID_FOR_UPDATE_ROLE);
        String targetID = input.readLine();
        String userType = dao.searchUserTypeBy(targetID);
        if (userType != null) {
            throw new UserRoleNotUpdatedException(ManagerPage.ALREADY_HAVE_ROLE.toString());
        }

        System.out.print(ManagerPage.ROLE_UPDATE_OPTION);
        String option = input.readLine();
        boolean ack = false;
        switch (option) {
            case "1" -> ack = dao.updateRole(targetID, "일반회원");
            case "2" -> {
                if (!currentManager.getPosition().equals("총관리자")) {
                    throw new UserNotHavePermissionException(ManagerPage.NOT_HAVE_PERMISSION.toString());
                }
                ack = dao.updateRole(targetID, "창고관리자");
            }
        }
        if (!ack) {
            throw new UserRoleNotUpdatedException(ManagerPage.ROLE_UPDATE_FAILED.toString());
        }
        System.out.println(ManagerPage.ROLE_UPDATE_COMPLETE);
    }

    public void restoreUser() throws IOException {
        System.out.print(ManagerPage.INPUT_ID_FOR_RESTORE);
        String targetID = input.readLine();
        boolean ack = dao.approve(targetID, true);
        if (!ack) {
            throw new UserNotRestoredException(ManagerPage.RESTORE_FAILED.toString());
        }
        System.out.println(ManagerPage.RESTORE_COMPLETE);
    }

    @Override
    public boolean delete() throws IOException {
        boolean quitDelete = false;
        boolean isUserDeleted = false;
        while (!quitDelete && !isUserDeleted) {
            try {
                System.out.print(ManagerPage.MANAGER_DELETE_TITLE);
                String menuNum = input.readLine();
                switch (menuNum) {
                    case "1":
                        if (currentManager.getPosition().equals("총관리자")) {
                            throw new UserNotHavePermissionException(ManagerPage.CHIEF_MANAGER_CANNOT_DELETE.toString());
                        }
                        isUserDeleted = deleteCurrentUser();
                        break;
                    case "2":
                        deleteUserRole();
                        break;
                    case "3":
                        quitDelete = quit();
                        break;
                }
            } catch (UserNotHavePermissionException
                     | UserRoleNotDeletedException
                     | UserNotDeletedException e) {
                System.out.println(e.getMessage());
            }
        }
        return isUserDeleted;
    }

    public boolean deleteCurrentUser() throws IOException {
        System.out.print(UserPage.USER_DELETE_TITLE);
        String yesOrNo = input.readLine();
        if (!yesOrNo.equalsIgnoreCase("Y")) {
            System.out.println(UserPage.USER_NOT_DELETE);
            return false;
        }
        boolean ack = dao.deleteUserInfo();
        if (!ack) {
            throw new UserNotDeletedException(UserPage.USER_DELETE_FAILED.toString());
        }
        System.out.println(UserPage.USER_DELETE);
        return true;
    }

    // 권한이 부여된 회원의 권한을 삭제
    // 창고관리자는 일반회원 권한만 삭제 가능
    public void deleteUserRole() throws IOException {
        System.out.print(ManagerPage.INPUT_ID_FOR_DELETE_ROLE);
        String targetID = input.readLine();
        String targetType = dao.searchUserTypeBy(targetID);

        // 총관리자는 창고관리자, 일반회원 권한 삭제 가능, 창고관리자는 일반회원만 삭제 가능
        if (targetType == null) {
            throw new UserRoleNotDeletedException(ManagerPage.ALREADY_DELETED_OR_NOT_EXIST.toString());
        }
        if (targetType.equals("총관리자")) {
            throw new UserNotHavePermissionException(ManagerPage.CHIEF_MANAGER_CANNOT_DELETE.toString());
        }
        if (!currentManager.getPosition().equals("총관리자") && targetType.equals("창고관리자")) {
            throw new UserNotHavePermissionException(ManagerPage.NOT_HAVE_PERMISSION.toString());
        }
        boolean ack = dao.deleteRole(targetID, targetType);
        if (!ack) {
            throw new UserRoleNotDeletedException(ManagerPage.ROLE_DELETE_FAILED.toString());
        }
        System.out.println(ManagerPage.ROLE_DELETE_COMPLETE);
    }

    private boolean quit() {
        System.out.println(UserPage.TO_PREVIOUS_MENU);
        return true;
    }
}
