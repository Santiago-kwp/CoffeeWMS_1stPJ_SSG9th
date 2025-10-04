package model.transaction;

import config.DBUtil;
import domain.transaction.Coffee;
import domain.transaction.LocationPlace;
import domain.transaction.dto.InboundRequestDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// 입고 관련 조회 및 검색 전용 DAO
public class InboundSearchDAOImpl implements InboundSearchDAO {

    /**
     * `coffees` 테이블에서 모든 커피 목록을 조회합니다.
     * @return 커피 DTO 리스트
     */
    public List<Coffee> getAllCoffees() {
        List<Coffee> coffeeList = new ArrayList<>();
        String sql = "SELECT * FROM coffees";
        try (Connection conn = DBUtil.getConnection();
             CallableStatement cs = conn.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Coffee coffee = new Coffee();
                coffee.setCoffeeId(rs.getString("coffee_id"));
                coffee.setCoffeeName(rs.getString("coffee_name"));
                coffee.setCoffeeOrigin(rs.getString("coffee_origin"));
                coffee.setDecaf(rs.getString("decaf"));
                coffee.setRoasted(rs.getString("coffee_roasted"));
                coffee.setCoffeeGrade(rs.getString("coffee_grade"));
                coffee.setCoffeeType(rs.getString("coffee_type"));
                coffee.setPrice(rs.getInt("price"));
                coffeeList.add(coffee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coffeeList;
    }


    /**
     * 입고 요청 상세 내역을 상태 및 회원 ID로 조회합니다.
     * @param memberId 조회할 회원 ID (null이면 모든 회원의 요청 조회)
     * @param requestStatus 조회할 입고 요청 상태 ('대기', '승인완료', '입고완료' 등)
     * @param managerId 조회할 관리자 ID (null이면 모든 관리자의 요청 조회, 승인 내역 조회 시 유용)
     * @return 조회된 입고 요청 상세 내역 리스트
     * @throws SQLException 데이터베이스 접근 오류 시
     */
    public List<InboundRequestDTO> getInboundRequestDetails(
            String memberId, String requestStatus, String managerId) throws SQLException {

        List<InboundRequestDTO> requests = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("SELECT ");
        sqlBuilder.append("    ir.inbound_request_id, ");
        sqlBuilder.append("    ir.request_date AS 입고_요청_날짜, ");
        sqlBuilder.append("    ir.approval_date AS 승인_날짜, "); // 승인되지 않은 요청의 경우 NULL
        sqlBuilder.append("    mgr.manager_name AS 승인_관리자명, "); // 승인되지 않은 요청의 경우 NULL
        sqlBuilder.append("    c.coffee_name AS 커피_이름, ");
        sqlBuilder.append("    iri.inbound_request_quantity AS 요청_수량, ");
        sqlBuilder.append("    ir.status AS 요청_상태, ");
        sqlBuilder.append("    ir.boundReceipt AS 입고_고지서 ");
        sqlBuilder.append("FROM ");
        sqlBuilder.append("    inbound_request ir ");
        sqlBuilder.append("JOIN ");
        sqlBuilder.append("    members m ON ir.member_id = m.member_id ");
        // manager_id가 NULL일 수도 있으므로 LEFT JOIN으로 변경
        sqlBuilder.append("LEFT JOIN ");
        sqlBuilder.append("    managers mgr ON ir.manager_id = mgr.manager_id ");
        sqlBuilder.append("JOIN ");
        sqlBuilder.append("    inbound_request_items iri ON ir.inbound_request_id = iri.inbound_request_id ");
        sqlBuilder.append("JOIN ");
        sqlBuilder.append("    coffees c ON iri.coffee_id = c.coffee_id ");
        sqlBuilder.append("WHERE ");
        sqlBuilder.append("    ir.is_deleted = 0 "); // 논리적으로 삭제되지 않은 요청은 항상 포함

        List<Object> params = new ArrayList<>();

        if (memberId != null && !memberId.isEmpty()) {
            sqlBuilder.append(" AND ir.member_id = ?");
            params.add(memberId);
        }
        if (requestStatus != null && !requestStatus.isEmpty()) {
            sqlBuilder.append(" AND ir.status = ?");
            params.add(requestStatus);
        }
        if (managerId != null && !managerId.isEmpty()) {
            sqlBuilder.append(" AND ir.manager_id = ?");
            params.add(managerId);
        }

        sqlBuilder.append(" ORDER BY ");
        // 승인 날짜 또는 요청 날짜 기준으로 정렬
        if ("승인완료".equals(requestStatus) || "입고완료".equals(requestStatus)) {
            sqlBuilder.append("    ir.approval_date DESC, ir.inbound_request_id DESC");
        } else {
            sqlBuilder.append("    ir.request_date DESC, ir.inbound_request_id DESC");
        }


        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlBuilder.toString())) {

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i)); // 파라미터 바인딩
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    requests.add(new InboundRequestDTO(rs));
                }
            }
        }
        return requests;
    }

    /*
  관리자가 위치를 지정하기 위해 창고위치(location_places 테이블의 데이터를 조회합니다.)
   */
    public List<LocationPlace> getAvailableLocationPlaces () {
        List<LocationPlace> locationPlaces = new ArrayList<>();

        String sql = "SELECT * FROM location_places";
        try (Connection conn = DBUtil.getConnection();
             CallableStatement cs = conn.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                LocationPlace locationPlace = new LocationPlace(
                        rs.getString("location_place_id"),
                        rs.getString("zone_id"),
                        rs.getString("zone_name"),
                        rs.getString("rack_id"),
                        rs.getString("rack_name"),
                        rs.getString("cell_id"),
                        rs.getString("cell_name")
                );
                locationPlaces.add(locationPlace);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locationPlaces;
    }


}
