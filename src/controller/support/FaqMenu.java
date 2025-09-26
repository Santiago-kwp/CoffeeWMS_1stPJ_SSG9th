package controller.support;

import constant.support.BoardErrorCode;
import constant.support.BoardText;
import constant.support.ValidCheck;
import domain.support.Faq;
import exception.support.InputException;
import exception.support.NotFoundException;
import model.support.service.dao.FaqDAO;
import model.support.service.dao.daoImpl.FaqDaoImpl;
import model.support.service.inputService.FaqInput;
import model.support.service.inputService.inputImpl.FaqInputImpl;
import model.support.service.readService.FaqRead;
import model.support.service.readService.readImpl.FaqReadImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FaqMenu {
    ValidCheck validCheck = new ValidCheck();
    FaqDAO faqDAO = new FaqDaoImpl();
    FaqInput faqInput = new FaqInputImpl();
    FaqRead faqRead = new FaqReadImpl();
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    // 창고관리자 & 회원 <FAQ> 메뉴 ------------------------------------------------------------------------------------------------------
    public void memberFaqMenu() {
        KIKI:
        while (true) {
            faqRead.faqReadAll();

            System.out.print(BoardText.FAQ_MENU_SIMPLE.getMessage());

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
                    System.out.print(BoardText.FAQ_INSERT_ID.getMessage());

                    int readChoice = 0;
                    try {
                        readChoice = Integer.parseInt(input.readLine());
                    } catch (IOException e) {
                        System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
                        memberFaqMenu();
                    } catch (NumberFormatException e) {
                        System.out.println(BoardErrorCode.NOT_INPUT_NUMBER.getMessage());
                        memberFaqMenu();
                    }

                    System.out.println(BoardText.LINE.getMessage());

                    System.out.println(BoardText.FAQ_CHOICE.getMessage());

                    Faq oneFaq = faqDAO.readFaqOne(readChoice);

                    try {
                        validCheck.isValidNotFoundFaq(oneFaq);
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                            BoardText.CREATE_DATE.getMessage(), oneFaq.getFaqDate(),
                            BoardText.CATEGORY.getMessage(), oneFaq.getFaqCategoryName(),
                            BoardText.QUEST.getMessage(), oneFaq.getFaqQuestion(),
                            BoardText.ANSWER.getMessage(), oneFaq.getFaqReply());
                    break;

                case "2":
                    System.out.println(BoardText.BACK.getMessage());
                    break KIKI;
            }
        }
    }

    // 총관리자 <FAQ> 메뉴 ------------------------------------------------------------------------------------------------------
    public void managerFaqMenu(String managerId) {
        KIKI:
        while (true) {
            faqRead.faqReadAll();

            System.out.print(BoardText.FAQ_MENU.getMessage());

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
                    Faq faq = faqInput.faqDataInput(managerId);

                    boolean pass = faqDAO.createFaq(faq);

                    if (pass) System.out.println(BoardText.FAQ_CREATE_SUCCESS.getMessage());
                    else System.out.println(BoardText.FAQ_CREATE_FAILURE.getMessage());
                    break;

                case "2":
                    System.out.print(BoardText.FAQ_INSERT_ID.getMessage());

                    int readChoice = 0;
                    try {
                        readChoice = Integer.parseInt(input.readLine());
                    } catch (IOException e) {
                        System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
                        managerFaqMenu(managerId);
                    } catch (NumberFormatException e) {
                        System.out.println(BoardErrorCode.NOT_INPUT_NUMBER.getMessage());
                        managerFaqMenu(managerId);
                    }

                    System.out.println(BoardText.LINE.getMessage());

                    System.out.println(BoardText.FAQ_CHOICE.getMessage());

                    Faq oneFaq = faqDAO.readFaqOne(readChoice);

                    try {
                        validCheck.isValidNotFoundFaq(oneFaq);
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                            BoardText.CREATE_DATE.getMessage(), oneFaq.getFaqDate(),
                            BoardText.CATEGORY.getMessage(), oneFaq.getFaqCategoryName(),
                            BoardText.QUEST.getMessage(), oneFaq.getFaqQuestion(),
                            BoardText.ANSWER.getMessage(), oneFaq.getFaqReply());
                    faqDetailMenu(readChoice, managerId);
                    break;

                case "3":
                    System.out.println(BoardText.BACK.getMessage());
                    break KIKI;
            }
        }
    }

    //  총관리자 <FAQ> 상세 메뉴 ---------------------------------------------------------------------------------------------------
    public void faqDetailMenu(Integer readChoice, String managerId) {
        System.out.print(BoardText.FAQ_DETAIL_MENU.getMessage());

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
                Faq faq = faqInput.faqDataUpdate(readChoice, managerId);

                boolean update = faqDAO.updateFaq(faq);

                if (update) System.out.println(BoardText.FAQ_UPDATE_SUCCESS.getMessage());
                else System.out.println(BoardText.FAQ_UPDATE_FAILURE.getMessage());
                break;

            case "2":
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
