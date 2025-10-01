package service.support.input.inputImpl;

import constant.support.BoardErrorCode;
import constant.support.BoardText;
import constant.support.ValidCheck;
import domain.support.Board;
import domain.support.Category;
import domain.support.Inquiry;
import exception.support.InputException;
import model.support.dao.InquiryDAO;
import model.support.dao.daoImpl.InquiryDaoImpl;
import service.support.input.InquiryInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class InquiryInputImpl implements InquiryInput {
    private static final InquiryDAO inquiryDAO = new InquiryDaoImpl();
    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    // 1:1 문의 데이터 입력
    @Override
    public Board dataInput(String memberId) {
        Inquiry inquiry = new Inquiry();
        InquiryDaoImpl inquiryDAO = new InquiryDaoImpl();

        System.out.println(BoardText.INQUIRY_CREATE.getMessage());

        boolean check = true;
        String categoryId = null;
        while (check) {
            System.out.println(BoardText.LINE.getMessage());

            System.out.println(BoardText.INQUIRY_CATEGORY.getMessage());
            System.out.printf("%-3s\t | %-10s\n", BoardText.NUMBER.getMessage(), BoardText.LIST_NAME.getMessage());
            List<Category> inquiryCategoryList = inquiryDAO.readInquiryCategory();
            int boardNumber = inquiryCategoryList.size();
            for (Category inquiryCategory : inquiryCategoryList) {
                System.out.printf("%-3s\t | %-10s\n", inquiryCategory.getCategoryId(), inquiryCategory.getCategoryName());
            }

            System.out.println(BoardText.LINE.getMessage());

            System.out.print(BoardText.CATEGORY_CHOICE.getMessage());

            try {
                categoryId = input.readLine();
                ValidCheck.isValidBoardNumber(categoryId, boardNumber);
                break;
            } catch (InputException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
            } catch (NumberFormatException e) {
                System.out.println(BoardErrorCode.NOT_INPUT_NUMBER.getMessage());
            }
        }
        inquiry.setInquiryCategoryId(Integer.parseInt(categoryId));

        System.out.print(BoardText.CONTENT.getMessage());
        String content = null;
        try {
            content = input.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        inquiry.setInquiryContent(content);

        inquiry.setInquiryMemberId(memberId);

        return inquiry;
    }

    // 1:1 문의 데이터 수정
    @Override
    public Board dataUpdate(Integer readChoice, String memberId) {
        Inquiry inquiry = new Inquiry();

        inquiry.setInquiryId(readChoice);

        System.out.println(BoardText.INQUIRY_UPDATE.getMessage());

        boolean check = true;
        String categoryId = null;
        while (check) {
            System.out.println(BoardText.LINE.getMessage());

            System.out.println(BoardText.INQUIRY_CATEGORY.getMessage());
            System.out.printf("%-3s\t | %-10s\n", "NO", "목록명");
            List<Category> inquiryCategoryList = inquiryDAO.readInquiryCategory();
            int boardNumber = inquiryCategoryList.size();
            for (Category inquiryCategory : inquiryCategoryList) {
                System.out.printf("%-3s\t | %-10s\n", inquiryCategory.getCategoryId(), inquiryCategory.getCategoryName());
            }

            System.out.println(BoardText.LINE.getMessage());

            System.out.print(BoardText.CATEGORY_CHOICE.getMessage());

            try {
                categoryId = input.readLine();
                ValidCheck.isValidBoardNumber(categoryId, boardNumber);
                check = false;
            } catch (InputException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
            } catch (NumberFormatException e) {
                System.out.println(BoardErrorCode.NOT_INPUT_NUMBER.getMessage());
            }
        }
        inquiry.setInquiryCategoryId(Integer.parseInt(categoryId));

        System.out.print(BoardText.CONTENT.getMessage());

        String content = null;
        try {
            content = input.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        inquiry.setInquiryContent(content);

        inquiry.setInquiryMemberId(memberId);

        return inquiry;
    }

    // 총관리자의 1:1 문의 답변 입력(수정)
    @Override
    public Inquiry dataReplyUpdate(Integer readChoice, String managerId) {
        Inquiry inquiry = new Inquiry();

        inquiry.setInquiryId(readChoice);

        System.out.println(BoardText.INQUIRY_REPLY.getMessage());

        System.out.print(BoardText.REPLY.getMessage());

        String content = null;
        try {
            content = input.readLine();
        } catch (IOException e) {
            System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
        }
        inquiry.setReplyContent(content);

        inquiry.setInquiryManagerId(managerId);

        return inquiry;
    }
}
