package model.support.service.input.inputImpl;

import domain.support.Inquiry;
import model.support.service.dao.InquiryDAO;
import model.support.service.dao.daoImpl.InquiryDaoImpl;
import model.support.service.input.InquiryInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InquiryInputImpl implements InquiryInput {
    InquiryDAO inquiryDAO = new InquiryDaoImpl();
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    // 1:1 문의 데이터 입력 -------------------------------------------------------------------------------------------------
    public Inquiry inquiryDataInput() throws IOException {
        Inquiry inquiry = new Inquiry();
        InquiryDaoImpl inquiryDAO = new InquiryDaoImpl();

        System.out.println("\n[1:1 문의 생성]");
        line();
        System.out.println("[문의 카테고리 목록]");
        inquiryDAO.readFaqCategory();
        line();
        System.out.println("카테고리 번호를 선택해주세요.");
        System.out.print("> ");
        Integer categoryId = Integer.parseInt(input.readLine());
        inquiry.setInquiryCategoryId(categoryId);

        System.out.println("내용 입력");
        System.out.print("> ");
        String content = input.readLine();
        inquiry.setInquiryContent(content);

        // 회원 아이디 가져오기 (임시)
        System.out.println("회원 아이디");
        System.out.print("> ");
        String memberId = input.readLine();

        inquiry.setInquiryMemberId(memberId);

        return inquiry;
    }

    // 회원 1:1 문의 데이터 수정 -------------------------------------------------------------------------------------------------
    public Inquiry memberInquiryDataUpdate(Integer readChoice) throws IOException {
        Inquiry inquiry = new Inquiry();

        inquiry.setInquiryId(readChoice);

        System.out.println("\n[1:1 문의 수정]");
        line();
        System.out.println("[문의 카테고리 목록]");
        inquiryDAO.readFaqCategory();
        line();
        System.out.println("카테고리 번호를 선택해주세요.");
        System.out.print("> ");
        Integer categoryId = Integer.parseInt(input.readLine());
        inquiry.setInquiryCategoryId(categoryId);

        System.out.println("내용 입력");
        System.out.print("> ");
        String content = input.readLine();
        inquiry.setInquiryContent(content);

        // 매니저 아이디 가져오기 (임시)
        System.out.println("회원 아이디");
        System.out.print("> ");
        String managerId = input.readLine();
        inquiry.setInquiryManagerId(managerId);

        return inquiry;
    }

    // 총관리자 1:1 문의 데이터 수정 -------------------------------------------------------------------------------------------------
    public Inquiry managerInquiryDataUpdate(Integer readChoice) throws IOException {
        Inquiry inquiry = new Inquiry();

        inquiry.setInquiryId(readChoice);

        System.out.println("\n[1:1 문의 수정]");

        System.out.println("답변 입력");
        System.out.print("> ");
        String content = input.readLine();
        inquiry.setReplyContent(content);

        // 매니저 아이디 가져오기 (임시)
        System.out.println("관리자 아이디");
        System.out.print("> ");
        String managerId = input.readLine();
        inquiry.setInquiryManagerId(managerId);

        return inquiry;
    }

    // 라인 -------------------------------------------------------------------------------------------------------------
    public void line() {
        System.out.println("--------------------------------------------------------------------------------");
    }
}
