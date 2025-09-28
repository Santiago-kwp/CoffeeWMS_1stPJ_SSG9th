package constant.user;

public enum InputMessage {

    INVALID_ID("유효하지 않은 아이디입니다."),
    INVALID_PASSWORD("유효하지 않은 비밀번호입니다."),
    INVALID_PHONE_FORMAT("유효하지 않은 핸드폰번호 형식입니다."),
    INVALID_COMPANY_CODE_FORMAT("유효하지 않은 사업자등록번호 형식입니다."),
    INVALID_EMAIL_FORMAT("유효하지 않은 이메일 형식입니다.");

    private final String msg;

    InputMessage(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
