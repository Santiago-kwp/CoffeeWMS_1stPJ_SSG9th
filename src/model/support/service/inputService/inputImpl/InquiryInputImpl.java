package model.support.service.inputService.inputImpl;

import constant.support.CSMenuMessage;
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

        System.out.println(CSMenuMessage.INQUIRY_CREATE.getMessage());

        System.out.println(CSMenuMessage.LINE.getMessage());

        System.out.println(CSMenuMessage.INQUIRY_CATEGORY.getMessage());
        System.out.printf("%-3s\t | %-10s\n","NO", "목록명");
        inquiryCategoryList = inquiryDAO.readInquiryCategory();
        for (Category inquiryCategory : inquiryCategoryList) {
            System.out.printf("%-3s\t | %-10s\n", inquiryCategory.getCategoryId(), inquiryCategory.getCategoryName());
        }

        System.out.println(CSMenuMessage.LINE.getMessage());

        System.out.print(CSMenuMessage.CATEGORY_CHOICE.getMessage());
        Integer categoryId = Integer.parseInt(input.readLine());
        inquiry.setInquiryCategoryId(categoryId);

        System.out.print(CSMenuMessage.CONTENT.getMessage());
        String content = input.readLine();
        inquiry.setInquiryContent(content);

        inquiry.setInquiryMemberId(memberId);

        return inquiry;
    }

    // 회원 1:1 문의 데이터 수정 (회원)-------------------------------------------------------------------------------------------------
    public Inquiry memberInquiryDataUpdate(String memberId, Integer readChoice) throws IOException {
        Inquiry inquiry = new Inquiry();

        inquiry.setInquiryId(readChoice);

        System.out.println(CSMenuMessage.INQUIRY_UPDATE.getMessage());

        System.out.println(CSMenuMessage.LINE.getMessage());

        System.out.println(CSMenuMessage.INQUIRY_CATEGORY.getMessage());
        System.out.printf("%-3s\t | %-10s\n","NO", "목록명");
        inquiryCategoryList = inquiryDAO.readInquiryCategory();
        for (Category inquiryCategory : inquiryCategoryList) {
            System.out.printf("%-3s\t | %-10s\n", inquiryCategory.getCategoryId(), inquiryCategory.getCategoryName());
        }

        System.out.println(CSMenuMessage.LINE.getMessage());

        System.out.print(CSMenuMessage.CATEGORY_CHOICE.getMessage());
        Integer categoryId = Integer.parseInt(input.readLine());
        inquiry.setInquiryCategoryId(categoryId);

        System.out.print(CSMenuMessage.CONTENT.getMessage());
        String content = input.readLine();
        inquiry.setInquiryContent(content);

        inquiry.setInquiryMemberId(memberId);

        return inquiry;
    }

    // 총관리자 1:1 문의 데이터 수정 (총관리자)----------------------------------------------------------------------------------
    public Inquiry managerInquiryDataUpdate(Integer readChoice, String managerId) throws IOException {
        Inquiry inquiry = new Inquiry();

        inquiry.setInquiryId(readChoice);

        System.out.println(CSMenuMessage.INQUIRY_REPLY.getMessage());

        System.out.print(CSMenuMessage.REPLY.getMessage());
        String content = input.readLine();
        inquiry.setReplyContent(content);

        inquiry.setInquiryManagerId(managerId);

        return inquiry;
    }
}
