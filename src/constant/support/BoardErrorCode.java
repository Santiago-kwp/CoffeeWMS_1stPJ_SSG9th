package constant.support;

public enum BoardErrorCode {

    MENU_NUMBER ("^\\d+$"),

    NOT_CREATE_BOARD ("[문의를 생성할 수 없습니다]"),

    NOT_UPDATE_BOARD ("[해당 문의를 수정할 수 없습니다]"),

    NOT_REPLY_BOARD ("[해당 문의에 답변할 수 없습니다]"),

    NOT_DELETE_BOARD ("[해당 문의를 삭제할 수 없습니다]"),

    NOT_FOUND_BOARD ("[해당 문의를 찾을 수 없습니다]"),

    NOT_FOUND_LIST ("[해당 목록을 찾을 수 없습니다]"),

    NOT_INPUT_OPTION ("[해당 메뉴에서 선택해주세요]"),

    NOT_INPUT_IO ("[입력 도중 오류가 발생했습니다]"),

    NOT_INPUT_NUMBER ("[숫자를 입력해 주세요]"),

    NOT_INPUT_EMPTY ("[값를 입력해 주세요]"),

    NOT_INPUT_ERROR ("[에러가 발생했습니다]"),

    YOU_ARE_NOT ("[해당 권한이 없습니다]")


    ;
    private final String message;

    BoardErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
