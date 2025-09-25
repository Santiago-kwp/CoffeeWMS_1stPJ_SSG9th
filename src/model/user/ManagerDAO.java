package model.user;

import config.DBUtil;
import domain.user.Manager;
import domain.user.Member;
import domain.user.User;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

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
        String sql = "{call manager_update(?, ?, ?, ?, ?, ?, ?)}";
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

    public String searchUserTypeBy(String targetID) {
        String sql = "{call other_user_type(?, ?)}";
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
}
