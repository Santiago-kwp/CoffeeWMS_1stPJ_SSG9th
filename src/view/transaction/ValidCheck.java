package view.transaction;


import exception.transaction.ValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ValidCheck {

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

    public LocalDate checkDate(String input) throws ValidationException {
        try {
            return LocalDate.parse(input.trim());
        } catch (DateTimeParseException e) {
            throw new ValidationException("날짜 형식이 올바르지 않습니다. (예: 2025-10-06)");
        }
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
