package constant.transaction;

public enum ErrorCode {
    INVALID_MENU_1_4_OPTION("잘못된 메뉴 번호입니다. [1-4]번 다시 입력하세요"),
    INVALID_MENU_1_3_OPTION("잘못된 메뉴 번호입니다. [1-3]번 다시 입력하세요"),
    INVALID_CHECK_OPTION("잘못된 번호입니다. [1 or 2]번을 입력하세요"),

    INVALID_COFFEE_NUMBER("커피ID 숫자 번호를 입력하세요!"),
    INVALID_COFFEE_SIZE_NUMBER("해당 커피ID는 존재하지 않습니다."),

    INVALID_INBOUND_EMPTY("입고된 커피ID 숫자 번호를 입력하세요."),
    INVALID_COFFEE_QUANTITY_NUMBER("신청 가능한 커피 수량(1포대 단위)은 0보다 크고 2,000보다 작아야 합니다."),
    INVALID_INBOUND_DATE("입고 요청 날짜는 금일 기준 최소 한달 후여야 합니다."),
    INVALID_MEMBER_ID("해당하는 회원이 없습니다."),
    INVALID_REQUEST_ID("회원의 입고 요청 ID를 잘못 입력하셨습니다."),
    INVALID_REQUEST_ITEM_ID("회원의 입고 요청 상세 ID를 잘못 입력하셨습니다."),
    INVALID_LOCATION_PLACE_NUMBER("해당 창고 위치 번호는 존재하지 않습니다."),
    INVALID_MONTH_NUMBER("잘못된 월입니다. [1-12]번 다시 입력하세요."),
    INVALID_OUTBOUND_DATE("출고 요청 날짜는 입고 완료 날짜보다 뒤여야 합니다."),

    /*
    아래의 에러코드는 리펙토링 후 추가된 에러코드
     */
    INBOUND_REQUEST_NOT_FOUND("입고 요청을 찾을 수 없습니다."),
    UNAUTHORIZED_ACCESS("권한이 없습니다."),
    INVALID_STATUS_FOR_OPERATION("현재 상태에서는 해당 작업을 수행할 수 없습니다."),
    EMPTY_INBOUND_ITEMS("입고 요청 항목이 비어있습니다."),
    DB_ERROR("데이터베이스 오류가 발생했습니다."); // SQLException 래핑용

    private final String msg;

    ErrorCode(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
