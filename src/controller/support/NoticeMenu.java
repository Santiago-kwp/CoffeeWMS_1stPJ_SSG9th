package controller.support;

import constant.support.BoardErrorCode;
import constant.support.BoardText;
import domain.support.Board;
import domain.support.Notice;
import domain.user.Manager;
import handler.support.inputHandler.InputHandlerImpl;
import handler.support.validHandler.ValidHandler;
import handler.support.validHandler.ValidHandlerImpl;
import model.support.dao.NoticeRepository;
import model.support.dao.daoImpl.NoticeRepositoryImpl;
import handler.support.cs.CSOption;
import handler.support.input.BoardInput;
import handler.support.cs.csServiceImpl.CSOptionImpl;
import handler.support.input.inputImpl.NoticeInputImpl;
import service.support.BoardService;

public class NoticeMenu {
    private static final NoticeRepository noticeDAO = new NoticeRepositoryImpl();
    private static final BoardInput noticeInput = new NoticeInputImpl();
    private final BoardService boardService;
    private static final InputHandlerImpl inputHandler = new InputHandlerImpl();
    private static final ValidHandler validHandler = new ValidHandlerImpl();
    private static final CSOption csOption = new CSOptionImpl();

    public NoticeMenu(BoardService boardService) {
        this.boardService = boardService;
    }

    // 회원의 공지사항 메뉴
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

                    Boolean pass;

                    if (board instanceof Notice notice) {
                        pass = noticeDAO.createNotice(notice);
                    } else {
                        System.out.println(BoardErrorCode.NOT_CREATE_BOARD.getMessage());
                        break;
                    }

                    if (pass) System.out.println(BoardText.NOTICE_CREATE_SUCCESS.getMessage());
                    else System.out.println(BoardText.NOTICE_CREATE_FAILURE.getMessage());
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
                Boolean update;

                if (board instanceof Notice notice) {
                    update = noticeDAO.updateNotice(notice);
                } else {
                    System.out.println(BoardErrorCode.NOT_CREATE_BOARD.getMessage());
                    break;
                }

                if (update) System.out.println(BoardText.NOTICE_UPDATE_SUCCESS.getMessage());
                else System.out.println(BoardText.NOTICE_UPDATE_FAILURE.getMessage());
            }
            case "2" -> {
                validHandler.managerCheck(manager);
                boolean delete = noticeDAO.deleteNotice(readChoice, managerId);

                if (delete) System.out.println(BoardText.NOTICE_DELETE_SUCCESS.getMessage());
                else System.out.println(BoardText.NOTICE_DELETE_FAILURE.getMessage());
            }
            case "3" -> System.out.println(BoardText.BACK.getMessage());
        }
    }
}
