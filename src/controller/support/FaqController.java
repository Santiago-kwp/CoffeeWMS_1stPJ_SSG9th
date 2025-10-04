package controller.support;

import constant.support.BoardText;
import domain.user.Manager;
import handler.support.inputHandler.InputHandlerImpl;
import handler.support.validHandler.ValidHandler;
import handler.support.validHandler.ValidHandlerImpl;
import handler.support.cs.CSOption;
import handler.support.input.BoardInput;
import handler.support.cs.csServiceImpl.CSOptionImpl;
import handler.support.input.inputImpl.FaqInputImpl;
import service.support.BoardService;

public class FaqController {
    private static final BoardInput faqInput = new FaqInputImpl();
    private final BoardService boardService;
    private static final InputHandlerImpl inputHandler = new InputHandlerImpl();
    private static final ValidHandler validHandler = new ValidHandlerImpl();
    private static final CSOption csOption = new CSOptionImpl();

    public FaqController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 회원의 FAQ 메뉴
    public void memberFaqMenu() {
        KIKI:
        while (true) {
            boardService.showAllFaq();
            System.out.print(BoardText.FAQ_MENU_SIMPLE.getMessage());
            String choice = inputHandler.twoMenuChoice();
            switch (choice) {
                case "1" -> {
                    System.out.print(BoardText.FAQ_INSERT_ID.getMessage());
                    int readChoice = inputHandler.choiceBoard();
                    System.out.println(BoardText.LINE.getMessage());
                    System.out.println(BoardText.FAQ_CHOICE.getMessage());
                    boardService.showOneFaq(readChoice);
                    csOption.backOption();
                }
                case "2" -> {
                    System.out.println(BoardText.BACK.getMessage());
                    break KIKI;
                }
            }
        }
    }

    // 관리자의 FAQ 메뉴
    public void managerFaqMenu(Manager manager) {
        KIKI:
        while (true) {
            String managerId = manager.getId();
            boardService.showAllFaq();
            System.out.print(BoardText.FAQ_MENU.getMessage());
            String choice = inputHandler.threeMenuChoice();
            switch (choice) {
                case "1" -> {
                    validHandler.managerCheck(manager);
                    boardService.createFaq(faqInput.dataInput(managerId));
                }
                case "2" -> {
                    System.out.print(BoardText.FAQ_INSERT_ID.getMessage());
                    int readChoice = inputHandler.choiceBoard();
                    System.out.println(BoardText.LINE.getMessage());
                    System.out.println(BoardText.FAQ_CHOICE.getMessage());
                    boardService.showOneFaq(readChoice);
                    faqDetailMenu(readChoice, manager);
                }
                case "3" -> {
                    System.out.println(BoardText.BACK.getMessage());
                    break KIKI;
                }
            }
        }
    }

    // 관리자 FAQ 상세 메뉴
    public void faqDetailMenu(Integer readChoice, Manager manager) {
        String managerId = manager.getId();
        System.out.print(BoardText.FAQ_DETAIL_MENU.getMessage());
        String choice = inputHandler.threeMenuChoice();
        switch (choice) {
            case "1" -> {
                validHandler.managerCheck(manager);
                boardService.updateFaq(faqInput.dataUpdate(readChoice, managerId));
            }
            case "2" -> {
                validHandler.managerCheck(manager);
                boardService.deleteFaq(readChoice, managerId);
            }
            case "3" -> System.out.println(BoardText.BACK.getMessage());
        }
    }
}
