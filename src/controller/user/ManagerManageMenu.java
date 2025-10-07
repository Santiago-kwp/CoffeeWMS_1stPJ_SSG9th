package controller.user;

import constant.user.ManagerPage;
import constant.user.MemberPage;
import constant.user.UserPage;
import domain.user.Manager;
import domain.user.Member;
import domain.user.User;
import exception.user.*;

import java.io.IOException;
import java.util.List;
import service.user.ManagerService;

public class ManagerManageMenu extends AbstractUserManageMenu {

    private final ManagerService managerService;

    private Manager currentManager;

    public ManagerManageMenu(Manager manager, ManagerService managerService) {
        super();
        this.currentManager = manager;
        this.managerService = managerService;
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
                String menuNum = consoleView.promptAndRead(ManagerPage.MANAGER_SELECT_TITLE.toString());
                menuNumberValidCheck.checkMenuNumber("^[1-4]", menuNum);
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
                String menuNum = consoleView.promptAndRead(ManagerPage.MANAGER_DETAIL_INFO_TITLE.toString());
                menuNumberValidCheck.checkMenuNumber("^[1-3]", menuNum);
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
        currentManager = (Manager) managerService.findMyDetails(currentManager.getId());
        ManagerPage.details(currentManager);
    }

    private void readOtherUser() throws IOException {
        String targetID = consoleView.promptAndRead(ManagerPage.INPUT_ID_FOR_SEARCH.toString());
        User foundUser = managerService.findOtherUser(targetID, currentManager);
        if (foundUser instanceof Manager) {
            ManagerPage.details((Manager)foundUser);
        } else {
            MemberPage.details((Member)foundUser);
        }
    }

    // 권한에 관계없이 전체 회원 조회(승인된 회원의 공통 정보만 조회)
    public void readAllUsers() {
        List<User> allUsers = managerService.findAllUsers(currentManager);
        System.out.print(UserPage.searchAllTitle());
        allUsers.forEach(System.out::println);
    }

    // 회원이 보유한 권한에 따라 회원 조회를 진행
    public void readUsersByRole() throws IOException {
        String menuNum = consoleView.promptAndRead(ManagerPage.MANAGER_SEARCH_BY_ROLE_TITLE.toString());
        menuNumberValidCheck.checkMenuNumber("^[1-2]", menuNum);
        switch (menuNum) {
            case "1" -> readMemberList();
            case "2" -> readManagerList();
        }
    }

    public void readMemberList() {
        List<String> searchResult = managerService.findMembers(currentManager);
        System.out.print(MemberPage.memberInfoTitle());
        searchResult.forEach(System.out::println);
    }

    public void readManagerList() {
        List<Manager> searchResult = managerService.findManagers(currentManager);
        System.out.print(ManagerPage.managerInfoTitle());
        searchResult.forEach(System.out::println);
    }

    @Override
    public void update() throws IOException {
        boolean quitUpdate = false;
        while (!quitUpdate) {
            try {
                String menuNum = consoleView.promptAndRead(ManagerPage.MANAGER_UPDATE_TITLE.toString());
                menuNumberValidCheck.checkMenuNumber("^[1-6]", menuNum);
                switch (menuNum) {
                    case "1" -> updateCurrentUser();
                    case "2" -> approveUser();
                    case "3" -> restoreUserRole();
                    case "4" -> restoreUser();
                    case "5" -> addCargoToManager();
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
        if (consoleView.checkCancel(UserPage.USER_UPDATE_TITLE.toString(), UserPage.TO_PREVIOUS_MENU.toString())) {
            return;
        }
        User newUserInfo = consoleView.inputManagerInfo(true);
        currentManager = (Manager) managerService.updateMyInfo(currentManager, newUserInfo);
        System.out.println(UserPage.USER_UPDATE);
    }

    private void approveUser() throws IOException {
        String targetID = consoleView.promptAndRead(ManagerPage.INPUT_ID_FOR_APPROVE.toString());
        managerService.approveUser(targetID, currentManager);
        System.out.println(ManagerPage.APPROVE_COMPLETE);
    }

    public void restoreUserRole() throws IOException {
        String targetID = consoleView.promptAndRead(ManagerPage.INPUT_ID_FOR_UPDATE_ROLE.toString());
        managerService.canRoleRestore(targetID);

        String option = consoleView.promptAndRead(ManagerPage.ROLE_UPDATE_OPTION.toString());
        menuNumberValidCheck.checkMenuNumber("^[1-2]", option);
        managerService.restoreUserRole(targetID, option, currentManager);

        System.out.println(ManagerPage.ROLE_UPDATE_COMPLETE);
    }

    public void restoreUser() throws IOException {
        managerService.canUpdateManager(currentManager);
        String targetID = consoleView.promptAndRead(ManagerPage.INPUT_ID_FOR_RESTORE.toString());
        managerService.restoreUser(targetID);
        System.out.println(ManagerPage.RESTORE_COMPLETE);
    }

    public void addCargoToManager() throws IOException {
        managerService.canUpdateManager(currentManager);
        String targetID = consoleView.promptAndRead(ManagerPage.INPUT_MANAGER_FOR_ADD_CARGO.toString());
        int cargoID = Integer.parseInt(consoleView.promptAndRead(ManagerPage.INPUT_CARGO_ID.toString()));

        managerService.assignCargo(targetID, cargoID);
        System.out.printf(ManagerPage.CARGO_ADD_SUCCESS.toString(), cargoID);
    }

    @Override
    public boolean delete() throws IOException {
        boolean quitDelete = false;
        boolean isUserDeleted = false;
        while (!quitDelete && !isUserDeleted) {
            try {
                String menuNum = consoleView.promptAndRead(ManagerPage.MANAGER_DELETE_TITLE.toString());
                menuNumberValidCheck.checkMenuNumber("^[1-3]", menuNum);
                switch (menuNum) {
                    case "1" -> isUserDeleted = deleteCurrentUser();
                    case "2" -> deleteUserRole();
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
        managerService.canDelete(currentManager);
        if (consoleView.checkCancel(UserPage.USER_DELETE_TITLE.toString(), UserPage.USER_NOT_DELETE.toString())) {
            return false;
        }
        managerService.deleteMyAccount(currentManager.getId());
        System.out.println(UserPage.USER_DELETE);
        return true;
    }

    public void deleteUserRole() throws IOException {
        managerService.canDelete(currentManager);
        String targetID = consoleView.promptAndRead(ManagerPage.INPUT_ID_FOR_DELETE_ROLE.toString());
        managerService.deleteUserRole(targetID);
        System.out.println(ManagerPage.ROLE_DELETE_COMPLETE);
    }

    private boolean quit() {
        System.out.println(UserPage.TO_PREVIOUS_MENU);
        return true;
    }
}
