package constant.user.validation;

import constant.user.InputMessage;

public class WMSValidCheck {

    private static final String WMS_MEMBER_MENU = "^[1-6]";
    private static final String WMS_MANAGER_MENU = "^[1-7]";

    public void checkMemberMenu(String menuNum) {
        if (!menuNum.matches(WMS_MEMBER_MENU)) {
            throw new IllegalArgumentException(InputMessage.INVALID_MENU_NUMBER.toString());
        }
    }

    public void checkManagerMenu(String menuNum) {
        if (!menuNum.matches(WMS_MANAGER_MENU)) {
            throw new IllegalArgumentException(InputMessage.INVALID_MENU_NUMBER.toString());
        }
    }
}
