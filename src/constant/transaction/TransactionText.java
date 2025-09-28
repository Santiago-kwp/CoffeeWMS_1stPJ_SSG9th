package constant.transaction;

public enum TransactionText {

  // ================= 공통 ======================
  BORDER_LINE("-".repeat(127)),

  // ================= (회원) 입고 ===============
  COFFEES_HEADER("-".repeat(52)+"<< 커피 상품 목록 >>" + "-".repeat(55)),
  COFFEE_ATTRIBUTES_HEADER(String.format("|요청번호| %-20s\t | %-10s\t | %-5s\t | %-5s\t | %-10s\t | %-10s\t |",
      "커피명", "원산지", "디카페인여부", "로스팅여부", "등급", "단가")),
  MEMBER_INBOUND_HEADER("-".repeat(55)+"<< 입고 (회원) >>" + "-".repeat(55)),
  MEMBER_INBOUND("1.입고요청 | 2.입고요청 내역 조회 | 3.입고완료 현황 조회 | 4.뒤로가기"),

  MEMBER_INBOUND_COMPLETION_LIST_HEADER("-".repeat(55)+"<< 입고 승인 완료 현황 조회 (회원) >>" + "-".repeat(55)),
  MEMBER_INBOUND_COMPLETION_LIST("1. 기간별 입고 현황 | 2. 월별 입고 현황 | 3. 뒤로가기"),

  INBOUND_COFFEE_SELECT("입고할 커피를 번호로 선택하세요."),
  INBOUND_COFFEE_QUANTITY_INPUT("입고 요청 커피 수량 (발주 가능 수량은 최대 2,000개)"),
  INBOUND_COFFEE_DATE_INPUT("입고 요청한 커피 상품들의 입고 날짜를 입력하세요."),






  // ================= (관리자) 입고 =================
  MANAGER_INBOUND_HEADER("-".repeat(55)+"<< 입고 (관리자) >>" + "-".repeat(55)),
  MANAGER_INBOUND("1.회원 입고요청 내역 조회 | 2.입고 고지서 조회 | 3.입고완료 현황 조회 | 4.뒤로가기"),
  MANAGER_INBOUND_LIST_HEADER("-".repeat(55)+"<< 회원 입고요청 내역 조회 (관리자) >>" + "-".repeat(55)),
  MANAGER_INBOUND_LIST("1. 회원 입고 요청 승인 | 2. 입고 요청 수정 | 3. 입고 요청 취소 | 4. 뒤로가가"),
  MANAGER_INBOUND_COMPLETION_LIST_HEADER("-".repeat(55)+"<< 입고 완료 현황 조회 (관리자) >>" + "-".repeat(55)),
  MANAGER_INBOUND_COMPLETION_LIST("1. 기간별 입고 현황 | 2. 월별 입고 현황 | 3. 뒤로가기"),
  WMSADMIN("wmsAdmin"),

  // ================= (회원) 출고 ===================
  MEMBER_OUTBOUND_HEADER("-".repeat(55)+"<< 출고 (회원) >>" + "-".repeat(55)),
  MEMBER_OUTBOUND("1.출고요청 | 2.출고요청 내역 조회 | 3.출고완료 현황 조회 | 4.뒤로가기"),
  MEMBER_OUTBOUND_COMPLETION_HEADER("-".repeat(55)+"<< 출고완료 현황 조회 (회원) >>" + "-".repeat(55)),
  MEMBER_OUTBOUND_COMPLETION("1. 출고지시서 조회 | 2. 출고리스트 조회 | 3. 출고상품 검색 | 4. 뒤로가기"),

  OUTBOUND_COFFEE_SELECT("출고 요청 커피ID(번호) 선택: "),
  OUTBOUND_COFFEE_QUANTITY_INPUT("출고 요청 커피 수량 (최대 현재 입고 수량까지) : "),
  OUTBOUND_COFFEE_DATE_INPUT("해당 커피의 출고 요청 날짜 : "),
  APPROVED_OUTBOUND_LIST_HEADER(String.format("|요청번호| %-15s | %-12s | %-15s | %-10s | %-15s",
          "회원 ID", "커피명", "커피 ID", "현재 수량", "입고 완료 날짜")),
  EMPTY_CURRENT_OUTBOUND("미승인된 출고 요청 내역이 없습니다."),
  UNAPPROVED_OUTBOUNDS_LIST("-".repeat(50)+"<< 출고요청 내역 >>" + "-".repeat(50)),
  UNAPPROVED_OUTBOUND_LIST_HEADER(String.format("%-12s | %-5s |%-15s| %-15s | %-10s | %-15s\n", "출고요청 ID", "출고상세ID" ,"회원 ID", "커피 이름", "수량", "출고 요청 날짜")),






