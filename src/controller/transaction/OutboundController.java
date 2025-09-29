package controller.transaction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import command.Command;
import command.transaction.*;
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
import java.util.*;
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



  /**
   * 1번 메뉴: 출고 요청
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



  /**
   * 애플리케이션의 메인 루프를 실행하여 관리자 사용자와 상호작용하는 메소드
   */
  public void runManager(String managerId) throws SQLException {
    boolean running = true;
    while (running) {
      outboundView.displayManagerOutboundMenu();
      int choice = outboundView.getThreeIntegerInput("메뉴 번호를 선택하세요: ");

      switch (choice) {
        case 1: // 회원의 출고 요청 조회
          showMemberUnapprovedOutboundRequest();
          break;
        case 2:
          // 출고 완료 현황 조회
          showMemberApprovedOutboundRequests();
          break;
        case 3:
          // 뒤로 가기
          break;
        default:
          outboundView.displayMessage("잘못된 입력입니다. 다시 시도하세요.");
      }
    }
  }

  // 1 번 메뉴 : 미 승인된 회원 아이디 및 미승인 건수 조회 -> 회원 아이디 입력 시 기존의 미승인된 회원의 입고 요청 내역 표시
  public void showMemberUnapprovedOutboundRequest(){

    // 실행 결과 가져오기
    Map<String, Integer> requests = outboundService.getUnapprovedRequests();
    // 뷰에 결과 표시
    outboundView.displayMemberUnapprovedOutboundRequests(requests);

    // 관리자가 회원의 출고 요청을 승인하기 위해 미승인 요청 건 조회 메소드
      try {
          showMemberUnapprovedOutboundList(requests);
      } catch (SQLException e) {
          throw new OutboundRequestException("출고 요청 건 조회 중 예외 발생!",e);
      }
  }


  // 관라자가 회원의 ID에 해당하는 미승인 요청건 목록을 보여줌
  public void showMemberUnapprovedOutboundList(Map<String, Integer> requests) throws SQLException {
    // 뷰를 통해 관리자가 입고 요청을 처리할 회원의 ID를 받음
    String memberId = outboundView.getMemberId(requests, "조회할 회원의 ID를 입력하세요.");

    // 해당 회원의 입고 요청 승인 혹은 뒤로감
    managerOutboundRequestMenu(memberId, showUnapprovedRequests(memberId));

  }



  public void managerOutboundRequestMenu(String memberId, List<String> outboundRequestIds) throws SQLException {
    boolean running = true;
    while (running) {
      // 미승인 입고 요청 내역 수정 및 취소
      outboundView.displayManagerOutboundRequestsMenu();
      int choice = outboundView.getFourIntegerInput("메뉴 번호를 선택하세요: ");
      switch (choice) {
        case 1:
          // 출고 요청 승인
          grantUnapprovedRequest(memberId, outboundRequestIds);
          return;
        case 2:
          // 출고 요청 수정
          break;
        case 3:
          // 출고 요청 취소
          break;
        case 4:
          // 뒤로가기
          running = false;
          break;
        default:
          outboundView.displayMessage("잘못된 입력입니다. 다시 시도하세요.");

      }
    }

  }

  public void grantUnapprovedRequest(String memberId, List<String> unapprovedRequestIds) throws SQLException {
    // 해당하는 회원의 입고 요청리스트 중 관리자에게 처리를 원하는 입고 요청 ID를 받음.
    String unapprovedRequestId = outboundView.getOneRequestId(unapprovedRequestIds,"출고 요청을 처리할 출고 요청 ID를 입력하세요.");
    processOutboundRequest(memberId, unapprovedRequestId);
  }

  private void processOutboundRequest(String memberId, String unapprovedRequestId) throws SQLException {
    List<OutboundItem> outboundItems = outboundService.getOutboundRequestItems(memberId, unapprovedRequestId);
    List<OutboundItem> outboundItemsApproved = new ArrayList<>();

    while (!outboundItems.isEmpty()) {
      List<Long> requestItemIds = outboundItems.stream().
              mapToLong(OutboundItem::getOutboundRequestItemId).boxed().toList();

      // 뷰에서 회원의 하나의 출고 요청에 해당하는 출고 상세 내역을 보여줌
      outboundView.displayOutboundRequestItems(outboundItems);

      // 뷰에서 해당 회원의 하나의 출고 요청 중 관리자가 위치를 지정할 상품 및 수량 한 건의 ID를 받음.
      long outboundRequestItemId = outboundView.getOutboundRequestItemIdByMember(memberId, requestItemIds, "회원의 출고 요청 상세 ID를 입력해주세요.");


      for (int i = 0; i < outboundItems.size(); i++) {
        if (outboundItems.get(i).getOutboundRequestItemId() == outboundRequestItemId) {
          // 선택한 출고 상품을 지정하고 기존의 출고 미승인 상품에서 제외
          outboundItemsApproved.add(outboundItems.get(i));
          outboundItems.remove(i);
          break;
        }
      }

    }

    // 뷰에서 해당 회원의 입고 요청 승인 여부를 사용자에게 출력하고 사용자의 승인 여부를 받음
    int choice = outboundView.getOutboundRequestGrant(memberId, unapprovedRequestId);

    switch (choice) {
      // 승인한 경우 출고 처리 프로세스 진행
      case 1:
        // 관리자가 창고 위치를 선택한 경우 회원의 미승인 요청 상품의 아이디의 선택한 위치를 인자로 넘겨줌
        processOutboundRequestGrant(unapprovedRequestId, outboundItemsApproved);
        break;
      case 0:
        // 승인하지 않은 경우 다시 회원의 출고 요청 목록을 보여줌
        showMemberUnapprovedOutboundRequest();
        break;
    }
  }

  private void processOutboundRequestGrant(String unapprovedRequestId, List<OutboundItem> outboundItemsApproved) {

    // ObjectMapper 인스턴스 생성
    ObjectMapper objectMapper = new ObjectMapper();
    // 날짜를 직렬화 시 해줘야 됨.
    objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    String jsonString;

    try {
      jsonString = objectMapper.writeValueAsString(outboundItemsApproved);
    } catch (JsonProcessingException e) {
      // Wrap the checked exception in a custom runtime exception
      throw new DataProcessingException("직렬화 실패... outbound items to JSON.", e);
    }

      try {
          outboundService.processOutboundRequest(unapprovedRequestId, jsonString);
      } catch (SQLException e) {
          throw new OutboundRequestException("출고 요청 승인 중 예외 발생",e);
      }

  }

  public void showMemberApprovedOutboundRequests() throws SQLException {

    // 실행 결과 가져오기
    Map<String, Integer> requests = outboundService.getMemberApprovedOutboundRequests();
    // 뷰에 결과 표시
    outboundView.displayMemberApprovedOutboundRequests(requests);

    // 관리자가 회원의 입고 요청을 승인하기 위해 미승인 요청 건 조회 메소드
    showMemberApprovedOutboundList(requests);
  }

  public void showMemberApprovedOutboundList(Map<String, Integer> requests) throws SQLException {
    // 뷰를 통해 관리자가 입고 완료 현황을 보고 싶은 회원의 ID를 받음
    String memberId = outboundView.getMemberId(requests, "조회할 회원의 ID를 입력하세요.");

    // 해당 회원의 승인된 입고 현황을 보여줌
    showApprovedOutboundStatus(memberId);
  }

  private void showApprovedOutboundStatus(String memberId) {
    outboundView.displayMessage("승인된 입고 완료 현황을 조회합니다.");

      List<OutboundItem> outboundItems = null;
      try {
          outboundItems = outboundService.getApprovedRequestsByMember(memberId);
      } catch (SQLException e) {
          throw new OutboundRequestException("출고 승인 완료 현황 조회 중 에러 발생!", e);
      }

      // 결과 표시 및 상세 조회
    try {
      approvedOutboundMenu(memberId, outboundItems);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private void approvedOutboundMenu(String memberId, List<OutboundItem> outboundItems) throws SQLException {
    boolean running = true;
    while (running) {
      // 전체 결과 조회
      outboundView.displayOutboundApprovedItems(outboundItems);
      int choice = outboundView.getThreeIntegerInput("메뉴 번호를 선택하세요: ");
      switch (choice) {
        case 1:
          // 기간별 입고 현황 조회
          break;
        case 2:
          // 월별 입고 현황
          break;
        case 3:
          // 뒤로가기
          running = false;
          break;
        default:
          outboundView.displayMessage("잘못된 입력입니다. 다시 시도하세요.");
      }
    }
  }



}
