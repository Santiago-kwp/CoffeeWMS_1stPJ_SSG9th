package controller.inventory;

import constant.inventory.InventoryMessage;
import constant.inventory.Role;
import domain.inventory.DueDiligenceVO;
import domain.inventory.UserVO;
import exception.inventory.DataNotFoundException;
import exception.inventory.DuplicateKeyException;
import java.util.List;
import service.inventory.DueDiligenceService;
import view.inventory.DueDiligenceView;
import view.inventory.InventoryView;


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
    try {
      DueDiligenceVO vo = diligenceView.inputCreateDiligence();
      diligenceService.create(user, vo);
      diligenceView.printMessage(InventoryMessage.PROCESS_SUCCESS, "실사 등록");

    } catch (DuplicateKeyException | DataNotFoundException e) {
      // ID가 중복되거나, 존재하지 않는 재고 ID를 입력한 경우를 모두 잡습니다.
      new InventoryView().printErrorMessage(e.getMessage());

    } catch (RuntimeException e) {
      new InventoryView().printErrorMessage(InventoryMessage.SYSTEM_ERROR.toString());
      e.printStackTrace();
    }
  }

  private void updateDiligence(UserVO user) {
    List<DueDiligenceVO> updatableList = diligenceService.getList(user, null, "UPDATABLE", 1, false);
    diligenceView.printDiligenceList(updatableList);

    if (updatableList == null || updatableList.isEmpty()) {
      System.out.println("\n>> 수정할 수 있는 실사 항목이 없습니다.");
      return;
    }

    try {
      // --- try 블록 시작 ---
      String id = diligenceView.inputDiligenceId("수정");
      if (id == null) {
        diligenceView.printMessage(InventoryMessage.PROCESS_FAILURE, "실사 수정 취소");
        return;
      }

      DueDiligenceVO vo = diligenceView.inputUpdateDiligence(id);

      // 서비스를 통해 수정 요청 (성공하면 다음 줄로, 실패하면 catch로 이동)
      diligenceService.update(user, vo);

      // try 블록이 끝까지 실행되었다면 성공한 것
      diligenceView.printMessage(InventoryMessage.PROCESS_SUCCESS, "실사 수정");

    } catch (DataNotFoundException e) {
      // DAO에서 던진 "해당 실사 ID를 찾을 수 없다"는 예외를 여기서 잡습니다.
      // e.getMessage()에는 "해당 실사 ID를 찾을 수 없거나..." 메시지가 담겨 있습니다.
      new InventoryView().printErrorMessage(e.getMessage());

    } catch (RuntimeException e) {
      // 그 외 예측하지 못한 다른 런타임 예외들을 처리하는 안전망입니다.
      new InventoryView().printErrorMessage(InventoryMessage.SYSTEM_ERROR.toString());
      e.printStackTrace(); // 개발 확인용 오류 로그
    }
    // --- try-catch 블록 끝 ---
    // catch 블록이 실행되더라도 메소드는 정상적으로 종료되고, 메뉴를 보여주는 while 루프로 돌아갑니다.
  }

  private void deleteDiligence(UserVO user) {
    List<DueDiligenceVO> list = diligenceService.getList(user, null, null, 1, false);
    diligenceView.printDiligenceList(list);

    // 수정할 항목이 없을 때 사용자에게 메시지를 보여주는 로직 추가 (선택 사항이지만 권장)
    if (list == null || list.isEmpty()) {
      System.out.println("\n>> 삭제할 수 있는 실사 항목이 없습니다.");
      return;
    }

    try {
      String id = diligenceView.inputDiligenceId("삭제");
      if (id == null) {
        diligenceView.printMessage(InventoryMessage.PROCESS_FAILURE, "실사 삭제 취소");
        return;
      }

      diligenceService.delete(user, id);
      diligenceView.printMessage(InventoryMessage.PROCESS_SUCCESS, "실사 삭제");

    } catch (DataNotFoundException e) {
      new InventoryView().printErrorMessage(e.getMessage());

    } catch (RuntimeException e) {
      new InventoryView().printErrorMessage(InventoryMessage.SYSTEM_ERROR.toString());
      e.printStackTrace();
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
    try {
      // ... (ID 입력받는 부분부터 try 블록 안에 넣기) ...
      String id = diligenceView.inputDiligenceId("처리");
      if (id == null) {
        diligenceView.printMessage(InventoryMessage.PROCESS_FAILURE, "승인/반려 처리 취소");
        return;
      }

      int choice = diligenceView.inputApprovalAction();
      String action = "";

      if (choice == 1) {
        diligenceService.approve(user, id); // 여기서 예외 발생 가능
        action = "승인";
      } else if (choice == 2) {
        diligenceService.reject(user, id); // 여기서 예외 발생 가능
        action = "반려";
      } else {
        diligenceView.printMessage(InventoryMessage.INVALID_MENU_CHOICE);
        return;
      }

      diligenceView.printMessage(InventoryMessage.PROCESS_SUCCESS, "실사 " + action);

    } catch (DataNotFoundException e) {
      // DAO에서 던진 "해당 실사 ID를 찾을 수 없다"는 예외를 잡아서 처리
      new InventoryView().printErrorMessage(e.getMessage());
    } catch (RuntimeException e) {
      new InventoryView().printErrorMessage(InventoryMessage.SYSTEM_ERROR.toString());
      e.printStackTrace();
    }
  }
}