package model.user;

import config.DBUtil;
import constant.user.UserPage;
import domain.user.Member;
import domain.user.User;
import exception.user.UserDeleteFailedException;
import exception.user.FailedToUserUpdateException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class MemberDAO implements UserDAO {

    private final Member member;

    public MemberDAO(Member loginUser) {
        this.member = loginUser;
    }

    @Override
    public Member searchUserDetails() {
        return member;
    }

    @Override
    public boolean updateUserInfo(User newInfo) {
        String sql = "{call member_update(?, ?, ?, ?, ?, ?, ?)}";
        try (Connection conn = DBUtil.getConnection();
             CallableStatement call = conn.prepareCall(sql)) {
            call.setString(1, member.getId());
            call.setString(2, newInfo.getPwd());
            call.setString(3, newInfo.getName());
            call.setString(4, newInfo.getPhone());
            call.setString(5, newInfo.getEmail());
            call.setString(6, newInfo.getCompanyCode());
            call.setString(7, newInfo.getAddress());

            call.execute();

            // 현재 사용자 정보 갱신 -> 다음에 자신의 정보 조회할 때 반영해야 함
            member.setPwd(newInfo.getPwd());
            member.setName(newInfo.getName());
            member.setPhone(newInfo.getPhone());
            member.setEmail(newInfo.getEmail());
            member.setCompanyCode(newInfo.getCompanyCode());
            member.setAddress(newInfo.getAddress());

            return true;
        } catch (SQLException e) {
            throw new FailedToUserUpdateException(UserPage.USER_UPDATE_FAILED.toString(), e);
        }
    }

    @Override
    public boolean deleteUserInfo() {   // 회원 탈퇴
        String sql = "{call member_delete(?, ?)}";
        try (Connection conn = DBUtil.getConnection();
             CallableStatement call = conn.prepareCall(sql)) {
            call.setString(1, member.getId());
            call.registerOutParameter(2, Types.INTEGER);
            call.execute();

            int affected = call.getInt(2);
            return affected > 0;
        } catch (SQLException e) {
            throw new UserDeleteFailedException(UserPage.USER_DELETE_FAILED.toString(), e);
        }
    }
}
