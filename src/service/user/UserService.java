package service.user;

import constant.user.validation.UserManagementValidCheck;
import domain.user.User;

public interface UserService {

    UserManagementValidCheck validCheck = new UserManagementValidCheck();

    User findMyDetails(String userID);
    User updateMyInfo(User original, User newUserInfo);
    void deleteMyAccount(String userID);
}
