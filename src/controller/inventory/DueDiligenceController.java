package controller.inventory;

import constant.inventory.InventoryMessage;
import constant.inventory.Role;
import domain.inventory.DueDiligenceVO;
import domain.inventory.UserVO;
import java.util.List;
import service.DueDiligenceService;
import view.inventory.DueDiligenceView;


public class DueDiligenceController {

  private static DueDiligenceController instance = new DueDiligenceController();

  private DueDiligenceController() {
  }

  public static DueDiligenceController getInstance() {
    return instance;
  }

  private final DueDiligenceService diligenceService = new DueDiligenceService();
  private final DueDiligenceView diligenceView = new DueDiligenceView();

  public void diligenceMainMenu(UserVO user) {
    while (true) {
      int choice = diligenceView.showMainMenuAndGetChoice(user);
      switch (choice) {
        case 1:
          searchDiligenceMenu(user);
          break;
        case 2:
          if (user.getRole() == Role.총관리자)
            approveDiligence(user);
          else if (user.getRole() == Role.창고관리자)
            createDiligence(user);
          break;
        case 3:
          if (user.getRole() == Role.창고관리자)
            updateDiligence(user);
          else
            diligenceView.printMessage(InventoryMessage.INVALID_MENU_CHOICE);
          break;
        case 4:
          if (user.getRole() == Role.창고관리자)
            deleteDiligence(user);
          else
            diligenceView.printMessage(InventoryMessage.INVALID_MENU_CHOICE);
          break;
        case 0:
          return;
        default:
          diligenceView.printMessage(InventoryMessage.INVALID_MENU_CHOICE);
      }
    }
  }

  private void searchDiligenceMenu(UserVO user) {
    // --- 정렬 상태를 관리하는 변수 ---
    int currentSortKey = 1; // 1:실사일자, 2:상품명 (기본값: 실사일자)
    boolean isFlipped = false; // false:기본 정렬, true:순서 반전

    // --- 검색 조건 입력 (최초 1회) ---
    String coffeeName = diligenceView.inputCoffeeName();
    String approvalStatus = diligenceView.inputApprovalStatus();

    // --- 정렬 및 재조회를 위한 반복문 ---
    while (true) {
      // Service를 통해 데이터 조회
      List<DueDiligenceVO> diligenceList = diligenceService.getList(
          user, coffeeName, approvalStatus, currentSortKey, isFlipped
      );

      // View를 통해 결과 목록 출력
      diligenceView.printDiligenceList(diligenceList);

      // View를 통해 정렬 메뉴를 보여주고, 다음 행동을 결정
      int sortChoice = diligenceView.showSortingMenuAndGetChoice(currentSortKey, isFlipped);

      if (sortChoice == 0) {
        // '이전 메뉴' 선택 시 실사 조회 메뉴 종료
        return;
      } else if (sortChoice >= 1 && sortChoice <= 2) {
        // 정렬 로직 처리
        if (currentSortKey == sortChoice) {
          isFlipped = !isFlipped; // 같은 키를 또 선택하면 순서 반전
        } else {
          currentSortKey = sortChoice; // 다른 키를 선택하면 키를 변경
          isFlipped = false; // 새로운 키는 항상 기본 정렬부터 시작
        }
      } else {
        diligenceView.printMessage(InventoryMessage.INVALID_MENU_CHOICE);
      }
    }
  }

  private void createDiligence(UserVO user) {
    DueDiligenceVO vo = diligenceView.inputCreateDiligence();
    int result = diligenceService.create(user, vo);
    if (result > 0)
      diligenceView.printMessage(InventoryMessage.PROCESS_SUCCESS, "실사 등록");
    else
      diligenceView.printMessage(InventoryMessage.PROCESS_FAILURE, "실사 등록");
  }

