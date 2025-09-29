package service.support.readService.readImpl;

import constant.support.BoardText;
import domain.support.Inquiry;
import model.support.dao.InquiryDAO;
import model.support.dao.daoImpl.InquiryDaoImpl;
import service.support.readService.InquiryRead;

import java.util.List;

public class InquiryReadImpl implements InquiryRead {
    InquiryDAO inquiryDAO = new InquiryDaoImpl();

    // 1:1문의 전체 조회 ---------------------------------------------------------------------------------------------------
    public void managerInquiryReadAll() {
        inquiryDAO = new InquiryDaoImpl();
        System.out.println(BoardText.INQUIRY_READ_ALL.getMessage());

        System.out.printf("%-5S\t | %-10S\t | %-12S\t | %-15S\t | %-10S\t\n", "NO", "문의날짜", "카테고리", "문의", "답변 상태");

        System.out.println(BoardText.LINE.getMessage());

        List<Inquiry> readAll = inquiryDAO.readInquiryManagerAll();
        for (Inquiry inquiry : readAll) {

            // 내용을 30글자만 출력
            String content = inquiry.getInquiryContent();
            if (content.length() > 10) content = content.substring(0, 10);

            String status = null;
            switch (inquiry.getInquiryStatus()) {
                case PENDING -> status = "답변 대기";
                case DONE -> status = "답변 완료";
            }
            System.out.printf("%-5S\t | %-10S\t | %-12S\t | %-15S\t | %-10S\t",
                    inquiry.getInquiryId(), inquiry.getInquiryDate(), inquiry.getInquiryCategoryName(), content, status);
            System.out.println();
        }
    }

    // 1:1문의 전체 조회 ---------------------------------------------------------------------------------------------------
    public void memberInquiryReadAll(String memberId) {
        inquiryDAO = new InquiryDaoImpl();
        System.out.println(BoardText.INQUIRY_READ_ALL.getMessage());

        System.out.printf("%-5S\t | %-10S\t | %-12S\t | %-15S\t | %-10S\t\n", "NO", "문의날짜", "카테고리", "문의", "답변 상태");

        System.out.println(BoardText.LINE.getMessage());

        List<Inquiry> readAll = inquiryDAO.readInquiryMemberAll(memberId);
        for (Inquiry inquiry : readAll) {

            // 내용을 30글자만 출력
            String content = inquiry.getInquiryContent();
            if (content.length() > 10) content = content.substring(0, 10);

            String status = null;
            switch (inquiry.getInquiryStatus()) {
                case PENDING -> status = "답변 대기";
                case DONE -> status = "답변 완료";
            }
            System.out.printf("%-5S\t | %-10S\t | %-12S\t | %-15S\t | %-10S\t",
                    inquiry.getInquiryId(), inquiry.getInquiryDate(), inquiry.getInquiryCategoryName(), content, status);
            System.out.println();
        }
    }

    public void inquiryReadOne(Inquiry oneInquiry) {
        String status;
        switch (oneInquiry.getInquiryStatus()) {
            case PENDING -> {
                status = BoardText.REPLY_PENDING.getMessage();
                System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                        BoardText.CREATE_DATE.getMessage(), oneInquiry.getInquiryDate(),
                        BoardText.CATEGORY.getMessage(), oneInquiry.getInquiryCategoryName(),
                        BoardText.QUEST.getMessage(), oneInquiry.getInquiryContent(),
                        BoardText.REPLY_STATUS.getMessage(), status);
            }
            case DONE -> {
                status = BoardText.REPLY_DONE.getMessage();
                System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                        BoardText.CREATE_DATE.getMessage(), oneInquiry.getInquiryDate(),
                        BoardText.CATEGORY.getMessage(), oneInquiry.getInquiryCategoryName(),
                        BoardText.QUEST.getMessage(), oneInquiry.getInquiryContent(),
                        BoardText.REPLY_STATUS.getMessage(), status,
                        BoardText.REPLY_DATE.getMessage(), oneInquiry.getReplyDate(),
                        BoardText.ANSWER.getMessage(), oneInquiry.getReplyContent());
            }
        }
    }
}