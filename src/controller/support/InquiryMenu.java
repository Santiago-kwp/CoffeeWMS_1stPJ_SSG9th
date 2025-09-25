package controller.support;

import constant.support.CSExceptionMessage;
import constant.support.CSMenuMessage;
import domain.support.Inquiry;
import exception.support.InputException;
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

            System.out.print(CSMenuMessage.INQUIRY_MENU_SIMPLE.getMessage());

            int choice;
            try {
                choice = Integer.parseInt(input.readLine());
                if (choice <= 0 || choice > 2) System.out.println(CSExceptionMessage.NOT_INPUT_OPTION.getMessage());
            } catch (IOException e) {
                throw new InputException(CSExceptionMessage.NOT_INPUT_IO.getMessage());
            } catch (NumberFormatException e1) {
                throw new InputException(CSExceptionMessage.NOT_INPUT_NUMBER.getMessage());
            } catch (Exception e2) {
                throw new InputException(CSExceptionMessage.NOT_INPUT_ERROR.getMessage());
            }

            switch (choice) {
                case 1:
                    System.out.print(CSMenuMessage.INQUIRY_INSERT_ID.getMessage());

                    int readChoice;
                    try {
                        readChoice = Integer.parseInt(input.readLine());
                    } catch (IOException e) {
                        throw new InputException(CSExceptionMessage.NOT_INPUT_IO.getMessage());
                    }

                    System.out.println(CSMenuMessage.LINE.getMessage());

                    System.out.println(CSMenuMessage.INQUIRY_CHOICE.getMessage());

                    String status;
                    Inquiry oneInquiry = inquiryDAO.readInquiryManagerOne(readChoice);
                    switch (oneInquiry.getInquiryStatus()) {
                        case PENDING -> {
                            status = "답변 대기";
                            System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                                    "문의날짜", oneInquiry.getInquiryDate(), "카테고리", oneInquiry.getInquiryCategoryName(),
                                    "문의", oneInquiry.getInquiryContent(), "답변 상태", status);
                        }
                        case DONE -> {
                            status = "답변 완료";
                            System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                                    "문의날짜", oneInquiry.getInquiryDate(), "카테고리", oneInquiry.getInquiryCategoryName(),
                                    "문의", oneInquiry.getInquiryContent(), "답변 상태", status,
                                    "답변날짜", oneInquiry.getReplyDate(), "답변", oneInquiry.getReplyContent());
                        }
                    }

                    managerInquiryDetailMenu(readChoice, managerId);

                    break;

                case 2:
                    System.out.println(CSMenuMessage.BACK.getMessage());
                    break KI;
            }
        }
    }

    // 회원 1:1문의 메뉴 -------------------------------------------------------------------------------------------------
    public void memberInquiryMenu(String memberId) {
        KI:
        while (true) {

            inquiryRead.memberInquiryReadAll(memberId);

            System.out.print(CSMenuMessage.INQUIRY_MENU.getMessage());

            int choice;
            try {
                choice = Integer.parseInt(input.readLine());
                if (choice <= 0 || choice > 3) System.out.println(CSExceptionMessage.NOT_INPUT_OPTION.getMessage());
            } catch (IOException e) {
                throw new InputException(CSExceptionMessage.NOT_INPUT_IO.getMessage());
            } catch (NumberFormatException e1) {
                throw new InputException(CSExceptionMessage.NOT_INPUT_NUMBER.getMessage());
            } catch (Exception e2) {
                throw new InputException(CSExceptionMessage.NOT_INPUT_ERROR.getMessage());
            }

            switch (choice) {
                case 1:
                    Inquiry inquiry = inquiryInput.inquiryDataInput(memberId);

                    boolean pass = inquiryDAO.createInquiry(inquiry);

                    if (pass) System.out.println(CSMenuMessage.INQUIRY_CREATE_SUCCESS.getMessage());
                    else System.out.println(CSMenuMessage.INQUIRY_CREATE_FAILURE.getMessage());
                    break;

                case 2:
                    System.out.print(CSMenuMessage.INQUIRY_INSERT_ID.getMessage());

                    int readChoice = 0;
                    try {
                        readChoice = Integer.parseInt(input.readLine());
                    } catch (IOException e) {
                        throw new InputException(CSExceptionMessage.NOT_INPUT_IO.getMessage());
                    }

                    System.out.println(CSMenuMessage.LINE.getMessage());

                    System.out.println(CSMenuMessage.INQUIRY_CHOICE.getMessage());

                    String status;
                    Inquiry oneInquiry = inquiryDAO.readInquiryMemberOne(memberId, readChoice);

                    if (oneInquiry == null) {
                        System.out.println(CSExceptionMessage.NOT_FOUND_BOARD.getMessage());
                        break;
                    }

                    switch (oneInquiry.getInquiryStatus()) {
                        case PENDING -> {
                            status = "답변 대기";
                            System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                                    "문의날짜", oneInquiry.getInquiryDate(), "카테고리", oneInquiry.getInquiryCategoryName(),
                                    "문의", oneInquiry.getInquiryContent(), "답변 상태", status);
                        }
                        case DONE -> {
                            status = "답변 완료";
                            System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                                    "문의날짜", oneInquiry.getInquiryDate(), "카테고리", oneInquiry.getInquiryCategoryName(),
                                    "문의", oneInquiry.getInquiryContent(), "답변 상태", status,
                                    "답변날짜", oneInquiry.getReplyDate(), "답변", oneInquiry.getReplyContent());
                        }
                        default -> System.out.println(CSExceptionMessage.NOT_FOUND_BOARD.getMessage());
                    }
                    memberInquiryDetailMenu(memberId, readChoice);
                    break;

                case 3:
                    System.out.println(CSMenuMessage.BACK.getMessage());
                    break KI;
            }
        }
    }

    // 총관리자 공지사항 상세 메뉴 ---------------------------------------------------------------------------------------------------
    public void managerInquiryDetailMenu(Integer readChoice, String managerId) {
        System.out.print(CSMenuMessage.INQUIRY_DETAIL_MENU_MANAGER.getMessage());

        int choice;
        try {
            choice = Integer.parseInt(input.readLine());
            if (choice <= 0 || choice > 3)
                System.out.println(CSExceptionMessage.NOT_INPUT_OPTION.getMessage());
        } catch (IOException e) {
            throw new InputException(CSExceptionMessage.NOT_INPUT_IO.getMessage());
        } catch (NumberFormatException e1) {
            throw new InputException(CSExceptionMessage.NOT_INPUT_NUMBER.getMessage());
        } catch (Exception e2) {
            throw new InputException(CSExceptionMessage.NOT_INPUT_ERROR.getMessage());
        }

        System.out.println(CSMenuMessage.LINE.getMessage());

        switch (choice) {
            case 1:
                Inquiry inquiry;
                inquiry = inquiryInput.managerInquiryDataUpdate(readChoice, managerId);

                boolean update = inquiryDAO.updateInquiryManager(inquiry);

                if (update) System.out.println(CSMenuMessage.INQUIRY_REPLY_SUCCESS.getMessage());
                else System.out.println(CSMenuMessage.INQUIRY_REPLY_FAILURE.getMessage());
                break;

            case 2:
                boolean delete = inquiryDAO.deleteInquiryManager(readChoice);

                if (delete) System.out.println(CSMenuMessage.INQUIRY_DELETE_SUCCESS.getMessage());
                else System.out.println(CSMenuMessage.INQUIRY_DELETE_FAILURE.getMessage());
                break;

            case 3:
                System.out.println(CSMenuMessage.BACK.getMessage());
                break;
        }
    }

    // 회원 공지사항 상세 메뉴 ---------------------------------------------------------------------------------------------------
    public void memberInquiryDetailMenu(String memberIdEx, Integer readChoice) {
        System.out.print(CSMenuMessage.INQUIRY_DETAIL_MENU_MEMBER.getMessage());

        int choice;
        try {
            choice = Integer.parseInt(input.readLine());
            if (choice <= 0 || choice > 3)
                System.out.println(CSExceptionMessage.NOT_INPUT_OPTION.getMessage());
        } catch (IOException e) {
            throw new InputException(CSExceptionMessage.NOT_INPUT_IO.getMessage());
        } catch (NumberFormatException e1) {
            throw new InputException(CSExceptionMessage.NOT_INPUT_NUMBER.getMessage());
        } catch (Exception e2) {
            throw new InputException(CSExceptionMessage.NOT_INPUT_ERROR.getMessage());
        }

        System.out.println(CSMenuMessage.LINE.getMessage());

        switch (choice) {
            case 1:
                Inquiry inquiry = inquiryInput.memberInquiryDataUpdate(memberIdEx, readChoice);

                boolean update = inquiryDAO.updateInquiryMember(inquiry);

                if (update) System.out.println(CSMenuMessage.INQUIRY_UPDATE_SUCCESS.getMessage());
                else System.out.println(CSMenuMessage.INQUIRY_UPDATE_FAILURE.getMessage());
                break;

            case 2:
                boolean delete = inquiryDAO.deleteInquiryMember(readChoice, memberIdEx);

                if (delete) System.out.println(CSMenuMessage.INQUIRY_DELETE_SUCCESS.getMessage());
                else System.out.println(CSMenuMessage.INQUIRY_DELETE_FAILURE.getMessage());
                break;

            case 3:
                System.out.println(CSMenuMessage.BACK.getMessage());
                break;
        }
    }
}
