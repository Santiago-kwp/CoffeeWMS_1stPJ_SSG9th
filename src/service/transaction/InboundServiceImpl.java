package service.transaction;

import config.DBUtil;
import constant.transaction.ErrorCode; // ErrorCode 사용
import domain.transaction.Coffee;
import domain.transaction.dto.InboundRequestDTO;
import domain.transaction.dto.InboundRequestItemDTO;
import domain.transaction.vo.InboundStatus;
import exception.transaction.TransactionException; // 사용자 정의 예외 사용
import model.inventory.CoffeeDAO;
import model.inventory.CoffeeDAOImpl;
import model.inventory.InventoryDAO;
import model.transaction.InboundRequestDAO;
import model.transaction.InboundRequestDAOImpl;
import model.transaction.InboundRequestItemDAO;
import model.transaction.InboundRequestItemDAOImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class InboundServiceImpl implements InboundService {

  // 1. private static final 인스턴스 변수 선언
  private static final InboundServiceImpl instance = new InboundServiceImpl();

  // 2. private 생성자
  private InboundServiceImpl() {}

  // 3. public static getInstance() 메서드
  public static InboundServiceImpl getInstance() {
    return instance;
  }

  // 의존하는 DAO들을 final 필드로 선언
  // 싱글톤 인스턴스가 생성될 때, 의존하는 DAO들의 싱글톤 인스턴스를 가져와 할당
  private final InboundRequestDAO inboundRequestDAO = InboundRequestDAOImpl.getInstance();
  private final InboundRequestItemDAO inboundRequestItemDAO = InboundRequestItemDAOImpl.getInstance();
  private final InventoryDAO inventoryDAO = InventoryDAO.getInstance();
  private final CoffeeDAO coffeeDAO = CoffeeDAOImpl.getInstance();




  @Override
  public Long requestInbound(InboundRequestDTO requestDto) throws SQLException, TransactionException {
    // 이 메서드는 Stored Procedure를 사용하며, 프로시저 내부에서 트랜잭션을 관리합니다.
    // 따라서 서비스 계층에서 별도의 트랜잭션 처리를 할 필요가 없습니다.

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
    Connection conn = null; // catch 나 finally 에서 커넥션 객체를 활용해야 하므로 try-with-resources 구문을 쓰지 않는다.
    // 'catch' 블록은 'try' 블록의 외부이므로 'conn' 변수에 접근할 수 없습니다!
    // 여기서 conn.rollback()을 호출해야 하는데, conn이 보이지 않아 컴파일 에러가 발생합니다.
    // ERROR: Cannot resolve symbol 'conn'
    try {
      // 1. 트랜잭션 시작
      conn = DBUtil.getConnection();
      conn.setAutoCommit(false);

      // 2. 비즈니스 규칙 검증
      InboundRequestDTO request = inboundRequestDAO.findInboundRequestById(inboundRequestId);
      if (request == null) {
        throw new TransactionException(ErrorCode.INBOUND_REQUEST_NOT_FOUND);
      }
      if (!request.getMemberId().equals(memberId)) {
        throw new TransactionException(ErrorCode.UNAUTHORIZED_ACCESS);
      }
      if (request.getStatus() != InboundStatus.PENDING) {
        throw new TransactionException(ErrorCode.INVALID_STATUS_FOR_OPERATION);
      }

      // 3. DAO 호출 (Connection 객체 전달)
      inboundRequestDAO.deleteInboundRequest(conn, inboundRequestId);

      // 4. 커밋
      conn.commit();
    } catch (SQLException | TransactionException e) {
      if (conn != null) conn.rollback();
      throw e;
    } finally {
      if (conn != null) {
        conn.setAutoCommit(true);
        conn.close();
      }
    }
  }

  @Override
  public void approveInbound(Long inboundRequestId, String managerId, String locationPlaceId) throws SQLException, TransactionException {
    Connection conn = null;

    try {
      conn = DBUtil.getConnection();
      conn.setAutoCommit(false);

      // 1. 기존 요청 상세 정보 조 (아이템 목록 포함)
      InboundRequestDTO request = inboundRequestDAO.findInboundRequestById(inboundRequestId);

      // 2. 비즈니스 규칙 검증
      if (request == null) {
        throw new TransactionException(ErrorCode.INBOUND_REQUEST_NOT_FOUND);
      }
      if (request.getStatus() != InboundStatus.PENDING) {
        throw new TransactionException(ErrorCode.INVALID_STATUS_FOR_OPERATION);
      }

      // 3. 재고 업데이트: 각 아이템에 대해 재고 추가/업데이트
      for (InboundRequestItemDTO item : request.getItems()) {
        inventoryDAO.upsertInventory(conn, item, locationPlaceId);
      }

      // 4. 입고 상태 상태 업데이트 => 승인 및 관리자 지정
      request.setStatus(InboundStatus.APPROVED);
      request.setManagerId(managerId);
      request.setApprovalDate(LocalDate.now()); // 승인 날짜는 현재
      request.setInboundReceipt("RCPT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase()); // 영수증 번호 생성

      inboundRequestDAO.updateInboundRequest(conn, request);

      conn.commit(); // 모든 작업 성공 시 커밋

    } catch (SQLException | TransactionException e) {
      if (conn != null) conn.rollback(); // 실패 시 롤백
      throw e;
    } finally {
      if (conn != null) {
        conn.setAutoCommit(true);
        conn.close();
      }
    }
  }


  @Override
  public void rejectInbound(Long inboundRequestId, String managerId) throws SQLException, TransactionException {
    Connection conn = null;
    try {
      // 1. 트랜잭션 시작
      conn = DBUtil.getConnection();
      conn.setAutoCommit(false);

      // 2. 비즈니스 규칙 검증
      InboundRequestDTO request = inboundRequestDAO.findInboundRequestById(inboundRequestId);
      if (request == null) {
        throw new TransactionException(ErrorCode.INBOUND_REQUEST_NOT_FOUND);
      }
      if (request.getStatus() != InboundStatus.PENDING) {
        throw new TransactionException(ErrorCode.INVALID_STATUS_FOR_OPERATION);
      }

      // 3. DTO 상태 변경 및 DAO 호출 (Connection 객체 전달)
      request.setStatus(InboundStatus.REJECTED);
      request.setManagerId(managerId);
      request.setApprovalDate(LocalDate.now());
      inboundRequestDAO.updateInboundRequest(conn, request); // conn 전달

      // 4. 커밋
      conn.commit();
    } catch (SQLException | TransactionException e) {
      if (conn != null) conn.rollback();
      throw e;
    } finally {
      if (conn != null) {
        conn.setAutoCommit(true);
        conn.close();
      }
    }
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

  @Override
  public List<Coffee> getAvailableCoffees() throws SQLException {
    // 현재는 단순히 모든 커피를 가져오지만,
    // 나중에 "단종 상품 제외" 등의 비즈니스 로직이 여기에 추가될 수 있습니다.
    return coffeeDAO.getAllCoffees();
  }

  @Override
  public List<InboundRequestDTO> getPendingInboundListForAdmin() throws SQLException {
    // 1. 먼저 '대기' 상태인 모든 입고 요청 헤더를 조회합니다.
    List<InboundRequestDTO> pendingRequests = inboundRequestDAO.findAllInboundRequestsByStatus(InboundStatus.PENDING);

    // 2. 각 입고 요청에 대해 상세 항목(items)을 조회하여 채워줍니다. (N+1 문제 발생 지점)
    /*
    DTO 클래스의 수를 늘리지 않는다는 장점이 있지만, N+1 조회 문제가 발생할 수 있다.
    즉, N개의 입고 요청 헤더를 조회한 후, 각 헤더에 대한 상세 항목을 조회하기 위해 N번의 추가적인 쿼리가 발생할 수 있습니다.
    작은 규모의 프로젝트에서는 괜찮지만, 데이터가 많아지면 성능 저하의 원인이 될 수 있습니다.
     */
    for (InboundRequestDTO request : pendingRequests) {
      List<InboundRequestItemDTO> items = inboundRequestItemDAO.findItemsByInboundRequestId(request.getInboundRequestId());
      request.setItems(items);
    }

    return pendingRequests;
  }

  @Override
  public List<InboundRequestDTO> getInboundRequestsByMemberAndDateRange(String memberId, InboundStatus status, LocalDate startDate, LocalDate endDate) throws SQLException {
    return inboundRequestDAO.findRequestsByMemberIdAndDateRange(memberId, status, startDate, endDate);
  }

  @Override
  public List<InboundRequestDTO> getInboundRequestsByMemberAndMonth(String memberId, InboundStatus status, int year, int month) throws SQLException {
    return inboundRequestDAO.findRequestsByMemberIdAndMonth(memberId, status, year, month);
  }

  @Override
  public List<InboundRequestDTO> getAllInboundRequestsByDateRange(InboundStatus status, LocalDate startDate, LocalDate endDate) throws SQLException {
    return inboundRequestDAO.findAllRequestsByDateRange(status, startDate, endDate);
  }

  @Override
  public List<InboundRequestDTO> getAllInboundRequestsByMonth(InboundStatus status, int year, int month) throws SQLException {
    return inboundRequestDAO.findAllRequestsByMonth(status, year, month);
  }

}