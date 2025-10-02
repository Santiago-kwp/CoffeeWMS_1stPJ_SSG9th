package model.user;

import config.DBUtil;
import constant.user.LoginPage;
import constant.user.UserPage;
import domain.user.Manager;
import domain.user.Member;
import domain.user.User;
import exception.user.UserNotFoundException;
import exception.user.FailedToUserRegisterException;
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
            throw new UserNotFoundException(UserPage.CANNOT_SEARCH_USER_TYPE.toString(), e);
        }
        return userType;
    }

    public User login(String userID, String userPwd) {
        try {
            String userType = searchUserTypeBy(userID, userPwd);
            if (userType == null) {
                throw new UserNotFoundException(LoginPage.USER_NOT_EXIST.toString());
            }
            if (userType.endsWith("관리자")) {
                return loginManager(userID, userPwd, userType);
            }
            return loginMember(userID, userPwd, userType);
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Manager loginManager(String userID, String userPwd, String userType) {
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
                    manager = new Manager(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(8)
                    );
                    manager.setLogin(rs.getBoolean(6));
                    manager.setHireDate(rs.getDate(7));
                }
            } while (call.getMoreResults());
        } catch (SQLException e) {
            throw new UserNotFoundException(LoginPage.USER_NOT_EXIST.toString(), e);
        }
        return manager;
    }

    public Member loginMember(String userID, String userPwd, String userType) {
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
                    member = new Member(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5)
                    );
                    member.setCompanyCode(rs.getString(6));
                    member.setAddress(rs.getString(7));
                    member.setLogin(rs.getBoolean(8));
                    member.setStart_date(rs.getDate(9));
                    member.setExpired_date(rs.getDate(10));
                }
            }
        } catch (SQLException e) {
            throw new UserNotFoundException(LoginPage.USER_NOT_EXIST.toString(), e);
        }
        return member;
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
            throw new FailedToUserRegisterException(LoginPage.REGISTER_FAILED.toString());
        }
    }

    public String findID(String userEmail) {
        String sql = "{call find_userID(?, ?)}";
        String foundID = null;
        try (Connection conn = DBUtil.getConnection();
             CallableStatement call = conn.prepareCall(sql)) {
            call.setString(1, userEmail);
            call.registerOutParameter(2, Types.VARCHAR);

            call.execute();

            foundID = call.getString(2);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return foundID;
    }

    public boolean isExistID(String userID) {
        String sql = "{call has_userID(?, ?)}";
        try (Connection conn = DBUtil.getConnection();
             CallableStatement call = conn.prepareCall(sql)) {
            call.setString(1, userID);
            call.registerOutParameter(2, Types.BOOLEAN);

            call.execute();

            return call.getBoolean(2);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
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
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void logout(String userID) {
        String sql = "call logout(?, ?)";
        try (Connection conn = DBUtil.getConnection();
             CallableStatement call = conn.prepareCall(sql)) {
            call.setString(1, userID);
            call.registerOutParameter(2, Types.VARCHAR);

            call.execute();

            String result = call.getString(2);
            if (result != null) {
                System.out.println(result);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
