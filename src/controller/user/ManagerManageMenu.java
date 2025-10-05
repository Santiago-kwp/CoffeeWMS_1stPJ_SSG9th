package controller.user;

import constant.user.LoginPage;
import constant.user.ManagerPage;
import constant.user.MemberPage;
import constant.user.UserPage;
import constant.user.validation.InputValidCheck;
import domain.user.Manager;
import domain.user.Member;
import domain.user.User;
import exception.user.*;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import model.user.ManagerDAO;

public class ManagerManageMenu implements UserManageMenu {

    private final ManagerDAO dao;
    private final InputValidCheck inputValidCheck;

    private Manager currentManager;

    public ManagerManageMenu(Manager manager) {
        this.currentManager = manager;
        this.dao = new ManagerDAO(manager);
        this.inputValidCheck = new InputValidCheck();
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
                validCheck.checkMenuNumber("^[1-4]", menuNum);
                switch (menuNum) {
                    case "1" -> readOneUser();
                    case "2" -> readAllUsers();
                    case "3" -> readUsersByRole();
                    case "4" -> quitRead = quit();
                }
            } catch (NotAllowedUserException
                     | UnableToReadUserException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void readOneUser() throws IOException {
        boolean quitRead = false;
        while (!quitRead) {
            try {
                System.out.print(ManagerPage.MANAGER_DETAIL_INFO_TITLE);
                String menuNum = input.readLine();
                validCheck.checkMenuNumber("^[1-3]", menuNum);
                switch (menuNum) {
                    case "1" -> readCurrentUser();
                    case "2" -> readOtherUser();
                    case "3" -> quitRead = quit();
                }
            } catch (UnableToReadUserException
                     | NotAllowedUserException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    private void readCurrentUser() {
        System.out.println(UserPage.CURRENT_USER_SELECT);
        Manager manager = dao.searchUserDetails();
        validCheck.checkUserFound(manager);
        ManagerPage.details(manager);
    }

    private void readOtherUser() throws IOException {
        System.out.print(ManagerPage.INPUT_ID_FOR_SEARCH);
        String targetID = input.readLine();
        String userType = dao.searchUserTypeBy(targetID, true);
        validCheck.checkUserType(userType);

        User foundUser = dao.searchUser(targetID, userType);
        validCheck.checkUserFound(foundUser);
        switch (currentManager.getPosition()) {
            case "창고관리자":
                validCheck.checkPermission(foundUser instanceof Manager);
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
    public void readAllUsers() {
        validCheck.checkPermission(currentManager.getPosition(), "총관리자", false);
        List<User> allUsers = dao.searchAllUser();
        validCheck.checkUserFound(allUsers);

        System.out.print(UserPage.searchAllTitle());
        allUsers.stream()
                .sorted(Comparator.comparing(User::getType).reversed().thenComparing(User::getId))
                .forEach(System.out::println);
    }

    // 회원이 보유한 권한에 따라 회원 조회를 진행
    public void readUsersByRole() throws IOException {
        System.out.print(ManagerPage.MANAGER_SEARCH_BY_ROLE_TITLE);
        String menuNum = input.readLine();
        validCheck.checkMenuNumber("^[1-2]", menuNum);
        switch (menuNum) {
            case "1":
                readMemberList();
                break;
            case "2":
                validCheck.checkPermission(currentManager.getPosition(), "총관리자", false);
                readManagerList();
                break;
        }
    }

    // 수정 필요
    public void readMemberList() {
        List<User> searchResult = dao.searchByRole("members");
        validCheck.checkUserFound(searchResult);
        System.out.print(MemberPage.memberInfoTitle());
        searchResult.stream()
                .map(user -> (Member)user)
                .forEach(member
                        -> System.out.println(currentManager.getPosition().equals("총관리자") ? member : member.maskedInfo()));
    }

    // 수정 필요
    public void readManagerList() {
        System.out.print(ManagerPage.managerInfoTitle());
        List<User> searchResult = dao.searchByRole("managers");
        validCheck.checkUserFound(searchResult);
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
                validCheck.checkMenuNumber("^[1-6]", menuNum);
                switch (menuNum) {
                    case "1" -> updateCurrentUser();
                    case "2" -> approveUser();
                    case "3" -> restoreUserRole();
                    case "4" -> {
                        validCheck.checkPermission(currentManager.getPosition(), "총관리자", false);
                        restoreUser();
                    }
                    case "5" -> {
                        validCheck.checkPermission(currentManager.getPosition(), "총관리자", false);
                        addCargoToManager();
                    }
                    case "6" -> quitUpdate = quit();
                }
            } catch (InvalidUserDataException
                     | FailedToUserUpdateException
                     | FailedToUserRoleUpdateException
                     | NotAllowedUserException
                     | FailedToApproveUserException
                     | FailedToUserRestoreException
                     | CargoAddToManagerFailedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void updateCurrentUser() throws IOException, InvalidUserDataException {
        System.out.print(UserPage.USER_UPDATE_TITLE);
        String yesOrNo = input.readLine();
        if (!yesOrNo.equalsIgnoreCase("Y")) {
            System.out.println(UserPage.TO_PREVIOUS_MENU);
            return;
        }

        User newUserInfo = inputNewManagerInfo();
        Manager updatedManager = dao.updateUserInfo(newUserInfo);
        validCheck.checkUserUpdated(updatedManager);
        currentManager = updatedManager;
        System.out.println(UserPage.USER_UPDATE);
    }

    public User inputNewManagerInfo() throws IOException, InvalidUserDataException {
        System.out.println(ManagerPage.MANAGER_UPDATE_SUBTITLE);
        System.out.println(LoginPage.INPUT_PWD);
        String userPwd = input.readLine();
        System.out.println(LoginPage.INPUT_NAME);
        String name = input.readLine();
        System.out.println(LoginPage.INPUT_PHONE);
        String phone = input.readLine();
        System.out.println(LoginPage.INPUT_EMAIL);
        String email = input.readLine();

        return User.Builder.update(userPwd, name)
                .phone(phone)
                .email(email)
                .build();
    }

    private void approveUser() throws IOException {
        System.out.print(ManagerPage.INPUT_ID_FOR_APPROVE);
        String targetID = input.readLine();
        String userRole = dao.searchUserTypeBy(targetID, false);
        validCheck.checkRoleExist(userRole);

        boolean ack = false;
        switch (userRole) {
            case "일반회원" -> ack = dao.approve(targetID, false);
            case "창고관리자" -> {
                validCheck.checkPermission(currentManager.getPosition(), "총관리자", false);
                ack = dao.approve(targetID, false);
            }
        }
        validCheck.checkUserApproved(ack, false);
        System.out.println(ManagerPage.APPROVE_COMPLETE);
    }

    // 아무런 권한이 없는 회원에게 권한을 부여
    // 창고관리자는 일반회원 권한만 부여 가능
    public void restoreUserRole() throws IOException {
        System.out.print(ManagerPage.INPUT_ID_FOR_UPDATE_ROLE);
        String targetID = input.readLine();
        String userRole = dao.searchUserTypeBy(targetID, true);
        validCheck.checkRoleDeleted(userRole);

        System.out.print(ManagerPage.ROLE_UPDATE_OPTION);
        String option = input.readLine();
        validCheck.checkMenuNumber("^[1-2]", option);
        boolean ack = false;
        switch (option) {
            case "1" -> ack = dao.restoreRole(targetID, "일반회원");
            case "2" -> {
                validCheck.checkPermission(currentManager.getPosition(), "총관리자", false);
                ack = dao.restoreRole(targetID, "창고관리자");
            }
        }
        validCheck.checkRoleUpdated(ack);
        System.out.println(ManagerPage.ROLE_UPDATE_COMPLETE);
    }

    public void restoreUser() throws IOException {
        System.out.print(ManagerPage.INPUT_ID_FOR_RESTORE);
        String targetID = input.readLine();
        boolean ack = dao.approve(targetID, true);
        validCheck.checkUserApproved(ack, true);
        System.out.println(ManagerPage.RESTORE_COMPLETE);
    }

    public void addCargoToManager() throws IOException {
        System.out.println("창고를 배정할 창고관리자의 아이디를 입력해주세요.");
        String targetID = input.readLine();
        System.out.println("관리자에게 배정할 창고의 아이디를 입력해주세요.");
        int cargoID = Integer.parseInt(input.readLine());

        // 창고를 배정할 아이디에 해당하는 회원의 권한이 관리자인지 확인
        String userType = dao.searchUserTypeBy(targetID, true);
        validCheck.checkPermission(!userType.contains("관리자"));  // 창고관리자, 총관리자가 아니면 예외 처리
        boolean isAdded = dao.insertCargoToManager(targetID, cargoID);
        validCheck.checkCargoAdded(isAdded);
        System.out.printf(ManagerPage.CARGO_ADD_SUCCESS.toString(), cargoID);
    }

    @Override
    public boolean delete() throws IOException {
        boolean quitDelete = false;
        boolean isUserDeleted = false;
        while (!quitDelete && !isUserDeleted) {
            try {
                System.out.print(ManagerPage.MANAGER_DELETE_TITLE);
                String menuNum = input.readLine();
                validCheck.checkMenuNumber("^[1-3]", menuNum);
                switch (menuNum) {
                    case "1" -> {
                        validCheck.checkPermission(currentManager.getPosition(), "창고관리자", true);
                        isUserDeleted = deleteCurrentUser();
                    }
                    case "2" -> {
                        validCheck.checkPermission(currentManager.getPosition(), "총관리자", true);
                        deleteUserRole();
                    }
                    case "3" -> quitDelete = quit();
                }
            } catch (NotAllowedUserException
                     | FailedToDeleteUserRoleException
                     | UserDeleteFailedException e) {
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
        validCheck.checkUserDeleted(ack);
        System.out.println(UserPage.USER_DELETE);
        return true;
    }

    public void deleteUserRole() throws IOException {
        System.out.print(ManagerPage.INPUT_ID_FOR_DELETE_ROLE);
        String targetID = input.readLine();
        String targetType = dao.searchUserTypeBy(targetID, true);
        validCheck.checkDeleteRoleAvailable(targetType);

        boolean ack = dao.deleteRole(targetID, targetType);
        validCheck.checkRoleDeleted(ack);
        System.out.println(ManagerPage.ROLE_DELETE_COMPLETE);
    }

    private boolean quit() {
        System.out.println(UserPage.TO_PREVIOUS_MENU);
        return true;
    }
}
