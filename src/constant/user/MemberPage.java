package constant.user;

import domain.user.Member;

public enum MemberPage {

    MEMBER_MANAGEMENT_MENU_TITLE("""
            ------------------<< 일반회원 회원관리 >>------------------
            1.내 정보 조회 | 2.내 정보 수정 | 3.탈퇴 | 4.뒤로가기
            ---------------------------------------------------------
            메뉴를 선택해주세요.
            """),
    MEMBER_DETAIL("""
            ----------------<< 일반회원 상세조회 >>-----------------
            아이디: %1$s
            소속사: %2$s
            연락처: %3$s
            이메일: %4$s
            사업자번호: %5$s
            주소: %6$s
            계약시작일: %7$s
            계약종료일: %8$s
            """),
    MEMBER_UPDATE_TITLE("--------------------<< 일반회원 회원정보 수정 >>--------------------------"),

    USER_UPDATE_TITLE("""
            --------------------<< 회원정보 수정 >>--------------------------
            현재 회원정보를 변경하시겠습니까?(Y 입력 시 진행)
            """),

    SEARCH_MEMBER_TITLE_FORMAT("""
            --------------------------------------------------------------<< 일반회원 리스트 >>--------------------------------------------------------------------------------------------
            %s
            -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            """),
    SEARCH_MEMBER_TITLE("%-9s\t%-9s\t\t%-9s\t%-10s\t"),
    SEARCHED_MEMBER_INFO("%-11s\t%-9s\t\t%-12s\t%-12s");

    private final String page;

    MemberPage(String page) {
        this.page = page;
    }

    public static void memberDetails(Member member) {
        System.out.printf(MEMBER_DETAIL.toString(),
                member.getId(), member.getName(),
                member.getPhone(), member.getEmail(), member.getCompanyCode(),
                member.getAddress(), member.getStart_date(), member.getExpired_date());
    }
    public static String memberInfoTitle() {
        return String.format(SEARCH_MEMBER_TITLE_FORMAT.toString(),
                UserPage.userCommonInfoTitle() + String.format(SEARCH_MEMBER_TITLE.toString(), "사업자번호", "주소지", "계약체결일", "계약만료일"));
    }

    @Override
    public String toString() {
        return page;
    }
}
