package model.transaction;

import config.DBUtil; // DB 연결 유틸리티 클래스
import domain.transaction.dto.InboundRequestItemDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * InboundRequestItemDAO 인터페이스의 JDBC 기반 구현체입니다.
 */
public class InboundRequestItemDAOImpl implements InboundRequestItemDAO {

    @Override
    public int insertInboundRequestItem(InboundRequestItemDTO dto) throws SQLException {
        String sql = "INSERT INTO inbound_request_items (inbound_request_id, coffee_id, inbound_request_quantity) VALUES (?, ?, ?)";
        int rowsAffected = 0;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, dto.getInboundRequestId());
            pstmt.setString(2, dto.getCoffeeId());
            pstmt.setInt(3, dto.getInboundRequestQuantity());

            rowsAffected = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting inbound request item: " + e.getMessage());
            throw e;
        }
        return rowsAffected;
    }

    @Override
    public void insertInboundRequestItems(Connection conn, List<InboundRequestItemDTO> items) throws SQLException {
        String sql = "INSERT INTO inbound_request_items (inbound_request_id, coffee_id, inbound_request_quantity) VALUES (?, ?, ?)";

        if (items == null || items.isEmpty()) {
            return; // 삽입할 아이템이 없으면 아무것도 하지 않음
        }

        // Connection은 서비스 계층에서 관리하므로 여기서 닫지 않습니다.
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (InboundRequestItemDTO item : items) {
                pstmt.setLong(1, item.getInboundRequestId());
                pstmt.setString(2, item.getCoffeeId());
                pstmt.setInt(3, item.getInboundRequestQuantity());
                pstmt.addBatch(); // 배치에 추가
            }
            pstmt.executeBatch(); // 배치 실행
        } catch (SQLException e) {
            System.err.println("Error batch inserting inbound request items: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public int updateInboundRequestItem(InboundRequestItemDTO dto) throws SQLException {
        String sql = "UPDATE inbound_request_items SET inbound_request_id = ?, coffee_id = ?, inbound_request_quantity = ? WHERE inbound_request_item_id = ?";
        int rowsAffected = 0;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, dto.getInboundRequestId());
            pstmt.setString(2, dto.getCoffeeId());
            pstmt.setInt(3, dto.getInboundRequestQuantity());
            pstmt.setLong(4, dto.getInboundRequestItemId());

            rowsAffected = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating inbound request item: " + e.getMessage());
            throw e;
        }
        return rowsAffected;
    }

    @Override
    public int deleteInboundRequestItem(Long inboundRequestItemId) throws SQLException {
        String sql = "DELETE FROM inbound_request_items WHERE inbound_request_item_id = ?";
        int rowsAffected = 0;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, inboundRequestItemId);
            rowsAffected = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting inbound request item: " + e.getMessage());
            throw e;
        }
        return rowsAffected;
    }

    @Override
    public List<InboundRequestItemDTO> findItemsByInboundRequestId(Long inboundRequestId) throws SQLException {
        List<InboundRequestItemDTO> items = new ArrayList<>();
        String sql = "SELECT * FROM inbound_request_items WHERE inbound_request_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, inboundRequestId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // DTO의 ResultSet 생성자를 활용하여 객체 생성 및 리스트에 추가
                    items.add(new InboundRequestItemDTO(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding inbound request items by request ID: " + e.getMessage());
            throw e;
        }
        return items;
    }

    // 추가된 메서드 구현
    @Override
    public int deleteItemsByInboundRequestId(Connection conn, Long inboundRequestId) throws SQLException {
        String sql = "DELETE FROM inbound_request_items WHERE inbound_request_id = ?";
        // Connection은 외부(Service)에서 관리
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, inboundRequestId);
            return pstmt.executeUpdate();
        }
    }

    // 추가된 메서드 구현 (이전의 insertInboundRequestItems와 동일)
    @Override
    public void insertItems(Connection conn, List<InboundRequestItemDTO> items) throws SQLException {
        String sql = "INSERT INTO inbound_request_items (inbound_request_id, coffee_id, inbound_request_quantity) VALUES (?, ?, ?)";
        if (items == null || items.isEmpty()) return;

        // Connection은 외부(Service)에서 관리
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (InboundRequestItemDTO item : items) {
                pstmt.setLong(1, item.getInboundRequestId());
                pstmt.setString(2, item.getCoffeeId());
                pstmt.setInt(3, item.getInboundRequestQuantity());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }
}