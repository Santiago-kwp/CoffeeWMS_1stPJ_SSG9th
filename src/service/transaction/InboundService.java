package service.transaction;

import domain.transaction.Coffee;
import domain.transaction.InboundRequest;
import domain.transaction.LocationPlace;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import view.transaction.InboundView;

/**
 * InboundService
 * Interface defining the business logic for inbound requests.
 */
public interface InboundService {
  void submitInboundRequest(InboundRequest request);
  // Add other methods for processing, viewing, etc.
  List<Coffee> getAllCoffees();
  List<LocationPlace> getAvailableLocationPlaces();

  /**
   * 특정 회원의 미승인 입고 요청 목록을 조회합니다.
   * @param memberId 입고 요청을 조회할 회원의 ID
   * @return 미승인 입고 요청 목록 (입고 상품, 수량, 입고 날짜 포함)
   * @throws SQLException 데이터베이스 접근 중 오류 발생 시
   */
  List<Map<String, Object>> getUnapprovedRequestsByMember(String memberId) throws SQLException;
  List<Map<String, Object>> getApprovedRequestsByMember(String memberId) throws SQLException;
  Map<String, Integer> getMemberUnapprovedInboundRequests();
}