package controller.support;

import constant.support.BoardErrorCode;
import constant.support.BoardText;
import domain.support.Board;
import domain.support.Faq;
import domain.user.Manager;
import handler.support.inputHandler.InputHandlerImpl;
import handler.support.validHandler.ValidHandler;
import handler.support.validHandler.ValidHandlerImpl;
import model.support.dao.FaqRepository;
import model.support.dao.daoImpl.FaqRepositoryImpl;
import handler.support.cs.CSOption;
import handler.support.input.BoardInput;
import handler.support.cs.csServiceImpl.CSOptionImpl;
import handler.support.input.inputImpl.FaqInputImpl;
import service.support.BoardService;

public class FaqMenu {
    private static final FaqRepository faqDAO = new FaqRepositoryImpl();
    private static final BoardInput faqInput = new FaqInputImpl();
    private final BoardService boardService;
    private static final InputHandlerImpl inputHandler = new InputHandlerImpl();
    private static final ValidHandler validHandler = new ValidHandlerImpl();
    private static final CSOption csOption = new CSOptionImpl();

    public FaqMenu(BoardService boardService) {
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
                case "1":
                    System.out.print(BoardText.FAQ_INSERT_ID.getMessage());
                    int readChoice = inputHandler.choiceBoard();
                    System.out.println(BoardText.LINE.getMessage());
                    System.out.println(BoardText.FAQ_CHOICE.getMessage());
                    boardService.showOneFaq(readChoice);
                    csOption.backOption();
                    break;
                case "2":
                    System.out.println(BoardText.BACK.getMessage());
                    break KIKI;
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
                case "1":
                    validHandler.managerCheck(manager);

                    Board board = faqInput.dataInput(managerId);
                    Boolean pass;

                    if (board instanceof Faq faq) {
                        pass = faqDAO.createFaq(faq);
                    } else {
                        System.out.println(BoardErrorCode.NOT_CREATE_BOARD.getMessage());
                        break;
                    }

                    if (pass) System.out.println(BoardText.FAQ_CREATE_SUCCESS.getMessage());
                    else System.out.println(BoardText.FAQ_CREATE_FAILURE.getMessage());
                    break;
                case "2":
                    System.out.print(BoardText.FAQ_INSERT_ID.getMessage());
                    int readChoice = inputHandler.choiceBoard();
                    System.out.println(BoardText.LINE.getMessage());
                    System.out.println(BoardText.FAQ_CHOICE.getMessage());
                    boardService.showOneFaq(readChoice);
                    faqDetailMenu(readChoice, manager);
                    break;
                case "3":
                    System.out.println(BoardText.BACK.getMessage());
                    break KIKI;
            }
        }
    }

    // 관리자 FAQ 상세 메뉴
    public void faqDetailMenu(Integer readChoice, Manager manager) {
        String managerId = manager.getId();
        System.out.print(BoardText.FAQ_DETAIL_MENU.getMessage());
        String choice = inputHandler.threeMenuChoice();
        switch (choice) {
            case "1":
                validHandler.managerCheck(manager);

                Board board = faqInput.dataUpdate(readChoice, managerId);
                Boolean update;

                if (board instanceof Faq faq) {
                    update = faqDAO.updateFaq(faq);
                } else {
                    System.out.println(BoardErrorCode.NOT_CREATE_BOARD.getMessage());
                    break;
                }

                if (update) System.out.println(BoardText.FAQ_UPDATE_SUCCESS.getMessage());
                else System.out.println(BoardText.FAQ_UPDATE_FAILURE.getMessage());
                break;

            case "2":
                validHandler.managerCheck(manager);
                boolean delete = faqDAO.deleteFaq(readChoice, managerId);

                if (delete) System.out.println(BoardText.FAQ_DELETE_SUCCESS.getMessage());
                else System.out.println(BoardText.FAQ_DELETE_FAILURE.getMessage());
                break;

            case "3":
                System.out.println(BoardText.BACK.getMessage());
                break;
        }
    }
}
