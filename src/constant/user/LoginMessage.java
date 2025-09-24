package constant.user;

public enum LoginMessage {

    LOGIN_MENU_TITLE("""
            ---------------------<< 로그인 화면 >>--------------------
            1.로그인 | 2.회원가입 | 3.아이디찾기 | 4.비밀번호찾기 | 5.종료
            ---------------------------------------------------------
            메뉴를 선택해주세요.
            """),
    SUB_MENU_TITLE("""
            ---------------------<< %s >>--------------------
            """),
    LOGIN("로그인"),
    SIGN_UP("회원가입"),
    MEMBER_REGISTER("일반회원 회원정보 입력"),
    MANAGER_REGISTER("관리자 회원정보 입력"),
    FIND_ID("아이디 찾기"),
    FIND_PWD("비밀번호 찾기"),
    EXIT_LOGIN_MENU("로그인 메뉴를 종료합니다."),

    INPUT_ID("아이디를 입력해주세요."),
    INPUT_PWD("비밀번호를 입력해주세요."),
    INPUT_COMPANY_NAME("소속사를 입력해주세요."),
    INPUT_NAME("이름을 입력해주세요."),
    INPUT_PHONE("연락처를 입력해주세요.(입력 형식: 010-XXXX-XXXX)"),
    INPUT_EMAIL("이메일을 입력해주세요."),
    INPUT_COMPANY_CODE("사업자등록번호를 입력해주세요.(형식은 XXX-XX-XXXXX)"),
    INPUT_ADDRESS("주소지를 입력해주세요."),
    INPUT_MANAGER_POSITION("관리자 직급을 입력하세요.(창고관리자, 총관리자 중 택1)"),

    INPUT_MEMBERSHIP_TYPE("""
            회원가입유형을 선택해주세요.
            1.일반회원     2.관리자
            -------------------------------------------------
            """);

    private final String page;

    LoginMessage(String page) {
        this.page = page;
    }

    public static void print(LoginMessage loginMenu) {
        System.out.printf(SUB_MENU_TITLE.toString(), loginMenu.page);
    }

    @Override
    public String toString() {
        return page;
    }
}
