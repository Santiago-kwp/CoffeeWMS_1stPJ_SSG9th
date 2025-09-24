package model.support.service.input.inputImpl;

import domain.support.Faq;
import model.support.service.dao.daoImpl.FaqDaoImpl;
import model.support.service.input.FaqInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FaqInputImpl implements FaqInput {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    FaqDaoImpl faqDAO = new FaqDaoImpl();

    // FAQ 데이터 입력 -------------------------------------------------------------------------------------------------
    public Faq faqDataInput() throws IOException {
        Faq faq = new Faq();
        faqDAO = new FaqDaoImpl();
        System.out.println("\n[FAQ 생성]");
        line();
        System.out.println("[FAQ 카테고리 목록]");
        faqDAO.readFaqCategory();
        line();
        System.out.println("카테고리 번호를 선택해주세요.");
        System.out.print("> ");
        Integer categoryId = Integer.parseInt(input.readLine());
        faq.setFaqCategoryId(categoryId);

        System.out.println("질문 입력");
        System.out.print("> ");
        String question = input.readLine();
        faq.setFaqQuestion(question);

        System.out.println("답안 입력");
        System.out.print("> ");
        String reply = input.readLine();
        faq.setFaqReply(reply);

        // 매니저 아이디 가져오기 (임시)
        System.out.println("관리자 아이디");
        System.out.print("> ");
        String managerId = input.readLine();
        faq.setFaqManagerId(managerId);

        return faq;
    }

    // FAQ 데이터 수정 -------------------------------------------------------------------------------------------------
    public Faq faqDataUpdate(Integer readChoice) throws IOException {
        Faq faq = new Faq();

        faq.setFaqId(readChoice);

        System.out.println("\n[FAQ 수정]");
        line();
        System.out.println("[FAQ 카테고리 목록]");
        faqDAO.readFaqCategory();
        line();
        System.out.println("카테고리 번호를 선택해주세요.");
        System.out.print("> ");
        Integer categoryId = Integer.parseInt(input.readLine());
        faq.setFaqCategoryId(categoryId);

        System.out.println("질문 입력");
        System.out.print("> ");
        String question = input.readLine();
        faq.setFaqQuestion(question);

        System.out.println("답안 입력");
        System.out.print("> ");
        String reply = input.readLine();
        faq.setFaqReply(reply);

        // 매니저 아이디 가져오기 (임시)
        System.out.println("관리자 아이디");
        System.out.print("> ");
        String managerId = input.readLine();
        faq.setFaqManagerId(managerId);

        return faq;
    }

    public void line() {
        System.out.println("--------------------------------------------------------------------------------");
    }
}
