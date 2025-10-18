package model.inventory;



import config.DBUtil;
import domain.inventory.InventoryVO;

import domain.inventory.UserVO;
import domain.transaction.dto.InboundRequestItemDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 재고 관련 데이터베이스 작업을 처리하는 DAO(Data Access Object).
 */
public class InventoryDAO {

  // ----------- kwp singleton pattern added ----------
  // 1. private static final 인스턴스 변수 선언 및 초기화
  private static final InventoryDAO instance = new InventoryDAO();

  // 2. private 생성자 선언하여 외부에서 new 키워드로 생성하는 것을 방지
  private InventoryDAO() {}

  // 3. public static getInstance() 메서드로 유일한 인스턴스에 접근하도록 함
  public static InventoryDAO getInstance() {
    return instance;
  }
  //---------------------------------------------------

  /**
   * 통합 재고 목록 조회를 위해 search_inventory 프로시저를 호출합니다.
   * @param user 현재 로그인한 사용자 정보
   * @param coffeeName 검색할 상품명
   * @param beanType 필터링할 원두상태 ('생두'/'원두')
   * @param isDecaf 필터링할 디카페인 여부 ('Y'/'N')
   * @param cargoName [수정] 검색할 창고 이름
   * @param companyName 검색할 회원사 이름
   * @param sortKey 정렬 기준 (1:이름, 2:날짜, 3:단가)
   * @param isFlipped 정렬 순서 뒤집기 여부
   * @return 재고 요약 목록
   */
  public List<InventoryVO> searchInventory(
      UserVO user,
      String coffeeName,
      String beanType,
      String isDecaf,
      String cargoName,       // int -> String
      String companyName,
      int sortKey,
      boolean isFlipped) {

    List<InventoryVO> list = new ArrayList<>();
    // 파라미터 개수는 8개로 동일
    String sql = "{call search_inventory(?, ?, ?, ?, ?, ?, ?, ?)}";

    try (Connection conn = DBUtil.getConnection();
        CallableStatement cstmt = conn.prepareCall(sql)) {

      // 프로시저의 IN 파라미터 설정
      cstmt.setString(1, user.getUserId());
      cstmt.setString(2, coffeeName);
      cstmt.setString(3, beanType);
      cstmt.setString(4, isDecaf);
      // [핵심 변경] 5번째 파라미터를 setInt에서 setString으로 변경
      cstmt.setString(5, cargoName);
      cstmt.setString(6, companyName);
      cstmt.setInt(7, sortKey);
      cstmt.setBoolean(8, isFlipped);

      try (ResultSet rs = cstmt.executeQuery()) {
        while (rs.next()) {
          InventoryVO summary = new InventoryVO();
          // ResultSet의 결과를 VO 객체에 매핑
          summary.setCoffeeId(rs.getString("coffee_id"));
          summary.setCoffeeName(rs.getString("coffee_name"));
          summary.setCoffeeOrigin(rs.getString("coffee_origin"));
          summary.setCoffeeRoasted(rs.getString("coffee_roasted"));
          summary.setDecaf(rs.getString("decaf"));
          summary.setPrice(rs.getInt("price"));
          summary.setCoffeeGrade(rs.getString("coffee_grade"));
          summary.setCoffeeType(rs.getString("coffee_type"));
          summary.setTotalQuantity(rs.getInt("total_quantity"));
          summary.setMinInboundDate(rs.getDate("min_inbound_date"));
          list.add(summary);
        }
      }
    } catch (Exception e) {
      // 실제 애플리케이션에서는 로깅 프레임워크 사용을 권장합니다.
      e.printStackTrace();
    }
    return list;
  }



  /** added by kwpark
   * 특정 위치에 있는 특정 커피의 재고를 증가시킵니다.
   * 재고가 없으면 새로 생성하고, 있으면 수량을 더합니다.
   * @param conn 서비스 계층에서 관리하는 Connection 객체
   * @param item 추가할 입고 항목 (coffeeId, quantity 포함)
   * @param locationPlaceId 재고를 추가할 위치 ID
   */
  public void upsertInventory(Connection conn, InboundRequestItemDTO item, String locationPlaceId) throws SQLException {
    // 1. 해당 위치에 해당 커피 재고가 이미 있는지 확인
    String selectSql = "SELECT inventory_id, inventory_quantity FROM inventory WHERE coffee_id = ? AND location_place_id = ?";
    try (PreparedStatement selectPstmt = conn.prepareStatement(selectSql)) {
      selectPstmt.setString(1, item.getCoffeeId());
      selectPstmt.setString(2, locationPlaceId);

      try (ResultSet rs = selectPstmt.executeQuery()) {
        if (rs.next()) {
          // 2-1. 재고가 있으면 UPDATE
          long inventoryId = rs.getLong("inventory_id");
          int currentQuantity = rs.getInt("inventory_quantity");
          int newQuantity = currentQuantity + item.getInboundRequestQuantity();

          String updateSql = "UPDATE inventory SET inventory_quantity = ? WHERE inventory_id = ?";
          try (PreparedStatement updatePstmt = conn.prepareStatement(updateSql)) {
            updatePstmt.setInt(1, newQuantity);
            updatePstmt.setLong(2, inventoryId);
            updatePstmt.executeUpdate();
          }
        } else {
          // 2-2. 재고가 없으면 INSERT
          String insertSql = "INSERT INTO inventory (inventory_id, coffee_id, location_place_id, inventory_quantity) VALUES (?, ?, ?, ?)";
          try (PreparedStatement insertPstmt = conn.prepareStatement(insertSql)) {
            // inventory_id는 char(12)이므로 고유 ID 생성 필요 (예: INV- + timestamp)
            String newInventoryId = "INV-" + System.currentTimeMillis();
            insertPstmt.setString(1, newInventoryId.substring(0, 12));
            insertPstmt.setString(2, item.getCoffeeId());
            insertPstmt.setString(3, locationPlaceId);
            insertPstmt.setInt(4, item.getInboundRequestQuantity());
            insertPstmt.executeUpdate();
          }
        }
      }
    }
}


}
