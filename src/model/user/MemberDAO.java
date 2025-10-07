package model.user;

import config.DBUtil;
import constant.user.UserPage;
import domain.user.Member;
import domain.user.User;
import exception.user.FailedToUserUpdateException;
import exception.user.UserDeleteFailedException;
import exception.user.UserNotFoundException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class MemberDAO implements UserDAO {

    private static class LazyHolder {
        private static final MemberDAO MEMBER_DAO = new MemberDAO();
    }

    private MemberDAO() {
    }

    public static MemberDAO getInstance() {
        return LazyHolder.MEMBER_DAO;
    }

    @Override
    public Member searchUserDetails(String memberID) {
        String sql = "{call current_member_info(?)}";
        try (Connection conn = DBUtil.getConnection();
                CallableStatement call = conn.prepareCall(sql)) {
            call.setString(1, memberID);
            call.execute();

            try (ResultSet rs = call.getResultSet()) {
                if (!rs.next()) {
                    return null;
                }
                return Member.Builder.from(rs);
            }
        } catch (SQLException e) {
            throw new UserNotFoundException(UserPage.CANNOT_SEARCH_USER.toString(), e);
        }
    }

    @Override
    public Member updateUserInfo(User original, User newInfo) {
        String sql = "{call member_update(?, ?, ?, ?, ?, ?, ?)}";
        try (Connection conn = DBUtil.getConnection();
             CallableStatement call = conn.prepareCall(sql)) {
            call.setString(1, original.getId());
            call.setString(2, newInfo.getPwd());
            call.setString(3, newInfo.getName());
            call.setString(4, newInfo.getPhone());
            call.setString(5, newInfo.getEmail());
            call.setString(6, newInfo.getCompanyCode());
            call.setString(7, newInfo.getAddress());

            call.execute();

            // 현재 사용자 정보 갱신 -> 다음에 자신의 정보 조회할 때 반영해야 함
            return Member.Builder.from((Member)original, newInfo);
        } catch (SQLException e) {
            throw new FailedToUserUpdateException(UserPage.USER_UPDATE_FAILED.toString(), e);
        }
    }

    @Override
    public boolean deleteUserInfo(String memberID) {   // 회원 탈퇴
        String sql = "{call member_delete(?, ?)}";
        try (Connection conn = DBUtil.getConnection();
             CallableStatement call = conn.prepareCall(sql)) {
            call.setString(1, memberID);
            call.registerOutParameter(2, Types.INTEGER);
            call.execute();

            int affected = call.getInt(2);
            return affected > 0;
        } catch (SQLException e) {
            throw new UserDeleteFailedException(UserPage.USER_DELETE_FAILED.toString(), e);
        }
    }
}
