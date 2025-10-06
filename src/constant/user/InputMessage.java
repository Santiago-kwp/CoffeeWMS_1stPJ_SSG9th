package constant.user;

public enum InputMessage {

    INPUT_ID("아이디를 입력해주세요.\n"),
    INPUT_PWD("비밀번호를 입력해주세요.\n"),
    INPUT_COMPANY_NAME("소속사를 입력해주세요.\n"),
    INPUT_NAME("이름을 입력해주세요.\n"),
    INPUT_PHONE("연락처를 입력해주세요.(입력 형식: 010-XXXX-XXXX)\n"),
    INPUT_EMAIL("이메일을 입력해주세요.\n"),
    INPUT_COMPANY_CODE("사업자등록번호를 입력해주세요.(형식은 XXX-XX-XXXXX)\n"),
    INPUT_ADDRESS("주소지를 입력해주세요.\n"),
    INPUT_NEW_PASSWORD("새로운 비밀번호를 입력해주세요.\n"),

    INVALID_ID("유효하지 않은 아이디입니다."),
    INVALID_PASSWORD("유효하지 않은 비밀번호입니다."),
    INVALID_PHONE_FORMAT("유효하지 않은 핸드폰번호 형식입니다."),
    INVALID_COMPANY_CODE_FORMAT("유효하지 않은 사업자등록번호 형식입니다."),
    INVALID_EMAIL_FORMAT("유효하지 않은 이메일 형식입니다."),
    INVALID_MENU_NUMBER("유효하지 않은 메뉴 번호입니다.");

    private final String msg;

    InputMessage(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
