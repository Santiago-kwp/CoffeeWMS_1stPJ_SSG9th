package controller.transaction;

import config.transaction.DBUtil;
import domain.transaction.Coffee;
import java.awt.Menu;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import view.transaction.InboundView;

public class InboundMenu {


  public void menuMember(String memberId) {
    InboundView view = new InboundView(null);
    InboundController controller = new InboundController(view);
    // 뷰 객체에 컨트롤러 연결
    view.setController(controller);
    // 컨트롤러의 회원 메소드
    controller.runMember(memberId);
  }

  public void menuManager(String managerId) {
    InboundView view = new InboundView(null);
    InboundController controller = new InboundController(view);
    // 뷰 객체에 컨트롤러 연결
    view.setController(controller);
    // 컨트롤러의 회원 메소드
    controller.runManager(managerId);
  }

}


