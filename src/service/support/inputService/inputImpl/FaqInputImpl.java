package service.support.inputService.inputImpl;

import constant.support.BoardErrorCode;
import constant.support.BoardText;
import constant.support.ValidCheck;
import domain.support.Board;
import domain.support.Faq;
import domain.support.Category;
import exception.support.InputException;
import model.support.dao.daoImpl.FaqDaoImpl;
import service.support.inputService.BoardInput;
//import service.support.inputService.FaqInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FaqInputImpl implements BoardInput {
    ValidCheck validCheck = new ValidCheck();
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    FaqDaoImpl faqDAO = new FaqDaoImpl();
    List<Category> faqCategoryList = new ArrayList<>();

    // FAQ 데이터 입력
    @Override
    public Board dataInput(String managerId) {
        Faq faq = new Faq();
        faqDAO = new FaqDaoImpl();

        System.out.println(BoardText.FAQ_CREATE.getMessage());

        boolean check = true;
        String categoryId = null;
        while (check) {
            System.out.println(BoardText.LINE.getMessage());

            System.out.println(BoardText.FAQ_CATEGORY.getMessage());
            System.out.printf("%-3s\t | %-10s\n", "NO", "목록명");
            faqCategoryList = faqDAO.readFaqCategory();
            int boardNumber = faqCategoryList.size();
            for (Category faqCategory : faqCategoryList) {
                System.out.printf("%-3s\t | %-10s\n", faqCategory.getCategoryId(), faqCategory.getCategoryName());
            }

            System.out.println(BoardText.LINE.getMessage());

            System.out.print(BoardText.CATEGORY_CHOICE.getMessage());

            try {
                categoryId = input.readLine();
                validCheck.isValidBoardNumber(categoryId, boardNumber);
                check = false;
            } catch (InputException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
            } catch (NumberFormatException e) {
                System.out.println(BoardErrorCode.NOT_INPUT_NUMBER.getMessage());
            }
        }
        faq.setFaqCategoryId(Integer.parseInt(categoryId));

        try {
            System.out.print(BoardText.QUESTION.getMessage());
            String question = input.readLine();
            faq.setFaqQuestion(question);

            System.out.print(BoardText.REPLY.getMessage());
            String reply = input.readLine();
            faq.setFaqReply(reply);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        faq.setFaqManagerId(managerId);

        return faq;
    }

    // FAQ 데이터 수정
    @Override
    public Board dataUpdate(Integer readChoice, String managerId) {
        Faq faq = new Faq();

        faq.setFaqId(readChoice);

        System.out.println(BoardText.FAQ_UPDATE.getMessage());

        boolean check = true;
        String categoryId = null;
        while (check) {
            System.out.println(BoardText.LINE.getMessage());

            System.out.println(BoardText.FAQ_CATEGORY.getMessage());
            System.out.printf("%-3s\t | %-10s\n", "NO", "목록명");
            faqCategoryList = faqDAO.readFaqCategory();
            int boardNumber = faqCategoryList.size();
            for (Category faqCategory : faqCategoryList) {
                System.out.printf("%-3s\t | %-10s\n", faqCategory.getCategoryId(), faqCategory.getCategoryName());
            }

            System.out.println(BoardText.LINE.getMessage());

            System.out.print(BoardText.CATEGORY_CHOICE.getMessage());

            try {
                categoryId = input.readLine();
                validCheck.isValidBoardNumber(categoryId, boardNumber);
                check = false;
            } catch (InputException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
            } catch (NumberFormatException e) {
                System.out.println(BoardErrorCode.NOT_INPUT_NUMBER.getMessage());
            }
        }
        faq.setFaqCategoryId(Integer.parseInt(categoryId));

        try {
            System.out.print(BoardText.QUESTION.getMessage());
            String question = input.readLine();
            faq.setFaqQuestion(question);

            System.out.print(BoardText.REPLY.getMessage());
            String reply = input.readLine();
            faq.setFaqReply(reply);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        faq.setFaqManagerId(managerId);

        return faq;
    }
}
