package controller.support;

import constant.support.BoardText;
import domain.support.Board;
import domain.user.Manager;
import handler.support.inputHandler.InputHandlerImpl;
import handler.support.validHandler.ValidHandler;
import handler.support.validHandler.ValidHandlerImpl;
import handler.support.cs.CSOption;
import handler.support.input.BoardInput;
import handler.support.cs.csServiceImpl.CSOptionImpl;
import handler.support.input.inputImpl.NoticeInputImpl;
import service.support.BoardService;

public class NoticeController {
    private static final BoardInput noticeInput = new NoticeInputImpl();
    private final BoardService boardService;
    private static final InputHandlerImpl inputHandler = new InputHandlerImpl();
    private static final ValidHandler validHandler = new ValidHandlerImpl();
    private static final CSOption csOption = new CSOptionImpl();

    public NoticeController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 일반회원의 공지사항 메뉴
    public void memberNoticeMenu() {
        KI:
        while (true) {
            boardService.showAllNotice();
            System.out.print(BoardText.NOTICE_MENU_SIMPLE.getMessage());
            String choice = inputHandler.twoMenuChoice();
            switch (choice) {
                case "1" -> {
                    System.out.print(BoardText.NOTICE_INSERT_ID.getMessage());
                    int readChoice = inputHandler.choiceBoard();
                    System.out.println(BoardText.LINE.getMessage());
                    System.out.println(BoardText.NOTICE_CHOICE.getMessage());
                    boardService.showOneNotice(readChoice);
                    csOption.backOption();
                }
                case "2" -> {
                    System.out.println(BoardText.BACK.getMessage());
                    break KI;
                }
            }
        }
    }

    // 관리자의 공지사항 메뉴
    public void managerNoticeMenu(Manager manager) {
        KIKI:
        while (true) {
            String managerId = manager.getId();
            boardService.showAllNotice();
            System.out.print(BoardText.NOTICE_MENU.getMessage());
            String choice = inputHandler.threeMenuChoice();
            switch (choice) {
                case "1" -> {
                    validHandler.managerCheck(manager);
                    Board board = noticeInput.dataInput(managerId);
                    boardService.createNotice(board);
                }
                case "2" -> {
                    System.out.print(BoardText.NOTICE_INSERT_ID.getMessage());
                    int readChoice = inputHandler.choiceBoard();
                    System.out.println(BoardText.LINE.getMessage());
                    System.out.println(BoardText.NOTICE_CHOICE.getMessage());
                    boardService.showOneNotice(readChoice);
                    noticeDetailMenu(readChoice, manager);
                }
                case "3" -> {
                    System.out.println(BoardText.BACK.getMessage());
                    break KIKI;
                }
            }
        }
    }

    // 관리자의 공지사항 상세 메뉴
    public void noticeDetailMenu(Integer readChoice, Manager manager) {
        System.out.print(BoardText.NOTICE_DETAIL_MENU.getMessage());
        String managerId = manager.getId();
        String choice = inputHandler.threeMenuChoice();
        System.out.println(BoardText.LINE.getMessage());
        switch (choice) {
            case "1" -> {
                validHandler.managerCheck(manager);
                Board board = noticeInput.dataUpdate(readChoice, managerId);
                boardService.updateNotice(board);
            }
            case "2" -> {
                validHandler.managerCheck(manager);
                boardService.deleteNotice(readChoice, managerId);
            }
            case "3" -> System.out.println(BoardText.BACK.getMessage());
        }
    }
}
