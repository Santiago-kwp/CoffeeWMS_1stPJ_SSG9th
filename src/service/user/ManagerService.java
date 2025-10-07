package service.user;

import domain.user.Manager;
import domain.user.User;
import java.util.List;

public interface ManagerService extends UserService {

    // 사전 상태 확인
    void canRoleRestore(String targetID);
    void canUpdateManager(Manager requester);
    void canDelete(Manager requester);

    // 조회
    User findOtherUser(String targetID, Manager requester);
    List<User> findAllUsers(Manager requester);
    List<String> findMembers(Manager requester);
    List<Manager> findManagers(Manager requester);

    // 변경
    void approveUser(String targetID, Manager requester);
    void restoreUser(String targetID);
    void restoreUserRole(String targetID, String roleOption, Manager requester);
    void assignCargo(String targetManager, Integer cargoID);
    void deleteUserRole(String targetID);
}