  private void updateDiligence(UserVO user) {
    // 1. [수정] '수정 가능한' (승인요청, 승인반려 상태) 실사 목록만 조회합니다.
    List<DueDiligenceVO> updatableList = diligenceService.getList(user, null, "UPDATABLE", 1, false);
    diligenceView.printDiligenceList(updatableList);

    // [추가] 수정할 항목이 없는 경우, 사용자에게 알리고 메소드를 종료합니다.
    if (updatableList == null || updatableList.isEmpty()) {
      System.out.println("\n>> 수정할 수 있는 실사 항목이 없습니다.");
      return;
    }

    // 2. 수정할 실사 ID를 입력받습니다.
    String id = diligenceView.inputDiligenceId("수정");
    if (id == null) {
      diligenceView.printMessage(InventoryMessage.PROCESS_FAILURE, "실사 수정 취소");
      return;
    }

    // 3. 새로운 실사 내용을 입력받습니다.
    DueDiligenceVO vo = diligenceView.inputUpdateDiligence(id);

    // 4. 서비스를 통해 수정을 요청합니다.
    int result = diligenceService.update(user, vo);
    if (result > 0) {
      diligenceView.printMessage(InventoryMessage.PROCESS_SUCCESS, "실사 수정");
    } else {
      // 이제 이 메시지는 거의 볼 일이 없지만, 혹시 모를 동시성 문제 등을 위해 남겨둡니다.
      diligenceView.printMessage(InventoryMessage.PROCESS_FAILURE, "실사 수정 (ID를 잘못 입력했거나 권한이 없습니다)");
    }
  }

  private void deleteDiligence(UserVO user) {
    // 1. 삭제 가능한 전체 실사 목록을 먼저 보여줍니다.
    List<DueDiligenceVO> list = diligenceService.getList(user, null, null, 1, false);
    diligenceView.printDiligenceList(list);

    // 2. 삭제할 실사 ID를 입력받습니다.
    String id = diligenceView.inputDiligenceId("삭제");
    if (id == null) {
      diligenceView.printMessage(InventoryMessage.PROCESS_FAILURE, "실사 삭제 취소");
      return;
    }

    // 3. 서비스를 통해 삭제를 요청합니다.
    int result = diligenceService.delete(user, id);
    if (result > 0) {
      diligenceView.printMessage(InventoryMessage.PROCESS_SUCCESS, "실사 삭제");
    } else {
      diligenceView.printMessage(InventoryMessage.PROCESS_FAILURE, "실사 삭제 (삭제 권한이 없습니다)");
    }
  }

  private void approveDiligence(UserVO user) {
    // 1. '승인요청' 상태인 목록만 보여줍니다.
    List<DueDiligenceVO> requestList = diligenceService.getList(user, null, "승인요청", 1, false);
    diligenceView.printDiligenceList(requestList);

    if (requestList == null || requestList.isEmpty()) {
      return; // 처리할 항목이 없으면 종료
    }

    // 2. 처리할 실사 ID를 입력받습니다.
    String id = diligenceView.inputDiligenceId("처리");
    if (id == null) {
      diligenceView.printMessage(InventoryMessage.PROCESS_FAILURE, "승인/반려 처리 취소");
      return;
    }

    // 3. '승인' 또는 '반려'를 선택받습니다.
    int choice = diligenceView.inputApprovalAction();
    int result = 0;
    String action = "";

    if (choice == 1) {
      // 4-1. 승인 처리
      result = diligenceService.approve(user, id);
      action = "승인";
    } else if (choice == 2) {
      // 4-2. 반려 처리
      result = diligenceService.reject(user, id);
      action = "반려";
    } else {
      diligenceView.printMessage(InventoryMessage.INVALID_MENU_CHOICE);
      return;
    }

    // 5. 결과 출력
    if (result > 0) {
      diligenceView.printMessage(InventoryMessage.PROCESS_SUCCESS, "실사 " + action);
    } else {
      diligenceView.printMessage(InventoryMessage.PROCESS_FAILURE, "실사 " + action);
    }
  }
}