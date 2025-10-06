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
    MEMBER_REGISTER("---------------------<< 일반회원 회원정보 입력 >>----------------------"),
    MANAGER_REGISTER("---------------------<< 관리자 회원정보 입력 >>----------------------"),
    FIND_ID("아이디 찾기"),
    FIND_PWD("비밀번호 찾기"),

    INPUT_MANAGER_POSITION("""
            ---------------------<< 회원가입 >>--------------------------
            1.창고관리자 | 2.총관리자
            ------------------------------------------------------------
            등록할 관리자의 직급을 선택하세요.(1,2 중 택1)
            """),
    INPUT_REGISTER_TYPE("""
            ---------------------<< 회원가입 >>--------------------------
            1.일반회원 | 2.관리자
            ------------------------------------------------------------
            회원가입유형을 선택해주세요.
            """),
    REGISTER_OR_NOT("회원가입을 진행하시겠습니까?(Y 입력 시 진행)\n"),

    REGISTER_SUCCESS("회원가입이 완료되었습니다."),
    REGISTER_FAILED("회원가입에 실패했습니다."),

    FOUND_ID("찾으시는 회원의 아이디는 %s입니다.\n"),
    NOT_FOUND_ID("입력한 이메일에 해당하는 아이디가 없습니다.\n"),

    CANNOT_LOGIN("현재 로그인 중이거나 존재하지 않는 계정입니다."),
    USER_NOT_EXIST("입력한 정보에 해당하는 회원을 찾을 수 없습니다.\n"),
    NEW_PASSWORD("새로운 비밀번호를 입력해주세요."),
    UPDATE_PASSWORD("비밀번호 재설정이 완료되었습니다.\n"),
    NOT_UPDATE_PASSWORD("비밀번호 변경에 실패했습니다."),
    TO_PREVIOUS_MENU("이전 메뉴로 돌아갑니다."),
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
