package view.transaction;


import constant.transaction.ErrorCode;
import exception.transaction.TransactionException;
import exception.transaction.ValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;

public class ValidCheck {

    // 1. private static final 인스턴스 변수 선언 및 초기화
    private static final ValidCheck instance = new ValidCheck();

    // 2. private 생성자
    private ValidCheck() {}

    // 3. public static getInstance() 메서드
    public static ValidCheck getInstance() {
        return instance;
    }

    public long checkLong(String input, String fieldName) throws ValidationException {
        try {
            return Long.parseLong(input.trim());
        } catch (NumberFormatException e) {
            throw new ValidationException(fieldName + "은(는) 숫자만 입력해야 합니다.");
        }
    }

    public int checkInteger(String input, String fieldName) throws ValidationException {
        try {
            int value = Integer.parseInt(input.trim());
            if (value <= 0) {
                throw new ValidationException(fieldName + "은(는) 0보다 커야 합니다.");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new ValidationException(fieldName + "은(는) 숫자만 입력해야 합니다.");
        }
    }

    public String checkMenuInput(String input) throws ValidationException {
        try {
            Integer.parseInt(input.trim());
            return input.trim();
        } catch (NumberFormatException e) {
            throw new ValidationException("메뉴 번호는 숫자만 입력 가능합니다.");
        }
    }

    /**
     * [신규] 날짜 형식('YYYY-MM-DD')만 검증하는 범용 메서드입니다.
     * @param input 사용자가 입력한 날짜 문자열
     * @return 유효성 검사를 통과한 LocalDate 객체
     * @throws ValidationException 형식이 맞지 않을 경우
     */
    public LocalDate checkDateFormat(String input) throws ValidationException {
        try {
            return LocalDate.parse(input.trim());
        } catch (DateTimeParseException e) {
            throw new ValidationException("날짜 형식이 올바르지 않습니다. (예: 2025-10-07)");
        }
    }

    /**
     * '새로운 입고 요청'에 대한 날짜 유효성을 검증하는 메서드입니다.
     * 이제 checkDateFormat을 먼저 호출하여 형식 검증을 위임합니다.
     * 규칙: 날짜는 오늘로부터 최소 한 달 이후여야 합니다.
     *
     * @param input 사용자가 입력한 날짜 문자열
     * @return 유효성 검사를 모두 통과한 LocalDate 객체
     * @throws ValidationException 형식 또는 규칙에 맞지 않을 경우
     */
    public LocalDate checkDate(String input) throws ValidationException {
        // 1. 형식 검증 (위임)
        LocalDate parsedDate = checkDateFormat(input);

        // 2. 비즈니스 규칙 검증 (고유의 책임)
        LocalDate today = LocalDate.now();
        LocalDate oneMonthLater = today.plusMonths(1);

        if (parsedDate.isBefore(oneMonthLater)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            throw new ValidationException("입고 요청 날짜는 오늘로부터 최소 한 달 이후여야 합니다. (입력 가능 시작일: " + oneMonthLater.format(formatter) + ")");
        }

        return parsedDate;
    }


    /**
     * "상품번호,수량" 형식의 입력을 검증합니다.
     * @param input 사용자 입력 문자열
     * @param maxIndex 유효한 상품 번호의 최댓값
     * @return 검증을 통과한 [상품번호, 수량] int 배열
     * @throws ValidationException 유효성 검사 실패 시
     */
    public int[] checkInboundItemInput(String input, int maxIndex) throws ValidationException {
        String[] parts = input.trim().split(",");
        if (parts.length != 2) {
            throw new ValidationException("입력 형식이 올바르지 않습니다. '상품번호,수량' 형식으로 입력해주세요.");
        }

        int itemIndex = checkInteger(parts[0], "상품번호");
        if (itemIndex <= 0 || itemIndex > maxIndex) {
            throw new ValidationException("존재하지 않는 상품번호입니다. (1 ~ " + maxIndex + " 사이)");
        }

        int quantity = checkInteger(parts[1], "수량");

        return new int[]{itemIndex, quantity};
    }


}
