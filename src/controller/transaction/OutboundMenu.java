package controller.transaction;

import view.transaction.OutboundView;

import java.sql.SQLException;

public class OutboundMenu {


  public void menuMember(String memberId) {
    OutboundView view = new OutboundView(null);
    OutboundController controller = new OutboundController(view);
    // 뷰 객체에 컨트롤러 연결
    view.setController(controller);
    // 컨트롤러의 회원 메소드
    controller.runMember(memberId);
  }

  public void menuManager(String managerId) {
    OutboundView view = new OutboundView(null);
    OutboundController controller = new OutboundController(view);
    // 뷰 객체에 컨트롤러 연결
    view.setController(controller);
    // 컨트롤러의 회원 메소드
      try {
          controller.runManager(managerId);
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
  }

}
