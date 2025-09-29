package view.inventory;

import constant.inventory.ErrorMessage;
import constant.inventory.InventoryMessage;
import constant.inventory.Role;
import domain.inventory.DueDiligenceVO;
import domain.inventory.UserVO;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class DueDiligenceView {
  private final Scanner sc = new Scanner(System.in);

  public int showMainMenuAndGetChoice(UserVO user) {
    System.out.println("\n--- " + InventoryMessage.DILIGENCE_MENU_TITLE + " ---");
    System.out.println(InventoryMessage.DILIGENCE_MENU_CHOICE_1);
    if (user.getRole() == Role.총관리자) {
      System.out.println(InventoryMessage.DILIGENCE_MENU_ADMIN_CHOICE_2);
    } else if (user.getRole() == Role.창고관리자) {
      System.out.println(InventoryMessage.DILIGENCE_MENU_WAREHOUSE_CHOICE_2);
      System.out.println(InventoryMessage.DILIGENCE_MENU_WAREHOUSE_CHOICE_3);
      System.out.println(InventoryMessage.DILIGENCE_MENU_WAREHOUSE_CHOICE_4);
    }
    System.out.println(InventoryMessage.INVENTORY_MENU_BACK);
    System.out.println(InventoryMessage.HORIZONTAL_LINE);
    while (true) {
      System.out.print(InventoryMessage.MENU_CHOICE_PROMPT);
      String input = sc.nextLine();
      try {
        return Integer.parseInt(input);
      } catch (NumberFormatException e) {
        ErrorMessageView.printError(ErrorMessage.INVALID_MENU_CHOICE);
      }
    }
  }

  public void printDiligenceList(List<DueDiligenceVO> list) {
    System.out.println(InventoryMessage.DILIGENCE_LIST_HEADER);
    if (list == null || list.isEmpty()) {
      System.out.println(InventoryMessage.NO_RESULTS);
      System.out.println(InventoryMessage.HORIZONTAL_LINE);
      return;
    }
    // [수정] 헤더에 '실사 로그'를 추가하고, 각 컬럼의 너비를 조정합니다.
    System.out.printf("%-15s %-25s %-30s %-12s %s\n", "실사ID", "상품명", "실사 로그", "실사일자", "상태");
    System.out.println(InventoryMessage.HORIZONTAL_LINE);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    for (DueDiligenceVO vo : list) {
      // [수정] vo.getLog()를 추가하여 실사 로그를 출력합니다.
      System.out.printf("%-15s %-25s %-30s %-12s %s\n",
          vo.getDueDiligenceId(),
          vo.getCoffeeName(),
          vo.getLog(),
          sdf.format(vo.getDate()),
          vo.getApprovalStatus());
    }
    System.out.println(InventoryMessage.HORIZONTAL_LINE);
  }

  public DueDiligenceVO inputCreateDiligence() {
    System.out.println("--- 신규 실사 등록 ---");
    DueDiligenceVO vo = new DueDiligenceVO();
    System.out.print("실사 ID 입력: ");
    vo.setDueDiligenceId(sc.nextLine());
    System.out.print("재고 ID 입력: ");
    vo.setInventoryId(sc.nextLine());
    System.out.print("실사 로그 입력: ");
    vo.setLog(sc.nextLine());
    vo.setDate(new Date()); // 현재 날짜로 자동 설정
    return vo;
  }

  public String inputDiligenceId(String type) {
    System.out.printf("%s할 실사 ID를 입력하세요 (취소: 0): ", type);
    String id = sc.nextLine();
    return "0".equals(id) ? null : id;
  }


  public String inputCoffeeName() {
    System.out.print(InventoryMessage.INPUT_COFFEE_NAME);
    String input = sc.nextLine();
    return input.isEmpty() ? null : input;
  }

  public String inputApprovalStatus() {
    while (true) { // 유효한 입력이 들어올 때까지 무한 반복
      System.out.print("승인 상태 필터 (1.승인요청 | 2.승인완료 | 3.승인반려 | 미입력 시 전체): ");
      String input = sc.nextLine();

      // 1. '미입력'(전체 조회)은 유효한 입력이므로 먼저 처리
      if (input.isEmpty()) {
        return null;
      }

      // 2. '1', '2', '3'도 유효한 입력이므로 처리
      if ("1".equals(input)) return "승인요청";
      if ("2".equals(input)) return "승인완료";
      if ("3".equals(input)) return "승인반려";

      // 3. 여기까지 왔다면 유효하지 않은 입력이므로 오류 메시지 출력 후 루프 계속
      ErrorMessageView.printError(ErrorMessage.INVALID_GENERAL_CHOICE);
    }
  }

  // ======================== 메뉴 출력 (신규 추가) ========================
  public int showSortingMenuAndGetChoice(int currentSortKey, boolean isFlipped) {
    System.out.println(InventoryMessage.SORTING_MENU_HEADER);
    // ▲: 오름차순, ▼: 내림차순
    String dateOrder = (currentSortKey == 1) ? (isFlipped ? "▲" : "▼") : "-"; // 실사일자는 DESC가 기본
    String nameOrder = (currentSortKey == 2) ? (isFlipped ? "▼" : "▲") : "-"; // 상품명은 ASC가 기본

    System.out.printf("1. 실사일자 (현재: %s)\n", dateOrder);
    System.out.printf("2. 상품명 (현재: %s)\n", nameOrder);
    System.out.println("0. 이전 메뉴 (실사 관리)");
    System.out.println("-----------------------------------");
    while (true) {
      System.out.print(InventoryMessage.MENU_CHOICE_PROMPT);
      String input = sc.nextLine();
      try {
        return Integer.parseInt(input);
      } catch (NumberFormatException e) {
        ErrorMessageView.printError(ErrorMessage.INVALID_MENU_CHOICE);
      }
    }
  }

  public DueDiligenceVO inputUpdateDiligence(String id) {
    System.out.println("\n--- [" + id + "] 실사 정보 수정 ---");
    DueDiligenceVO vo = new DueDiligenceVO();
    vo.setDueDiligenceId(id); // 컨트롤러에서 받은 ID를 설정

    System.out.print("새로운 실사 로그 입력: ");
    vo.setLog(sc.nextLine());

    // 날짜는 현재 날짜로 자동 갱신
    vo.setDate(new Date());
    System.out.println("실사 일자가 현재 날짜로 갱신됩니다.");

    return vo;
  }

  public int inputApprovalAction() {
    while (true) {
      System.out.print("\n작업을 선택하세요 (1: 승인 | 2: 반려): ");
      String input = sc.nextLine();
      try {
        return Integer.parseInt(input);
      } catch (NumberFormatException e) {
        ErrorMessageView.printError(ErrorMessage.INVALID_NUMBER_FORMAT);
      }
    }
  }

  public void printMessage(InventoryMessage message) { System.out.println(message); }
  public void printMessage(InventoryMessage message, String arg) { System.out.printf(message.toString(), arg); }
}
