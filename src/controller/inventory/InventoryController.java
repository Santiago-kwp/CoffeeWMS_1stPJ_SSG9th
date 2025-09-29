package controller.inventory;

import constant.inventory.InventoryMessage;
import constant.inventory.Role;
import domain.inventory.InventoryVO;
import domain.inventory.UserVO;
import java.util.List;
import service.inventory.InventoryService;
import view.inventory.InventoryView;

public class InventoryController {
  // --- 싱글톤 패턴 적용 ---
  private static InventoryController instance = new InventoryController();
  private InventoryController() {}
  public static InventoryController getInstance() {
    return instance;
  }

  // --- 의존성 객체 (Service와 View에 의존) ---
  private final InventoryService inventoryService = new InventoryService();
  private final InventoryView inventoryView = new InventoryView();

  /**
   * WMS 메인 메뉴에서 '재고관리'를 선택했을 때 호출되는 시작 메소드.
   * @param user 로그인한 사용자 정보
   */
  public void inventoryMainMenu(UserVO user) {
    // 일반회원이면 재고 조회 메뉴로 바로 이동
    if (user.getRole() == Role.일반회원) {
      inventorySearchMenu(user);
      return; // 재고 조회가 끝나면 바로 WMS 메인 메뉴로 돌아감
    }

    // 관리자는 '재고 조회'와 '실사 관리' 중 선택 가능
    while (true) {
      int choice = inventoryView.showMainMenuAndGetChoice(user);
      switch (choice) {
        case 1:
          // 재고 조회 서브메뉴 호출
          inventorySearchMenu(user);
          break;
        case 2:
          // DueDiligenceController를 호출하는 로직
          DueDiligenceController.getInstance().diligenceMainMenu(user);
          break;
        case 0:
          return; // WMS 메인 메뉴로 복귀
        default:
          inventoryView.printMessage(InventoryMessage.INVALID_MENU_CHOICE);
          break;
      }
    }
  }

  /**
   * 재고 조회 및 정렬을 담당하는 서브 메뉴.
   * 사용자의 입력을 받아 DAO를 호출하고, 결과를 출력한 후 정렬 메뉴로 이어지는 흐름을 반복합니다.
   * @param user 로그인한 사용자 정보
   */
  private void inventorySearchMenu(UserVO user) {
    // --- 정렬 상태를 관리하는 변수 ---
    int currentSortKey = 1; // 1:이름, 2:날짜, 3:단가 (기본값: 이름)
    boolean isFlipped = false; // false:기본 정렬, true:순서 반전

    inventoryView.printMessage(InventoryMessage.INVENTORY_SEARCH_MENU_TITLE);


    // 역할에 따라 View가 알아서 필요한 질문만 하도록 호출
    String cargoName = inventoryView.inputCargoName(user);
    String companyName = inventoryView.inputCompanyName(user);

    // 공통 필터 입력 받기
    String coffeeName = inventoryView.inputCoffeeName();
    String beanType = inventoryView.inputBeanType();
    String isDecaf = inventoryView.inputIsDecaf();


    // [핵심 변경 2] 정렬 및 재조회를 위한 반복문 시작
    while (true) {
      // Service를 통해 데이터 조회 (저장된 검색 조건과 현재 정렬 조건 사용)
      List<InventoryVO> inventoryList = inventoryService.searchInventory(
          user, coffeeName, beanType, isDecaf, cargoName, companyName, // 이전에 입력받은 값 재사용
          currentSortKey, isFlipped // 계속 바뀌는 정렬 값
      );

      // View를 통해 결과 목록 출력
      inventoryView.printInventoryList(inventoryList);

      // View를 통해 정렬 메뉴를 보여주고, 다음 행동을 결정
      int sortChoice = inventoryView.showSortingMenuAndGetChoice(currentSortKey, isFlipped);

      if (sortChoice == 0) {
        // '이전 메뉴' 선택 시 재고 조회 메뉴 종료 (상위 메뉴로 복귀)
        return;
      } else if (sortChoice >= 1 && sortChoice <= 3) {
        // 정렬 로직 처리
        if (currentSortKey == sortChoice) {
          isFlipped = !isFlipped; // 같은 키를 또 선택하면 순서 반전
        } else {
          currentSortKey = sortChoice; // 다른 키를 선택하면 키를 변경
          isFlipped = false; // 새로운 키는 항상 기본 정렬부터 시작
        }
        // 정렬 상태 변경 후, 루프의 처음으로 돌아가 재조회
      } else {
        inventoryView.printMessage(InventoryMessage.INVALID_MENU_CHOICE);
      }
    }
  }
}
