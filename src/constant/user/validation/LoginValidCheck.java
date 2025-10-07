package constant.user.validation;

import constant.user.LoginPage;
import domain.user.User;
import exception.user.FailedToLoginException;
import exception.user.UserNotFoundException;
import exception.user.FailedToRegisterException;
import exception.user.FailedToUpdateUserException;

public class LoginValidCheck {

    public void checkUserType(String userType) {
        if (userType == null || userType.trim().isEmpty()) {
            throw new FailedToLoginException(LoginPage.USER_NOT_EXIST.toString());
        }
    }

    public void checkLoginSuccess(User loginUser) {
        if (loginUser == null) {
            throw new FailedToLoginException(LoginPage.CANNOT_LOGIN.toString());
        }
    }

    public void checkUserRegistered(boolean registerACK) {
        if (!registerACK) {
            throw new FailedToRegisterException(LoginPage.REGISTER_FAILED.toString());
        }
    }

    public void checkIDFound(String foundID) {
        if (foundID == null || foundID.trim().isEmpty()) {
            throw new UserNotFoundException(LoginPage.NOT_FOUND_ID.toString());
        }
    }

    public void checkPwdUpdated(boolean pwdUpdateACK) {
        if (!pwdUpdateACK) {
            throw new FailedToUpdateUserException(LoginPage.NOT_UPDATE_PASSWORD.toString());
        }
    }
}
