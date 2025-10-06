package controller.transaction;

import controller.command.Command;
import controller.command.inbound.*;
import domain.user.Manager;
import domain.user.Member;
import model.transaction.InboundRequestDAOImpl;
import model.transaction.InboundRequestItemDAOImpl;
import service.transaction.InboundService;
import service.transaction.InboundServiceImpl;
import view.transaction.InboundViewDeprecated; // 입출력 담당 View

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InboundMenu {
  private final InboundService inboundService;
  private final InboundViewDeprecated inboundView;
  private final Map<String, Command> memberCommands = new HashMap<>();
  private final Map<String, Command> managerCommands = new HashMap<>();
  private final Scanner scanner = new Scanner(System.in);

  public InboundMenu() {
    // 의존성 주입 (실제 애플리케이션에서는 Main에서 주입받는 것이 좋음)
    // InboundMenu가 직접 InboundServiceImpl과 InboundView를 "만들어 쓴다".
    this.inboundService = new InboundServiceImpl(new InboundRequestDAOImpl(), new InboundRequestItemDAOImpl());
    this.inboundView = new InboundViewDeprecated();
    initializeCommands();
  }

  private void initializeCommands() {
    // 회원용 커맨드 초기화
    memberCommands.put("1", new RequestInboundCommand(inboundService, inboundView));
    memberCommands.put("2", new ModifyInboundCommand(inboundService, inboundView));
    memberCommands.put("3", new CancelInboundCommand(inboundService, inboundView));
    memberCommands.put("4", new ViewInboundCommand(inboundService, inboundView));
    // ... (회원용 조회 커맨드 추가)
    memberCommands.put("5", new ViewMemberInboundListCommand(inboundService, inboundView)); // (추가) 목록 조회

    // 관리자용 커맨드 초기화
    managerCommands.put("1", new ApproveInboundCommand(inboundService, inboundView));
    managerCommands.put("2", new RejectInboundCommand(inboundService, inboundView));
    managerCommands.put("3", new ViewInboundCommand(inboundService, inboundView));
    // ... (관리자용 조회 커맨드 추가)
    managerCommands.put("4", new ViewAllInboundListCommand(inboundService, inboundView)); // (추가) 목록 조회

  }

  public void memberMenu(Member member) {
    while (true) {
      inboundView.displayMemberMenu(); // 1. 입고 요청, 2. 요청 수정 ...
      String choice = scanner.nextLine();
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
      String choice = scanner.nextLine();
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