package controller.support;

import constant.support.BoardText;
import domain.support.Board;
import handler.support.inputHandler.InputHandlerImpl;
import handler.support.input.InquiryInput;
import handler.support.input.inputImpl.InquiryInputImpl;
import service.support.BoardService;

public class InquiryController {
    private static final InquiryInput inquiryInput = new InquiryInputImpl();
    private final BoardService boardService;
    private static final InputHandlerImpl inputHandler = new InputHandlerImpl();

    public InquiryController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 회원의 1:1 문의 메뉴
    public void memberInquiryMenu(String memberId) {
        KI:
        while (true) {
            boardService.showAllInquiryMember(memberId);
            System.out.print(BoardText.INQUIRY_MENU.getMessage());
            String choice = inputHandler.threeMenuChoice();
            switch (choice) {
                case "1"-> boardService.createInquiry(inquiryInput.dataInput(memberId));
                case "2" -> {
                    System.out.print(BoardText.INQUIRY_INSERT_ID.getMessage());
                    int readChoice = inputHandler.choiceBoard();
                    System.out.println(BoardText.LINE.getMessage());
                    System.out.println(BoardText.INQUIRY_CHOICE.getMessage());
                    boardService.showOneInquiryMember(memberId, readChoice);
                    memberInquiryDetailMenu(memberId, readChoice);
                }
                case "3" -> {
                    System.out.println(BoardText.BACK.getMessage());
                    break KI;
                }
            }
        }
    }

    // 관리자의 1:1 문의 메뉴
    public void managerInquiryMenu(String managerId) {
        KI:
        while (true) {
            boardService.showAllInquiryManager();
            System.out.print(BoardText.INQUIRY_MENU_SIMPLE.getMessage());
            String choice = inputHandler.twoMenuChoice();
            switch (choice) {
                case "1" -> {
                    System.out.print(BoardText.INQUIRY_INSERT_ID.getMessage());
                    int readChoice = inputHandler.choiceBoard();
                    System.out.println(BoardText.LINE.getMessage());
                    System.out.println(BoardText.INQUIRY_CHOICE.getMessage());
                    boardService.showOneInquiryManager(readChoice);
                    managerInquiryDetailMenu(readChoice, managerId);
                }
                case "2" -> {
                    System.out.println(BoardText.BACK.getMessage());
                    break KI;
                }
            }
        }
    }

    // 회원의 1:1 문의 상세 메뉴
    public void memberInquiryDetailMenu(String memberIdEx, Integer readChoice) {
        System.out.print(BoardText.INQUIRY_DETAIL_MENU_MEMBER.getMessage());
        String choice = inputHandler.threeMenuChoice();
        System.out.println(BoardText.LINE.getMessage());
        switch (choice) {
            case "1" -> boardService.updateInquiryMember(inquiryInput.dataUpdate(readChoice, memberIdEx));
            case "2" -> boardService.deleteInquiryMember(readChoice, memberIdEx);
            case "3" -> System.out.println(BoardText.BACK.getMessage());
        }
    }

    // 관리자의 1:1 문의 상세 메뉴
    public void managerInquiryDetailMenu(Integer readChoice, String managerId) {
        System.out.print(BoardText.INQUIRY_DETAIL_MENU_MANAGER.getMessage());
        String choice = inputHandler.threeMenuChoice();
        System.out.println(BoardText.LINE.getMessage());
        switch (choice) {
            case "1" -> {
                Board board = inquiryInput.dataReplyUpdate(readChoice, managerId);
                boardService.updateInquiryManager(board);
            }
            case "2" -> boardService.deleteInquiryManager(readChoice);
            case "3" -> System.out.println(BoardText.BACK.getMessage());
        }
    }
}
