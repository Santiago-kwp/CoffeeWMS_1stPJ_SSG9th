package controller.support;

import constant.support.BoardErrorCode;
import constant.support.BoardText;
import domain.user.Manager;
import domain.user.Member;
import exception.support.InputException;
import exception.support.NotFoundException;
import handler.support.inputHandler.InputHandler;
import handler.support.inputHandler.InputHandlerImpl;
import handler.support.validHandler.ValidHandler;
import handler.support.validHandler.ValidHandlerImpl;
import service.support.BoardService;

public class CSController {
    private final NoticeController noticeMenu;
    private final InquiryController inquiryMenu;
    private final FaqController faqMenu;
    private static final InputHandler inputHandler = new InputHandlerImpl();
    private static final ValidHandler validHandler = new ValidHandlerImpl();

    public CSController(BoardService boardService) {
        this.noticeMenu = new NoticeController(boardService);
        this.inquiryMenu = new InquiryController(boardService);
        this.faqMenu = new FaqController(boardService);
    }

    // 일반회원의 고객센터 메뉴
    public void memberCSMenu(Member member) {
        TheEndCS:
        while (true) {
            try {
                System.out.print(BoardText.CS_MENU.getMessage());
                String choice = inputHandler.fourMenuChoice();
                switch (choice) {
                    case "1" -> noticeMenu.memberNoticeMenu();
                    case "2" -> inquiryMenu.memberInquiryMenu(member.getId());
                    case "3" -> faqMenu.memberFaqMenu();
                    case "4" -> {
                        System.out.println(BoardText.BACK.getMessage());
                        break TheEndCS;
                    }
                }
            } catch (NullPointerException e) {
                System.out.println(BoardErrorCode.PLEASE_AGAIN.getMessage());
            } catch (InputException | NotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // 관리자의 고객센터 메뉴
    public void managerCSMenu(Manager manager) {
        TheEndCS:
        while (true) {
            try {
                System.out.print(BoardText.CS_MENU.getMessage());
                String choice = inputHandler.fourMenuChoice();
                switch (choice) {
                    case "1" -> noticeMenu.managerNoticeMenu(manager);
                    case "2"->{
                        validHandler.managerCheck(manager);
                        inquiryMenu.managerInquiryMenu(manager.getId());
                    }
                    case "3" -> faqMenu.managerFaqMenu(manager);
                    case "4" -> {
                        System.out.println(BoardText.BACK.getMessage());
                        break TheEndCS;
                    }
                }
            } catch (NullPointerException e) {
                System.out.println(BoardErrorCode.PLEASE_AGAIN.getMessage());
            } catch (InputException | NotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
