package controller.support;

import constant.support.CSMenuMessage;
import domain.support.Faq;
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
    FaqDAO faqDAO = new FaqDaoImpl();
    FaqInput faqInput = new FaqInputImpl();
    FaqRead faqRead = new FaqReadImpl();
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    // 창고관리자 & 회원 <FAQ> 메뉴 ------------------------------------------------------------------------------------------------------
    public void memberFaqMenu() throws IOException {
        KIKI:
        while (true) {
            faqRead.faqReadAll();

            System.out.print(CSMenuMessage.FAQ_MENU_SIMPLE.getMessage());

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
                    System.out.print(CSMenuMessage.FAQ_INSERT_ID.getMessage());

                    int readChoice = Integer.parseInt(input.readLine());

                    System.out.println(CSMenuMessage.LINE.getMessage());

                    System.out.println(CSMenuMessage.FAQ_CHOICE.getMessage());

                    Faq oneFaq = faqDAO.readFaqOne(readChoice);
                    System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                            "작성일", oneFaq.getFaqDate(), "카테고리", oneFaq.getFaqCategoryName(), "질문", oneFaq.getFaqQuestion(), "답변", oneFaq.getFaqReply());
                    break;

                case 2:
                    System.out.println(CSMenuMessage.BACK.getMessage());
                    break KIKI;
            }
        }
    }

    // 총관리자 <FAQ> 메뉴 ------------------------------------------------------------------------------------------------------
    public void managerFaqMenu(String managerId) throws IOException {
        KIKI:
        while (true) {
            faqRead.faqReadAll();

            System.out.print(CSMenuMessage.FAQ_MENU.getMessage());

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
                    Faq faq = faqInput.faqDataInput(managerId);

                    boolean pass = faqDAO.createFaq(faq);

                    if (pass) System.out.println(CSMenuMessage.FAQ_CREATE_SUCCESS.getMessage());
                    else System.out.println(CSMenuMessage.FAQ_CREATE_FAILURE.getMessage());
                    break;

                case 2:
                    System.out.print(CSMenuMessage.FAQ_INSERT_ID.getMessage());

                    int readChoice = Integer.parseInt(input.readLine());

                    System.out.println(CSMenuMessage.LINE.getMessage());

                    System.out.println(CSMenuMessage.FAQ_CHOICE.getMessage());

                    Faq oneFaq = faqDAO.readFaqOne(readChoice);
                    System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n", "작성일", oneFaq.getFaqDate(), "카테고리", oneFaq.getFaqCategoryName(), "질문", oneFaq.getFaqQuestion(), "답변", oneFaq.getFaqReply());
                    faqDetailMenu(readChoice, managerId);
                    break;

                case 3:
                    System.out.println(CSMenuMessage.BACK.getMessage());
                    break KIKI;
            }
        }
    }

    //  총관리자 <FAQ> 상세 메뉴 ---------------------------------------------------------------------------------------------------
    public void faqDetailMenu(Integer readChoice, String managerId) throws IOException {
        System.out.print(CSMenuMessage.FAQ_DETAIL_MENU.getMessage());

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
                Faq faq = faqInput.faqDataUpdate(readChoice, managerId);

                boolean update = faqDAO.updateFaq(faq);

                if (update) System.out.println(CSMenuMessage.FAQ_UPDATE_SUCCESS.getMessage());
                else System.out.println(CSMenuMessage.FAQ_UPDATE_FAILURE.getMessage());
                break;

            case 2:
                boolean delete = faqDAO.deleteFaq(readChoice, managerId);

                if (delete) System.out.println(CSMenuMessage.FAQ_DELETE_SUCCESS.getMessage());
                else System.out.println(CSMenuMessage.FAQ_DELETE_FAILURE.getMessage());
                break;

            case 3:
                System.out.println(CSMenuMessage.BACK.getMessage());
                break;
        }
    }
}
