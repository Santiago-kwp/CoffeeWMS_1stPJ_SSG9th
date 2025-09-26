package constant.inventory;


public enum InventoryMessage {
  // === 공통 메뉴 ===
  INVENTORY_MAIN_MENU_TITLE("<< 재고 관리 >>"),
  INVENTORY_MENU_CHOICE_1("1. 재고 조회"),
  INVENTORY_MENU_CHOICE_2("2. 실사 관리"),
  INVENTORY_MENU_BACK("0. 뒤로가기 (WMS 메인 메뉴)"),
  MENU_CHOICE_PROMPT("메뉴를 선택해주세요: "),

  // === 재고 조회 메뉴 ===
  INVENTORY_SEARCH_MENU_TITLE("<< 재고 조회 >>"),
  INPUT_WAREHOUSE_CHOICE("[총관리자/일반회원] 창고 선택 (1.부산신항창고 | 2.곤지암 창고 | 3.대덕창고 | 미입력 시 전체): "),
  INPUT_MEMBER_CHOICE("[총관리자/창고관리자] 회원사 선택 (1.스타벅스 | 2.투썸플레이스 | 미입력 시 전체): "),
  INPUT_COFFEE_NAME("상품명으로 검색 (미입력 시 전체): "),
  INPUT_BEAN_TYPE("로스팅 상태 (1.원두 | 2.생두 | 미입력 시 전체): "),
  INPUT_IS_DECAF("디카페인 여부 (1.디카페인 | 2.일반 | 미입력 시 전체): "),

  // --- 정렬 메뉴 ---
  SORTING_MENU_HEADER("----------<< 정렬 메뉴 >>----------"),
  SORTING_MENU_CHOICE_1("1. 상품명 (현재: %s)"),
  SORTING_MENU_CHOICE_2("2. 입고일 (현재: %s)"),
  SORTING_MENU_CHOICE_3("3. 단가 (현재: %s)"),
  SORTING_MENU_BACK("0. 이전 메뉴 (재고 조회)"),

  // === 실사 관리 메뉴 (역할별로 구분됨) ===
  DILIGENCE_MENU_TITLE("<< 실사 관리 >>"),
  DILIGENCE_MENU_CHOICE_1("1. 실사 조회"),
  DILIGENCE_MENU_ADMIN_CHOICE_2("2. 실사 승인"),
  DILIGENCE_MENU_WAREHOUSE_CHOICE_2("2. 실사 등록"),
  DILIGENCE_MENU_WAREHOUSE_CHOICE_3("3. 실사 수정"),
  DILIGENCE_MENU_WAREHOUSE_CHOICE_4("4. 실사 삭제"),

  // === 결과 및 상태 메시지 ===
  INVENTORY_LIST_HEADER("\n==============================[ 재고 목록 ]=============================="),
  DILIGENCE_LIST_HEADER("\n==============================[ 실사 목록 ]=============================="),
  HORIZONTAL_LINE("-------------------------------------------------------------------------"),
  INVALID_MENU_CHOICE("잘못된 메뉴 번호입니다. 다시 입력해주세요."),
  NO_RESULTS("조회된 데이터가 없습니다."),
  PROCESS_SUCCESS("\n>> %s 처리가 완료되었습니다."),
  PROCESS_FAILURE("\n>> %s 처리에 실패했습니다."),
  SYSTEM_ERROR("시스템 오류가 발생했습니다. 관리자에게 문의하세요.");

  private final String message;

  InventoryMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return message;
  }
}