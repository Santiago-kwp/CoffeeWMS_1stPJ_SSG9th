package controller;

import constant.support.BoardText;
import constant.support.MainMenuText;
import controller.user.LoginMenu;
import handler.support.inputHandler.InputHandlerImpl;

public class MainMenu {
    private static final LoginMenu loginMenu = LoginMenu.getInstance();
    private static final InputHandlerImpl inputHandler = new InputHandlerImpl();
//    private final BoardService boardService;

//    public MainMenu(BoardServiceImpl boardService) {
//        this.boardService = boardService;
//    }

    // 메인 페이지
    public void mainMenu() {
        TheEnd:
        while (true) {
            System.out.print(MainMenuText.MAIN_MENU.getMessage());
//            boardService.showNoticePreview();
            System.out.print(MainMenuText.MAIN_MENU_OPTION.getMessage());

            String choice = inputHandler.twoMenuChoice();

            switch (choice) {
                case "1":
                    loginMenu.loginMenu();
                    break;
                case "2":
                    System.out.println(BoardText.LINE.getMessage());
                    System.out.println(MainMenuText.MAIN_MENU_END.getMessage());
                    break TheEnd;
            }
        }
    }
}
