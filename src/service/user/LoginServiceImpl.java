package service.user;

import domain.user.User;
import exception.user.FailedToRegisterException;
import exception.user.FailedToUpdateUserException;
import exception.user.FailedToLoginException;
import exception.user.UserNotFoundException;
import model.user.LoginDAO;

public class LoginServiceImpl implements LoginService, LogoutService {

    private final LoginDAO dao;

    public LoginServiceImpl(LoginDAO dao) {
        this.dao = dao;
    }

    @Override
    public User login(String userID, String userPwd) throws FailedToLoginException {
        String userType = dao.searchUserTypeBy(userID, userPwd);
        loginValidCheck.checkUserType(userType);

        User loginUser = userType.endsWith("관리자")
                ? dao.loginManagerBy(userID, userPwd, userType)
                : dao.loginMemberBy(userID, userPwd, userType);
        loginValidCheck.checkLoginSuccess(loginUser);

        return loginUser;
    }

    @Override
    public void register(User user) throws FailedToRegisterException {
        boolean ack = dao.register(user);
        loginValidCheck.checkUserRegistered(ack);
    }

    @Override
    public String findIdByEmail(String email) throws UserNotFoundException {
        String foundID = dao.findID(email);
        loginValidCheck.checkIDFound(foundID);
        return foundID;
    }

    @Override
    public void updatePassword(String userID, String newPassword) throws FailedToUpdateUserException {
        boolean ack = dao.updatePassword(userID, newPassword);
        loginValidCheck.checkPwdUpdated(ack);
    }

    @Override
    public String logout(String userID) {
        return dao.logout(userID);
    }
}
