package view.transaction;

import constant.transaction.TransactionText;
import domain.transaction.Coffee; // Coffee DTO
import domain.transaction.dto.InboundRequestDTO;
import domain.transaction.dto.InboundRequestItemDTO;
import domain.transaction.vo.InboundStatus;
import exception.transaction.ValidationException;
import view.transaction.InputHandler;
import view.transaction.ValidCheck;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InboundView {

    private final ValidCheck validCheck;

    public InboundView() {
        this.validCheck = new ValidCheck();
    }

    // ================== 메뉴 출력 ==================
    public void displayMemberMenu() {
        System.out.println("\n===== 입고 관리 (회원) =====");
        System.out.println("1. 입고 요청 등록");
        System.out.println("2. 입고 요청 수정");
        System.out.println("3. 입고 요청 취소");
        System.out.println("4. 입고 요청 상세 조회");
        System.out.println("5. 입고 요청 목록 조회");
        System.out.println("0. 이전 메뉴로");
        System.out.println(TransactionText.BORDER_LINE.getText());
    }

    public void displayManagerMenu() {
        System.out.println("\n===== 입고 관리 (관리자) =====");
        System.out.println("1. 입고 요청 승인");
        System.out.println("2. 입고 요청 거절");
        System.out.println("3. 입고 요청 상세 조회");
        System.out.println("4. 입고 요청 목록 조회");
        System.out.println("0. 이전 메뉴로");
        System.out.println(TransactionText.BORDER_LINE.getText());
    }

    // ================== 정보 출력 ==================
    public void displayInboundRequestDetail(InboundRequestDTO dto) {
        System.out.println(TransactionText.BORDER_LINE.getText());
        System.out.println("===== 입고 요청 상세 정보 =====");
        System.out.println("요청 ID: " + dto.getInboundRequestId());
        System.out.println("요청자 ID: " + dto.getMemberId());
        System.out.println("요청일: " + dto.getRequestDate());
        System.out.println("상태: " + dto.getStatus().getDisplayName());
        System.out.println("처리 관리자 ID: " + (dto.getManagerId() != null ? dto.getManagerId() : "N/A"));
        System.out.println("처리일: " + (dto.getApprovalDate() != null ? dto.getApprovalDate() : "N/A"));
        System.out.println("입고증: " + (dto.getInboundReceipt() != null ? dto.getInboundReceipt() : "N/A"));
        System.out.println("--- 요청 항목 ---");
        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            System.out.println("요청된 항목이 없습니다.");
        } else {
            for (InboundRequestItemDTO item : dto.getItems()) {
                System.out.printf(" - 커피 ID: %s, 수량: %d\n", item.getCoffeeId(), item.getInboundRequestQuantity());
            }
        }
        System.out.println(TransactionText.BORDER_LINE.getText());
    }

    public void displayInboundRequestList(List<InboundRequestDTO> dtoList) {
        System.out.println(TransactionText.BORDER_LINE.getText());
        if (dtoList == null || dtoList.isEmpty()) {
            System.out.println("조회된 입고 요청이 없습니다.");
        } else {
            System.out.println("요청 ID | 요청자 ID | 요청일 | 상태");
            System.out.println("----------------------------------------------------------");
            for (InboundRequestDTO dto : dtoList) {
                System.out.printf("%-7d | %-10s | %-12s | %s\n",
                        dto.getInboundRequestId(),
                        dto.getMemberId(),
                        dto.getRequestDate().toString(),
                        dto.getStatus().getDisplayName()
                );
            }
        }
        System.out.println(TransactionText.BORDER_LINE.getText());
    }

    // ================== 사용자 입력 처리 ==================
    /**
     * 입고 요청에 필요한 상세 항목 리스트를 사용자로부터 입력받습니다.
     * @param coffeeList 사용자에게 보여줄 전체 커피 상품 리스트
     * @return 사용자가 입력한 InboundRequestItemDTO 리스트
     * @throws IOException 입력 오류 발생 시
     */
    public List<InboundRequestItemDTO> getInboundItemsFromUser(List<Coffee> coffeeList) throws IOException {
        System.out.println(TransactionText.COFFEES_HEADER.getText());
        // 1. 주문 가능한 커피 목록 출력
        for (int i = 0; i < coffeeList.size(); i++) {
            Coffee coffee = coffeeList.get(i);
            System.out.printf("%d. %s (ID: %s) - %,d원\n", i + 1, coffee.getCoffeeName(), coffee.getCoffeeId(), coffee.getPrice());
        }
        System.out.println(TransactionText.BORDER_LINE.getText());

        List<InboundRequestItemDTO> itemsToAdd = new ArrayList<>();
        while (true) {
            try {
                String input = InputHandler.readInput("추가할 '상품번호,수량'을 입력하세요 (입력 종료: exit): ");
                if (input.equalsIgnoreCase("exit")) {
                    if (itemsToAdd.isEmpty()) {
                        displayError("적어도 하나 이상의 상품을 추가해야 합니다.");
                        continue; // 종료하지 않고 다시 입력받음
                    }
                    break; // 입력 종료
                }

                // 2. 입력값 검증
                int[] itemInfo = validCheck.checkInboundItemInput(input, coffeeList.size());
                int itemIndex = itemInfo[0] - 1; // 0-based index로 변환
                int quantity = itemInfo[1];

                // 3. DTO 생성 및 리스트에 추가
                Coffee selectedCoffee = coffeeList.get(itemIndex);
                InboundRequestItemDTO newItem = new InboundRequestItemDTO();
                newItem.setCoffeeId(selectedCoffee.getCoffeeId());
                newItem.setInboundRequestQuantity(quantity);
                itemsToAdd.add(newItem);

                displaySuccess(String.format("'%s' %d개 추가 완료.", selectedCoffee.getCoffeeName(), quantity));

            } catch (ValidationException e) {
                displayError(e.getMessage());
            }
        }
        return itemsToAdd;
    }

    /**
     * 사용자로부터 입고 요청 날짜를 입력받습니다.
     * @return 사용자가 입력한 LocalDate
     * @throws IOException 입력 오류 발생 시
     */
    public LocalDate getRequestDateFromUser() throws IOException {
        while (true) {
            try {
                String input = InputHandler.readInput("입고 요청 날짜를 입력하세요 (예: 2025-10-06): ");
                return validCheck.checkDate(input);
            } catch (ValidationException e) {
                displayError(e.getMessage());
            }
        }
    }

    public Long getInboundRequestIdFromUser() throws IOException {
        while (true) {
            try {
                String input = InputHandler.readInput("처리할 입고 요청 ID를 입력하세요: ");
                return validCheck.checkLong(input, "요청 ID");
            } catch (ValidationException e) {
                displayError(e.getMessage());
            }
        }
    }

    public InboundStatus getStatusFromUser() throws IOException {
        while (true) {
            System.out.println("조회할 입고 요청 상태를 선택하세요:");
            String choice = InputHandler.readInput("1. 대기 | 2. 승인완료 | 3. 거절 | 4. 입고완료 : ");
            switch (choice) {
                case "1": return InboundStatus.PENDING;
                case "2": return InboundStatus.APPROVED;
                case "3": return InboundStatus.REJECTED;
                case "4": return InboundStatus.COMPLETED;
                default: displayError("잘못된 입력입니다. 1~4 사이의 숫자를 입력해주세요.");
            }
        }
    }

    // ================== 사용자 입력 처리 ==================

    /**
     * 사용자로부터 메뉴 선택 번호를 입력받아 문자열로 반환합니다.
     * 유효성 검사는 컨트롤러에서 수행하므로, 여기서는 입력만 받습니다.
     * @return 사용자가 입력한 메뉴 번호 문자열
     * @throws IOException 입력 중 오류 발생 시
     */
    public String getMenuChoiceFromUser() throws IOException {
        return InputHandler.readInput("메뉴 번호를 입력해주세요: ");
    }


    // ================== 메시지 출력 ==================
    public void displaySuccess(String message) {
        System.out.println("✅ 성공: " + message);
    }

    public void displayError(String errorMessage) {
        System.out.println("❌ 오류: " + errorMessage);
    }

    // ================== 헤더 출력 ==================
    public void displayRequestInboundHeader() { System.out.println("\n== 입고 요청 등록 =="); }
    public void displayModifyInboundHeader() { System.out.println("\n== 입고 요청 수정 =="); }
    public void displayCancelInboundHeader() { System.out.println("\n== 입고 요청 취소 =="); }
    public void displayApproveInboundHeader() { System.out.println("\n== 입고 요청 승인 =="); }
    public void displayRejectInboundHeader() { System.out.println("\n== 입고 요청 거절 =="); }
    public void displayViewInboundHeader() { System.out.println("\n== 입고 요청 상세 조회 =="); }
}