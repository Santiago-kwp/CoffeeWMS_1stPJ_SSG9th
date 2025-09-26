package view.inventory;


import controller.inventory.InventoryController;
import domain.inventory.UserVO;
import java.util.Scanner;
import service.UserService;

public class Main {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    UserService userService = new UserService(); // UserService 객체 생성

    System.out.println("====== WMS 프로그램을 시작합니다 ======");
    System.out.print("로그인할 사용자 ID를 입력하세요: ");
    String inputId = sc.nextLine();

    // 입력받은 ID로 로그인 시도
    UserVO loggedInUser = userService.login(inputId);

    // 로그인 성공 여부 확인
    if (loggedInUser == null) {
      System.out.println(">> 로그인 실패: 해당 아이디가 존재하지 않습니다. 프로그램을 종료합니다.");
      return; // 프로그램 종료
    }

    // 로그인 성공 시 환영 메시지 출력
    System.out.println("\n======================================================");
    System.out.printf("'%s'님(%s)으로 WMS 재고 관리 프로그램을 시작합니다.\n",
        loggedInUser.getUserId(), loggedInUser.getRole().name());
    System.out.println("======================================================");

    // 재고 관리 메인 메뉴 호출
    InventoryController.getInstance().inventoryMainMenu(loggedInUser);

    System.out.println("\n프로그램을 종료합니다.");
  }
}