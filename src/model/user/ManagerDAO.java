package model.user;

import config.user.DBUtil;
import domain.user.Manager;
import domain.user.Member;
import domain.user.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ManagerDAO implements UserDAO {

    private final Manager manager;

    public ManagerDAO(Manager manager) {
        this.manager = manager;
    }

    @Override
    public Manager searchUserDetails() {
        return manager;
    }

    @Override
    public boolean updateUserInfo(User newInfo) {
        String sql = "{call manager_update(?, ?, ?, ?, ?)}";
        try (Connection conn = DBUtil.getConnection();
             CallableStatement call = conn.prepareCall(sql)) {
            call.setString(1, manager.getId());
            call.setString(2, newInfo.getPwd());
            call.setString(3, newInfo.getName());
            call.setString(4, newInfo.getPhone());
            call.setString(5, newInfo.getEmail());

            call.execute();

            // 현재 사용자 정보 갱신 -> 다음에 자신의 정보 조회할 때 반영해야 함
            manager.setPwd(newInfo.getPwd());
            manager.setName(newInfo.getName());
            manager.setPhone(newInfo.getPhone());
            manager.setEmail(newInfo.getEmail());

            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteUserInfo() {
        String sql = "{call manager_delete(?, ?)}";
        try (Connection conn = DBUtil.getConnection();
             CallableStatement call = conn.prepareCall(sql)) {
            call.setString(1, manager.getId());
            call.setInt(2, Types.INTEGER);
            call.execute();

            int affected = call.getInt(2);
            return affected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public String searchUserTypeBy(String targetID, boolean isApproved) {
        String sql = isApproved
                ? "{call other_user_type(?, ?)}"
                : "{call not_approved_user_type(?, ?)}";
        try (Connection conn = DBUtil.getConnection();
                CallableStatement call = conn.prepareCall(sql)) {
            call.setString(1, targetID);
            call.registerOutParameter(2, Types.VARCHAR);

            call.execute();

            return call.getString(2);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public User searchUser(String targetID, String targetType) {
        // 찾을 아이디가 현재 로그인한 회원의 것이라면 자신의 상세정보를 출력
        if (targetID.equals(manager.getId())) {
            return searchUserDetails();
        }
        String sql = (targetType.endsWith("관리자"))
                ? "{call search_other_manager(?)}"
                : "{call search_other_member(?)}";
        try (Connection conn = DBUtil.getConnection();
                CallableStatement call = conn.prepareCall(sql)) {
            call.setString(1, targetID);

            try (ResultSet rs = call.executeQuery()) {
                if (rs.next()) {
                    return (targetType.endsWith("관리자")) ? getManager(rs) : getMember(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    private Member getMember(ResultSet rs) throws SQLException {
        Member member = new Member(
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

        return member;
    }
    private Manager getManager(ResultSet rs) throws SQLException {
        Manager manager = new Manager(
                rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(8)
        );
        manager.setLogin(rs.getBoolean(6));
        manager.setHireDate(rs.getDate(7));

        return manager;
    }

    public List<User> searchAllUser() {
        List<User> allUsers = new ArrayList<>();
        String sql = "{call search_all_users()}";
        try (Connection conn = DBUtil.getConnection();
             CallableStatement call = conn.prepareCall(sql)) {

            call.execute();

            try (ResultSet rs = call.getResultSet()) {
                while (rs.next()) {
                    String userID = rs.getString("user_id");
                    String userPwd = rs.getString("user_pwd");
                    String userName = rs.getString("user_name");
                    String userPhone = rs.getString("user_phone");
                    String userEmail = rs.getString("user_email");
                    String userType = rs.getString("user_type");

                    allUsers.add(new User(userID, userPwd, userName, userPhone, userEmail, userType));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allUsers;
    }

    public List<User> searchByRole(String groupName) {
        String sql = "{call search_by_role(?)}";

        List<User> searchResult = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             CallableStatement call = conn.prepareCall(sql)) {
            call.setString(1, groupName);
            call.execute();

            try (ResultSet rs = call.executeQuery()) {
                while (rs.next()) {
                    switch (groupName) {
                        case "members" -> searchResult.add(getMember(rs));
                        case "managers" -> searchResult.add(getManager(rs));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return searchResult;
    }

    public boolean approve(String targetID, boolean isRestore) {
        String sql = isRestore
                ? "{call restore_user(?, ?)}"
                : "{call approve_user(?, ?)}";
        try (Connection conn = DBUtil.getConnection();
                CallableStatement call = conn.prepareCall(sql)) {
            call.setString(1, targetID);
            call.registerOutParameter(2, Types.INTEGER);

            call.execute();

            int affected = call.getInt(2);
            return affected == 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean restoreRole(String targetID, String newRole) {
        String sql = "{call restore_role(?, ?, ?)}";
        try (Connection conn = DBUtil.getConnection();
                CallableStatement call = conn.prepareCall(sql)) {
            call.setString(1, targetID);
            call.setString(2, newRole);
            call.registerOutParameter(3, Types.INTEGER);

            call.execute();

            int affected = call.getInt(3);
            return affected == 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean insertCargoToManager(String targetID, int cargoID) {
        String sql = "call add_cargo_to_manager(?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             CallableStatement call = conn.prepareCall(sql)) {
            call.setString(1, targetID);
            call.setInt(2, cargoID);
            call.setInt(3, Manager.CARGO_LIMIT);
            call.registerOutParameter(4, Types.BOOLEAN);

            call.execute();

            return call.getBoolean(4);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteRole(String targetID, String targetType) {
        String sql = "{call delete_role(?, ?, ?)}";
        try (Connection conn = DBUtil.getConnection();
                CallableStatement call = conn.prepareCall(sql)) {
            call.setString(1, targetID);
            call.setString(2, targetType);
            call.registerOutParameter(3, Types.INTEGER);

            call.execute();

            int affected = call.getInt(3);
            return affected == 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
