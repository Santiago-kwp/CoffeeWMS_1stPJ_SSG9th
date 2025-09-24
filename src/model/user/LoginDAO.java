package model.user;

import config.DBUtil;
import domain.user.Manager;
import domain.user.Member;
import domain.user.User;

import java.sql.*;

public class LoginDAO {

    public String getUserType(String userID, String userPwd) throws SQLException {
        String sql = "{call get_user_type(?, ?, ?)}";
        String userType;
        try (Connection conn = DBUtil.getConnection();
             CallableStatement call = conn.prepareCall(sql)) {
            call.setString(1, userID);
            call.setString(2, userPwd);
            call.registerOutParameter(3, Types.VARCHAR);
            call.execute();

            userType = call.getString(3);
        }
        return userType;
    }

    public User login(String userID, String userPwd) {
        try {
            String userType = getUserType(userID, userPwd);
            if (userType.contains("관리자")) {
                return loginManager(userID, userPwd, userType);
            } else {
                return loginMember(userID, userPwd, userType);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Manager loginManager(String userID, String userPwd, String userType) throws SQLException {
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
        }
        return manager;
    }

    public Member loginMember(String userID, String userPwd, String userType) throws SQLException {
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
        }
        return member;
    }

    public boolean register(User user) {
        String sql = "call register(?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection(); CallableStatement call = conn.prepareCall(sql)) {
            call.setString(1, user.getId());
            call.setString(2, user.getPwd());
            call.setString(3, user.getName());
            call.setString(4, user.getPhone());
            call.setString(5, user.getEmail());
            call.setString(6, user.getCompanyCode());
            call.setString(7, user.getAddress());
            call.setString(8, user.getType());

            int affected = call.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
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

    public String findPassword(String userID) {
        String sql = "{call find_pwd(?, ?)}";
        String foundPwd = null;
        try (Connection conn = DBUtil.getConnection();
             CallableStatement call = conn.prepareCall(sql)) {
            call.setString(1, userID);
            call.registerOutParameter(2, Types.VARCHAR);

            call.execute();

            foundPwd = call.getString(2);
            return foundPwd;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return foundPwd;
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
