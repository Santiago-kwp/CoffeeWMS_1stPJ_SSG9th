package service.user;

import domain.user.User;
import exception.user.FailedToUserRegisterException;
import exception.user.FailedToUserUpdateException;
import exception.user.LoginException;
import exception.user.UserNotFoundException;
import model.user.LoginDAO;

public class LoginServiceImpl implements LoginService {

    private final LoginDAO dao;

    public LoginServiceImpl(LoginDAO dao) {
        this.dao = dao;
    }

    @Override
    public User login(String userID, String userPwd) throws LoginException {
        String userType = dao.searchUserTypeBy(userID, userPwd);
        loginValidCheck.checkUserType(userType);

        User loginUser = dao.login(userID, userPwd, userType);
        loginValidCheck.checkLoginSuccess(loginUser);

        return loginUser;
    }

    @Override
    public void register(User user) throws FailedToUserRegisterException {
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
    public void updatePassword(String userID, String newPassword) throws FailedToUserUpdateException {
        boolean ack = dao.updatePassword(userID, newPassword);
        loginValidCheck.checkPwdUpdated(ack);
    }
}
