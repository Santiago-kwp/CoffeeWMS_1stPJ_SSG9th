package constant.inventory;

public enum ErrorMessage {
  // === 입력 형식 오류 ===
  INVALID_NUMBER_FORMAT("숫자만 입력 가능합니다."),
  INVALID_MENU_CHOICE("메뉴 번호는 숫자만 입력 가능합니다."),
  INVALID_APPROVAL_CHOICE("1(승인) 또는 2(반려)만 입력 가능합니다."),
  INVALID_GENERAL_CHOICE("잘못된 번호입니다."),

  // === 데이터베이스 및 비즈니스 규칙 오류 ===
  DUPLICATE_DILIGENCE_ID("이미 존재하는 실사 ID입니다."),
  INVENTORY_NOT_FOUND("존재하지 않는 재고 ID입니다."),
  DILIGENCE_NOT_FOUND("해당 실사 ID를 찾을 수 없거나, 이미 처리된 상태일 수 있습니다."),
  DB_PROCESSING_ERROR("데이터베이스 처리 중 오류가 발생했습니다.");

  private final String message;

  ErrorMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}