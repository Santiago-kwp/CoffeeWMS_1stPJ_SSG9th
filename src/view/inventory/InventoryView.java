package view.inventory;

import constant.inventory.ErrorMessage;
import constant.inventory.InventoryMessage;
import constant.inventory.Role;
import domain.inventory.InventoryVO;
import domain.inventory.UserVO;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class InventoryView {
  private final Scanner sc = new Scanner(System.in);

  // ======================== 메뉴 출력 ========================

  /**
   * 관리자용 재고 관리 메인 메뉴를 보여주고 입력을 받습니다.
   * (회원은 이 메뉴를 보지 않고 재고 조회로 바로 넘어갑니다)
   */
  public int showMainMenuAndGetChoice(UserVO user) {
    System.out.println("\n--- " + InventoryMessage.INVENTORY_MAIN_MENU_TITLE + " ---");
    System.out.println(InventoryMessage.INVENTORY_MENU_CHOICE_1);
    System.out.println(InventoryMessage.INVENTORY_MENU_CHOICE_2);
    System.out.println(InventoryMessage.INVENTORY_MENU_BACK);
    System.out.println(InventoryMessage.HORIZONTAL_LINE);
    while (true) { // 무한 루프 시작
      System.out.print(InventoryMessage.MENU_CHOICE_PROMPT);
      String input = sc.nextLine();
      try {
        return Integer.parseInt(input); // 성공 시 값 반환 및 루프 종료
      } catch (NumberFormatException e) {
        ErrorMessageView.printError(ErrorMessage.INVALID_MENU_CHOICE); // 실패 시 오류 메시지 출력 후 루프 계속
      }
    }
  }

  /**
   * 정렬 메뉴를 보여주고 사용자의 선택을 받습니다.
   * 현재 정렬 상태를 화살표(▲/▼)로 표시합니다.
   */
  public int showSortingMenuAndGetChoice(int currentSortKey, boolean isFlipped) {
    System.out.println(InventoryMessage.SORTING_MENU_HEADER);
    // 각 정렬 옵션의 현재 상태(오름차순/내림차순)를 계산하여 표시
    // ▲: 오름차순, ▼: 내림차순
    String nameOrder = (currentSortKey == 1) ? (isFlipped ? "▼" : "▲") : "-";
    String dateOrder = (currentSortKey == 2) ? (isFlipped ? "▲" : "▼") : "-";
    String priceOrder = (currentSortKey == 3) ? (isFlipped ? "▼" : "▲") : "-";

    System.out.printf(InventoryMessage.SORTING_MENU_CHOICE_1.toString() + "\n", nameOrder);
    System.out.printf(InventoryMessage.SORTING_MENU_CHOICE_2.toString() + "\n", dateOrder);
    System.out.printf(InventoryMessage.SORTING_MENU_CHOICE_3.toString() + "\n", priceOrder);
    System.out.println(InventoryMessage.SORTING_MENU_BACK);
    System.out.println("-----------------------------------");
    while (true) { // 무한 루프 시작
      System.out.print(InventoryMessage.MENU_CHOICE_PROMPT);
      String input = sc.nextLine();
      try {
        return Integer.parseInt(input); // 성공 시 값 반환 및 루프 종료
      } catch (NumberFormatException e) {
        ErrorMessageView.printError(ErrorMessage.INVALID_MENU_CHOICE); // 실패 시 오류 메시지 출력 후 루프 계속
      }
    }
  }

  // ======================== 사용자 입력 ========================

  /**
   * 사용자의 역할에 따라 창고 "이름" 입력을 받습니다.
   * @param user 로그인한 사용자 정보
   * @return 입력받은 창고 이름, 입력 안하면 null
   */
  public String inputCargoName(UserVO user) {
    if (user.getRole() == Role.총관리자 || user.getRole() == Role.일반회원) {
      while (true) {
        System.out.print(InventoryMessage.INPUT_WAREHOUSE_CHOICE);
        String input = sc.nextLine();
        if (input.isEmpty()) return null; // 미입력 시 null 반환
        switch (input) {
          case "1": return "부산신항창고";
          case "2": return "곤지암창고";
          case "3": return "대덕창고";
          default:
            // 1,2,3,미입력이 아니면 오류 메시지 출력 후 다시 질문
            ErrorMessageView.printError(ErrorMessage.INVALID_GENERAL_CHOICE);
        }
      }
    }
    return null;
  }

  /**
   * 사용자의 역할에 따라 회원사 선택 입력을 받습니다.
   * @param user 로그인한 사용자 정보
   * @return 선택한 회사 이름, 입력 안하면 null
   */
  public String inputCompanyName(UserVO user) {
    if (user.getRole() == Role.총관리자 || user.getRole() == Role.창고관리자) {
      while (true) {
        System.out.print(InventoryMessage.INPUT_MEMBER_CHOICE);
        String input = sc.nextLine();
        if (input.isEmpty()) return null;
        switch (input) {
          case "1": return "스타벅스";
          case "2": return "투썸플레이스";
          default:
            ErrorMessageView.printError(ErrorMessage.INVALID_GENERAL_CHOICE);
        }
      }
    }
    return null;
  }

  public String inputCoffeeName() {
    System.out.print(InventoryMessage.INPUT_COFFEE_NAME);
    return sc.nextLine();
  }

  public String inputBeanType() {
    while (true) {
      System.out.print(InventoryMessage.INPUT_BEAN_TYPE);
      String input = sc.nextLine();
      if (input.isEmpty()) return null;
      switch (input) {
        case "1": return "원두";
        case "2": return "생두";
        default:
          ErrorMessageView.printError(ErrorMessage.INVALID_GENERAL_CHOICE);
      }
    }
  }

  public String inputIsDecaf() {
    while (true) {
      System.out.print(InventoryMessage.INPUT_IS_DECAF);
      String input = sc.nextLine();
      if (input.isEmpty()) return null;
      switch (input) {
        case "1": return "Y";
        case "2": return "N";
        default:
          ErrorMessageView.printError(ErrorMessage.INVALID_GENERAL_CHOICE);
      }
    }
  }

  // ======================== 결과 출력 ========================

  /**
   * 재고 목록을 형식에 맞춰 출력합니다.
   */
  public void printInventoryList(List<InventoryVO> list) {
    System.out.println(InventoryMessage.INVENTORY_LIST_HEADER);
    if (list == null || list.isEmpty()) {
      System.out.println(InventoryMessage.NO_RESULTS);
      System.out.println(InventoryMessage.HORIZONTAL_LINE);
      return;
    }

    System.out.printf("%-12s | %-20s | %-5s | %-5s | %-10s | %-10s | %-10s | %-10s | %-7s | %s\n",
        "상품ID", "상품명", "산지", "원두", "디카페인", "등급", "품종", "단가", "총수량", "입고일");
    System.out.println(InventoryMessage.HORIZONTAL_LINE);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    for (InventoryVO vo : list) {
      String formattedDate = (vo.getMinInboundDate() != null) ? sdf.format(vo.getMinInboundDate()) : "N/A";
      System.out.printf("%-12s | %-20s | %-5s | %-5s | %-5s | %-8s | %-10s | %-8d | %-7d | %s\n",
          vo.getCoffeeId(),
          vo.getCoffeeName(),
          vo.getCoffeeOrigin(),
          vo.getCoffeeRoasted(),
          vo.getDecaf(),
          vo.getCoffeeGrade(),
          vo.getCoffeeType(),
          vo.getPrice(),
          vo.getTotalQuantity(),
          formattedDate);
    }
    System.out.println(InventoryMessage.HORIZONTAL_LINE);
  }

  /**
   * 공통 메시지를 출력합니다.
   */
  public void printMessage(InventoryMessage message) {
    System.out.println(message);
  }

  public void printErrorMessage(String message) {
    System.out.println("\n!!! " + message + " !!!\n");
  }
}