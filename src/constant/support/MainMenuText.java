package constant.support;

public enum MainMenuText {
    MAIN_MENU("""
            --------------------------------------------------------------------------------
                                      Welcome to the Coffee World
            ----------------------------------<< 공지사항 >>----------------------------------
            """),

    MAIN_MENU_OPTION("""
            ---------------------------------<< 메인 메뉴 >>---------------------------------
            메인 메뉴: 1.로그인 | 2.공지사항 | 3.종료
            메뉴 선택 >"""),

    MAIN_MENU_END("[종료]");

    private final String message;

    MainMenuText(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

