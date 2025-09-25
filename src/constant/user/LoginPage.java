package constant.user;

public enum LoginPage {

    LOGIN_MENU_TITLE("""
            ---------------------<< 로그인 화면 >>-----------------------
            1.로그인 | 2.회원가입 | 3.아이디찾기 | 4.비밀번호변경 | 5.뒤로가기
            ------------------------------------------------------------
            메뉴를 선택해주세요.
            """),
    SUB_MENU_TITLE("""
            ---------------------<< %s >>----------------------
            """),

    LOGIN("로그인"),
    SIGN_UP("회원가입"),
    MEMBER_REGISTER("일반회원 회원정보 입력"),
    MANAGER_REGISTER("관리자 회원정보 입력"),
    FIND_ID("아이디 찾기"),
    FIND_PWD("비밀번호 찾기"),

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
            1.일반회원     2.관리자
            ------------------------------------------------------------
            회원가입유형을 선택해주세요.
            """),

    REGISTER_SUCCESS("회원가입이 완료되었습니다."),
    REGISTER_FAILED("회원가입에 실패했습니다."),

    FOUND_ID("찾으시는 회원의 아이디는 %s입니다.\n"),
    NOT_FOUND_ID("입력한 이메일에 해당하는 아이디가 없습니다.\n"),

    ID_NOT_EXIST("입력한 아이디에 해당하는 회원을 찾을 수 없습니다.\n"),
    NEW_PASSWORD("새로운 비밀번호를 입력해주세요."),
    UPDATE_PASSWORD("비밀번호 재설정이 완료되었습니다.\n"),
    NOT_UPDATE_PASSWORD("비밀번호 변경에 실패했습니다."),

    EXIT_LOGIN_MENU("로그인 메뉴를 종료합니다.");

    private final String page;

    LoginPage(String page) {
        this.page = page;
    }

    public static void print(LoginPage loginMenu) {
        System.out.printf(SUB_MENU_TITLE.toString(), loginMenu.page);
    }

    @Override
    public String toString() {
        return page;
    }
}
