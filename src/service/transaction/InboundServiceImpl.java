package service.transaction;

import config.DBUtil;
import constant.transaction.ErrorCode; // ErrorCode 사용
import domain.transaction.dto.InboundRequestDTO;
import domain.transaction.dto.InboundRequestItemDTO;
import domain.transaction.vo.InboundStatus;
import exception.transaction.TransactionException; // 사용자 정의 예외 사용
import model.transaction.InboundRequestDAO;
import model.transaction.InboundRequestItemDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class InboundServiceImpl implements InboundService {

  // 의존성을 final 필드로 선언
  private final InboundRequestDAO inboundRequestDAO;
  private final InboundRequestItemDAO inboundRequestItemDAO;

  /**
   * 생성자를 통해 의존성을 주입받습니다.
   * @param inboundRequestDAO InboundRequestDAO의 구현체
   * @param inboundRequestItemDAO InboundRequestItemDAO의 구현체
   */
  public InboundServiceImpl(InboundRequestDAO inboundRequestDAO, InboundRequestItemDAO inboundRequestItemDAO) {
    // 내부에서 직접 생성하지 않고, 외부에서 받은 객체를 할당
    this.inboundRequestDAO = inboundRequestDAO;
    this.inboundRequestItemDAO = inboundRequestItemDAO;
  }


  @Override
  public Long requestInbound(InboundRequestDTO requestDto) throws SQLException, TransactionException {
    // 1. 기본값 설정 및 유효성 검사
    requestDto.setStatus(InboundStatus.PENDING);

    // 요청 날짜는 컨트롤러에서 사용자가 입력한 값이 DTO에 설정되어 있다고 가정합니다.
    if (requestDto.getRequestDate() == null) {
      // 혹시라도 입력되지 않았다면 현재 날짜로 설정 (선택 사항)
      requestDto.setRequestDate(LocalDate.now());
    }

    if (requestDto.getItems() == null || requestDto.getItems().isEmpty()) {
      throw new TransactionException(ErrorCode.EMPTY_INBOUND_ITEMS);
    }

    // 2. DAO 호출 (프로시저 사용으로 트랜잭션 자동 관리)
    return inboundRequestDAO.insertInboundRequest(requestDto);
  }

  @Override
  public void modifyPendingInbound(InboundRequestDTO requestDto, String memberId) throws SQLException, TransactionException {
    InboundRequestDTO originalRequest = getInboundRequestDetail(requestDto.getInboundRequestId());

    // 비즈니스 규칙 검증
    if (!originalRequest.getMemberId().equals(memberId)) {
      throw new TransactionException(ErrorCode.UNAUTHORIZED_ACCESS); // 예: 권한 없음
    }
    if (originalRequest.getStatus() != InboundStatus.PENDING) {
      throw new TransactionException(ErrorCode.INVALID_STATUS_FOR_OPERATION); // 예: 대기 상태 아님
    }
    if (requestDto.getItems() == null || requestDto.getItems().isEmpty()) {
      throw new TransactionException(ErrorCode.EMPTY_INBOUND_ITEMS);
    }

    // 서비스 계층 트랜잭션 관리 (수정 작업은 여러 단계이므로)
    Connection conn = null;
    try {
      conn = DBUtil.getConnection();
      conn.setAutoCommit(false); // 트랜잭션 시작

      // 1. 기존 아이템 전체 삭제 (InboundRequestItemDAO에 추가된 메서드 사용)
      inboundRequestItemDAO.deleteItemsByInboundRequestId(conn, requestDto.getInboundRequestId());

      // 2. 새로운 아이템 추가 (InboundRequestItemDAO에 추가된 메서드 사용)
      for (InboundRequestItemDTO item : requestDto.getItems()) {
        item.setInboundRequestId(requestDto.getInboundRequestId()); // ID 연결 보장
      }
      inboundRequestItemDAO.insertItems(conn, requestDto.getItems());

      // 만약 헤더 정보(예: 요청 날짜)도 수정해야 한다면 여기서 inboundRequestDAO.updateInboundRequest(conn, requestDto) 호출
      // (현재 DAO의 update는 conn을 받지 않으므로 수정 필요, 혹은 프로시저 사용 고려)
      // 간단하게는 아이템만 수정하는 것으로 가정.

      conn.commit(); // 성공 시 커밋
    } catch (SQLException e) {
      if (conn != null) conn.rollback(); // 실패 시 롤백
      throw e; // 혹은 new TransactionException(ErrorCode.DB_ERROR)로 감싸기
    } finally {
      if (conn != null) {
        conn.setAutoCommit(true);
        conn.close();
      }
    }
  }

  @Override
  public void cancelPendingInbound(Long inboundRequestId, String memberId) throws SQLException, TransactionException {
    InboundRequestDTO request = inboundRequestDAO.findInboundRequestById(inboundRequestId);

    // 비즈니스 규칙 검증
    if (request == null) {
      throw new TransactionException(ErrorCode.INBOUND_REQUEST_NOT_FOUND);
    }
    if (!request.getMemberId().equals(memberId)) {
      throw new TransactionException(ErrorCode.UNAUTHORIZED_ACCESS);
    }
    if (request.getStatus() != InboundStatus.PENDING) {
      throw new TransactionException(ErrorCode.INVALID_STATUS_FOR_OPERATION);
    }

    // DAO 호출 (논리적 삭제)
    inboundRequestDAO.deleteInboundRequest(inboundRequestId);
  }

  @Override
  public void approveInbound(Long inboundRequestId, String managerId) throws SQLException, TransactionException {
    InboundRequestDTO request = inboundRequestDAO.findInboundRequestById(inboundRequestId);

    // 비즈니스 규칙 검증
    if (request == null) {
      throw new TransactionException(ErrorCode.INBOUND_REQUEST_NOT_FOUND);
    }
    if (request.getStatus() != InboundStatus.PENDING) {
      throw new TransactionException(ErrorCode.INVALID_STATUS_FOR_OPERATION);
    }

    // DTO 상태 업데이트
    request.setStatus(InboundStatus.APPROVED);
    request.setManagerId(managerId);
    request.setApprovalDate(LocalDate.now()); // 승인 날짜는 현재
    request.setInboundReceipt("RCPT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase()); // 영수증 번호 생성

    // DAO 호출
    inboundRequestDAO.updateInboundRequest(request);
  }

  @Override
  public void rejectInbound(Long inboundRequestId, String managerId) throws SQLException, TransactionException {
    InboundRequestDTO request = inboundRequestDAO.findInboundRequestById(inboundRequestId);

    if (request == null) {
      throw new TransactionException(ErrorCode.INBOUND_REQUEST_NOT_FOUND);
    }
    if (request.getStatus() != InboundStatus.PENDING) {
      throw new TransactionException(ErrorCode.INVALID_STATUS_FOR_OPERATION);
    }

    request.setStatus(InboundStatus.REJECTED);
    request.setManagerId(managerId);
    request.setApprovalDate(LocalDate.now()); // 거절 날짜는 현재

    inboundRequestDAO.updateInboundRequest(request);
  }

  @Override
  public InboundRequestDTO getInboundRequestDetail(Long inboundRequestId) throws SQLException, TransactionException {
    // 1. 헤더 조회
    InboundRequestDTO headerDto = inboundRequestDAO.findInboundRequestById(inboundRequestId);
    if (headerDto == null) {
      throw new TransactionException(ErrorCode.INBOUND_REQUEST_NOT_FOUND);
    }

    // 2. 상세 항목 조회
    List<InboundRequestItemDTO> items = inboundRequestItemDAO.findItemsByInboundRequestId(inboundRequestId);
    headerDto.setItems(items);

    return headerDto;
  }

  @Override
  public List<InboundRequestDTO> getInboundRequestsByMember(String memberId, InboundStatus status) throws SQLException {
    // 목록 조회는 일반적으로 상세 항목까지 모두 가져오진 않음 (성능상 이유)
    // 필요하다면 여기서 반복문으로 채워넣거나, 조인 쿼리를 사용하는 DAO 메서드를 만들어야 함.
    return inboundRequestDAO.findInboundRequestsByMemberId(memberId, status);
  }

  @Override
  public List<InboundRequestDTO> getAllInboundRequestsByStatus(InboundStatus status) throws SQLException {
    return inboundRequestDAO.findAllInboundRequestsByStatus(status);
  }
}