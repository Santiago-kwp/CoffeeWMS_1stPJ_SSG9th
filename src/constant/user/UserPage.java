package constant.user;

public enum UserPage {

    CURRENT_USER_SELECT("현재 회원정보를 조회합니다."),
    CANNOT_SEARCH_USER("조회할 수 있는 회원이 없습니다."),

    USER_UPDATE_TITLE("""
            --------------------<< 회원정보 수정 >>--------------------------
            현재 회원정보를 변경하시겠습니까?(Y 입력 시 진행)
            """),
    USER_UPDATE("현재 회원정보의 변경이 완료되었습니다."),
    USER_UPDATE_FAILED("현재 회원정보의 변경에 실패했습니다."),

    USER_DELETE_TITLE("""
            --------------------<< 회원탈퇴 >>--------------------------
            회원탈퇴를 진행하시겠습니까?(Y 입력 시 진행)
            """),
    USER_DELETE("회원 탈퇴가 완료되었습니다. 로그인 메뉴로 되돌아갑니다."),
    USER_NOT_DELETE("회원탈퇴를 진행하지 않습니다. 회원관리 메뉴로 되돌아갑니다."),
    USER_DELETE_FAILED("회원탈퇴를 진행할 수 없습니다. 작업을 중단합니다."),
    TO_PREVIOUS_MENU("이전 메뉴로 돌아갑니다."),

    SEARCH_COMMON_TITLE_FORMAT("""
            -------------------------------------------------<< 전체 회원 리스트 >>------------------------------------------------
            %s
            --------------------------------------------------------------------------------------------------------------------
            """),
    SEARCH_COMMON_TITLE("%-13s\t%-19s\t%-9s\t\t%-12s\t%-27s\t\t%-7s\t"),
    SEARCHED_COMMON_INFO("%-16s\t%-20s\t%-12s\t%-12s\t\t%-30s\t%-8s\t");

    private final String page;

    UserPage(String page) {
        this.page = page;
    }

    public static String searchAllTitle() {
        return String.format(SEARCH_COMMON_TITLE_FORMAT.toString(), userCommonInfoTitle());
    }
    public static String userCommonInfoTitle() {
        return String.format(SEARCH_COMMON_TITLE.toString(), "아이디", "비밀번호", "소속사/이름", "연락처", "이메일", "회원유형");
    }

    @Override
    public String toString() {
        return page;
    }
}
