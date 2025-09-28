package controller.transaction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import command.Command;
import command.transaction.CreateInboundRequestCommand;
import command.transaction.CreateOutboundRequestCommand;
import command.transaction.ShowApprovedRequestsCommand;
import command.transaction.ShowUnapprovedRequestsCommand;
import constant.transaction.TransactionText;
import constant.transaction.ValidCheck;
import domain.transaction.InboundItem;
import domain.transaction.InboundRequest;
import domain.transaction.OutboundItem;
import domain.transaction.OutboundRequest;
import exception.transaction.DataProcessingException;
import exception.transaction.OutboundRequestException;
import service.transaction.InboundService;
import service.transaction.InboundServiceImpl;
import service.transaction.OutboundService;
import service.transaction.OutboundServiceImpl;
import view.transaction.InboundView;
import view.transaction.OutboundView;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OutboundController {
  private static ValidCheck validCheck = new ValidCheck();
  private OutboundService outboundService;
  private final OutboundView outboundView;
  private InboundService inboundService;

  public OutboundController(OutboundView outboundView) {
    // 출고 컨트롤러는 출고 서비스에 의존한다. 출고 Dao에 직접 의존하지 않음 => 약한 결합
    // 컨트롤러는 사용자의 요청을 받고, 어떤 서비스를 호출할지만 결정하자
    this.inboundService = new InboundServiceImpl();
    this.outboundService = new OutboundServiceImpl();
    this.outboundView = outboundView;
  }

  // 커맨드 패턴의 명령 수행 메소드
  public void executeCommand(Command command) {
    command.execute();
  }

  public void runMember(String memberId) {
    boolean running = true;
    while (running) {
      outboundView.displayMemberOutboundMenu();
      int choice = outboundView.getFourIntegerInput("메뉴 번호를 선택하세요: ");

      switch (choice) {
        case 1:
          // 출고 요청
          submitNewOutboundRequest(memberId);
          break;
        case 2:
          // 출고 요청 내역 조회
          showUnapprovedRequests(memberId);
        break;
        case 3:
          // 출고 완료 현황 조회
          showApprovedOutboundStatus(memberId);
          break;
        case 4:
          running = false;
          break;
        default:
          outboundView.displayMessage("잘못된 입력입니다. 다시 시도하세요.");
      }
    }
  }

  private void showApprovedOutboundStatus(String memberId) {
  }

  private void memberOutboundRequestMenu(String memberId) {

  }

  /**
   * 1번 메뉴: 입고 요청
   * 사용자로부터 입고 요청에 필요한 데이터를 받아 커맨드 객체를 생성하고 실행합니다.
   */
  private void submitNewOutboundRequest(String memberId) {
    // 뷰를 통해 출고 가능한 현재 회원의 입고 완료 현황을 보여줌

    // 커맨드 객체 생성 및 실행
    ShowApprovedRequestsCommand command = new ShowApprovedRequestsCommand(inboundService,memberId);
    command.execute();

    // 실행 결과 가져오기
    List<InboundItem> inboundItems = command.getResult();

    // 사용자로부터 필요한 데이터 입력 받기
    List<OutboundItem> outboundItems = outboundView.getOutboundInput(inboundItems, "출고 요청 커피ID 및 출고 수량을 입력하세요. (\"exit\" 입력시 종료)");

    
    // ObjectMapper 인스턴스 생성
    ObjectMapper objectMapper = new ObjectMapper();
    // 날짜를 직렬화 시 해줘야 됨.
    objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    String jsonString;

    try {
      jsonString = objectMapper.writeValueAsString(outboundItems);
    } catch (JsonProcessingException e) {
      // Wrap the checked exception in a custom runtime exception
      throw new DataProcessingException("직렬화 실패... outbound items to JSON.", e);
    }


    // DTO 객체 생성 및 데이터 주입
    OutboundRequest newRequest = new OutboundRequest();
    newRequest.setOutboundRequestId("REQ" + UUID.randomUUID().toString().substring(0, 8)); // 고유 ID 생성
    newRequest.setMemberId(memberId);
    newRequest.setManagerId(TransactionText.WMSADMIN.getText()); // 얘는 일단 후순위로 처리하기
    newRequest.setJsonItems(jsonString);


    // 커맨드 생성 및 실행
    Command createCommand = new CreateOutboundRequestCommand(outboundService, newRequest);
    executeCommand(createCommand);
  }

  /**
   * 2번 메뉴: 출고 요청 내역 조회
   * 아직 승인되지 않은 출고 요청 목록을 조회합니다.
   * 조회 후 해당 멤버의 출고 요청 ID 리스트를 반환합니다.
   */
  public List<String> showUnapprovedRequests(String memberId) {
    outboundView.displayMessage(String.format("회원 ID : %s 님의 미승인된 출고 요청 내역을 조회합니다.", memberId));


    // 실행 결과 가져오기
      List<OutboundItem> outboundItems = null;
      try {
          outboundItems = outboundService.getUnapprovedRequestsByMember(memberId);
      } catch (SQLException e) {
          throw new OutboundRequestException("출고 요청 조회 실패", e);
      }

      // 뷰에 결과 표시
    outboundView.displayOutboundRequestItems(outboundItems);

    // 해당 회원의 미승인 요청 ID 리스트 반환
    List<String> outboundRequestIds = outboundItems.stream()
            .map(OutboundItem::getOutboundRequestId)
            .collect(Collectors.toList());
    return outboundRequestIds;

  }





  public void runManager(String managerId) {

  }
}
