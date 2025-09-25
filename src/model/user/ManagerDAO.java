package model.user;

import config.DBUtil;
import domain.user.Manager;
import domain.user.User;

import java.sql.CallableStatement;
import java.sql.Connection;
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

    public User searchUser(String targetID) {
        if (targetID.equals(manager.getId())) {
            return searchUserDetails();
        }
        String sql = "{}";

        return null;
    }
}
