package controller;

import constant.support.BoardErrorCode;
import constant.support.BoardText;
import constant.support.MainMenuText;
import controller.support.NoticeController;
import controller.user.LoginMenu;
import exception.support.InputException;
import exception.support.NotFoundException;
import handler.support.inputHandler.InputHandlerImpl;
import service.support.BoardService;
import service.support.BoardServiceImpl;

import java.io.IOException;

public class MainMenu {
    private static final LoginMenu loginMenu = LoginMenu.getInstance();
    private static final InputHandlerImpl inputHandler = new InputHandlerImpl();
    private final BoardService boardService = new BoardServiceImpl();
    NoticeController noticeController = new NoticeController(boardService);

    // 메인 페이지
    public void mainMenu() {
        TheEnd:
        while (true) {
            try {
                System.out.print(MainMenuText.MAIN_MENU.getMessage());
                boardService.showNoticePreview();
                System.out.print(MainMenuText.MAIN_MENU_OPTION.getMessage());

                String choice = inputHandler.threeMenuChoice();

                switch (choice) {
                    case "1" -> loginMenu.loginMenu();
                    case "2" -> noticeController.memberNoticeMenu();
                    case "3" -> {
                        System.out.println(BoardText.LINE.getMessage());
                        System.out.println(MainMenuText.MAIN_MENU_END.getMessage());
                        break TheEnd;
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
