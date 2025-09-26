package service.transaction;

import config.DBUtil;
import domain.transaction.Coffee;
import domain.transaction.InboundRequest;
import domain.transaction.LocationPlace;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.stream.Location;
import model.transaction.InboundDao;

/**
 * InboundServiceImpl
 * Implementation of the InboundService interface.
 * It handles the business logic and delegates data access to the DAO.
 */
public class InboundServiceImpl implements InboundService {

  private InboundDao inboundDao;

  // 연결 객체를 받고, DAO 메소드를 사용하기 위해 의존성 주입
  public InboundServiceImpl() {
    this.inboundDao = new InboundDao();
  }

  // 사용자의 입고 요청을 DB에 제출하는 메소드
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
    // DAO 메소드를 호출하여 전체 커피 상품 목록을 가져옵니다.
    return inboundDao.getAllCoffees();
  }

  @Override
  public List<LocationPlace> getAvailableLocationPlaces() {
    return inboundDao.getAvailableLocationPlaces();
  }


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

  @Override
  public Map<String, Integer> getMemberUnapprovedInboundRequests() {
    // DAO 메소드를 호출하여 미승인 요청이 있는 회원의 ID 목록 및 요청 건수를 가져옵니다.
    return inboundDao.getAllMemberHasUnapprovedInboundRequest();
  }

}
