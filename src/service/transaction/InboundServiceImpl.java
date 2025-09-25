package service.transaction;

import domain.transaction.Coffee;
import domain.transaction.InboundRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.transaction.InboundDao;

/**
 * InboundServiceImpl
 * Implementation of the InboundService interface.
 * It handles the business logic and delegates data access to the DAO.
 */
public class InboundServiceImpl implements InboundService {

  private InboundDao inboundDao;
  private Connection conn;

  // In a real application, you would use dependency injection (e.g., Spring)
  public InboundServiceImpl(Connection connection) {
    this.conn = connection;
    this.inboundDao = new InboundDao(conn);
  }

  @Override
  public void submitInboundRequest(InboundRequest request) {
    System.out.println("서비스: 입고 요청서를 제출 중입니다...");
    boolean success = inboundDao.callSubmitInboundRequestProcedure(
        request.getInboundRequestId(),
        request.getMemberId(),
        request.getManagerId(),
        request.getRequestItemsJson(),
        new java.sql.Date(request.getInboundRequestDate().getTime())
    );
    if (success) {
      System.out.println("서비스: 입고 요청이 성공적으로 수행되었습니다.");
    } else {
      System.out.println("서비스: 입고 요청이 실패했습니다.");
    }
  }

  @Override
  public List<Coffee> getAllCoffees() {
    return inboundDao.getAllCoffees();
  }

  /**
   * 특정 회원의 미승인 입고 요청 목록을 조회하는 메서드입니다.
   *
   * @param memberId 입고 요청을 조회할 회원의 ID
   * @return 미승인 입고 요청 목록
   * @throws SQLException 데이터베이스 접근 중 오류 발생 시
   */
  @Override
  public List<Map<String, Object>> getUnapprovedRequestsByMember(String memberId) throws SQLException {
    // DAO 메서드를 호출하여 미승인 요청 목록을 가져옵니다.
    return inboundDao.getUnapprovedRequestsByMember(memberId);
  }

  @Override
  public List<Map<String, Object>> getApprovedRequestsByMember(String memberId) throws SQLException {
    // DAO 메서드를 호출하여 미승인 요청 목록을 가져옵니다.
    return inboundDao.getApprovedRequestsByMember(memberId);
  }

}
