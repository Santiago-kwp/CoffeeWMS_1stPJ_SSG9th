package model.support.service.inputService.inputImpl;

import domain.support.Category;
import domain.support.Inquiry;
import model.support.service.dao.InquiryDAO;
import model.support.service.dao.daoImpl.InquiryDaoImpl;
import model.support.service.inputService.InquiryInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class InquiryInputImpl implements InquiryInput {
    InquiryDAO inquiryDAO = new InquiryDaoImpl();
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    List<Category> inquiryCategoryList = new ArrayList<>();

    // 1:1 문의 데이터 입력 (회원)-------------------------------------------------------------------------------------------
    public Inquiry inquiryDataInput(String memberId) throws IOException {
        Inquiry inquiry = new Inquiry();
        InquiryDaoImpl inquiryDAO = new InquiryDaoImpl();

        System.out.println("\n[1:1 문의 생성]");
        line();
        System.out.println("[문의 카테고리 목록]");
        System.out.printf("%-3s\t | %-10s\n","NO", "목록명");
        inquiryCategoryList = inquiryDAO.readInquiryCategory();
        for (Category inquiryCategory : inquiryCategoryList) {
            System.out.printf("%-3s\t | %-10s\n", inquiryCategory.getCategoryId(), inquiryCategory.getCategoryName());
        }
        line();
        System.out.println("카테고리 번호를 선택해주세요.");
        System.out.print("> ");
        Integer categoryId = Integer.parseInt(input.readLine());
        inquiry.setInquiryCategoryId(categoryId);

        System.out.println("내용 입력");
        System.out.print("> ");
        String content = input.readLine();
        inquiry.setInquiryContent(content);

        inquiry.setInquiryMemberId(memberId);

        return inquiry;
    }

    // 회원 1:1 문의 데이터 수정 (회원)-------------------------------------------------------------------------------------------------
    public Inquiry memberInquiryDataUpdate(String memberId, Integer readChoice) throws IOException {
        Inquiry inquiry = new Inquiry();

        inquiry.setInquiryId(readChoice);

        System.out.println("\n[1:1 문의 수정]");
        line();
        System.out.println("[문의 카테고리 목록]");
        System.out.printf("%-3s\t | %-10s\n","NO", "목록명");
        inquiryCategoryList = inquiryDAO.readInquiryCategory();
        for (Category inquiryCategory : inquiryCategoryList) {
            System.out.printf("%-3s\t | %-10s\n", inquiryCategory.getCategoryId(), inquiryCategory.getCategoryName());
        }
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

    // 총관리자 1:1 문의 데이터 수정 (총관리자)----------------------------------------------------------------------------------
    public Inquiry managerInquiryDataUpdate(Integer readChoice, String managerId) throws IOException {
        Inquiry inquiry = new Inquiry();

        inquiry.setInquiryId(readChoice);

        System.out.println("\n[1:1 문의 수정]");

        System.out.println("답변 입력");
        System.out.print("> ");
        String content = input.readLine();
        inquiry.setReplyContent(content);

        inquiry.setInquiryManagerId(managerId);

        return inquiry;
    }

    // 라인 -------------------------------------------------------------------------------------------------------------
    public void line() {
        System.out.println("--------------------------------------------------------------------------------");
    }
}