  // ================== (관리자) 출고 =============================
  MANAGER_OUTBOUND_HEADER("-".repeat(55)+"<< 출고 (관리자) >>" + "-".repeat(55)),
  MANAGER_OUTBOUND("1.회원 출고요청내역 조회 | 2.출고완료 현황 조회 | 3.뒤로가기"),
  MANAGER_OUTBOUND_COMPLETION_HEADER("-".repeat(55)+"<< 출고완료 현황 조회 (관리자) >>" + "-".repeat(55)),
  MANAGER_OUTBOUND_COMPLETION("1. 출고지시서 조회 | 2. 출고리스트 조회 | 3. 출고상품 검색 | 4. 뒤로가기"),


  EMPTY_UNAPPROVED_INBOUND("미승인된 입고 요청 내역이 없습니다."),
  UNAPPROVED_INBOUNDS_LIST("-".repeat(50)+"<< 입고요청 내역 >>" + "-".repeat(50)),
  UNAPPROVED_INBOUND_LIST_HEADER(String.format("%-12s | %-5s |%-15s| %-15s | %-10s | %-15s\n", "입고요청 ID", "입고상세ID" ,"회원 ID", "커피 이름", "수량", "입고 요청 날짜")),



  EMPTY_APPROVED_INBOUND("승인된 입고 요청 내역이 없습니다."),
  APPROVED_INBOUNDS_LIST("-".repeat(50)+"<< 승인된 입고요청 내역 >>" + "-".repeat(50)),
  APPROVED_INBOUND_LIST_HEADER(String.format("%-12s | %-5s |%-15s| %-15s | %-10s | %-15s\n", "입고요청 ID", "입고상세ID" ,"회원 ID", "커피 이름", "수량", "입고 요청 날짜")),

  EMPTY_MEMBER_UNAPPROVED_INBOUND("미승인된 입고 요청이 있는 회원이 없습니다."),
  MEMBER_UNAPPROVED_INBOUNDS_HEADER("-".repeat(50)+"<< 입고 승인 요청 회원 ID 목록 >>" + "-".repeat(50)),
  MEMBER_UNAPPROVED_INBOUNDS_LIST(String.format("%-15s | %-15s\n", "회원 ID", "입고 요청 건수")),


  // ===================== 공통 ===================
  COMMON_INBOUND_REQUESTS_HEADER("-".repeat(55)+"<< 입고 요청 내역 조회 >>" + "-".repeat(55)),
  COMMON_INBOUND_REQUESTS_LIST("1. 입고 요청 수정 | 2. 입고 요청 취소 | 3. 뒤로가기"),
  COMMON_APPROVED_INBOUND_LIST("1. 기간별 입고 현황 | 2. 월별 입고 현황 | 3. 뒤로가기"),

  REQUEST_UPDATE("(수정)"),
  CHECK_MENU("보조 메뉴: 1.OK | 2. Cancel"),
  NO_AVAILABLE_COFFEE("현재 입고 가능한 커피가 없습니다."),
  EMPTY_CURRENT_INBOUND("현재 입고 요청 내역이 없습니다."),
  EMPTY_MEMBER_APPROVED_INBOUND("현재 승인된 입고 내역이 없습니다."),
  MEMBER_APPROVED_INBOUNDS_HEADER("-".repeat(50)+"<< 입고 승인 완료 회원 ID 목록 >>" + "-".repeat(50)),
  MEMBER_APPROVED_INBOUNDS_LIST(String.format("%-15s | %-15s\n", "회원 ID", "입고 승인 건수")),
  COMMON_OUTBOUND_REQUESTS_HEADER("-".repeat(55)+"<< 출고 요청 >>" + "-".repeat(55)),
  ;


  private final String text;

  TransactionText(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

}
