package service.support;

import constant.support.BoardText;
import constant.support.ValidCheck;
import domain.support.Board;
import domain.support.Faq;
import domain.support.Inquiry;
import domain.support.Notice;
import model.support.dao.FaqRepository;
import model.support.dao.InquiryRepository;
import model.support.dao.NoticeRepository;
import model.support.dao.daoImpl.FaqRepositoryImpl;
import model.support.dao.daoImpl.InquiryRepositoryImpl;
import model.support.dao.daoImpl.NoticeRepositoryImpl;
import view.support.PrintBoard;
import view.support.printImpl.PrintBoardImpl;

import java.util.List;

public class BoardServiceImpl implements BoardService {
    private final NoticeRepository noticeRepository;
    private final FaqRepository faqRepository;
    private final InquiryRepository inquiryRepository;
    private final PrintBoard printBoard;

    public BoardServiceImpl() {
        this.noticeRepository = new NoticeRepositoryImpl();
        this.faqRepository = new FaqRepositoryImpl();
        this.inquiryRepository = new InquiryRepositoryImpl();
        this.printBoard = new PrintBoardImpl();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 입력
     */
    @Override
    public void createNotice(Board board) {
        if (board instanceof Notice notice) {
            boolean pass = noticeRepository.createNotice(notice);
            ValidCheck.validateCreatable(pass);
            System.out.println(BoardText.NOTICE_CREATE_SUCCESS.getMessage());
        }
    }

    @Override
    public void createInquiry(Board board) {
        if (board instanceof Inquiry inquiry) {
            boolean pass = inquiryRepository.createInquiry(inquiry);
            ValidCheck.validateCreatable(pass);
            System.out.println(BoardText.INQUIRY_CREATE_SUCCESS.getMessage());
        }
    }

    @Override
    public void createFaq(Board board) {
        if (board instanceof Faq faq) {
            boolean pass = faqRepository.createFaq(faq);
            ValidCheck.validateCreatable(pass);
            System.out.println(BoardText.FAQ_CREATE_SUCCESS.getMessage());
        }
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 수정
     */
    @Override
    public void updateNotice(Board board) {
        if (board instanceof Notice notice) {
            boolean pass = noticeRepository.updateNotice(notice);
            ValidCheck.validateCreatable(pass);
            System.out.println(BoardText.NOTICE_UPDATE_SUCCESS.getMessage());
        }
    }

    @Override
    public void updateInquiryMember(Board board) {
        if (board instanceof Inquiry inquiry) {
            boolean pass = inquiryRepository.updateInquiryMember(inquiry);
            ValidCheck.validateCreatable(pass);
            System.out.println(BoardText.INQUIRY_UPDATE_SUCCESS.getMessage());
        }
    }

    @Override
    public void updateInquiryManager(Board board) {
        if (board instanceof Inquiry inquiry) {
            boolean pass = inquiryRepository.updateInquiryManager(inquiry);
            ValidCheck.validateCreatable(pass);
            System.out.println(BoardText.INQUIRY_REPLY_SUCCESS.getMessage());
        }
    }

    @Override
    public void updateFaq(Board board) {
        if (board instanceof Faq faq) {
            boolean pass = faqRepository.updateFaq(faq);
            ValidCheck.validateCreatable(pass);
            System.out.println(BoardText.FAQ_UPDATE_SUCCESS.getMessage());
        }
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 삭제
     */
    @Override
    public void deleteNotice(Integer noticeId, String managerId) {
        boolean pass = noticeRepository.deleteNotice(noticeId, managerId);
        ValidCheck.validateCreatable(pass);
        System.out.println(BoardText.NOTICE_DELETE_SUCCESS.getMessage());
    }

    @Override
    public void deleteInquiryMember(Integer inquiryId, String memberId) {
        boolean pass = inquiryRepository.deleteInquiryMember(inquiryId, memberId);
        ValidCheck.validateCreatable(pass);
        System.out.println(BoardText.INQUIRY_DELETE_SUCCESS.getMessage());
    }

    @Override
    public void deleteInquiryManager(Integer inquiryId) {
        boolean pass = inquiryRepository.deleteInquiryManager(inquiryId);
        ValidCheck.validateCreatable(pass);
        System.out.println(BoardText.INQUIRY_DELETE_SUCCESS.getMessage());
    }

    @Override
    public void deleteFaq(Integer faqId, String managerId) {
        boolean pass = faqRepository.deleteFaq(faqId, managerId);
        ValidCheck.validateCreatable(pass);
        System.out.println(BoardText.FAQ_DELETE_SUCCESS.getMessage());
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 출력
     */
    @Override
    public void showNoticePreview() {
        List<Board> noticePreviewList = noticeRepository.readNoticePreview();
        ValidCheck.isValidNotFoundList(noticePreviewList);
        printBoard.printTopNotices(noticePreviewList);
    }

    @Override
    public void showAllNotice() {
        List<Board> noticeList = noticeRepository.readNoticeAll();
        ValidCheck.isValidNotFoundList(noticeList);
        printBoard.printAll(noticeList);
    }

    @Override
    public void showOneNotice(Integer noticeId) {
        Board notice = noticeRepository.readNoticeOne(noticeId);
        ValidCheck.isValidNotFoundBoard(notice);
        printBoard.printOne(notice);
    }

    @Override
    public void showAllInquiryManager() {
        List<Board> boardList = inquiryRepository.readInquiryManagerAll();
        ValidCheck.isValidNotFoundList(boardList);
        printBoard.printAll(boardList);
    }

    @Override
    public void showAllInquiryMember(String memberId) {
        List<Board> boardList = inquiryRepository.readInquiryMemberAll(memberId);
        ValidCheck.isValidNotFoundList(boardList);
        printBoard.printAll(boardList);
    }

    @Override
    public void showOneInquiryManager(Integer inquiryId) {
        Board inquiry = inquiryRepository.readInquiryManagerOne(inquiryId);
        ValidCheck.isValidNotFoundBoard(inquiry);
        printBoard.printOne(inquiry);
    }

    @Override
    public void showOneInquiryMember(String memberId, Integer inquiryId) {
        Board inquiry = inquiryRepository.readInquiryMemberOne(memberId, inquiryId);
        ValidCheck.isValidNotFoundBoard(inquiry);
        printBoard.printOne(inquiry);
    }

    @Override
    public void showAllFaq() {
        List<Board> boardList = faqRepository.readFaqAll();
        ValidCheck.isValidNotFoundList(boardList);
        printBoard.printAll(boardList);
    }

    @Override
    public void showOneFaq(Integer faqId) {
        Board faq = faqRepository.readFaqOne(faqId);
        ValidCheck.isValidNotFoundBoard(faq);
        printBoard.printOne(faq);
    }
}
