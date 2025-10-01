package controller.support;

import constant.support.BoardErrorCode;
import constant.support.BoardText;
import constant.support.ValidCheck;
import domain.support.Board;
import domain.support.Inquiry;
import domain.support.Notice;
import exception.support.InputException;
import exception.support.NotFoundException;
import model.support.dao.InquiryDAO;
import model.support.dao.daoImpl.InquiryDaoImpl;
import service.support.inputService.InquiryInput;
import service.support.inputService.inputImpl.InquiryInputImpl;
import service.support.readService.InquiryRead;
import service.support.readService.readImpl.InquiryReadImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InquiryMenu {
    private static final InquiryDAO inquiryDAO = new InquiryDaoImpl();
    private static final InquiryInput inquiryInput = new InquiryInputImpl();
    private static final InquiryRead inquiryRead = new InquiryReadImpl();
    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    // 회원의 1:1 문의 메뉴
    public void memberInquiryMenu(String memberId) {
        KI:
        while (true) {

            inquiryRead.memberInquiryReadAll(memberId);

            System.out.print(BoardText.INQUIRY_MENU.getMessage());

            String choice = null;
            try {
                choice = input.readLine();
                ValidCheck.isThreeMenuValid(choice);
            } catch (InputException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
            }
            switch (choice) {
                case "1":
//                    Inquiry inquiry = (Inquiry) inquiryInput.dataInput(memberId);
//                    boolean pass = inquiryDAO.createInquiry(inquiry);
                    Board board = inquiryInput.dataInput(memberId);

                    Boolean pass;

                    if (board instanceof Inquiry inquiry) {
                        pass = inquiryDAO.createInquiry(inquiry);
                    } else {
                        System.out.println(BoardErrorCode.NOT_CREATE_BOARD.getMessage());
                        break;
                    }

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

                    Inquiry oneInquiry = inquiryDAO.readInquiryMemberOne(memberId, readChoice);

                    try {
                        ValidCheck.isValidNotFoundInquiry(oneInquiry);
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    inquiryRead.readOne(oneInquiry);

                    memberInquiryDetailMenu(memberId, readChoice);
                    break;

                case "3":
                    System.out.println(BoardText.BACK.getMessage());
                    break KI;
            }
        }
    }

    // 관리자의 1:1 문의 메뉴
    public void managerInquiryMenu(String managerId) {
        KI:
        while (true) {
            inquiryRead.readAll();

            System.out.print(BoardText.INQUIRY_MENU_SIMPLE.getMessage());

            String choice = null;
            try {
                choice = input.readLine();
                ValidCheck.isTwoMenuValid(choice);
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


                    Inquiry oneInquiry = inquiryDAO.readInquiryManagerOne(readChoice);

                    try {
                        ValidCheck.isValidNotFoundInquiry(oneInquiry);
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                    }

                    inquiryRead.readOne(oneInquiry);

                    managerInquiryDetailMenu(readChoice, managerId);

                    break;

                case "2":
                    System.out.println(BoardText.BACK.getMessage());
                    break KI;
            }
        }
    }

    // 회원의 1:1 문의 상세 메뉴
    public void memberInquiryDetailMenu(String memberIdEx, Integer readChoice) {
        System.out.print(BoardText.INQUIRY_DETAIL_MENU_MEMBER.getMessage());

        String choice = null;
        try {
            choice = input.readLine();
            ValidCheck.isThreeMenuValid(choice);
        } catch (InputException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
        }

        System.out.println(BoardText.LINE.getMessage());

        switch (choice) {
            case "1":
//                Inquiry inquiry = (Inquiry) inquiryInput.dataUpdate(readChoice, memberIdEx);
//                boolean update = inquiryDAO.updateInquiryMember(inquiry);

                Board board = inquiryInput.dataUpdate(readChoice, memberIdEx);
                Boolean update;

                if (board instanceof Inquiry inquiry) {
                    update = inquiryDAO.updateInquiryMember(inquiry);
                } else {
                    System.out.println(BoardErrorCode.NOT_CREATE_BOARD.getMessage());
                    break;
                }

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

    // 관리자의 1:1 문의 상세 메뉴
    public void managerInquiryDetailMenu(Integer readChoice, String managerId) {
        System.out.print(BoardText.INQUIRY_DETAIL_MENU_MANAGER.getMessage());

        String choice = null;
        try {
            choice = input.readLine();
            ValidCheck.isThreeMenuValid(choice);
        } catch (InputException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
        }

        System.out.println(BoardText.LINE.getMessage());

        switch (choice) {
            case "1":
                Inquiry inquiry;
                inquiry = inquiryInput.dataReplyUpdate(readChoice, managerId);

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
}
