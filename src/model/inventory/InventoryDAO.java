package model.inventory;



import config.inventory.DBUtil;
import domain.inventory.InventoryVO;

import domain.inventory.UserVO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 재고 관련 데이터베이스 작업을 처리하는 DAO(Data Access Object).
 */
public class InventoryDAO {

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
}
