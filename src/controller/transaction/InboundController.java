package controller.transaction;

import command.Command;
import command.transaction.CreateInboundRequestCommand;
import command.transaction.ShowApprovedRequestsCommand;
import command.transaction.ShowUnapprovedRequestsCommand;
import config.transaction.DBUtil;
import constant.transaction.TransactionText;
import constant.transaction.ValidCheck;
import domain.transaction.Coffee;
import domain.transaction.InboundRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import service.transaction.InboundService;
import service.transaction.InboundServiceImpl;
import view.transaction.InboundView;

/**
 * InboundController
 * This class acts as the Controller in the MVC pattern.
 * It receives user requests and orchestrates the execution of the Command objects.
 */
public class InboundController {
  private static ValidCheck validCheck = new ValidCheck();
  private InboundService inboundService;
  private final InboundView inboundView;

  public InboundController(Connection connection, InboundView inboundView) {
    // 입고 컨트롤러는 입고 서비스에 의존한다. 입고 Dao에 직접 의존하지 않음 => 약한 결합
    this.inboundService = new InboundServiceImpl(connection);
    this.inboundView = inboundView;
  }

  // 커맨드 패턴의 명령 수행 메소드
  public void executeCommand(Command command) {
    command.execute();
  }

  /**
   * 이 메서드는 뷰(View)가 입고 가능한 커피 목록을 표시하도록 요청하는 역할
   * @return 입고 가능한 커피 목록
   */
  public List<Coffee> showAvailableCoffees() {
    return inboundService.getAllCoffees();
  }

  /**
   * 애플리케이션의 메인 루프를 실행하여 회원 사용자와 상호작용하는 메소드
   */
  public void runMember() {
    boolean running = true;
    while (running) {
      inboundView.displayMemberInboundMenu();
      int choice = inboundView.getIntegerInput("메뉴 번호를 선택하세요: ");

      switch (choice) {
        case 1:
          submitNewInboundRequest();
          break;
        case 2:
          showUnapprovedRequests();
          break;
        case 3:
          showApprovedInboundStatus();
          break;
        case 4:
          // 합치면 뒤로 가기로 로그인 메인 페이지로 가도록!
          inboundView.displayMessage("프로그램을 종료합니다.");
          running = false;
          break;
        default:
          inboundView.displayMessage("잘못된 입력입니다. 다시 시도하세요.");
      }
    }
  }

  /**
   * 1번 메뉴: 입고 요청
   * 사용자로부터 입고 요청에 필요한 데이터를 받아 커맨드 객체를 생성하고 실행합니다.
   */
  private void submitNewInboundRequest() {
    // 뷰를 통해 입고 가능한 커피 목록을 보여줌
    inboundView.displayCoffees(inboundService.getAllCoffees());

    // 사용자로부터 필요한 데이터 입력 받기
//    String memberId = inboundView.getStringInput("회원 ID를 입력하세요: ");
//    String managerId = inboundView.getStringInput("관리자 ID를 입력하세요: ");
    String itemsJson = inboundView.getJsonInput("입고 요청 상품ID 및 수량을 입력하세요. exit 입력시 종료됩니다.");
    Date requestDate = inboundView.getDateInput("입고 요청 날짜를 입력하세요. ");

    // DTO 객체 생성 및 데이터 주입
    InboundRequest newRequest = new InboundRequest();
    newRequest.setInboundRequestId("REQ" + UUID.randomUUID().toString().substring(0, 8)); // 고유 ID 생성
    newRequest.setMemberId("member12346"); // 나중에 합치면 받아야 함
    newRequest.setManagerId("manager1235"); // 마찬가지
    newRequest.setRequestItemsJson(itemsJson);
    newRequest.setInboundRequestDate(requestDate);

    // 커맨드 생성 및 실행
    Command createCommand = new CreateInboundRequestCommand(inboundService, newRequest);
    executeCommand(createCommand);
  }


  /**
   * 2번 메뉴: 입고 요청 내역 조회
   * 아직 승인되지 않은 입고 요청 목록을 조회합니다.
   */
  public void showUnapprovedRequests() {
    inboundView.displayMessage("미승인된 입고 요청 내역을 조회합니다.");
    String memberId = "member12346"; // 병합시 받아서 넣어야 함.

    // 커맨드 객체 생성 및 실행
    ShowUnapprovedRequestsCommand command = new ShowUnapprovedRequestsCommand(inboundService, memberId);
    command.execute();

    // 실행 결과 가져오기
    List<Map<String, Object>> requests = command.getResult();

    // 뷰에 결과 표시
    inboundView.displayUnapprovedRequests(requests);

  }

  /**
   * 3번 메뉴: 입고 완료 현황 조회
   * 승인된 입고 요청의 현황을 조회합니다.
   */
  private void showApprovedInboundStatus() {
    inboundView.displayMessage("승인된 입고 완료 현황을 조회합니다.");
    String memberId = "member12347"; // 병합시 받아서 넣어야 함.

    // 커맨드 객체 생성 및 실행
    ShowApprovedRequestsCommand command = new ShowApprovedRequestsCommand(inboundService,memberId);
    command.execute();

    // 실행 결과 가져오기
    List<Map<String, Object>> requests = command.getResult();

    // 뷰에 결과 표시
    inboundView.displayApprovedRequests(requests);
  }


  /*
  관리자
   */
  /**
   * 애플리케이션의 메인 루프를 실행하여 회원 사용자와 상호작용하는 메소드
   */
  public void runManager() {
    boolean running = true;
    while (running) {
      inboundView.displayManagerInboundMenu();
      int choice = inboundView.getIntegerInput("메뉴 번호를 선택하세요: ");

      switch (choice) {
        case 1:
          submitNewInboundRequest();
          break;
        case 2:
          showUnapprovedRequests();
          break;
        case 3:
          showApprovedInboundStatus();
          break;
        case 4:
          // 합치면 뒤로 가기로 로그인 메인 페이지로 가도록!
          inboundView.displayMessage("프로그램을 종료합니다.");
          running = false;
          break;
        default:
          inboundView.displayMessage("잘못된 입력입니다. 다시 시도하세요.");
      }
    }
  }




}