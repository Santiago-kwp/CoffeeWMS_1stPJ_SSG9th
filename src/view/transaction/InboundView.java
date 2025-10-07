package view.transaction;

import constant.transaction.TransactionText;
import domain.cargo.dto.LocationDTO;
import domain.transaction.Coffee; // Coffee DTO
import domain.transaction.dto.InboundRequestDTO;
import domain.transaction.dto.InboundRequestItemDTO;
import domain.transaction.vo.InboundStatus;
import exception.transaction.ValidationException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InboundView {
    // 취소/뒤로가기용 상수 정의
    private static final String CANCEL_KEYWORD = "0";

    // 1. private static final 인스턴스 변수 선언
    private static final InboundView instance = new InboundView();

    // 2. private 생성자
    private InboundView() {
    }

    // 3. public static getInstance() 메서드
    public static InboundView getInstance() {
        return instance;
    }

    // ValidCheck 객체를 필드로 가지되, new로 생성하지 않고 싱글톤 인스턴스를 가져옴
    private final ValidCheck validCheck = ValidCheck.getInstance();





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

    /**
     * 사용자가 선택한 입고 요청 항목들의 최종 내역을 화면에 출력합니다.
     * @param items 사용자가 선택한 InboundRequestItemDTO 리스트
     * @param allCoffees 전체 커피 목록 (커피 이름을 찾기 위해 필요)
     */
    public void displayRequestConfirmation(List<InboundRequestItemDTO> items, List<Coffee> allCoffees) {
        System.out.println(TransactionText.BORDER_LINE.getText());
        System.out.println("===== 최종 요청 내역 확인 =====");
        System.out.println("아래 내용으로 입고를 요청하시겠습니까?");
        System.out.println();
        for (InboundRequestItemDTO item : items) {
            // 아이템 DTO의 coffeeId를 사용하여 전체 커피 목록에서 커피 이름을 찾습니다.
            String coffeeName = findCoffeeNameById(item.getCoffeeId(), allCoffees)
                    .orElse("알 수 없는 상품"); // ID에 해당하는 이름이 없을 경우 대비
            System.out.printf("- 상품명: %-20s (ID: %s), 수량: %d\n",
                    coffeeName, item.getCoffeeId(), item.getInboundRequestQuantity());
        }
        System.out.println(TransactionText.BORDER_LINE.getText());
    }

    /**
     * coffeeId를 사용하여 전체 커피 리스트에서 커피 이름을 찾는 헬퍼 메서드
     */
    private Optional<String> findCoffeeNameById(String coffeeId, List<Coffee> allCoffees) {
        return allCoffees.stream()
                .filter(coffee -> coffee.getCoffeeId().equals(coffeeId))
                .map(Coffee::getCoffeeName)
                .findFirst();
    }

    /**
     * 관리자에게 '대기' 상태인 입고 요청 목록을 상세 항목과 함께 보여줍니다.
     * @param pendingRequests 상세 항목이 채워진 InboundRequestDTO 리스트
     * @param allCoffees 커피 이름을 찾기 위한 전체 커피 목록
     */
    public void displayPendingInboundList(List<InboundRequestDTO> pendingRequests, List<Coffee> allCoffees) {
        System.out.println(TransactionText.BORDER_LINE.getText());
        System.out.println("===== 승인 대기 중인 입고 요청 목록 =====");

        if (pendingRequests == null || pendingRequests.isEmpty()) {
            System.out.println("승인 대기 중인 요청이 없습니다.");
            System.out.println(TransactionText.BORDER_LINE.getText());
            return;
        }

        // 각 요청(헤더)을 순회하며 출력
        for (InboundRequestDTO request : pendingRequests) {
            System.out.printf("\n[요청 ID: %d | 요청자: %s | 요청일: %s]\n",
                    request.getInboundRequestId(),
                    request.getMemberId(),
                    request.getRequestDate());

            // 각 요청에 속한 상세 항목(아이템)들을 출력
            if (request.getItems() == null || request.getItems().isEmpty()) {
                System.out.println("  - 요청된 항목 정보가 없습니다.");
            } else {
                for (InboundRequestItemDTO item : request.getItems()) {
                    String coffeeName = findCoffeeNameById(item.getCoffeeId(), allCoffees)
                            .orElse("알 수 없는 상품");

                    System.out.printf("  - 커피명: %-20s (ID: %s), 수량: %d\n",
                            coffeeName, item.getCoffeeId(), item.getInboundRequestQuantity());
                }
            }
        }
        System.out.println(TransactionText.BORDER_LINE.getText());
    }

    /**
     * 관리자에게 최종 승인할 내역 (요청 항목 + 할당 위치)을 보여줍니다.
     * @param request 승인할 InboundRequestDTO
     * @param chosenLocation 관리자가 선택한 LocationDTO
     * @param allCoffees 커피 이름을 찾기 위한 전체 커피 목록
     */
    public void displayApproveConfirmation(InboundRequestDTO request, LocationDTO chosenLocation, List<Coffee> allCoffees) {
        System.out.println(TransactionText.BORDER_LINE.getText());
        System.out.println("===== 최종 승인 내역 확인 =====");
        System.out.println("아래 내용으로 입고 승인을 진행하시겠습니까?");
        System.out.println();
        System.out.printf("[요청 ID: %d | 요청자: %s]\n", request.getInboundRequestId(), request.getMemberId());

        System.out.println("--- 요청 항목 ---");
        for (InboundRequestItemDTO item : request.getItems()) {
            String coffeeName = findCoffeeNameById(item.getCoffeeId(), allCoffees)
                    .orElse("알 수 없는 상품");
            System.out.printf("  - 상품명: %-20s, 수량: %d\n", coffeeName, item.getInboundRequestQuantity());
        }

        System.out.println("\n--- 할당 위치 ---");
        System.out.println("  " + chosenLocation.toString());

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
            System.out.printf("%d. %s (ID: %s | 등급: %s | 디카페인 : %s | 원두/생두 : %s ) - %,d원\n", i + 1,
                    coffee.getCoffeeName(), coffee.getCoffeeId(), coffee.getCoffeeGrade(), coffee.getDecaf(), coffee.getRoasted(), coffee.getPrice());
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
     * [수정] '새로운 입고 요청' 전용 날짜 입력 메서드. (내부 로직 변경 없음)
     * 이 메서드는 이제 명확하게 '신규 요청' 시에만 사용됩니다.
     */
    public LocalDate getRequestDateFromUser() throws IOException {
        while (true) {
            try {
                String input = InputHandler.readInput("입고 요청 날짜를 입력하세요 (예: 2025-12-08): ");
                // "한 달 이후" 규칙이 포함된 checkDate 호출
                return validCheck.checkDate(input);
            } catch (ValidationException e) {
                displayError(e.getMessage());
            }
        }
    }

    /**
     * [신규] 기간별 조회 등 비즈니스 규칙이 없는 범용 날짜를 입력받는 메서드입니다.
     * @param prompt 사용자에게 보여줄 안내 메시지
     * @return 사용자가 입력한 LocalDate, 취소 시 null
     */
    public LocalDate getSearchDateFromUser(String prompt) throws IOException {
        while (true) {
            try {
                String input = InputHandler.readInput(prompt + " (예: 2025-10-07) (취소: 0): ");
                if (input.trim().equals("0")) {
                    return null;
                }
                // 비즈니스 규칙이 없는 checkDateFormat 호출
                return validCheck.checkDateFormat(input);
            } catch (ValidationException e) {
                displayError(e.getMessage());
            }
        }
    }


    public Long getInboundRequestIdFromUser() throws IOException {
        while (true) {
            try {
                String input = InputHandler.readInput(String.format("처리할 입고 요청 ID를 입력하세요 (취소: %s): ", CANCEL_KEYWORD));
                if (input.trim().equals(CANCEL_KEYWORD)) {
                    return null; // 취소 신호로 null 반환
                }

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

    /**
     * 사용자로부터 y 또는 n 입력을 받아 boolean 값으로 반환합니다.
     * @return 사용자가 'y'를 입력하면 true, 'n'을 입력하면 false
     * @throws IOException 입력 오류 발생 시
     */
    public boolean getConfirmationFromUser() throws IOException {
        while (true) {
            String input = InputHandler.readInput("최종 제출하시겠습니까? (y/n): ");
            if (input.equalsIgnoreCase("y")) {
                return true;
            } else if (input.equalsIgnoreCase("n")) {
                return false;
            } else {
                displayError("y 또는 n만 입력해주세요.");
            }
        }
    }

    /**
     * 관리자에게 할당 가능한 위치 목록을 보여주고 선택을 받습니다.
     * @param locations 보여줄 위치 DTO 리스트
     * @return 선택된 위치의 locationPlaceId
     * @throws IOException 입력 오류
     * @throws ValidationException 유효하지 않은 선택
     */
    public String getLocationChoiceFromUser(List<LocationDTO> locations) throws IOException, ValidationException {
        System.out.println(TransactionText.BORDER_LINE.getText());
        System.out.println("재고를 할당할 위치를 선택하세요.");
        for (int i = 0; i < locations.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, locations.get(i).toString());
        }
        System.out.println(TransactionText.BORDER_LINE.getText());

        while (true) {
            String input = InputHandler.readInput(String.format("위치 번호를 입력하세요 (취소: %s): ", CANCEL_KEYWORD));
            if (input.trim().equals(CANCEL_KEYWORD)) {
                return null; // 취소 신호로 null 반환
            }
            int choiceIndex = validCheck.checkInteger(input, "위치 번호");
            if (choiceIndex > 0 && choiceIndex <= locations.size()) {
                return locations.get(choiceIndex - 1).getLocationPlaceId();
            } else {
                displayError("목록에 없는 번호입니다. 다시 입력해주세요.");
            }
        }
    }

    // ================== 사용자 입력 처리 ==================

    /**
     * 조회 방식을 선택하는 하위 메뉴를 표시하고 입력을 받습니다.
     * @return 사용자가 선택한 SearchFilterType Enum
     */
    public SearchFilterType getSearchFilterTypeFromUser() throws IOException {
        System.out.println("조회 방식을 선택하세요:");
        while (true) {
            String choice = InputHandler.readInput("1. 전체 조회 | 2. 기간별 조회 | 3. 월별 조회 (뒤로가기: 0): ");
            switch (choice) {
                case "1": return SearchFilterType.ALL;
                case "2": return SearchFilterType.DATE_RANGE;
                case "3": return SearchFilterType.BY_MONTH;
                case "0": return null; // 취소 신호
                default: displayError("잘못된 입력입니다. 0~3 사이의 숫자를 입력해주세요.");
            }
        }
    }

    /**
     * [수정] 기간별 조회를 위한 시작일과 종료일을 입력받습니다.
     * 이제 새로 만든 getSearchDateFromUser 메서드를 사용합니다.
     * @return 시작일과 종료일이 담긴 LocalDate 배열, 취소 시 null
     */
    public LocalDate[] getDateRangeFromUser() throws IOException {
        // 시작일 입력
        LocalDate startDate = getSearchDateFromUser("조회를 시작할 날짜를 입력하세요");
        if (startDate == null) return null; // 시작일 입력 중 취소

        // 종료일 입력
        while (true) {
            LocalDate endDate = getSearchDateFromUser("조회를 종료할 날짜를 입력하세요");
            if (endDate == null) return null; // 종료일 입력 중 취소

            if (endDate.isBefore(startDate)) {
                displayError("종료일은 시작일보다 이전일 수 없습니다. 다시 입력해주세요.");
                continue;
            }
            return new LocalDate[]{startDate, endDate};
        }
    }

    /**
     * 월별 조회를 위한 연도와 월을 입력받습니다.
     * @return 연도와 월이 담긴 int 배열, 취소 시 null
     */
    public int[] getYearMonthFromUser() throws IOException {
        while (true) {
            try {
                String yearInput = InputHandler.readInput("조회할 연도를 입력하세요 (예: 2025) (취소: 0): ");
                if (yearInput.equals("0")) return null;
                int year = validCheck.checkInteger(yearInput, "연도");

                String monthInput = InputHandler.readInput("조회할 월을 입력하세요 (예: 10) (취소: 0): ");
                if (monthInput.equals("0")) return null;
                int month = validCheck.checkInteger(monthInput, "월");
                if (month < 1 || month > 12) {
                    displayError("월은 1에서 12 사이의 숫자여야 합니다.");
                    continue;
                }
                return new int[]{year, month};
            } catch (ValidationException e) {
                displayError(e.getMessage());
            }
        }
    }

    /**
     * 사용자로부터 메뉴 선택 번호를 입력받아 유효성 검사 후 문자열로 반환합니다.
     * 숫자가 아닌 입력은 다시 입력받습니다.
     * @return 사용자가 입력한 메뉴 번호 문자열
     */
    public String getMenuChoiceFromUser() {
        while (true) {
            try {
                String input = InputHandler.readInput("메뉴 번호를 입력해주세요: ");
                // 숫자인지, 그리고 0 이상인지 정도의 기본적인 검사만 수행
                // 유효한 메뉴 범위(1~5)인지는 컨트롤러가 판단
                validCheck.checkMenuInput(input);

                return input.trim();
            } catch (IOException e) {
                displayError("입력 처리 중 오류가 발생했습니다. 다시 시도해주세요.");
            } catch (ValidationException e) {
                // checkInteger에서 "메뉴 번호은(는) 숫자만 입력해야 합니다." 와 같은 메시지가 출력됨
                displayError(e.getMessage());
            }
        }
    }


    // ================== 메시지 출력 ==================
    public void displaySuccess(String message) {
        System.out.println("✅ 성공: " + message);
    }

    public void displayError(String errorMessage) {
        System.out.println("❌ 오류: " + errorMessage);
    }

    // displayMessage 메서드가 없다면 추가합니다.
    public void displayMessage(String message) {
        System.out.println("ℹ️ 정보: " + message);
    }



    // ================== 헤더 출력 ==================
    public void displayRequestInboundHeader() { System.out.println("\n== 입고 요청 등록 =="); }
    public void displayModifyInboundHeader() { System.out.println("\n== 입고 요청 수정 =="); }
    public void displayCancelInboundHeader() { System.out.println("\n== 입고 요청 취소 =="); }
    public void displayApproveInboundHeader() { System.out.println("\n== 입고 요청 승인 =="); }
    public void displayRejectInboundHeader() { System.out.println("\n== 입고 요청 거절 =="); }
    public void displayViewInboundHeader() { System.out.println("\n== 입고 요청 상세 조회 =="); }


}