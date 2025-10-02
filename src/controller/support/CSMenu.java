package controller.support;

import constant.support.BoardText;
import domain.user.Manager;
import domain.user.Member;
import handler.support.inputHandler.InputHandler;
import handler.support.inputHandler.InputHandlerImpl;
import handler.support.validHandler.ValidHandler;
import handler.support.validHandler.ValidHandlerImpl;
import service.support.BoardService;

public class CSMenu {
    private NoticeMenu noticeMenu;
    private InquiryMenu inquiryMenu;
    private FaqMenu faqMenu;
    private static final InputHandler inputHandler = new InputHandlerImpl();
    private static final ValidHandler validHandler = new ValidHandlerImpl();

    public CSMenu(BoardService boardService) {
        this.noticeMenu = new NoticeMenu(boardService);
        this.inquiryMenu = new InquiryMenu(boardService);
        this.faqMenu = new FaqMenu(boardService);
    }

    // 일반회원의 고객센터 메뉴
    public void memberCSMenu(Member member) {
        TheEndCS:
        while (true) {
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
        }
    }

    // 관리자의 고객센터 메뉴
    public void managerCSMenu(Manager manager) {
        TheEndCS:
        while (true) {
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
        }
    }
}
