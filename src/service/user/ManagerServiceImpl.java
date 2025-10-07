package service.user;

import domain.user.Manager;
import domain.user.Member;
import domain.user.User;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import model.user.ManagerDAO;

public class ManagerServiceImpl implements ManagerService {

    private final ManagerDAO dao;

    public ManagerServiceImpl(ManagerDAO dao) {
        this.dao = dao;
    }

    @Override
    public Manager findMyDetails(String memberID) {
        Manager manager = dao.searchUserDetails(memberID);
        validCheck.checkUserFound(manager);
        return manager;
    }

    @Override
    public User findOtherUser(String targetID, Manager requester) {
        String userType = dao.searchRegisterTypeBy(targetID, true);
        validCheck.checkUserType(userType);

        User foundUser = targetID.equals(requester.getId())
                ? dao.searchUserDetails(targetID)
                : dao.searchOtherUser(targetID, userType);

        validCheck.checkUserFound(foundUser);
        validCheck.checkPermission(requester.getPosition().equals("총관리자") || foundUser instanceof Member);
        return foundUser;
    }

    @Override
    public List<User> findAllUsers(Manager requester) {
        validCheck.checkPermission(requester.getPosition(), "총관리자", false);
        List<User> allUsers = dao.searchAllUser();
        validCheck.checkUserFound(allUsers);

        return allUsers.stream()
                .sorted(Comparator.comparing(User::getType).reversed().thenComparing(User::getId))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findMembers(Manager requester) {
        List<User> searchResult = dao.searchByRole("members");
        validCheck.checkUserFound(searchResult);
        return searchResult.stream()
                .map(user -> (Member)user)
                .sorted(Comparator.comparing(Member::getName).thenComparing(Member::getId))
                .map(member -> requester.getPosition().equals("총관리자") ? member.toString() : member.maskedInfo())
                .collect(Collectors.toList());
    }

    @Override
    public List<Manager> findManagers(Manager requester) {
        validCheck.checkPermission(requester.getPosition(), "총관리자", false);
        List<User> searchResult = dao.searchByRole("managers");
        validCheck.checkUserFound(searchResult);

        return searchResult.stream()
                .map(user -> (Manager)user)
                .sorted(Comparator.comparing(Manager::getPosition).reversed().thenComparing(Manager::getId))
                .collect(Collectors.toList());
    }

    @Override
    public Manager updateMyInfo(User original, User newUserInfo) {
        Manager updatedManager = dao.updateUserInfo(original, newUserInfo);
        validCheck.checkUserUpdated(updatedManager);
        return updatedManager;
    }

    @Override
    public void approveUser(String targetID, Manager requester) {
        String targetRole = canUserApprove(targetID);
        boolean ack = false;
        switch (targetRole) {
            case "일반회원" -> ack = dao.approve(targetID, false);
            case "창고관리자" -> {
                validCheck.checkPermission(requester.getPosition(), "총관리자", false);
                ack = dao.approve(targetID, false);
            }
        }
        validCheck.checkUserApproved(ack, false);
    }
    private String canUserApprove(String targetID) {
        String userRole = dao.searchRegisterTypeBy(targetID, false);
        validCheck.checkRoleDeleted(userRole, false);
        return userRole;
    }

    @Override
    public void restoreUser(String targetID) {
        boolean ack = dao.approve(targetID, true);
        validCheck.checkUserApproved(ack, true);
    }

    @Override
    public void restoreUserRole(String targetID, String roleOption, Manager requester) {
        boolean ack = false;
        switch (roleOption) {
            case "1" -> ack = dao.restoreRole(targetID, "일반회원");
            case "2" -> {
                validCheck.checkPermission(requester.getPosition(), "총관리자", false);
                ack = dao.restoreRole(targetID, "창고관리자");
            }
        }
        validCheck.checkRoleUpdated(ack);
    }

    @Override
    public void assignCargo(String targetManager, Integer cargoID) {
        String userType = dao.searchRegisterTypeBy(targetManager, true);
        validCheck.checkPermission(userType.contains("관리자"));  // 창고관리자, 총관리자가 아니면 예외 처리
        boolean isAdded = dao.insertCargoToManager(targetManager, cargoID);
        validCheck.checkCargoAdded(isAdded);
    }

    @Override
    public void deleteMyAccount(String managerID) {
        boolean ack = dao.deleteUserInfo(managerID);
        validCheck.checkUserDeleted(ack);
    }

    @Override
    public void deleteUserRole(String targetID) {
        String targetRole = dao.searchRegisterTypeBy(targetID, true);
        validCheck.checkDeleteRoleAvailable(targetRole);
        boolean ack = dao.deleteRole(targetID, targetRole);
        validCheck.checkRoleDeleted(ack);
    }

    @Override
    public void canRoleRestore(String targetID) {
        String userRole = dao.searchRegisterTypeBy(targetID, true);
        validCheck.checkRoleDeleted(userRole, true);
    }

    @Override
    public void canUpdateManager(Manager requester) {
        validCheck.checkPermission(requester.getPosition(), "총관리자", false);
    }

    @Override
    public void canDelete(Manager requester) {
        validCheck.checkPermission(requester.getPosition(), "총관리자", true);
    }
}
