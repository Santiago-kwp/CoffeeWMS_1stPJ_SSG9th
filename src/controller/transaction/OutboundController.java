package controller.transaction;

import command.Command;
import constant.transaction.ValidCheck;
import service.transaction.InboundService;
import service.transaction.InboundServiceImpl;
import service.transaction.OutboundService;
import service.transaction.OutboundServiceImpl;
import view.transaction.InboundView;
import view.transaction.OutboundView;

public class OutboundController {
  private static ValidCheck validCheck = new ValidCheck();
  private OutboundService outboundService;
  private final OutboundView outboundView;

  public OutboundController(OutboundView outboundView) {
    // 출고 컨트롤러는 출고 서비스에 의존한다. 출고 Dao에 직접 의존하지 않음 => 약한 결합
    // 컨트롤러는 사용자의 요청을 받고, 어떤 서비스를 호출할지만 결정하자
    this.outboundService = new OutboundServiceImpl();
    this.outboundView = outboundView;
  }

  // 커맨드 패턴의 명령 수행 메소드
  public void executeCommand(Command command) {
    command.execute();
  }

  public void runMember(String memberId) {
  }

  public void runManager(String managerId) {

  }
}
