package model.transaction;

// ... (기존 import 및 ObjectMapper 인스턴스 선언) ...

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.DBUtil;
import domain.transaction.dto.InboundRequestDTO;
import domain.transaction.vo.InboundStatus;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class InboundRequestDAOImpl implements InboundRequestDAO {

    // 1. private static final 인스턴스 변수 선언 및 초기화
    private static final InboundRequestDAOImpl instance = new InboundRequestDAOImpl();

    // 2. private 생성자 선언하여 외부에서 new 키워드로 생성하는 것을 방지
    private InboundRequestDAOImpl() {}

    // 3. public static getInstance() 메서드로 유일한 인스턴스에 접근하도록 함
    public static InboundRequestDAOImpl getInstance() {
        return instance;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Long insertInboundRequest(InboundRequestDTO requestDto) throws SQLException {
        // 프로시저 호출 SQL 정의
        String sql = "{CALL CreateInboundRequest(?, ?, ?, ?)}"; // 3개의 IN 파라미터 + 1개의 OUT 파라미터

        Long generatedId = null;

        String itemsJson = null;
        try {
            if (requestDto.getItems() != null && !requestDto.getItems().isEmpty()) {
                itemsJson = objectMapper.writeValueAsString(requestDto.getItems());
            } else {
                itemsJson = "[]";
            }
        } catch (JsonProcessingException e) {
            System.err.println("Error converting InboundRequestItemDTO list to JSON: " + e.getMessage());
            throw new SQLException("Failed to serialize inbound items to JSON", e);
        }

        try (Connection conn = DBUtil.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            // CallableStatement 파라미터 설정
            cstmt.setString(1, requestDto.getMemberId());
            cstmt.setDate(2, Date.valueOf(requestDto.getRequestDate()));
            cstmt.setString(3, itemsJson); // JSON 문자열 전달
            cstmt.registerOutParameter(4, Types.BIGINT); // OUT 파라미터 등록 (인덱스 변경)

            cstmt.execute();

            generatedId = cstmt.getLong(4); // 생성된 ID 가져오기 (인덱스 변경)
            requestDto.setInboundRequestId(generatedId);

        } catch (SQLException e) {
            System.err.println("Error inserting inbound request via procedure: " + e.getMessage());
            throw e;
        }
        return generatedId;
    }


    /**
     * 입고 요청을 논리적으로 삭제합니다.
     * is_deleted 필드를 true로 설정하고 is_deleted_at 필드를 현재 날짜로 업데이트합니다.
     *
     * @param inboundRequestId 삭제할 입고 요청의 ID
     * @return 업데이트된 레코드 수 (보통 1)
     * @throws SQLException DB 작업 중 오류 발생 시
     */
    @Override
    public int deleteInboundRequest(Connection conn, Long inboundRequestId) throws SQLException {
        String sql = "UPDATE inbound_request SET is_deleted = ?, is_deleted_at = ? WHERE inbound_request_id = ?";
        int rowsAffected = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBoolean(1, true); // is_deleted = true
            pstmt.setDate(2, Date.valueOf(LocalDate.now())); // is_deleted_at = 현재 날짜
            pstmt.setLong(3, inboundRequestId);

            rowsAffected = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error deleting (logically) inbound request: " + e.getMessage());
            throw e;
        }
        return rowsAffected;
    }

    /**
     * 지정된 ID의 단일 입고 요청을 조회합니다.
     * 이 메서드는 입고 요청 헤더 정보만 가져옵니다.
     * 연관된 InboundRequestItemDTO 리스트는 별도의 DAO 호출을 통해 가져와야 합니다.
     *
     * @param inboundRequestId 조회할 입고 요청의 ID
     * @return 해당 ID의 InboundRequestDTO 객체, 없으면 null
     * @throws SQLException DB 작업 중 오류 발생 시
     */
    @Override
    public InboundRequestDTO findInboundRequestById(Long inboundRequestId) throws SQLException {
        String sql = "SELECT * FROM inbound_request WHERE inbound_request_id = ? AND is_deleted = 0"; // 삭제되지 않은 것만 조회
        InboundRequestDTO dto = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, inboundRequestId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    dto = new InboundRequestDTO(rs); // ResultSet 생성자 활용
                    // Note: items 리스트는 여기서 채워지지 않습니다. 서비스 계층에서 InboundRequestItemDAO를 통해 추가로 로드해야 합니다.
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding inbound request by ID: " + e.getMessage());
            throw e;
        }
        return dto;
    }

    /**
     * 입고 요청(헤더)을 수정합니다.
     * DTO의 inboundRequestId를 사용하여 기존 레코드를 찾고, 나머지 필드를 업데이트합니다.
     * 이 메서드는 상태 변경, 매니저 ID 할당, 영수증 추가 등 다양한 업데이트에 사용될 수 있습니다.
     *
     * @param dto 업데이트할 입고 요청 정보를 담은 InboundRequestDTO 객체
     * @return 업데이트된 레코드 수 (보통 1)
     * @throws SQLException DB 작업 중 오류 발생 시
     */
    @Override
    public int updateInboundRequest(Connection conn, InboundRequestDTO dto) throws SQLException {
        // manager_id, approval_date, status, inbound_receipt, is_deleted, is_deleted_at 등
        // 모든 필드를 업데이트 가능한 형태로 쿼리를 작성합니다.
        // 클라이언트(Service)에서 DTO에 필요한 값만 설정하여 호출하면 됩니다.
        String sql = "UPDATE inbound_request SET " +
                "member_id = ?, manager_id = ?, request_date = ?, approval_date = ?, " +
                "status = ?, inbound_receipt = ?, is_deleted = ?, is_deleted_at = ? " +
                "WHERE inbound_request_id = ?";
        int rowsAffected = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int paramIndex = 1;
            pstmt.setString(paramIndex++, dto.getMemberId());

            // manager_id (NULL 허용)
            if (dto.getManagerId() != null) {
                pstmt.setString(paramIndex++, dto.getManagerId());
            } else {
                pstmt.setNull(paramIndex++, Types.VARCHAR);
            }

            pstmt.setDate(paramIndex++, Date.valueOf(dto.getRequestDate()));

            // approval_date (NULL 허용)
            if (dto.getApprovalDate() != null) {
                pstmt.setDate(paramIndex++, Date.valueOf(dto.getApprovalDate()));
            } else {
                pstmt.setNull(paramIndex++, Types.DATE);
            }

            pstmt.setString(paramIndex++, dto.getStatus().getDisplayName()); // Enum을 String으로 변환

            // inbound_receipt (NULL 허용)
            if (dto.getInboundReceipt() != null) {
                pstmt.setString(paramIndex++, dto.getInboundReceipt());
            } else {
                pstmt.setNull(paramIndex++, Types.LONGVARCHAR); // TEXT 타입에 적합
            }

            pstmt.setBoolean(paramIndex++, dto.isDeleted());

            // is_deleted_at (NULL 허용)
            if (dto.getIsDeletedAt() != null) {
                pstmt.setDate(paramIndex++, Date.valueOf(dto.getIsDeletedAt()));
            } else {
                pstmt.setNull(paramIndex++, Types.DATE);
            }

            pstmt.setLong(paramIndex, dto.getInboundRequestId());

            rowsAffected = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error updating inbound request: " + e.getMessage());
            throw e;
        }
        return rowsAffected;
    }

    /**
     * 특정 회원의 특정 상태에 해당하는 입고 요청 목록을 조회합니다.
     *
     * @param memberId 조회할 회원의 ID
     * @param status   조회할 입고 요청의 상태 (InboundStatus Enum)
     * @return 조회된 InboundRequestDTO 객체들의 리스트
     * @throws SQLException DB 작업 중 오류 발생 시
     */
    @Override
    public List<InboundRequestDTO> findInboundRequestsByMemberId(String memberId, InboundStatus status) throws SQLException {
        List<InboundRequestDTO> requestList = new ArrayList<>();
        // is_deleted = 0 (삭제되지 않은) 요청만 조회
        String sql = "SELECT * FROM inbound_request WHERE member_id = ? AND status = ? AND is_deleted = 0 ORDER BY request_date DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, memberId);
            pstmt.setString(2, status.getDisplayName()); // Enum을 String으로 변환

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    requestList.add(new InboundRequestDTO(rs)); // ResultSet 생성자 활용
                    // Note: items 리스트는 여기서 채워지지 않습니다. 서비스 계층에서 추가 로드 필요.
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding inbound requests by member ID and status: " + e.getMessage());
            throw e;
        }
        return requestList;
    }

    /**
     * 특정 상태에 해당하는 모든 입고 요청 목록을 조회합니다. (관리자용)
     *
     * @param status 조회할 입고 요청의 상태 (InboundStatus Enum)
     * @return 조회된 InboundRequestDTO 객체들의 리스트
     * @throws SQLException DB 작업 중 오류 발생 시
     */
    @Override
    public List<InboundRequestDTO> findAllInboundRequestsByStatus(InboundStatus status) throws SQLException {
        List<InboundRequestDTO> requestList = new ArrayList<>();
        // is_deleted = 0 (삭제되지 않은) 요청만 조회
        String sql = "SELECT * FROM inbound_request WHERE status = ? AND is_deleted = 0 ORDER BY request_date DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status.getDisplayName()); // Enum을 String으로 변환

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    requestList.add(new InboundRequestDTO(rs)); // ResultSet 생성자 활용
                    // Note: items 리스트는 여기서 채워지지 않습니다.
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding all inbound requests by status: " + e.getMessage());
            throw e;
        }
        return requestList;
    }

    @Override
    public List<InboundRequestDTO> findRequestsByMemberIdAndDateRange(String memberId, InboundStatus status, LocalDate startDate, LocalDate endDate) throws SQLException {
        List<InboundRequestDTO> requestList = new ArrayList<>();
        String sql = "SELECT * FROM inbound_request WHERE member_id = ? AND status = ? AND is_deleted = 0 " +
                "AND request_date BETWEEN ? AND ? ORDER BY request_date DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            pstmt.setString(2, status.getDisplayName());
            pstmt.setDate(3, Date.valueOf(startDate));
            pstmt.setDate(4, Date.valueOf(endDate));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    requestList.add(new InboundRequestDTO(rs));
                }
            }
        }
        return requestList;
    }

    @Override
    public List<InboundRequestDTO> findRequestsByMemberIdAndMonth(String memberId, InboundStatus status, int year, int month) throws SQLException {
        List<InboundRequestDTO> requestList = new ArrayList<>();
        String sql = "SELECT * FROM inbound_request WHERE member_id = ? AND status = ? AND is_deleted = 0 " +
                "AND YEAR(request_date) = ? AND MONTH(request_date) = ? ORDER BY request_date DESC";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            pstmt.setString(2, status.getDisplayName());
            pstmt.setInt(3, year);
            pstmt.setInt(4, month);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    requestList.add(new InboundRequestDTO(rs));
                }
            }
        }
        return requestList;
    }

    @Override
    public List<InboundRequestDTO> findAllRequestsByDateRange(InboundStatus status, LocalDate startDate, LocalDate endDate) throws SQLException {
        List<InboundRequestDTO> requestList = new ArrayList<>();
        String sql = "SELECT * FROM inbound_request WHERE status = ? AND is_deleted = 0 " +
                "AND request_date BETWEEN ? AND ? ORDER BY request_date DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status.getDisplayName());
            pstmt.setDate(2, Date.valueOf(startDate));
            pstmt.setDate(3, Date.valueOf(endDate));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    requestList.add(new InboundRequestDTO(rs));
                }
            }
        }
        return requestList;
    }

    @Override
    public List<InboundRequestDTO> findAllRequestsByMonth(InboundStatus status, int year, int month) throws SQLException {
        List<InboundRequestDTO> requestList = new ArrayList<>();
        String sql = "SELECT * FROM inbound_request WHERE status = ? AND is_deleted = 0 " +
                "AND YEAR(request_date) = ? AND MONTH(request_date) = ? ORDER BY request_date DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status.getDisplayName());
            pstmt.setInt(2, year);
            pstmt.setInt(3, month);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    requestList.add(new InboundRequestDTO(rs));
                }
            }
        }
        return requestList;
    }







}