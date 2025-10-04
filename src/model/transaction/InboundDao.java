package model.transaction;

import config.DBUtil;
import domain.transaction.Coffee;
import domain.transaction.InboundItem;
import domain.transaction.LocationPlace;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/*
  DB의 저장 프로시저를 호출하는 입고 Dao
 */
public class InboundDao {

  /**
   * 관리자가 회원의 미승인된 요청 건수를 확인합니다.
   * @return 멤버ID, 해당 맴버의 미승인 입고 요청 건수
   */
  public Map<String, Integer> getAllMemberHasUnapprovedInboundRequest() {
    Map<String, Integer> memberHasUnapprovedInboundRequest = new HashMap<>();
    String sql = "{CALL get_all_member_has_unapproved_inbound_request()}";
    try (Connection conn = DBUtil.getConnection();
        CallableStatement cs = conn.prepareCall(sql);
         ResultSet rs = cs.executeQuery()) {

      while (rs.next()) {
        memberHasUnapprovedInboundRequest.put(rs.getString("member_id"), rs.getInt("unapproved_request_num"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return memberHasUnapprovedInboundRequest;
  }

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
   * `submit_member_inbound_request` 프로시저 호출
   */
  public boolean callSubmitInboundRequestProcedure(
      String requestId, String memberId, String managerId, String itemsJson, java.sql.Date requestDate) {

    String sql = "{CALL submit_member_inbound_request(?, ?, ?, ?, ?)}";
    try (Connection conn = DBUtil.getConnection();
        CallableStatement cs = conn.prepareCall(sql)) {
      cs.setString(1, requestId);
      cs.setString(2, memberId);
      cs.setString(3, managerId);
      cs.setString(4, itemsJson);
      cs.setDate(5, requestDate);

      cs.execute();

      System.out.println("입고 요청이 정상적으로 완료되었습니다!");
      return true;
    } catch (SQLException e) {
      System.out.println("입고 요청에 문제 발생!");
      e.printStackTrace();
      return false;
    }
  }

  /**
   * `update_member_inbound_request` 프로시저 호출
   */
  public boolean updateInboundRequestProcedure(
          String requestId, String memberId, String managerId, String itemsJson, java.sql.Date requestDate) {

    String sql = "{CALL update_member_inbound_request(?, ?, ?, ?, ?, ?)}";
    try (Connection conn = DBUtil.getConnection();
         CallableStatement cs = conn.prepareCall(sql)) {
      // Register the OUT parameter first
      cs.registerOutParameter(6, java.sql.Types.INTEGER);

      cs.setString(1, requestId);
      cs.setString(2, memberId);
      cs.setString(3, managerId);
      cs.setString(4, itemsJson);
      cs.setDate(5, requestDate);

      cs.execute();

      // Retrieve the value of the OUT parameter
      int resultCode = cs.getInt(6);
      if (resultCode > 0) {
        return true;
      }
    } catch (SQLException e) {
      System.out.println("입고 수정 요청에 문제 발생!");
      e.printStackTrace();
      return false;
    }
    return false;
  }

  public boolean deleteInboundRequestProcedure(String requestId, String memberId) {
    String sql = "{CALL delete_inbound_request(?, ?, ?)}";
    try(Connection conn = DBUtil.getConnection();
    CallableStatement cs = conn.prepareCall(sql);) {
      cs.registerOutParameter(3, java.sql.Types.INTEGER);
      cs.setString(1, requestId);
      cs.setString(2, memberId);

      cs.execute();
      // Retrieve the value of the OUT parameter
      int resultCode = cs.getInt(3);
      if (resultCode > 0) {
        return true;
      }

    } catch (SQLException e) {
      System.out.println("입고 삭제 요청에 문제 발생!");
      System.out.println(e.getMessage());
      return false;
    }
    return false;

  }

  /**
   * 특정 회원의 미승인된 입고 요청 목록을 데이터베이스에서 조회합니다.
   *
   * @param memberId 입고 요청을 조회할 회원의 ID
   * @return 미승인된 입고 요청 목록 (입고 상품, 수량, 입고 날짜 포함)
   * @throws SQLException 데이터베이스 접근 중 오류 발생 시
   */
  public List<InboundItem> getUnapprovedRequestsByMember(String memberId) throws SQLException {
    List<InboundItem> inboundItems = new ArrayList<>();

    String sql = "{call get_unapproved_inbounds_by_member(?)}";

    try (Connection conn = DBUtil.getConnection();
        CallableStatement cstmt = conn.prepareCall(sql)) {
      cstmt.setString(1, memberId);

      boolean hasResultSet = cstmt.execute();
      if (hasResultSet) {
        try (ResultSet rs = cstmt.getResultSet()) {
          // 결과 집합 순회
          while (rs.next()) {
            InboundItem inboundItem = new InboundItem(
                    rs.getString("member_id"),
                    rs.getString("coffee_id"),
                    rs.getString("coffee_name"),
                    rs.getString("inbound_request_id"),
                    rs.getLong("inbound_request_item_id"),
                    rs.getInt("inbound_request_quantity"),
                    rs.getDate("inbound_request_date")
            );
            inboundItems.add(inboundItem);
          }
        }
      }
    }
    return inboundItems;
  }

  public List<InboundItem> getInboundRequestItems(String memberId, String requestId) throws SQLException {
    List<InboundItem> inboundItems = new ArrayList<>();

    String sql = "{call get_one_inbound_by_member_requestId(?, ?)}";

    try (Connection conn = DBUtil.getConnection();
         CallableStatement cstmt = conn.prepareCall(sql)) {
      cstmt.setString(1, memberId);
      cstmt.setString(2, requestId);

      boolean hasResultSet = cstmt.execute();
      if (hasResultSet) {
        try (ResultSet rs = cstmt.getResultSet()) {
          // 결과 집합 순회
          while (rs.next()) {
            InboundItem inboundItem = new InboundItem(
                    rs.getString("member_id"),
                    rs.getString("coffee_id"),
                    rs.getString("coffee_name"),
                    rs.getString("inbound_request_id"),
                    rs.getLong("inbound_request_item_id"),
                    rs.getInt("inbound_request_quantity"),
                    rs.getDate("inbound_request_date")
            );
            inboundItems.add(inboundItem);
          }
        }
      }
    }
    return inboundItems;
  }


  public List<InboundItem> getApprovedRequestsByMember(String memberId) throws SQLException {
    List<InboundItem> inboundItems = new ArrayList<>();

    String sql = "{call get_approved_inbounds_by_member(?)}";

    try (Connection conn = DBUtil.getConnection();
        CallableStatement cstmt = conn.prepareCall(sql)) {
      cstmt.setString(1, memberId);

      boolean hasResultSet = cstmt.execute();
      if (hasResultSet) {
        try (ResultSet rs = cstmt.getResultSet()) {
          // 결과 집합 순회
          while (rs.next()) {
            InboundItem inboundItem = new InboundItem(
                    rs.getString("member_id"),
                    rs.getString("coffee_id"),
                    rs.getString("coffee_name"),
                    rs.getString("inbound_request_id"),
                    rs.getLong("inbound_request_item_id"),
                    rs.getInt("inbound_request_quantity"),
                    rs.getDate("inbound_request_date")
            );
            inboundItems.add(inboundItem);
          }
        }
      }
    }
    return inboundItems;
  }

  /**
   * 입고 요청을 승인하고 재고를 업데이트하는 프로시저를 호출합니다.
   * 관리자가 입고 요청을 승인할 때 사용됩니다.
   *
   * @param inboundRequestId 승인할 입고 요청의 ID
   * @param items 승인할 품목 목록 (JSON 형태로 변환될 Map의 리스트)
   * @throws SQLException 데이터베이스 오류 발생 시
   */
  public void processInboundRequest(String inboundRequestId, List<InboundItem> items) throws SQLException {
    // DTO 객체를 json 으로 변환하여 DB에 전달
    String jsonItems = convertListToJson(items);
    String sql = "{call process_inbound_request(?, ?)}";

    try (Connection conn = DBUtil.getConnection();
        CallableStatement cs = conn.prepareCall(sql)) {

      // 첫 번째 파라미터: 입고 요청 ID
      cs.setString(1, inboundRequestId);
      // 두 번째 파라미터: 품목 목록을 JSON 문자열로 변환
      cs.setString(2, jsonItems);

      // 프로시저 실행
      cs.execute();
      System.out.println("입고 요청이 성공적으로 승인되고 재고가 업데이트되었습니다.");

    } catch (SQLException e) {
      System.err.println("입고 요청 승인 중 데이터베이스 오류가 발생했습니다: " + e.getMessage());
      throw e;
    }
  }

  /**
   * InboundItem 객체의 리스트를 JSON 문자열로 변환합니다.
   * Jackson 라이브러리 없이 수동으로 JSON 문자열을 생성합니다.
   *
   * @param items 변환할 InboundItem 객체의 리스트
   * @return JSON 문자열
   */
  private String convertListToJson(List<InboundItem> items) {
    if (items == null || items.isEmpty()) {
      return "[]";
    }

    StringBuilder jsonBuilder = new StringBuilder();
    jsonBuilder.append("[");

    for (int i = 0; i < items.size(); i++) {
      InboundItem item = items.get(i);
      jsonBuilder.append("{");

      // 각 필드를 JSON 형식으로 추가
      jsonBuilder.append("\"coffee_id\":\"").append(item.getCoffeeId()).append("\",");
      jsonBuilder.append("\"location_place_id\":\"").append(item.getLocationPlaceId()).append("\",");
      jsonBuilder.append("\"quantity\":").append(item.getQuantity()).append(",");
      jsonBuilder.append("\"inbound_request_item_id\":").append(item.getInboundRequestItemId());

      jsonBuilder.append("}");

      // 마지막 항목이 아니면 콤마 추가
      if (i < items.size() - 1) {
        jsonBuilder.append(",");
      }
    }
    jsonBuilder.append("]");

    return jsonBuilder.toString();
  }



  public List<InboundItem> showInboundPeriod(String memberId, java.sql.Date startDate, java.sql.Date endDate) throws SQLException {
    List<InboundItem> inboundItems = new ArrayList<>();

    String sql = "{call show_inbound_period(?, ?, ?)}";

    try (Connection conn = DBUtil.getConnection();
         CallableStatement cs = conn.prepareCall(sql)) {

      // 첫 번째 파라미터: 입고 요청 ID
      cs.setString(1, memberId);
      // 두 번째 파라미터: 품목 목록을 JSON 문자열로 변환
      cs.setDate(2, startDate);
      cs.setDate(3, endDate);

      // 프로시저 실행
      boolean hasResultSet = cs.execute();
      if (hasResultSet) {
        try (ResultSet rs = cs.getResultSet()) {
          // 결과 집합 순회
          while (rs.next()) {
            InboundItem inboundItem = new InboundItem(
                    rs.getString("member_id"),
                    rs.getString("coffee_id"),
                    rs.getString("coffee_name"),
                    rs.getString("inbound_request_id"),
                    rs.getLong("inbound_request_item_id"),
                    rs.getInt("inbound_request_quantity"),
                    rs.getDate("inbound_request_date")
            );
            inboundItems.add(inboundItem);
          }
        }
      }
    }
    return inboundItems;
  }

  public List<InboundItem> showInboundMonth(String memberId, int month) throws SQLException {
    List<InboundItem> inboundItems = new ArrayList<>();

    String sql = "{call show_inbound_month(?, ?)}";

    try (Connection conn = DBUtil.getConnection();
         CallableStatement cs = conn.prepareCall(sql)) {

      // 첫 번째 파라미터: 입고 요청 ID
      cs.setString(1, memberId);
      // 두 번째 파라미터: 품목 목록을 JSON 문자열로 변환
      cs.setInt(2, month);

      // 프로시저 실행
      boolean hasResultSet = cs.execute();
      if (hasResultSet) {
        try (ResultSet rs = cs.getResultSet()) {
          // 결과 집합 순회
          while (rs.next()) {
            InboundItem inboundItem = new InboundItem(
                    rs.getString("member_id"),
                    rs.getString("coffee_id"),
                    rs.getString("coffee_name"),
                    rs.getString("inbound_request_id"),
                    rs.getLong("inbound_request_item_id"),
                    rs.getInt("inbound_request_quantity"),
                    rs.getDate("inbound_request_date")
            );
            inboundItems.add(inboundItem);
          }
        }
      }
    }
    return inboundItems;
  }

  public Map<String, Integer> getMemberApprovedInboundRequests() {
    Map<String, Integer> memberHasApprovedInboundRequests = new HashMap<>();
    String sql = "{CALL get_all_member_has_approved_inbound_requests()}";
    try (Connection conn = DBUtil.getConnection();
         CallableStatement cs = conn.prepareCall(sql);
         ResultSet rs = cs.executeQuery()) {

      while (rs.next()) {
        memberHasApprovedInboundRequests.put(rs.getString("member_id"), rs.getInt("approved_request_num"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return memberHasApprovedInboundRequests;
  }
}




