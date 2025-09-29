package model.inventory;



import config.DBUtil;

import constant.inventory.ErrorMessage;
import domain.inventory.DueDiligenceVO;
import domain.inventory.UserVO;
import exception.inventory.DataNotFoundException;
import exception.inventory.DuplicateKeyException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 재고 실사 관련 데이터베이스 작업을 처리하는 DAO.
 */
public class DueDiligenceDAO {

  /**
   * 재고 실사 목록 조회를 위해 get_due_diligence_list 프로시저를 호출합니다.
   */
  public List<DueDiligenceVO> getDueDiligenceList(UserVO user, String coffeeName, String approvalStatus, int sortKey, boolean isFlipped) {
    List<DueDiligenceVO> list = new ArrayList<>();
    String sql = "{call get_due_diligence_list(?, ?, ?, ?, ?)}";

    try (Connection conn = DBUtil.getConnection();
        CallableStatement cstmt = conn.prepareCall(sql)) {

      cstmt.setString(1, user.getUserId());
      cstmt.setString(2, coffeeName);
      cstmt.setString(3, approvalStatus);
      cstmt.setInt(4, sortKey);
      cstmt.setBoolean(5, isFlipped);

      try (ResultSet rs = cstmt.executeQuery()) {
        while(rs.next()) {
          DueDiligenceVO diligence = new DueDiligenceVO();
          diligence.setDueDiligenceId(rs.getString("due_diligence_id"));
          diligence.setInventoryId(rs.getString("inventory_id"));
          diligence.setCoffeeName(rs.getString("coffee_name"));
          diligence.setLog(rs.getString("due_diligence_log"));
          diligence.setDate(rs.getDate("due_diligence_date"));
          diligence.setApprovalStatus(rs.getString("due_diligence_approval"));
          list.add(diligence);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  /**
   * 실사 관리(CUD, 승인, 반려)를 위해 manage_due_diligence 프로시저를 호출하는 private 헬퍼 메소드
   */
  private int executeManageDiligence(String userId, String mode, String diligenceId, String inventoryId, String log, java.util.Date date) {
    String sql = "{call manage_due_diligence(?, ?, ?, ?, ?, ?)}";
    int result = 0;
    Connection conn = null; // Connection 객체를 try-with-resources 밖으로 뺍니다.

    try {
      // 1. 데이터베이스 연결
      conn = DBUtil.getConnection();

      // 2. [핵심] 트랜잭션 시작: AutoCommit 모드를 비활성화
      conn.setAutoCommit(false);

      // 3. 프로시저 호출 실행
      try (CallableStatement cstmt = conn.prepareCall(sql)) {
        cstmt.setString(1, userId);
        cstmt.setString(2, mode);
        cstmt.setString(3, diligenceId);
        cstmt.setString(4, inventoryId);
        cstmt.setString(5, log);
        if (date != null) {
          cstmt.setDate(6, new java.sql.Date(date.getTime()));
        } else {
          cstmt.setNull(6, java.sql.Types.DATE);
        }
        result = cstmt.executeUpdate();
      }

      // 4. [핵심] 작업 성공 시 DB에 영구 저장 (COMMIT)
      conn.commit();

    } catch (SQLException e) {
      // 롤백 로직
      if (conn != null) {
        try {
          conn.rollback();
        } catch (SQLException ex) {
          ex.printStackTrace();
        }
      }

      // MySQL 에러 코드를 분석하여 적절한 커스텀 예외 발생
      if (e.getErrorCode() == 1062) { // 1062: Duplicate entry for key (PK/UNIQUE 중복)
        throw new DuplicateKeyException(ErrorMessage.DUPLICATE_DILIGENCE_ID.getMessage());
      } else if (e.getErrorCode() == 1452) { // 1452: Foreign key constraint fails (FK 위반)
        throw new DataNotFoundException(ErrorMessage.INVENTORY_NOT_FOUND.getMessage());
      } else {
        // 그 외의 SQL 오류는 로깅 후 일반적인 예외 발생
        e.printStackTrace();
        throw new RuntimeException(ErrorMessage.DB_PROCESSING_ERROR.getMessage());
      }

    } finally {
      if (conn != null) {
        try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); }
      }
    }
    // result == 0 일 때 발생하는 예외 메시지도 ErrorMessage enum을 사용합니다.
    if (!mode.equals("INSERT") && result == 0) {
      throw new DataNotFoundException(ErrorMessage.DILIGENCE_NOT_FOUND.getMessage());
    }
    // =================================================================
    return result;
  }

  public int createDueDiligence(UserVO user, DueDiligenceVO diligence) {
    return executeManageDiligence(user.getUserId(), "INSERT", diligence.getDueDiligenceId(), diligence.getInventoryId(), diligence.getLog(), diligence.getDate());
  }

  public int updateDueDiligence(UserVO user, DueDiligenceVO diligence) {
    return executeManageDiligence(user.getUserId(), "UPDATE", diligence.getDueDiligenceId(), null, diligence.getLog(), diligence.getDate());
  }

  public int deleteDueDiligence(UserVO user, String dueDiligenceId) {
    return executeManageDiligence(user.getUserId(), "DELETE", dueDiligenceId, null, null, null);
  }

  public int approveDueDiligence(UserVO user, String dueDiligenceId) {
    return executeManageDiligence(user.getUserId(), "APPROVE", dueDiligenceId, null, null, null);
  }

  public int rejectDueDiligence(UserVO user, String dueDiligenceId) {
    return executeManageDiligence(user.getUserId(), "REJECT", dueDiligenceId, null, null, null);
  }
}