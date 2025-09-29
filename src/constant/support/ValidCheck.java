package constant.support;

import domain.support.Faq;
import domain.support.Inquiry;
import domain.support.Notice;
import domain.user.Manager;
import domain.user.User;
import exception.support.InputException;
import exception.support.NotFoundException;

import static java.lang.Character.toUpperCase;

public class ValidCheck {

    public void isTwoMenuValid(String menu) {
        if (menu == null || menu.trim().isEmpty()) {
            throw new InputException(BoardErrorCode.NOT_INPUT_EMPTY.getMessage());
        }
        if (!(menu.matches(BoardErrorCode.MENU_NUMBER.getMessage()))) {
            throw new InputException(BoardErrorCode.NOT_INPUT_NUMBER.getMessage());
        }
        try {
            int menuNumber = Integer.parseInt(menu);
            if (menuNumber < 1 || menuNumber > 2) {
                throw new InputException(BoardErrorCode.NOT_INPUT_OPTION.getMessage());
            }
        } catch (NumberFormatException e) {
            throw new InputException(BoardErrorCode.NOT_INPUT_NUMBER.getMessage());
        }
    }

    public void isThreeMenuValid(String menu) {
        if (menu == null || menu.trim().isEmpty()) {
            throw new InputException(BoardErrorCode.NOT_INPUT_EMPTY.getMessage());
        }
        if (!(menu.matches(BoardErrorCode.MENU_NUMBER.getMessage()))) {
            throw new InputException(BoardErrorCode.NOT_INPUT_NUMBER.getMessage());
        }
        try {
            int menuNumber = Integer.parseInt(menu);
            if (menuNumber < 1 || menuNumber > 3) {
                throw new InputException(BoardErrorCode.NOT_INPUT_OPTION.getMessage());
            }
        } catch (NumberFormatException e) {
            throw new InputException(BoardErrorCode.NOT_INPUT_NUMBER.getMessage());
        }
    }

    public void isFourMenuValid(String menu) {
        if (menu == null || menu.trim().isEmpty()) {
            throw new InputException(BoardErrorCode.NOT_INPUT_EMPTY.getMessage());
        }
        if (!(menu.matches(BoardErrorCode.MENU_NUMBER.getMessage()))) {
            throw new InputException(BoardErrorCode.NOT_INPUT_NUMBER.getMessage());
        }
        try {
            int menuNumber = Integer.parseInt(menu);
            if (menuNumber < 1 || menuNumber > 4) {
                throw new InputException(BoardErrorCode.NOT_INPUT_OPTION.getMessage());
            }
        } catch (NumberFormatException e) {
            throw new InputException(BoardErrorCode.NOT_INPUT_NUMBER.getMessage());
        }
    }

    public void isValidBoardNumber(String menu, int boardNumber) {
        int num = Integer.parseInt(menu);
        if (num < 1 || num > boardNumber) {
            throw new InputException(BoardErrorCode.NOT_INPUT_OPTION.getMessage());
        }
    }

    public void isValidNotFoundNotice(Notice notice) {
        if (notice == null) throw new NotFoundException(BoardErrorCode.NOT_FOUND_BOARD.getMessage());
    }

    public void isValidNotFoundInquiry(Inquiry inquiry) {
        if (inquiry == null) throw new NotFoundException(BoardErrorCode.NOT_FOUND_BOARD.getMessage());
    }

    public void isValidNotFoundFaq(Faq faq) {
        if (faq == null) throw new NotFoundException(BoardErrorCode.NOT_FOUND_BOARD.getMessage());
    }

    public void managerCheck(Manager manager) {
        if (!manager.getPosition().equals("총관리자")) {
            throw new InputException(BoardErrorCode.YOU_ARE_NOT.getMessage());
        }
    }

    public void yCheck (String y){
        if (y == null || y.trim().isEmpty()) throw new InputException(BoardErrorCode.NOT_INPUT_EMPTY.getMessage());
        if (!y.trim().equalsIgnoreCase("Y")) throw new InputException(BoardErrorCode.NOT_INPUT_OPTION.getMessage());
    }
}
