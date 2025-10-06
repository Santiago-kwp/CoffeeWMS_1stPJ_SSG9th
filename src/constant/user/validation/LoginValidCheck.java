package constant.user.validation;

import constant.user.LoginPage;
import domain.user.User;
import exception.user.LoginException;
import exception.user.UserNotFoundException;
import exception.user.FailedToUserRegisterException;
import exception.user.FailedToUserUpdateException;

public class LoginValidCheck {

    public void checkUserType(String userType) {
        if (userType == null || userType.trim().isEmpty()) {
            throw new LoginException(LoginPage.USER_NOT_EXIST.toString());
        }
    }

    public void checkLoginSuccess(User loginUser) {
        if (loginUser == null) {
            throw new LoginException(LoginPage.CANNOT_LOGIN.toString());
        }
    }

    public void checkUserRegistered(boolean registerACK) {
        if (!registerACK) {
            throw new FailedToUserRegisterException(LoginPage.REGISTER_FAILED.toString());
        }
    }

    public void checkIDFound(String foundID) {
        if (foundID == null || foundID.trim().isEmpty()) {
            throw new UserNotFoundException(LoginPage.NOT_FOUND_ID.toString());
        }
    }

    public void checkPwdUpdated(boolean pwdUpdateACK) {
        if (!pwdUpdateACK) {
            throw new FailedToUserUpdateException(LoginPage.NOT_UPDATE_PASSWORD.toString());
        }
    }
}
