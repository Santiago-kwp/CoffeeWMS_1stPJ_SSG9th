package model.transaction;

import config.DBUtil;
import domain.transaction.InboundItem;
import domain.transaction.OutboundItem;
import exception.transaction.OutboundRequestException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OutboundDao {

    /**
     * `submit_member_inbound_request` 프로시저 호출
     */
    public boolean callSubmitOutboundRequestProcedure(
            String requestId, String memberId, String managerId, String itemsJson) {

        String sql = "{CALL submit_member_outbound_request(?, ?, ?, ?)}";
        try (Connection conn = DBUtil.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, requestId);
            cs.setString(2, memberId);
            cs.setString(3, managerId);
            cs.setString(4, itemsJson);

            cs.execute();

            System.out.println("출고 요청이 정상적으로 완료되었습니다!");
            return true;
        } catch (SQLException e) {
            System.out.println("출고 요청에 문제 발생!");
            e.printStackTrace();
            return false;
        }
    }

    public List<OutboundItem> getUnapprovedRequestsByMember(String memberId) {
        List<OutboundItem> outboundItems = new ArrayList<>();
        String sql = "{CALL get_unapproved_outbounds_by_member(?)}";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, memberId);

            boolean hasResultSet = cstmt.execute();
            if (hasResultSet) {
                try (ResultSet rs = cstmt.getResultSet()) {
                    // 결과 집합 순회
                    while (rs.next()) {
                        OutboundItem outboundItem = new OutboundItem(
                                rs.getString("member_id"),
                                rs.getString("coffee_id"),
                                rs.getString("coffee_name"),
                                rs.getString("outbound_request_id"),
                                rs.getLong("outbound_request_item_id"),
                                rs.getInt("outbound_request_quantity"),
                                rs.getDate("outbound_request_date")
                        );
                        outboundItems.add(outboundItem);
                    }
                }
            }
        } catch (SQLException e) {
            throw new OutboundRequestException("출고 미승인 요청 조회 실패", e);
        }
        return outboundItems;
    }
}
