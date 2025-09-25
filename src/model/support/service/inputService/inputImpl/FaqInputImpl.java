package model.support.service.inputService.inputImpl;

import constant.support.CSExceptionMessage;
import constant.support.CSMenuMessage;
import domain.support.Faq;
import domain.support.Category;
import exception.support.InputException;
import exception.support.NotFoundException;
import model.support.service.dao.daoImpl.FaqDaoImpl;
import model.support.service.inputService.FaqInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FaqInputImpl implements FaqInput {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    FaqDaoImpl faqDAO = new FaqDaoImpl();
    List<Category> faqCategoryList = new ArrayList<>();

    // FAQ 데이터 입력 (총관리자)--------------------------------------------------------------------------------------------
    public Faq faqDataInput(String managerId) {
        Faq faq = new Faq();
        faqDAO = new FaqDaoImpl();

        System.out.println(CSMenuMessage.FAQ_CREATE.getMessage());

        boolean check = true;
        Integer categoryId = null;
        while (check) {
            System.out.println(CSMenuMessage.LINE.getMessage());

            System.out.println(CSMenuMessage.FAQ_CATEGORY.getMessage());
            System.out.printf("%-3s\t | %-10s\n", "NO", "목록명");
            faqCategoryList = faqDAO.readFaqCategory();
            for (Category faqCategory : faqCategoryList) {
                System.out.printf("%-3s\t | %-10s\n", faqCategory.getCategoryId(), faqCategory.getCategoryName());
            }

            System.out.println(CSMenuMessage.LINE.getMessage());

            System.out.print(CSMenuMessage.CATEGORY_CHOICE.getMessage());

            try {
                categoryId = Integer.parseInt(input.readLine());
                if (categoryId <= 0 || categoryId > faqCategoryList.size()) {
                    System.out.println(CSExceptionMessage.NOT_INPUT_OPTION.getMessage());
                } else check = false;
            } catch (NumberFormatException e1) {
                throw new InputException(CSExceptionMessage.NOT_INPUT_NUMBER.getMessage());
            } catch (Exception e2) {
                throw new InputException(CSExceptionMessage.NOT_INPUT_ERROR.getMessage());
            }
        }

        faq.setFaqCategoryId(categoryId);

        try {
            System.out.print(CSMenuMessage.QUESTION.getMessage());
            String question = input.readLine();
            faq.setFaqQuestion(question);

            System.out.print(CSMenuMessage.REPLY.getMessage());
            String reply = input.readLine();
            faq.setFaqReply(reply);
        } catch (IOException e) {
            throw new InputException(CSExceptionMessage.NOT_INPUT_IO.getMessage());
        }

        faq.setFaqManagerId(managerId);

        return faq;
    }

    // FAQ 데이터 수정 (총관리자)--------------------------------------------------------------------------------------------
    public Faq faqDataUpdate(Integer readChoice, String managerId) {
        Faq faq = new Faq();

        faq.setFaqId(readChoice);

        System.out.println(CSMenuMessage.FAQ_UPDATE.getMessage());

        boolean check = true;
        Integer categoryId = null;
        while (check) {
            System.out.println(CSMenuMessage.LINE.getMessage());

            System.out.println(CSMenuMessage.FAQ_CATEGORY.getMessage());
            System.out.printf("%-3s\t | %-10s\n", "NO", "목록명");
            faqCategoryList = faqDAO.readFaqCategory();
            for (Category faqCategory : faqCategoryList) {
                System.out.printf("%-3s\t | %-10s\n", faqCategory.getCategoryId(), faqCategory.getCategoryName());
            }

            System.out.println(CSMenuMessage.LINE.getMessage());

            System.out.print(CSMenuMessage.CATEGORY_CHOICE.getMessage());

            try {
                categoryId = Integer.parseInt(input.readLine());
                if (categoryId <= 0 || categoryId > faqCategoryList.size()) {
                    System.out.println(CSExceptionMessage.NOT_INPUT_OPTION.getMessage());
                } else check = false;
            } catch (NumberFormatException e1) {
                throw new InputException(CSExceptionMessage.NOT_INPUT_NUMBER.getMessage());
            } catch (Exception e2) {
                throw new InputException(CSExceptionMessage.NOT_INPUT_ERROR.getMessage());
            }
        }

        faq.setFaqCategoryId(categoryId);

        try {
            System.out.print(CSMenuMessage.QUESTION.getMessage());
            String question = input.readLine();
            faq.setFaqQuestion(question);

            System.out.print(CSMenuMessage.REPLY.getMessage());
            String reply = input.readLine();
            faq.setFaqReply(reply);
        } catch (IOException e) {
            throw new InputException(CSExceptionMessage.NOT_INPUT_IO.getMessage());
        }

        faq.setFaqManagerId(managerId);

        return faq;
    }
}
