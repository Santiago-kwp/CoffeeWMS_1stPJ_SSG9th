package service.transaction;

import config.DBUtil;
import domain.transaction.Coffee;
import domain.transaction.InboundItem;
import domain.transaction.InboundRequest;
import domain.transaction.LocationPlace;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
  public void updateInboundRequest(InboundRequest request) {
    System.out.println("서비스: 수정한 입고 요청서를 제출 중입니다...");
    boolean success = inboundDao.updateInboundRequestProcedure(
            request.getInboundRequestId(),
            request.getMemberId(),
            request.getManagerId(),
            request.getRequestItemsJson(),
            new java.sql.Date(request.getInboundRequestDate().getTime())
    );
    if (success) {
      System.out.println("서비스: 입고 수정 요청이 성공적으로 수행되었습니다.");
    } else {
      System.out.println("서비스: 입고 수정 요청이 실패했습니다.");
    }
  }

  @Override
  public void deleteInboundRequest(String requestId, String memberId) {
    System.out.println("서비스: 입고 요청서를 삭제 중입니다...");
    boolean success = inboundDao.deleteInboundRequestProcedure(requestId, memberId);
    if (success) {
      System.out.println("서비스: 입고 삭제 요청이 성공적으로 수행되었습니다.");
    } else {
      System.out.println("서비스: 입고 삭제 요청이 실패했습니다.");
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
  public List<InboundItem> getInboundRequestItems(String memberId, String requestId) throws SQLException {
    return inboundDao.getInboundRequestItems(memberId, requestId);
  }


  @Override
  public List<InboundItem> getUnapprovedRequestsByMember(String memberId) throws SQLException {
    // DAO 메서드를 호출하여 미승인 요청 목록을 가져옵니다.
    return inboundDao.getUnapprovedRequestsByMember(memberId);
  }

  @Override
  public List<InboundItem> getApprovedRequestsByMember(String memberId) throws SQLException {
    // DAO 메서드를 호출하여 미승인 요청 목록을 가져옵니다.
    return inboundDao.getApprovedRequestsByMember(memberId);
  }

  @Override
  public Map<String, Integer> getMemberUnapprovedInboundRequests() {
    // DAO 메소드를 호출하여 미승인 요청이 있는 회원의 ID 목록 및 요청 건수를 가져옵니다.
    return inboundDao.getAllMemberHasUnapprovedInboundRequest();
  }

  @Override
  public Map<String, Integer> getMemberApprovedInboundRequests() {
    return inboundDao.getMemberApprovedInboundRequests();
  }

  @Override
  public void processInboundRequest(String inboundRequestId, List<InboundItem> items) throws SQLException {
    System.out.println("서비스: 입고 요청 승인 및 입고 상품을 적치 중입니다...");
    inboundDao.processInboundRequest(inboundRequestId, items);
    System.out.println("서비스 : 입고 요청 승인 완료 및 상품들이 아래의 위치에 적치되었습니다.");
    for (InboundItem item : items) {
      System.out.printf("상품 ID : %s, 수량 : %d, 적치 위치 ID : %s\n",item.getCoffeeId(),item.getQuantity(), item.getLocationPlaceId());
    }
  }

  @Override
  public List<InboundItem> showInboundPeriod(String memberId, Date startDate, Date endDate) throws SQLException {
    return inboundDao.showInboundPeriod(memberId, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
  }

  @Override
  public List<InboundItem> showInboundMonth(String memberId, int month) throws SQLException {
    return inboundDao.showInboundMonth(memberId, month);
  }

}
