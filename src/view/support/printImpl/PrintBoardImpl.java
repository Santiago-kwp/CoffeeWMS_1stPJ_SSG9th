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
            System.out.printf(BoardText.NOTICE_TAP.getMessage(),
                    BoardText.NUMBER.getMessage(),
                    BoardText.NOTICE_DATE.getMessage(),
                    BoardText.TITLE_.getMessage(),
                    BoardText.CONTENT_.getMessage());
            System.out.println(BoardText.LINE.getMessage());

            List<Notice> noticeList = boards.stream().map(board -> (Notice) board).toList();
            for (Notice notice : noticeList) {
                String content = notice.getNoticeContent();
                if (content.length() > 10) content = content.substring(0, 10);
                System.out.printf(BoardText.NOTICE_LIST_TAP.getMessage(),
                        notice.getNoticeId(), notice.getNoticeDate(), notice.getNoticeTitle(), content);
                System.out.println();
            }
        } else if (!boards.isEmpty() && boards.get(0) instanceof Inquiry) {
            System.out.println(BoardText.INQUIRY_READ_ALL.getMessage());
            System.out.printf(BoardText.INQUIRY_TAP.getMessage(),
                    BoardText.NUMBER.getMessage(),
                    BoardText.CREATE_DATE.getMessage(),
                    BoardText.CATEGORY.getMessage(),
                    BoardText.QUEST.getMessage(),
                    BoardText.REPLY_STATUS.getMessage());
            System.out.println(BoardText.LINE.getMessage());

            List<Inquiry> inquiryList = boards.stream().map(board -> (Inquiry) board).toList();
            for (Inquiry inquiry : inquiryList) {
                String content = inquiry.getInquiryContent();
                if (content.length() > 10) content = content.substring(0, 10);
                String status = null;
                switch (inquiry.getInquiryStatus()) {
                    case PENDING -> status = BoardText.REPLY_PENDING.getMessage();
                    case DONE -> status = BoardText.REPLY_DONE.getMessage();
                }
                System.out.printf(BoardText.INQUIRY_LIST_TAP.getMessage(),
                        inquiry.getInquiryId(), inquiry.getInquiryDate(), inquiry.getInquiryCategoryName(), content, status);
                System.out.println();
            }
        } else if (!boards.isEmpty() && boards.get(0) instanceof Faq) {
            System.out.println(BoardText.FAQ_READ_ALL.getMessage());
            System.out.printf(BoardText.FAQ_TAP.getMessage(),
                    BoardText.NUMBER.getMessage(),
                    BoardText.WRITE_DATE.getMessage(),
                    BoardText.CATEGORY.getMessage(),
                    BoardText.QUEST.getMessage(),
                    BoardText.ANSWER.getMessage());
            System.out.println(BoardText.LINE.getMessage());

            List<Faq> faqList = boards.stream().map(board -> (Faq) board).toList();
            for (Faq faq : faqList) {
                String q = faq.getFaqQuestion();
                if (q.length() > 10) q = q.substring(0, 10);
                String r = faq.getFaqReply();
                if (r.length() > 10) r = r.substring(0, 10);
                System.out.printf(BoardText.FAQ_LIST_TAP.getMessage(), faq.getFaqId(), faq.getFaqDate(), faq.getFaqCategoryName(), q, r);
                System.out.println();
            }
        }
    }

    // 상세 출력
    @Override
    public void printOne(Board board) {
        if (board instanceof Notice oneNotice) {
            System.out.printf(BoardText.ONE_NOTICE_TAP.getMessage(),
                    BoardText.CREATE_DATE.getMessage(), oneNotice.getNoticeDate(),
                    BoardText.TITLE_.getMessage(), oneNotice.getNoticeTitle(),
                    BoardText.CONTENT_.getMessage(), oneNotice.getNoticeContent());
        } else if (board instanceof Inquiry oneInquiry) {
            String status;
            switch (oneInquiry.getInquiryStatus()) {
                case PENDING -> {
                    status = BoardText.REPLY_PENDING.getMessage();
                    System.out.printf(BoardText.ONE_INQUIRY_PENDING_TAP.getMessage(),
                            BoardText.CREATE_DATE.getMessage(), oneInquiry.getInquiryDate(),
                            BoardText.CATEGORY.getMessage(), oneInquiry.getInquiryCategoryName(),
                            BoardText.QUEST.getMessage(), oneInquiry.getInquiryContent(),
                            BoardText.REPLY_STATUS.getMessage(), status);
                }
                case DONE -> {
                    status = BoardText.REPLY_DONE.getMessage();
                    System.out.printf(BoardText.ONE_INQUIRY_DONE_TAP.getMessage(),
                            BoardText.CREATE_DATE.getMessage(), oneInquiry.getInquiryDate(),
                            BoardText.CATEGORY.getMessage(), oneInquiry.getInquiryCategoryName(),
                            BoardText.QUEST.getMessage(), oneInquiry.getInquiryContent(),
                            BoardText.REPLY_STATUS.getMessage(), status,
                            BoardText.REPLY_DATE.getMessage(), oneInquiry.getReplyDate(),
                            BoardText.ANSWER.getMessage(), oneInquiry.getReplyContent());
                }
            }
        } else if (board instanceof Faq oneFaq) {
            System.out.printf(BoardText.ONE_FAQ_TAP.getMessage(),
                    BoardText.CREATE_DATE.getMessage(), oneFaq.getFaqDate(),
                    BoardText.CATEGORY.getMessage(), oneFaq.getFaqCategoryName(),
                    BoardText.QUEST.getMessage(), oneFaq.getFaqQuestion(),
                    BoardText.ANSWER.getMessage(), oneFaq.getFaqReply());
        }
    }

    // 메인화면 공지 출력
    @Override
    public void printTopNotices(List<Board> boards) {
        List<Notice> noticeList = boards.stream().map(board -> (Notice) board).toList();
        if (!boards.isEmpty() && boards.get(0) instanceof Notice) {
            for (Notice notice : noticeList) {
                notice.setNoticeDate(notice.getNoticeDate());
                notice.setNoticeTitle(notice.getNoticeTitle());
                System.out.printf(BoardText.TOP_NOTICE_TAP.getMessage(), notice.getNoticeDate(), notice.getNoticeTitle());
                System.out.println();
            }
        }
    }
}
