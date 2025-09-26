package model.inventory;

import config.DBUtil;
import constant.inventory.Role;
import domain.inventory.UserVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
  /**
   * 사용자 ID를 기반으로 managers 테이블과 members 테이블을 조회하여
   * 사용자의 실제 역할(Role)을 찾아 UserVO 객체를 생성하여 반환합니다.
   * @param userId 로그인 시도하는 사용자 ID
   * @return 사용자 정보(ID, Role)가 담긴 UserVO 객체, 없으면 null
   */
  public UserVO findUserById(String userId) {
    // managers 테이블과 members 테이블을 동시에 조회하는 SQL
    String sql = "SELECT user_id, user_role FROM (" +
        "  SELECT manager_id AS user_id, manager_position AS user_role FROM managers" +
        "  UNION ALL" +
        "  SELECT member_id AS user_id, '일반회원' AS user_role FROM members" +
        ") AS all_users " +
        "WHERE user_id = ?";

    try (Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setString(1, userId);

      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          // 사용자를 찾았을 경우
          String id = rs.getString("user_id");
          String roleString = rs.getString("user_role");

          // DB에서 가져온 문자열('총관리자')을 Role Enum 타입으로 변환
          Role role = Role.fromString(roleString);

          if (role != null) {
            return new UserVO(id, role);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    // 사용자를 찾지 못했거나 에러가 발생한 경우
    return null;
  }
}
