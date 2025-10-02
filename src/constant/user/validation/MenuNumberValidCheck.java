package constant.user.validation;

import constant.user.InputMessage;

public class MenuNumberValidCheck {

    public void checkMenuNumber(String regex, String menuNum) {
        if (!menuNum.matches(regex)) {
            throw new IllegalArgumentException(InputMessage.INVALID_MENU_NUMBER.toString());
        }
    }
}
