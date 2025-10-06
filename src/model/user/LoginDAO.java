package model.user;

import config.user.DBUtil;
import constant.user.LoginPage;
import domain.user.Manager;
import domain.user.Member;
import domain.user.User;
import exception.user.FailedToAccessLoginDataException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class LoginDAO {

    public String searchUserTypeBy(String userID, String userPwd) {
        String sql = "{call get_user_type(?, ?, ?)}";
        String userType;
        try (Connection conn = DBUtil.getConnection();
             CallableStatement call = conn.prepareCall(sql)) {
            call.setString(1, userID);
            call.setString(2, userPwd);
            call.registerOutParameter(3, Types.VARCHAR);
            call.execute();

            userType = call.getString(3);
        } catch (SQLException e) {
            throw new FailedToAccessLoginDataException(LoginPage.DB_ACCESS_ERROR_USER_TYPE.toString(), e);
        }
        return userType;
    }

    public Manager loginManagerBy(String userID, String userPwd, String userType) {
        String sql = "{call login_manager(?, ?, ?)}";
        Manager manager = null;

        try (Connection conn = DBUtil.getConnection();
             CallableStatement call = conn.prepareCall(sql)) {
            call.setString(1, userID);
            call.setString(2, userPwd);
            call.setString(3, userType);

            boolean hasResultSet = call.execute();
            do {
                ResultSet rs = call.getResultSet();
                if (hasResultSet && rs.next()) {
                    manager = Manager.Builder.from(rs);
                }
            } while (call.getMoreResults());

            return manager;
        } catch (SQLException e) {
            throw new FailedToAccessLoginDataException(LoginPage.DB_ACCESS_ERROR_LOGIN.toString(), e);
        }
    }

    public Member loginMemberBy(String userID, String userPwd, String userType) {
        String sql = "{call login_member(?, ?, ?)}";
        Member member = null;
        try (Connection conn = DBUtil.getConnection();
             CallableStatement call = conn.prepareCall(sql)) {
            call.setString(1, userID);
            call.setString(2, userPwd);
            call.setString(3, userType);

            call.execute();
            try (ResultSet rs = call.getResultSet()) {
                if (rs.next()) {
                    member = Member.Builder.from(rs);
                }
            }
            return member;
        } catch (SQLException e) {
            throw new FailedToAccessLoginDataException(LoginPage.DB_ACCESS_ERROR_LOGIN.toString(), e);
        }
    }

    public boolean register(User user) {
        String sql = "call register(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
                CallableStatement call = conn.prepareCall(sql)) {
            call.setString(1, user.getId());
            call.setString(2, user.getPwd());
            call.setString(3, user.getName());
            call.setString(4, user.getPhone());
            call.setString(5, user.getEmail());
            call.setString(6, user.getCompanyCode());
            call.setString(7, user.getAddress());
            call.setString(8, user.getType());

            call.execute();

            int affected = call.getInt(9);
            return affected > 0;
        } catch (SQLException e) {
            throw new FailedToAccessLoginDataException(LoginPage.DB_ACCESS_ERROR_REGISTER.toString(), e);
        }
    }

    public String findID(String userEmail) {
        String sql = "{call find_userID(?, ?)}";
        try (Connection conn = DBUtil.getConnection();
             CallableStatement call = conn.prepareCall(sql)) {
            call.setString(1, userEmail);
            call.registerOutParameter(2, Types.VARCHAR);

            call.execute();

            return call.getString(2);
        } catch (SQLException e) {
            throw new FailedToAccessLoginDataException(LoginPage.DB_ACCESS_ERROR_FIND_ID.toString(), e);
        }
    }

    public boolean updatePassword(String userID, String newPwd) {
        String sql = "{call update_pwd(?, ?, ?)}";
        try (Connection conn = DBUtil.getConnection();
             CallableStatement call = conn.prepareCall(sql)) {
            call.setString(1, userID);
            call.setString(2, newPwd);
            call.registerOutParameter(3, Types.INTEGER);

            call.execute();

            int affected = call.getInt(3);
            return affected == 1;
        } catch (SQLException e) {
            throw new FailedToAccessLoginDataException(LoginPage.DB_ACCESS_ERROR_UPDATE_PASSWORD.toString(), e);
        }
    }

    public String logout(String userID) {
        String sql = "call logout(?, ?)";
        try (Connection conn = DBUtil.getConnection();
             CallableStatement call = conn.prepareCall(sql)) {
            call.setString(1, userID);
            call.registerOutParameter(2, Types.VARCHAR);

            call.execute();

            return call.getString(2);
        } catch (SQLException e) {
            throw new FailedToAccessLoginDataException(LoginPage.DB_ACCESS_ERROR_LOGOUT.toString(), e);
        }
    }
}
