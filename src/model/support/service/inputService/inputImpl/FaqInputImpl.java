package model.support.service.inputService.inputImpl;

import constant.support.CSMenuMessage;
import domain.support.Faq;
import domain.support.Category;
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
    public Faq faqDataInput(String managerId) throws IOException {
        Faq faq = new Faq();
        faqDAO = new FaqDaoImpl();

        System.out.println(CSMenuMessage.FAQ_CREATE.getMessage());

        System.out.println(CSMenuMessage.LINE.getMessage());

        System.out.println(CSMenuMessage.FAQ_CATEGORY.getMessage());
        System.out.printf("%-3s\t | %-10s\n","NO", "목록명");
        faqCategoryList = faqDAO.readFaqCategory();
        for (Category faqCategory : faqCategoryList) {
            System.out.printf("%-3s\t | %-10s\n", faqCategory.getCategoryId(), faqCategory.getCategoryName());
        }

        System.out.println(CSMenuMessage.LINE.getMessage());

        System.out.print(CSMenuMessage.CATEGORY_CHOICE.getMessage());
        Integer categoryId = Integer.parseInt(input.readLine());
        faq.setFaqCategoryId(categoryId);

        System.out.print(CSMenuMessage.QUESTION.getMessage());
        String question = input.readLine();
        faq.setFaqQuestion(question);

        System.out.print(CSMenuMessage.REPLY.getMessage());
        String reply = input.readLine();
        faq.setFaqReply(reply);

        faq.setFaqManagerId(managerId);

        return faq;
    }

    // FAQ 데이터 수정 (총관리자)--------------------------------------------------------------------------------------------
    public Faq faqDataUpdate(Integer readChoice, String managerId) throws IOException {
        Faq faq = new Faq();

        faq.setFaqId(readChoice);

        System.out.println(CSMenuMessage.FAQ_UPDATE.getMessage());

        System.out.println(CSMenuMessage.LINE.getMessage());

        System.out.println(CSMenuMessage.FAQ_CATEGORY.getMessage());
        System.out.printf("%-3s\t | %-10s\n","NO", "목록명");
        faqCategoryList = faqDAO.readFaqCategory();
        for (Category faqCategory : faqCategoryList) {
            System.out.printf("%-3s\t | %-10s\n", faqCategory.getCategoryId(), faqCategory.getCategoryName());
        }

        System.out.println(CSMenuMessage.LINE.getMessage());

        System.out.print(CSMenuMessage.CATEGORY_CHOICE.getMessage());
        Integer categoryId = Integer.parseInt(input.readLine());
        faq.setFaqCategoryId(categoryId);

        System.out.print(CSMenuMessage.QUESTION.getMessage());
        String question = input.readLine();
        faq.setFaqQuestion(question);

        System.out.print(CSMenuMessage.REPLY.getMessage());
        String reply = input.readLine();
        faq.setFaqReply(reply);

        faq.setFaqManagerId(managerId);

        return faq;
    }
}
