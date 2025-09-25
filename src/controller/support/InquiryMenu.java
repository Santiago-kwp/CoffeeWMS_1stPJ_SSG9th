package controller.support;

import constant.support.CSMenuMessage;
import domain.support.Inquiry;
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
    public void managerInquiryMenu(String managerId) throws IOException {
        KI:
        while (true) {
            inquiryRead.managerInquiryReadAll();

            System.out.print(CSMenuMessage.INQUIRY_MENU_SIMPLE.getMessage());

            int choice = 0;
            try {
                choice = Integer.parseInt(input.readLine());
            } catch (IOException e) {
                System.out.println("입력도중 에러 발생");
            } catch (NumberFormatException e1) {
                System.out.println("숫자만 입력");
            } catch (Exception e2) {
                System.out.println("에러 발생");
            }

            switch (choice) {
                case 1:
                    System.out.print(CSMenuMessage.INQUIRY_INSERT_ID.getMessage());

                    int readChoice = Integer.parseInt(input.readLine());

                    System.out.println(CSMenuMessage.LINE.getMessage());

                    System.out.println(CSMenuMessage.INQUIRY_CHOICE.getMessage());

                    String status = null;
                    Inquiry oneInquiry = inquiryDAO.readInquiryManagerOne(readChoice);
                    switch (oneInquiry.getInquiryStatus()) {
                        case PENDING -> {
                            status = "답변 대기";
                            System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                                    "문의날짜", oneInquiry.getInquiryDate(), "카테고리", oneInquiry.getInquiryCategoryName(), "문의", oneInquiry.getInquiryContent(), "답변 상태", status);
                        }
                        case DONE -> {
                            status = "답변 완료";
                            System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                                    "문의날짜", oneInquiry.getInquiryDate(), "카테고리", oneInquiry.getInquiryCategoryName(), "문의", oneInquiry.getInquiryContent(), "답변 상태", status, "답변날짜", oneInquiry.getReplyDate(), "답변", oneInquiry.getReplyContent());
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
    public void memberInquiryMenu(String memberId) throws IOException {
        KI:
        while (true) {

            inquiryRead.memberInquiryReadAll(memberId);

            System.out.print(CSMenuMessage.INQUIRY_MENU.getMessage());

            int choice = 0;
            try {
                choice = Integer.parseInt(input.readLine());
            } catch (IOException e) {
                System.out.println("입력도중 에러 발생");
            } catch (NumberFormatException e1) {
                System.out.println("숫자만 입력");
            } catch (Exception e2) {
                System.out.println("에러 발생");
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

                    int readChoice = Integer.parseInt(input.readLine());

                    System.out.println(CSMenuMessage.LINE.getMessage());

                    System.out.println(CSMenuMessage.INQUIRY_CHOICE.getMessage());

                    String status = null;
                    Inquiry oneInquiry = inquiryDAO.readInquiryMemberOne(memberId, readChoice);
                    switch (oneInquiry.getInquiryStatus()) {
                        case PENDING -> {
                            status = "답변 대기";
                            System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                                    "문의날짜", oneInquiry.getInquiryDate(), "카테고리", oneInquiry.getInquiryCategoryName(), "문의", oneInquiry.getInquiryContent(), "답변 상태", status);
                        }
                        case DONE -> {
                            status = "답변 완료";
                            System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                                    "문의날짜", oneInquiry.getInquiryDate(), "카테고리", oneInquiry.getInquiryCategoryName(), "문의", oneInquiry.getInquiryContent(), "답변 상태", status, "답변날짜", oneInquiry.getReplyDate(), "답변", oneInquiry.getReplyContent());
                        }
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
    public void managerInquiryDetailMenu(Integer readChoice, String managerId) throws IOException {
        System.out.print(CSMenuMessage.INQUIRY_DETAIL_MENU_MANAGER.getMessage());

        int choice = 0;
        try {
            choice = Integer.parseInt(input.readLine());
        } catch (IOException e) {
            System.out.println("입력도중 에러 발생");
        } catch (NumberFormatException e1) {
            System.out.println("숫자만 입력");
        } catch (Exception e2) {
            System.out.println("에러 발생");
        }

        System.out.println(CSMenuMessage.LINE.getMessage());

        switch (choice) {
            case 1:
                Inquiry inquiry = inquiryInput.managerInquiryDataUpdate(readChoice, managerId);

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
    public void memberInquiryDetailMenu(String memberIdEx, Integer readChoice) throws IOException {
        System.out.print(CSMenuMessage.INQUIRY_DETAIL_MENU_MEMBER.getMessage());

        int choice = 0;
        try {
            choice = Integer.parseInt(input.readLine());
        } catch (IOException e) {
            System.out.println("입력도중 에러 발생");
        } catch (NumberFormatException e1) {
            System.out.println("숫자만 입력");
        } catch (Exception e2) {
            System.out.println("에러 발생");
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
