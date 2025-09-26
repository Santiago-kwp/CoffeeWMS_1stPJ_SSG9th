package controller.transaction;

import config.transaction.DBUtil;
import domain.transaction.Coffee;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import view.transaction.InboundView;

public class InboundMenu {

  /**
   * 메인 메서드 - 애플리케이션 시작
   * 뷰가 컨트롤러와 상호작용하는 방식을 보여줍니다.
   */
  public static void main(String[] args) {
    Connection connection = null;
    try {
      connection = DBUtil.getConnection();
      InboundView view = new InboundView(null); // 컨트롤러가 뷰를 주입받아 사용
      InboundController controller = new InboundController(connection, view);

      // 뷰 객체에 컨트롤러 연결
      view.setController(controller);

      // 컨트롤러의 회원 메소드
      controller.runMember();


    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }


}
