package controller.transaction;

import controller.command.Command;
import controller.command.inbound.*;
import domain.user.Manager;
import domain.user.Member;
// 필요한 서비스들을 모두 import
import service.cargo.LocationService;
import service.cargo.LocationServiceImpl;
import service.transaction.InboundService;
import service.transaction.InboundServiceImpl;
import view.transaction.InboundView;

import java.util.HashMap;
import java.util.Map;

public class InboundController {
  // 모든 의존성은 final로 선언하여 불변성 보장
  // 의존성 주입 대신, 각 싱글톤 클래스의 getInstance()를 호출하여 인스턴스를 가져옴
  private final InboundService inboundService = InboundServiceImpl.getInstance();
  private final LocationService locationService = LocationServiceImpl.getInstance();
  private final InboundView inboundView = InboundView.getInstance();

  private final Map<String, Command> memberCommands = new HashMap<>();
  private final Map<String, Command> managerCommands = new HashMap<>();



  public InboundController() {
    initializeCommands(); // 커맨드 초기화
  }

  private void initializeCommands() {
    // 회원용 커맨드 초기화 (필요한 모든 의존성 전달)
    memberCommands.put("1", new RequestInboundCommand(inboundService, inboundView));
    memberCommands.put("2", new ModifyInboundCommand(inboundService, inboundView));
    memberCommands.put("3", new CancelInboundCommand(inboundService, inboundView));
    memberCommands.put("4", new ViewInboundCommand(inboundService, inboundView));
    memberCommands.put("5", new ViewMemberInboundListCommand(inboundService, inboundView));

    // 관리자용 커맨드 초기화 (필요한 모든 의존성 전달)
    managerCommands.put("1", new ApproveInboundCommand(inboundService, inboundView, locationService));
    managerCommands.put("2", new RejectInboundCommand(inboundService, inboundView));
    managerCommands.put("3", new ViewInboundCommand(inboundService, inboundView));
    managerCommands.put("4", new ViewAllInboundListCommand(inboundService, inboundView));
  }

  public void memberMenu(Member member) {
    while (true) {
      inboundView.displayMemberMenu(); // 1. 입고 요청, 2. 요청 수정 ...
      String choice = inboundView.getMenuChoiceFromUser(); // View의 헬퍼 메서드 사용
      if (choice.equals("0")) break; // 뒤로가기

      Command command = memberCommands.get(choice);
      if (command != null) {
        try {
          command.execute(member); // Member 객체 전달
        } catch (Exception e) {
          inboundView.displayError("예상치 못한 오류 발생: " + e.getMessage());
        }
      } else {
        inboundView.displayError("잘못된 메뉴 선택입니다.");
      }
    }
  }

  // WMSMenu에서 호출하는 메서드
  public void managerMenu(Manager manager) {
    while (true) {
      inboundView.displayManagerMenu(); // 1. 요청 승인, 2. 요청 거절 ...
      String choice = inboundView.getMenuChoiceFromUser(); // View의 헬퍼 메서드 사용
      if (choice.equals("0")) break; // 뒤로가기

      Command command = managerCommands.get(choice);
      if (command != null) {
        try {
          command.execute(manager); // Manager 객체 전달
        } catch (Exception e) {
          inboundView.displayError("예상치 못한 오류 발생: " + e.getMessage());
        }
      } else {
        inboundView.displayError("잘못된 메뉴 선택입니다.");
      }
    }
  }
}