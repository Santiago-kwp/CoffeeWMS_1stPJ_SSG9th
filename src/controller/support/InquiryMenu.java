package controller.support;

import constant.support.BoardErrorCode;
import constant.support.BoardText;
import constant.support.ValidCheck;
import domain.support.Inquiry;
import exception.support.InputException;
import exception.support.NotFoundException;
import model.support.service.dao.InquiryDAO;
import model.support.service.dao.daoImpl.InquiryDaoImpl;
import model.support.service.inputService.InquiryInput;
import model.support.service.inputService.inputImpl.InquiryInputImpl;
import model.support.service.readService.InquiryRead;
import model.support.service.readService.readImpl.InquiryReadImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InquiryMenu {
    ValidCheck validCheck = new ValidCheck();
    InquiryDAO inquiryDAO = new InquiryDaoImpl();
    InquiryInput inquiryInput = new InquiryInputImpl();
    InquiryRead inquiryRead = new InquiryReadImpl();
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

//    public void inquiryMenu(User user) throws IOException {
//        if (user instanceof Manager manager){
//            managerInquiryMenu();
//        } else if (user instanceof Member member) {
//            memberInquiryMenu();
//        }
//    }

    // 총관리자 1:1문의 메뉴 -------------------------------------------------------------------------------------------------
    public void managerInquiryMenu(String managerId) {
        KI:
        while (true) {
            inquiryRead.managerInquiryReadAll();

            System.out.print(BoardText.INQUIRY_MENU_SIMPLE.getMessage());

            String choice = null;
            try {
                choice = input.readLine();
                validCheck.isTwoMenuValid(choice);
            } catch (InputException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
            }

            switch (choice) {
                case "1":
                    System.out.print(BoardText.INQUIRY_INSERT_ID.getMessage());

                    int readChoice = 0;
                    try {
                        readChoice = Integer.parseInt(input.readLine());
                    } catch (IOException e) {
                        System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
                        managerInquiryMenu(managerId);
                    } catch (NumberFormatException e) {
                        System.out.println(BoardErrorCode.NOT_INPUT_NUMBER.getMessage());
                        managerInquiryMenu(managerId);
                    }

                    System.out.println(BoardText.LINE.getMessage());

                    System.out.println(BoardText.INQUIRY_CHOICE.getMessage());

                    String status;
                    Inquiry oneInquiry = inquiryDAO.readInquiryManagerOne(readChoice);

                    try {
                        validCheck.isValidNotFoundInquiry(oneInquiry);
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                    }

                    switch (oneInquiry.getInquiryStatus()) {
                        case PENDING -> {
                            status = BoardText.REPLY_PENDING.getMessage();
                            System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                                    BoardText.CREATE_DATE.getMessage(), oneInquiry.getInquiryDate(),
                                    BoardText.CATEGORY.getMessage(), oneInquiry.getInquiryCategoryName(),
                                    BoardText.QUEST.getMessage(), oneInquiry.getInquiryContent(),
                                    BoardText.REPLY_STATUS.getMessage(), status);
                        }
                        case DONE -> {
                            status = BoardText.REPLY_DONE.getMessage();
                            System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                                    BoardText.CREATE_DATE.getMessage(), oneInquiry.getInquiryDate(),
                                    BoardText.CATEGORY.getMessage(), oneInquiry.getInquiryCategoryName(),
                                    BoardText.QUEST.getMessage(), oneInquiry.getInquiryContent(),
                                    BoardText.REPLY_STATUS.getMessage(), status,
                                    BoardText.REPLY_DATE.getMessage(), oneInquiry.getReplyDate(),
                                    BoardText.REPLY_STATUS.getMessage(), status);

                        }
                    }

                    managerInquiryDetailMenu(readChoice, managerId);

                    break;

                case "2":
                    System.out.println(BoardText.BACK.getMessage());
                    break KI;
            }
        }
    }

    // 회원 1:1문의 메뉴 -------------------------------------------------------------------------------------------------
    public void memberInquiryMenu(String memberId) {
        KI:
        while (true) {

            inquiryRead.memberInquiryReadAll(memberId);

            System.out.print(BoardText.INQUIRY_MENU.getMessage());

            String choice = null;
            try {
                choice = input.readLine();
                validCheck.isThreeMenuValid(choice);
            } catch (InputException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
            }
            switch (choice) {
                case "1":
                    Inquiry inquiry = inquiryInput.inquiryDataInput(memberId);

                    boolean pass = inquiryDAO.createInquiry(inquiry);

                    if (pass) System.out.println(BoardText.INQUIRY_CREATE_SUCCESS.getMessage());
                    else System.out.println(BoardText.INQUIRY_CREATE_FAILURE.getMessage());
                    break;

                case "2":
                    System.out.print(BoardText.INQUIRY_INSERT_ID.getMessage());

                    int readChoice = 0;
                    try {
                        readChoice = Integer.parseInt(input.readLine());
                    } catch (IOException e) {
                        System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
                        memberInquiryMenu(memberId);
                    } catch (NumberFormatException e) {
                        System.out.println(BoardErrorCode.NOT_INPUT_NUMBER.getMessage());
                        memberInquiryMenu(memberId);
                    }

                    System.out.println(BoardText.LINE.getMessage());

                    System.out.println(BoardText.INQUIRY_CHOICE.getMessage());

                    String status;
                    Inquiry oneInquiry = inquiryDAO.readInquiryMemberOne(memberId, readChoice);

                    try {
                        validCheck.isValidNotFoundInquiry(oneInquiry);
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    switch (oneInquiry.getInquiryStatus()) {
                        case PENDING -> {
                            status = "답변 대기";
                            System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                                    BoardText.CREATE_DATE.getMessage(), oneInquiry.getInquiryDate(),
                                    BoardText.CATEGORY.getMessage(), oneInquiry.getInquiryCategoryName(),
                                    BoardText.QUEST.getMessage(), oneInquiry.getInquiryContent(),
                                    BoardText.REPLY_STATUS.getMessage(), status);
                        }
                        case DONE -> {
                            status = "답변 완료";
                            System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                                    BoardText.CREATE_DATE.getMessage(), oneInquiry.getInquiryDate(),
                                    BoardText.CATEGORY.getMessage(), oneInquiry.getInquiryCategoryName(),
                                    BoardText.QUEST.getMessage(), oneInquiry.getInquiryContent(),
                                    BoardText.REPLY_STATUS.getMessage(), status,
                                    BoardText.REPLY_DATE.getMessage(), oneInquiry.getReplyDate(),
                                    BoardText.REPLY_STATUS.getMessage(), status);
                        }
                        default -> System.out.println(BoardErrorCode.NOT_FOUND_BOARD.getMessage());
                    }
                    memberInquiryDetailMenu(memberId, readChoice);
                    break;

                case "3":
                    System.out.println(BoardText.BACK.getMessage());
                    break KI;
            }
        }
    }

    // 총관리자 1:1 문의 상세 메뉴 ---------------------------------------------------------------------------------------------------
    public void managerInquiryDetailMenu(Integer readChoice, String managerId) {
        System.out.print(BoardText.INQUIRY_DETAIL_MENU_MANAGER.getMessage());

        String choice = null;
        try {
            choice = input.readLine();
            validCheck.isThreeMenuValid(choice);
        } catch (InputException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
        }

        System.out.println(BoardText.LINE.getMessage());

        switch (choice) {
            case "1":
                Inquiry inquiry;
                inquiry = inquiryInput.managerInquiryDataUpdate(readChoice, managerId);

                boolean update = inquiryDAO.updateInquiryManager(inquiry);

                if (update) System.out.println(BoardText.INQUIRY_REPLY_SUCCESS.getMessage());
                else System.out.println(BoardText.INQUIRY_REPLY_FAILURE.getMessage());
                break;

            case "2":
                boolean delete = inquiryDAO.deleteInquiryManager(readChoice);

                if (delete) System.out.println(BoardText.INQUIRY_DELETE_SUCCESS.getMessage());
                else System.out.println(BoardText.INQUIRY_DELETE_FAILURE.getMessage());
                break;

            case "3":
                System.out.println(BoardText.BACK.getMessage());
                break;
        }
    }

    // 회원 공지사항 상세 메뉴 ---------------------------------------------------------------------------------------------------
    public void memberInquiryDetailMenu(String memberIdEx, Integer readChoice) {
        System.out.print(BoardText.INQUIRY_DETAIL_MENU_MEMBER.getMessage());

        String choice = null;
        try {
            choice = input.readLine();
            validCheck.isThreeMenuValid(choice);
        } catch (InputException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
        }

        System.out.println(BoardText.LINE.getMessage());

        switch (choice) {
            case "1":
                Inquiry inquiry = inquiryInput.memberInquiryDataUpdate(memberIdEx, readChoice);

                boolean update = inquiryDAO.updateInquiryMember(inquiry);

                if (update) System.out.println(BoardText.INQUIRY_UPDATE_SUCCESS.getMessage());
                else System.out.println(BoardText.INQUIRY_UPDATE_FAILURE.getMessage());
                break;

            case "2":
                boolean delete = inquiryDAO.deleteInquiryMember(readChoice, memberIdEx);

                if (delete) System.out.println(BoardText.INQUIRY_DELETE_SUCCESS.getMessage());
                else System.out.println(BoardText.INQUIRY_DELETE_FAILURE.getMessage());
                break;

            case "3":
                System.out.println(BoardText.BACK.getMessage());
                break;
        }
    }
}
