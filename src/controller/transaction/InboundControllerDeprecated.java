package controller.transaction;

import controller.command.Command;
import command.transaction.*;

import constant.transaction.TransactionText;
import constant.transaction.ValidCheck;
import domain.transaction.Coffee;
import domain.transaction.InboundItem;
import domain.transaction.InboundRequest;

import domain.transaction.LocationPlace;


import java.sql.SQLException;
import java.util.*;

import java.util.stream.Collectors;
import service.transaction.InboundService;
import service.transaction.InboundServiceImpl;
import view.transaction.InboundViewDeprecated;

/**
 * InboundController
 * This class acts as the Controller in the MVC pattern.
 * It receives user requests and orchestrates the execution of the Command objects.
 */
public class InboundControllerDeprecated {
  private static ValidCheck validCheck = new ValidCheck();
  private InboundService inboundService;
  private final InboundViewDeprecated inboundView;

  public InboundControllerDeprecated(InboundViewDeprecated inboundView) {
    // 입고 컨트롤러는 입고 서비스에 의존한다. 입고 Dao에 직접 의존하지 않음 => 약한 결합
    // 컨트롤러는 사용자의 요청을 받고, 어떤 서비스를 호출할지만 결정하자
    this.inboundService = new InboundServiceImpl();
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
  public void runMember(String memberId) {
    boolean running = true;
    while (running) {
      inboundView.displayMemberInboundMenu();
      int choice = inboundView.getFourIntegerInput("메뉴 번호를 선택하세요: ");

      switch (choice) {
        case 1:
          // 입고 요청
          submitNewInboundRequest(memberId);
          break;
        case 2:
          // 입고 요청 내역 조회 => 입고 요청 수정 / 취소
          memberInboundRequestMenu(memberId, showUnapprovedRequests(memberId));
          break;
        case 3:
          // 입고 현황 조회
          showApprovedInboundStatus(memberId);
          break;
        case 4:
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
  private void submitNewInboundRequest(String memberId) {
    // 뷰를 통해 입고 가능한 커피 목록을 보여줌
    inboundView.displayCoffees(inboundService.getAllCoffees());

    // 사용자로부터 필요한 데이터 입력 받기
    String itemsJson = inboundView.getJsonInput("입고 요청 상품ID 및 수량을 입력하세요. (\"exit\" 입력시 종료)");
    Date requestDate = inboundView.getDateInput("입고 요청 날짜를 입력하세요. (yyyy-MM-dd) ");

    // DTO 객체 생성 및 데이터 주입
    InboundRequest newRequest = new InboundRequest();
    newRequest.setInboundRequestId("REQ" + UUID.randomUUID().toString().substring(0, 8)); // 고유 ID 생성
    newRequest.setMemberId(memberId);
    newRequest.setManagerId(TransactionText.WMSADMIN.getText()); // 얘는 일단 후순위로 처리하기
    newRequest.setRequestItemsJson(itemsJson);
    newRequest.setInboundRequestDate(requestDate);

    // 커맨드 생성 및 실행
    Command createCommand = new CreateInboundRequestCommand(inboundService, newRequest);
    executeCommand(createCommand);
  }


  /**
   * 2번 메뉴: 입고 요청 내역 조회
   * 아직 승인되지 않은 입고 요청 목록을 조회합니다.
   * 조회 후 해당 멤버의 입고 요청 ID 리스트를 반환합니다.
   */
  public List<String> showUnapprovedRequests(String memberId) {
    inboundView.displayMessage(String.format("회원 ID : %s 님의 미승인된 입고 요청 내역을 조회합니다.", memberId));

    // 커맨드 생성 및 실행
    ShowUnapprovedRequestsCommand showCommand = new ShowUnapprovedRequestsCommand(inboundService, memberId);
    executeCommand(showCommand);

    // 실행 결과 가져오기
    List<InboundItem> inboundItems = showCommand.getResult();

    // 뷰에 결과 표시
    inboundView.displayInboundRequestItems(inboundItems);

    // 해당 회원의 미승인 요청 ID 리스트 반환
    List<String> inboundRequestIds = inboundItems.stream()
            .map(InboundItem::getInboundRequestId)
            .collect(Collectors.toList());
    return inboundRequestIds;

  }

  public void memberInboundRequestMenu(String memberId, List<String> inboundRequestIds) {
    boolean running = true;
    while (running) {
      // 미승인 입고 요청 내역 수정 및 취소
      inboundView.displayInboundRequestsMenu();
      int choice = inboundView.getThreeIntegerInput("메뉴 번호를 선택하세요: ");
      switch (choice) {
        case 1:
          // 입고 요청 수정
          updateInboundRequest(memberId, inboundRequestIds);
          break;
        case 2:
          // 입고 요청 취소
          deleteInboundRequest(memberId, inboundRequestIds);
          break;
        case 3:
          // 뒤로가기
          running = false;
          break;
        default:
          inboundView.displayMessage("잘못된 입력입니다. 다시 시도하세요.");

      }
    }

  }

  public void managerInboundRequestMenu(String memberId, List<String> inboundRequestIds) throws SQLException {
    boolean running = true;
    while (running) {
      // 미승인 입고 요청 내역 수정 및 취소
      inboundView.displayManagerInboundRequestsMenu();
      int choice = inboundView.getFourIntegerInput("메뉴 번호를 선택하세요: ");
      switch (choice) {
        case 1:
          // 입고 요청 승인
          grantUnapprovedRequest(memberId, inboundRequestIds);
          return;
        case 2:
          // 입고 요청 수정
          updateInboundRequest(memberId, inboundRequestIds);
          break;
        case 3:
          // 입고 요청 취소
          deleteInboundRequest(memberId, inboundRequestIds);
          break;
        case 4:
          // 뒤로가기
          running = false;
          break;
        default:
          inboundView.displayMessage("잘못된 입력입니다. 다시 시도하세요.");

      }
    }

  }

  private void deleteInboundRequest(String memberId, List<String> unapprovedRequestIds) {
    // 해당하는 회원의 입고 요청리스트 중 회원이 처리를 원하는 입고 요청 ID를 받음.
    String unapprovedRequestId = inboundView.getOneRequestId(unapprovedRequestIds,"삭제할 입고 요청 ID를 입력하세요.");

    // 삭제 처리
    inboundService.deleteInboundRequest(unapprovedRequestId, memberId);

    // 정상 삭제 시 다시 입고 요청 내역 조회 페이지로
    showUnapprovedRequests(memberId);
  }

  /**
   * 회원의 미 승인 입고 요청 수정 메소드
   * @param memberId
   * @param unapprovedRequestIds
   */
  private void updateInboundRequest(String memberId, List<String> unapprovedRequestIds) {

    // 해당하는 회원의 입고 요청리스트 중 회원이 처리를 원하는 입고 요청 ID를 받음.
    String unapprovedRequestId = inboundView.getOneRequestId(unapprovedRequestIds,"수정할 입고 요청 ID를 입력하세요.");
    // 수정 프로세스 진행 => 커피 목록부터 다시 받기
    updateNewInboundRequest(memberId, unapprovedRequestId);

    // 정상 수정 시 다시 입고 요청 내역 조회 페이지로
    showUnapprovedRequests(memberId);
  }

  /**
   * 회원의 미승인 입고 요청 수정을 위해 새로 입력 받는 메소드
   * @param memberId
   * @param unapprovedRequestId
   */

  private void updateNewInboundRequest(String memberId, String unapprovedRequestId) {
    // 뷰를 통해 입고 가능한 커피 목록을 보여줌
    inboundView.displayCoffees(inboundService.getAllCoffees());

    // 사용자로부터 필요한 데이터 입력 받기
    String itemsJson = inboundView.getJsonInput("(수정) 입고 요청 상품ID 및 수량을 입력하세요. exit 입력시 종료됩니다.");
    Date requestDate = inboundView.getDateInput("(수정) 입고 요청 날짜를 입력하세요. ");

    // DTO 객체 생성 및 데이터 주입
    InboundRequest newRequest = new InboundRequest();
    newRequest.setInboundRequestId(unapprovedRequestId);
    newRequest.setMemberId(memberId);
    newRequest.setManagerId(TransactionText.WMSADMIN.getText()); // 얘는 일단 후순위로 처리하기
    newRequest.setRequestItemsJson(itemsJson);
    newRequest.setInboundRequestDate(requestDate);

    // 수정사항 반영
    inboundService.updateInboundRequest(newRequest);

  }

  /**
   * 3번 메뉴: 입고 완료 현황 조회
   * 승인된 입고 요청의 현황을 조회합니다.
   */
  private void showApprovedInboundStatus(String memberId) {
    inboundView.displayMessage("승인된 입고 완료 현황을 조회합니다.");

    // 커맨드 객체 생성 및 실행
    ShowApprovedRequestsCommand command = new ShowApprovedRequestsCommand(inboundService,memberId);
    command.execute();

    // 실행 결과 가져오기
    List<InboundItem> inboundItems = command.getResult();

    // 결과 표시 및 상세 조회
      try {
          approvedInboundMenu(memberId, inboundItems);
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
  }

  private void approvedInboundMenu(String memberId, List<InboundItem> inboundItems) throws SQLException {
    boolean running = true;
    while (running) {
      // 전체 결과 조회
      inboundView.displayInboundApprovedItems(inboundItems);
      int choice = inboundView.getThreeIntegerInput("메뉴 번호를 선택하세요: ");
      switch (choice) {
        case 1:
          // 기간별 입고 현황 조회
          showInboundPeriod(memberId);
          break;
        case 2:
          // 월별 입고 현황
          showInboundMonth(memberId);
          break;
        case 3:
          // 뒤로가기
          running = false;
          break;
        default:
          inboundView.displayMessage("잘못된 입력입니다. 다시 시도하세요.");
      }
    }
  }

  private void showInboundPeriod(String memberId) throws SQLException {
    Date startDate = inboundView.getDateStartInput("조회할 시작 날짜를 입력하세요. (yyyy-MM-dd) ");
    Date endDate = inboundView.getDateEndInput(startDate, "조회할 마지막 날짜를 입력하세요. (yyyy-MM-dd) ");

    List<InboundItem> inboundItems = inboundService.showInboundPeriod(memberId, startDate, endDate);

    // 뷰에 결과 표시
    inboundView.displayInboundRequestItems(inboundItems);

  }

  public void showInboundMonth(String memberId) throws SQLException {
    int month = inboundView.getMonthInput("조회할 월을 입력하세요. (1 ~ 12) ");

    List<InboundItem> inboundItems = inboundService.showInboundMonth(memberId, month);

    // 뷰에 결과 표시
    inboundView.displayInboundRequestItems(inboundItems);
  }


  /**
   * 애플리케이션의 메인 루프를 실행하여 관리자 사용자와 상호작용하는 메소드
   */
  public void runManager(String managerId) throws SQLException {
    boolean running = true;
    while (running) {
      inboundView.displayManagerInboundMenu();
      int choice = inboundView.getFourIntegerInput("메뉴 번호를 선택하세요: ");

      switch (choice) {
        case 1: // 회원의 입고 요청 조회
          showMemberUnapprovedInboundRequest();
          break;
        case 2:
          // 입고 고지서 조회 => 미구현
          break;
        case 3:
          // 입고 완료 현황 조회
          showMemberApprovedInboundRequests();
          break;
        case 4:
          // 뒤로 가기
          running = false;
          break;
        default:
          inboundView.displayMessage("잘못된 입력입니다. 다시 시도하세요.");
      }
    }
  }

  // 1 번 메뉴 : 미 승인된 회원 아이디 및 미승인 건수 조회 -> 회원 아이디 입력 시 기존의 미승인된 회원의 입고 요청 내역 표시
  public void showMemberUnapprovedInboundRequest() throws SQLException {
    // 커맨드 객체 생성 및 실행
    ShowMemberUnapprovedCommand showCommand = new ShowMemberUnapprovedCommand(inboundService);
    executeCommand(showCommand);

    // 실행 결과 가져오기
    Map<String, Integer> requests = showCommand.getResult();
    // 뷰에 결과 표시
    inboundView.displayMemberUnapprovedInboundRequests(requests);

    // 관리자가 회원의 입고 요청을 승인하기 위해 미승인 요청 건 조회 메소드
    showMemberUnapprovedInboundList(requests);
  }


  // 관라자가 회원의 ID에 해당하는 미승인 요청건 목록을 보여줌
  public void showMemberUnapprovedInboundList(Map<String, Integer> requests) throws SQLException {
      // 뷰를 통해 관리자가 입고 요청을 처리할 회원의 ID를 받음
      String memberId = inboundView.getMemberId(requests, "조회할 회원의 ID를 입력하세요.");

      // 해당 회원의 입고 요청 승인, 수정, 삭제 페이지로 이동
      managerInboundRequestMenu(memberId, showUnapprovedRequests(memberId));

  }

  public void showMemberApprovedInboundList(Map<String, Integer> requests) throws SQLException {
    // 뷰를 통해 관리자가 입고 완료 현황을 보고 싶은 회원의 ID를 받음
    String memberId = inboundView.getMemberId(requests, "조회할 회원의 ID를 입력하세요.");

    // 해당 회원의 승인된 입고 현황을 보여줌
    showApprovedInboundStatus(memberId);
  }

  public void grantUnapprovedRequest(String memberId, List<String> unapprovedRequestIds) throws SQLException {
    // 해당하는 회원의 입고 요청리스트 중 관리자에게 처리를 원하는 입고 요청 ID를 받음.
    String unapprovedRequestId = inboundView.getOneRequestId(unapprovedRequestIds,"입고 요청을 처리할 입고 요청 ID를 입력하세요.");
    processInboundRequestLocation(memberId, unapprovedRequestId);
  }

  // 3 번 메뉴 : 회원 아이디 및 승인 건수 조회 -> 회원 아이디 입력 시 승인된 회원의 입고 요청 내역 표시
  public void showMemberApprovedInboundRequests() throws SQLException {

    // 실행 결과 가져오기
    Map<String, Integer> requests = inboundService.getMemberApprovedInboundRequests();
    // 뷰에 결과 표시
    inboundView.displayMemberApprovedInboundRequests(requests);

    // 관리자가 회원의 입고 요청을 승인하기 위해 미승인 요청 건 조회 메소드
    showMemberApprovedInboundList(requests);
  }



  // 관리자가 해당 회원의 입고 요청 정보를 입고 요청 ID별로 위치를 지정하는 메소드
  public void processInboundRequestLocation(String memberId, String unapprovedRequestId) throws SQLException {

    List<InboundItem> inboundItems = inboundService.getInboundRequestItems(memberId, unapprovedRequestId);
    List<InboundItem> inboundItemsApproved = new ArrayList<>();
    while (!inboundItems.isEmpty()) {
      List<Long> requestItemIds = inboundItems.stream().
              mapToLong(InboundItem::getInboundRequestItemId).boxed().toList();

      // 뷰에서 회원의 하나의 입고 요청에 해당하는 입고 상세 내역을 보여줌
      inboundView.displayInboundRequestItems(inboundItems);

      // 뷰에서 해당 회원의 하나의 입고 요청 중 관리자가 위치를 지정할 상품 및 수량 한 건의 ID를 받음.
      long inboundRequestItemId = inboundView.getInboundRequestItemIdByMember(memberId, requestItemIds, "회원의 입고 요청 상세 ID를 입력해주세요.");

      // 해당 상품의 가능한 창고 위치를 먼저 보여주고 위치 ID를 받음
      String locationPlaceId = getAvailableLocationPlaces();

      for (int i = 0; i < inboundItems.size(); i++) {
        if (inboundItems.get(i).getInboundRequestItemId() == inboundRequestItemId) {
          // 해당 아이템의 위치를 지정하고 새로운 상품에 추가 뒤 지움
          inboundItems.get(i).setLocationPlaceId(locationPlaceId);
          inboundItemsApproved.add(inboundItems.get(i));
          inboundItems.remove(i);
          break;
        }
      }
    }

    // 뷰에서 해당 회원의 입고 요청 승인 여부를 사용자에게 출력하고 사용자의 승인 여부를 받음
    int choice = inboundView.getInboundRequestGrant(memberId, unapprovedRequestId);

    switch (choice) {
      // 승인한 경우 입고 처리 프로세스 진행
      case 1:

        // 관리자가 창고 위치를 선택한 경우 회원의 미승인 요청 상품의 아이디의 선택한 위치를 인자로 넘겨줌
        processInboundRequest(unapprovedRequestId, inboundItemsApproved);
        break;
      case 0:
        // 승인하지 않은 경우 다시 회원의 입고 요청 목록을 보여줌
        showMemberUnapprovedInboundRequest();
        break;
    }

  }

  private void processInboundRequest(String unapprovedRequestId, List<InboundItem> inboundItemsApproved) {
    // 커맨드 객체 생성 및 실행
    ProcessInboundRequestCommand command = new ProcessInboundRequestCommand(inboundService, unapprovedRequestId, inboundItemsApproved);
    executeCommand(command);

  }


  // 가능한 창고 위치를 보여주고 창고 위치ID를 반환하는 메소드
  public String getAvailableLocationPlaces() {
    // 커맨드 객체 생성 및 실행
    ShowAvailableLocationPlacesCommand showCommand = new ShowAvailableLocationPlacesCommand(inboundService);
    executeCommand(showCommand);

    // 실행 결과 가져오기
    List<LocationPlace> locationPlaces = showCommand.getResult();

    // 뷰에서 보여주고, 사용자에게 창고 위치ID를 받아오기
    return inboundView.showAvailableLocationPlaces(locationPlaces);
  }



}