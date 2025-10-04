package model.transaction;

import config.DBUtil;
import domain.transaction.dto.InboundRequestDTO;
import domain.transaction.dto.InboundRequestItemDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class InboundRequestDAOImpl implements InboundRequestDAO {

    // Helper method to map ResultSet to InboundRequestDTO
    private InboundRequestDTO mapResultSetToInboundRequestDTO(ResultSet rs) throws SQLException {
        Long id = rs.getLong("inbound_request_id");
        String memberId = rs.getString("member_id");
        String managerId = rs.getString("manager_id");
        LocalDate requestDate = rs.getDate("request_date") != null ? rs.getDate("request_date").toLocalDate() : null;
        LocalDate approvalDate = rs.getDate("approval_date") != null ? rs.getDate("approval_date").toLocalDate() : null;
        String status = rs.getString("status");
        String inboundReceipt = rs.getString("inbound_receipt");
        boolean isDeleted = rs.getBoolean("is_deleted");
        LocalDate isDeletedAt = rs.getDate("is_deleted_at") != null ? rs.getDate("is_deleted_at").toLocalDate() : null;

        // DTO는 항상 isDeleted가 false인 유효한 요청을 반환하는 것이 일반적
        if (isDeleted) {
            return null; // 논리적으로 삭제된 요청은 DTO로 변환하지 않음 (필요에 따라 변경 가능)
        }

        // 입고 요청 상세 항목들도 함께 조회하여 DTO에 담기
        List<InboundRequestItemDTO> items = getInboundRequestItemsByRequestId(id);

        // InboundRequestDTO의 생성자를 활용
        return new InboundRequestDTO(id, memberId, managerId, requestDate, status, inboundReceipt, items);
        // Note: approvalDate와 isDeletedAt은 DTO에는 직접적인 필드가 없으므로, 필요하면 InboundRequestDTO에 추가해야 함.
        // 현재 InboundRequestDTO는 VO의 모든 필드를 포함하지 않으므로, 이 부분은 유연하게 처리해야 합니다.
        // 여기서는 DTO가 VO의 모든 필드를 포함한다고 가정하고, LocalDate -> Date 변환을 수행했습니다.
        // 이상적인 DTO는 필요한 정보만 가짐. 여기서는 편의상 VO와 유사하게 필드를 매핑했습니다.
    }

    // Helper method to map ResultSet to InboundRequestItemDTO
    private InboundRequestItemDTO mapResultSetToInboundRequestItemDTO(ResultSet rs) throws SQLException {
        Long itemId = rs.getLong("inbound_request_item_id");
        String coffeeId = rs.getString("coffee_id");
        Integer quantity = rs.getInt("inbound_request_quantity");
        LocalDate itemRequestDate = rs.getDate("inbound_request_date") != null ? rs.getDate("inbound_request_date").toLocalDate() : null;

        return new InboundRequestItemDTO(itemId, coffeeId, quantity, itemRequestDate);
    }


    @Override
    public Long createInboundRequest(InboundRequestDTO requestDTO) throws SQLException {
        String sql = "INSERT INTO inbound_request (member_id, request_date, status, inbound_receipt) VALUES (?, ?, ?, ?)";
        Long generatedId = null;

        try (Connection connection = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, requestDTO.getMemberId());
            pstmt.setDate(2, Date.valueOf(LocalDate.now())); // 요청 날짜는 현재 날짜로 고정
            pstmt.setString(3, requestDTO.getStatus()); // 초기 상태는 DTO에서 '대기'로 설정될 것
            pstmt.setString(4, requestDTO.getInboundReceipt());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("입고 요청 생성 실패, 영향받은 행이 없습니다.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getLong(1);
                } else {
                    throw new SQLException("입고 요청 생성 실패, ID를 얻지 못했습니다.");
                }
            }
        }

        // 입고 요청 상세 항목들 처리
        if (generatedId != null && requestDTO.getItems() != null) {
            for (InboundRequestItemDTO item : requestDTO.getItems()) {
                createInboundRequestItem(conn, generatedId, item);
            }
        }
        return generatedId;
    }

    @Override
    public InboundRequestDTO getInboundRequestById(Long inboundRequestId) throws SQLException {
        String sql = "SELECT * FROM inbound_request WHERE inbound_request_id = ? AND is_deleted = 0"; // 삭제되지 않은 요청만
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, inboundRequestId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToInboundRequestDTO(rs);
                }
            }
        }
        return null;
    }

    @Override
    public boolean updateInboundRequestStatus(Long inboundRequestId, String newStatus, String managerId) throws SQLException {
        String sql = "UPDATE inbound_request SET status = ?, manager_id = ?, approval_date = ? WHERE inbound_request_id = ? AND is_deleted = 0";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            pstmt.setString(2, managerId); // 관리자 ID
            pstmt.setDate(3, Date.valueOf(LocalDate.now())); // 승인/거절 처리 날짜
            pstmt.setLong(4, inboundRequestId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    @Override
    public boolean softDeleteInboundRequest(Long inboundRequestId) throws SQLException {
        String sql = "UPDATE inbound_request SET is_deleted = 1, is_deleted_at = ? WHERE inbound_request_id = ? AND is_deleted = 0";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(LocalDate.now())); // 삭제 날짜
            pstmt.setLong(2, inboundRequestId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    @Override
    public List<InboundRequestDTO> getInboundRequestsByMemberId(String memberId) throws SQLException {
        List<InboundRequestDTO> requestList = new ArrayList<>();
        String sql = "SELECT * FROM inbound_request WHERE member_id = ? AND is_deleted = 0 ORDER BY request_date DESC";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    InboundRequestDTO dto = mapResultSetToInboundRequestDTO(rs, conn);
                    if (dto != null) { // 삭제된 요청은 제외
                        requestList.add(dto);
                    }
                }
            }
        }
        return requestList;
    }

    @Override
    public List<InboundRequestDTO> getAllInboundRequests() throws SQLException {
        List<InboundRequestDTO> requestList = new ArrayList<>();
        String sql = "SELECT * FROM inbound_request WHERE is_deleted = 0 ORDER BY request_date DESC";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    InboundRequestDTO dto = mapResultSetToInboundRequestDTO(rs, conn);
                    if (dto != null) { // 삭제된 요청은 제외
                        requestList.add(dto);
                    }
                }
            }
        }
        return requestList;
    }

    @Override
    public List<InboundRequestItemDTO> getInboundRequestItemsByRequestId(Long inboundRequestId) throws SQLException {
        List<InboundRequestItemDTO> items = new ArrayList<>();
        String sql = "SELECT * FROM inbound_request_items WHERE inbound_request_id = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, inboundRequestId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    items.add(mapResultSetToInboundRequestItemDTO(rs));
                }
            }
        }
        return items;
    }

    @Override
    public void createInboundRequestItem(Long inboundRequestId, InboundRequestItemDTO itemDTO) throws SQLException {
        String sql = "INSERT INTO inbound_request_items (inbound_request_id, coffee_id, inbound_request_quantity, inbound_request_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, inboundRequestId);
            pstmt.setString(2, itemDTO.getCoffeeId());
            pstmt.setInt(3, itemDTO.getInboundRequestQuantity());
            pstmt.setDate(4, Date.valueOf(LocalDate.now())); // 상세 요청 날짜도 현재 날짜로 고정

            pstmt.executeUpdate();
        }
    }
}