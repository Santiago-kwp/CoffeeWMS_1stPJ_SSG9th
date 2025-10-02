package constant.user.validation;

import constant.user.InputMessage;
import constant.user.LoginPage;
import domain.user.User;
import exception.user.LoginException;
import exception.user.UserNotFoundException;
import exception.user.FailedToUserRegisterException;
import exception.user.FailedToUserUpdateException;

public class LoginValidCheck {

    private static final String LOGIN_MENU = "^[1-5]";

    public void checkMenuNumber(String menuOption) {
        if (!menuOption.matches(LOGIN_MENU)) {
            throw new IllegalArgumentException(InputMessage.INVALID_MENU_NUMBER.toString());
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

    public void checkIDFound(boolean findIDACK) {
        if (!findIDACK) {
            throw new UserNotFoundException(LoginPage.NOT_FOUND_ID.toString());
        }
    }

    public void checkPwdUpdated(boolean pwdUpdateACK) {
        if (!pwdUpdateACK) {
            throw new FailedToUserUpdateException(LoginPage.NOT_UPDATE_PASSWORD.toString());
        }
    }
}
