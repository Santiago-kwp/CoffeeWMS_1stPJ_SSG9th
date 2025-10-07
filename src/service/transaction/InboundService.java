package service.transaction;

import domain.transaction.Coffee;
import domain.transaction.dto.InboundRequestDTO;
import domain.transaction.vo.InboundStatus;
import exception.transaction.TransactionException; // 사용자 정의 예외

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface InboundService {

  Long requestInbound(InboundRequestDTO requestDto) throws SQLException, TransactionException;
  void modifyPendingInbound(InboundRequestDTO requestDto, String memberId) throws SQLException, TransactionException;
  void cancelPendingInbound(Long inboundRequestId, String memberId) throws SQLException, TransactionException;
  void approveInbound(Long inboundRequestId, String managerId, String locationPlaceId) throws SQLException, TransactionException;
  void rejectInbound(Long inboundRequestId, String managerId) throws SQLException, TransactionException;
  InboundRequestDTO getInboundRequestDetail(Long inboundRequestId) throws SQLException, TransactionException;
  List<InboundRequestDTO> getInboundRequestsByMember(String memberId, InboundStatus status) throws SQLException;
  List<InboundRequestDTO> getAllInboundRequestsByStatus(InboundStatus status) throws SQLException;

  /**
   * 주문 가능한 모든 커피 상품 목록을 조회합니다.
   * @return Coffee DTO 리스트
   */
  List<Coffee> getAvailableCoffees() throws SQLException;

  /**
   * 관리자를 위해 '대기' 상태인 모든 입고 요청 목록을 상세 정보와 함께 조회합니다.
   * @return 상세 항목(items)이 채워진 InboundRequestDTO 리스트
   */
  List<InboundRequestDTO> getPendingInboundListForAdmin() throws SQLException;

  /*
  기간 관련 상세 조회 메소드
   */
  List<InboundRequestDTO> getInboundRequestsByMemberAndDateRange(String memberId, InboundStatus status, LocalDate startDate, LocalDate endDate) throws SQLException;
  List<InboundRequestDTO> getInboundRequestsByMemberAndMonth(String memberId, InboundStatus status, int year, int month) throws SQLException;
  List<InboundRequestDTO> getAllInboundRequestsByDateRange(InboundStatus status, LocalDate startDate, LocalDate endDate) throws SQLException;
  List<InboundRequestDTO> getAllInboundRequestsByMonth(InboundStatus status, int year, int month) throws SQLException;



}