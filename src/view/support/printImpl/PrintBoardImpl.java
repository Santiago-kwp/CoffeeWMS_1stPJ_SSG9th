package view.support.printImpl;

import constant.support.BoardText;
import domain.support.Board;
import domain.support.Faq;
import domain.support.Inquiry;
import domain.support.Notice;
import model.support.dao.NoticeRepository;
import model.support.dao.daoImpl.NoticeRepositoryImpl;
import view.support.PrintBoard;

import java.util.List;

public class PrintBoardImpl implements PrintBoard {
    private static final NoticeRepository noticeDAO = new NoticeRepositoryImpl();

    // 전체 출력
    @Override
    public void printAll(List<Board> boards) {
        if (!boards.isEmpty() && boards.get(0) instanceof Notice) {
            System.out.println(BoardText.NOTICE_READ_ALL.getMessage());
            System.out.printf("%-5S\t | %-10S\t | %-35S\t | %-10S\t\n",
                    "NO", "날짜", "제목", "내용");
            System.out.println(BoardText.LINE.getMessage());

            List<Notice> noticeList = boards.stream().map(board -> (Notice) board).toList();
            for (Notice notice : noticeList) {
                String content = notice.getNoticeContent();
                if (content.length() > 10) content = content.substring(0, 10);
                System.out.printf("%-5S\t | %-10S\t | %-30S\t | %-10S\t",
                        notice.getNoticeId(), notice.getNoticeDate(), notice.getNoticeTitle(), content);
                System.out.println();
            }
        } else if (!boards.isEmpty() && boards.get(0) instanceof Inquiry) {
            System.out.println(BoardText.INQUIRY_READ_ALL.getMessage());
            System.out.printf("%-5S\t | %-10S\t | %-12S\t | %-15S\t | %-10S\t\n", "NO", "문의날짜", "카테고리", "문의", "답변 상태");
            System.out.println(BoardText.LINE.getMessage());

            List<Inquiry> inquiryList = boards.stream().map(board -> (Inquiry) board).toList();
            for (Inquiry inquiry : inquiryList) {
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
        } else if (!boards.isEmpty() && boards.get(0) instanceof Faq) {
            System.out.println(BoardText.FAQ_READ_ALL.getMessage());
            System.out.printf("%-5S\t | %-10S\t | %-13S\t | %-18S\t | %-15S\t\n", "NO", "날짜", "카테고리", "질문", "답변");
            System.out.println(BoardText.LINE.getMessage());

            List<Faq> faqList = boards.stream().map(board -> (Faq) board).toList();
            for (Faq faq : faqList) {
                String q = faq.getFaqQuestion();
                if (q.length() > 10) q = q.substring(0, 10);
                String r = faq.getFaqReply();
                if (r.length() > 10) r = r.substring(0, 10);
                System.out.printf("%-5S\t | %-10S\t | %-13S\t | %-15S\t | %-15S\t", faq.getFaqId(), faq.getFaqDate(), faq.getFaqCategoryName(), q, r);
                System.out.println();
            }
        }
    }

    // 상세 출력
    @Override
    public void printOne(Board board) {
        if (board instanceof Notice oneNotice) {
            System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                BoardText.CREATE_DATE.getMessage(), oneNotice.getNoticeDate(),
                BoardText.TITLE_.getMessage(), oneNotice.getNoticeTitle(),
                BoardText.CONTENT_.getMessage(), oneNotice.getNoticeContent());
        } else if (board instanceof Faq oneFaq) {
            System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                BoardText.CREATE_DATE.getMessage(), oneFaq.getFaqDate(),
                BoardText.CATEGORY.getMessage(), oneFaq.getFaqCategoryName(),
                BoardText.QUEST.getMessage(), oneFaq.getFaqQuestion(),
                BoardText.ANSWER.getMessage(), oneFaq.getFaqReply());
        } else if (board instanceof Inquiry oneInquiry){
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

    @Override
    public void printTopNotices(List<Board> boards) {
        List<Notice> noticeList = boards.stream().map(board -> (Notice) board).toList();
        if (!boards.isEmpty() && boards.get(0) instanceof Notice) {
            for (Notice notice : noticeList) {
                notice.setNoticeDate(notice.getNoticeDate());
                notice.setNoticeTitle(notice.getNoticeTitle());
                System.out.printf("%S %S", notice.getNoticeDate(), notice.getNoticeTitle());
                System.out.println();
            }
        }
    }
}
