package view.support.readImpl;

import constant.support.BoardErrorCode;
import constant.support.BoardText;
import domain.support.Board;
import domain.support.Inquiry;
import model.support.dao.InquiryDAO;
import model.support.dao.daoImpl.InquiryDaoImpl;
import view.support.PrintInquiry;

import java.util.List;

public class PrintInquiryImpl implements PrintInquiry {
    private static final InquiryDAO inquiryDAO = new InquiryDaoImpl();

    // 1:1 문의 전체 출력
    @Override
    public void printAll() {
        System.out.println(BoardText.INQUIRY_READ_ALL.getMessage());

        System.out.printf("%-5S\t | %-10S\t | %-12S\t | %-15S\t | %-10S\t\n", "NO", "문의날짜", "카테고리", "문의", "답변 상태");

        System.out.println(BoardText.LINE.getMessage());

        List<Inquiry> readAll = inquiryDAO.readInquiryManagerAll();
        for (Inquiry inquiry : readAll) {

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

    // 한 가지 1:1 문의 출력
    @Override
    public void printOne(Board board) {
        if (board instanceof Inquiry oneInquiry){
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
        } else System.out.println(BoardErrorCode.NOT_FOUND_BOARD.getMessage());
    }

    // 로그인한 일반회원 본인이 작성한 전체 1:1 문의 출력
    public void printMemberInquiries(String memberId) {
        System.out.println(BoardText.INQUIRY_READ_ALL.getMessage());

        System.out.printf("%-5S\t | %-10S\t | %-12S\t | %-15S\t | %-10S\t\n", "NO", "문의날짜", "카테고리", "문의", "답변 상태");

        System.out.println(BoardText.LINE.getMessage());

        List<Inquiry> readAll = inquiryDAO.readInquiryMemberAll(memberId);
        for (Inquiry inquiry : readAll) {

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
}