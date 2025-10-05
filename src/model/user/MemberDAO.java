package model.user;

import config.user.DBUtil;
import constant.user.UserPage;
import domain.user.Member;
import domain.user.User;
import exception.user.UserDeleteFailedException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class MemberDAO implements UserDAO {

    private Member member;

    public MemberDAO(Member loginUser) {
        this.member = loginUser;
    }

    @Override
    public Member searchUserDetails() {
        return member;
    }

    @Override
    public Member updateUserInfo(User newInfo) {
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
            member = Member.Builder.from(member, newInfo);
            return member;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
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
