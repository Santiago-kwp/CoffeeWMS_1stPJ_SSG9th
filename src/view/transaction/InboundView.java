package view.transaction;

import controller.transaction.InboundController;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class InboundView {

  private final InboundController inboundController;
  private final Scanner sc;

  public InboundView() {
    this.inboundController = new InboundController();
    sc = new Scanner();
  }

  public void displayInboundMenu(String memberId) {
    System.out.println("--- 입고(회원) ---");
    while (true) {
      System.out.println("1. 입고 요청 | 2. 입고 요청내역 조회 | 3. 입고현황 조회 | 4. 뒤로 가기 >> 로그인(회원)");
      System.out.print("메뉴 선택: ");
      try {
        int menu = sc.nextInt();
        sc.nextLine(); // 버퍼 비우기
        switch (menu) {
          case 1:
            requestInbound(memberId);
            break;
          case 2:
            viewInboundRequestHistory(memberId);
            break;
          case 3:
            viewInboundStatus(memberId);
            break;
          case 4:
            System.out.println("이전 메뉴로 돌아갑니다.");
            return;
          default:
            System.out.println("잘못된 메뉴 번호입니다. 다시 입력해주세요.");
            break;
        }
      } catch (InputMismatchException e) {
        System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
        sc.nextLine(); // 잘못된 입력 버퍼 비우기
      }
    }
  }

  private void requestInbound(String memberId) {
    System.out.println("--- 입고 요청 ---");
    try {
      System.out.print("입고 요청할 창고 관리자 아이디를 입력하세요: ");
      String managerId = sc.nextLine();

      // InboundRequest 객체 생성 (기본정보)
      InboundRequest request = new InboundRequest();
      request.setMemberId(memberId);
      request.setManagerId(managerId);

      // 입고 요청 아이템 목록 입력
      System.out.println("입고 요청할 품목을 입력하세요 (입력을 마치려면 'end' 입력)");
      do {
        System.out.print("커피 ID: ");
        String coffeeId = sc.nextLine();
        if (coffeeId.equalsIgnoreCase("end")) break;

        System.out.print("수량: ");
        int quantity = sc.nextInt();
        sc.nextLine(); // 버퍼 비우기

        InboundRequestItems item = new InboundRequestItems();
        item.setCoffeeId(coffeeId);
        item.setInboundRequestQuantity(quantity);
        request.addInboundItem(item);
      } while (true);

      boolean success = inboundController.requestInbound(request);
      if (success) {
        System.out.println("입고 요청이 성공적으로 접수되었습니다. 관리자 승인을 기다려주세요.");
      } else {
        System.out.println("입고 요청에 실패했습니다. 입력 정보를 확인해주세요.");
      }
    } catch (InputMismatchException e) {
      System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
      sc.nextLine();
    }
  }

  private void viewInboundRequestHistory(String memberId) {
    System.out.println("--- 입고 요청내역 조회 ---");
    try {
      List<InboundRequest> requests = inboundController.getInboundRequestHistory(memberId);
      if (requests.isEmpty()) {
        System.out.println("입고 요청내역이 없습니다.");
        return;
      }

      for (InboundRequest request : requests) {
        System.out.println("----------------------------------------");
        System.out.println("요청 ID: " + request.getInboundRequestId());
        System.out.println("요청일: " + request.getInboundDate());
        System.out.println("승인 상태: " + (request.isInboundRequestApproved() ? "승인완료" : "미승인"));
        System.out.println("관리자 ID: " + request.getManagerId());
        System.out.println("--- 요청 품목 ---");
        for (InboundRequestItems item : request.getInboundItems()) {
          System.out.println("  - 커피 ID: " + item.getCoffeeId() + ", 수량: " + item.getInboundRequestQuantity());
        }
      }
      System.out.println("----------------------------------------");
    } catch (Exception e) {
      System.out.println("입고 요청내역 조회 중 오류가 발생했습니다: " + e.getMessage());
    }
  }

  private void viewInboundStatus(String memberId) {
    System.out.println("--- 입고 현황 조회 ---");
    try {
      List<InboundRequest> approvedRequests = inboundController.getApprovedInboundRequests(memberId);
      if (approvedRequests.isEmpty()) {
        System.out.println("승인된 입고 요청이 없습니다.");
        return;
      }

      System.out.println("입고가 완료된/진행 중인 요청 목록:");
      for (InboundRequest request : approvedRequests) {
        System.out.println("----------------------------------------");
        System.out.println("요청 ID: " + request.getInboundRequestId());
        System.out.println("입고일: " + request.getInboundDate());
        System.out.println("관리자 ID: " + request.getManagerId());
        System.out.println("--- 입고 품목 ---");
        for (InboundRequestItems item : request.getInboundItems()) {
          System.out.println("  - 커피 ID: " + item.getCoffeeId() + ", 입고 수량: " + item.getInboundRequestQuantity());
        }
      }
      System.out.println("----------------------------------------");

    } catch (Exception e) {
      System.out.println("입고 현황 조회 중 오류가 발생했습니다: " + e.getMessage());
    }
  }
}


}
