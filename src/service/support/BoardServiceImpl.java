package service.support;

import domain.support.Board;
import handler.support.input.BoardInput;
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
     * 공지사항 출력
     * */
    @Override
    public void showNoticePreview() {
        List<Board> noticePreviewList = noticeRepository.readNoticePreview();
        printBoard.printTopNotices(noticePreviewList);
    }

    @Override
    public void showAllNotice() {
        List<Board> noticeList = noticeRepository.readNoticeAll();
        printBoard.printAll(noticeList);
    }

    @Override
    public void showOneNotice(Integer noticeId) {
        Board notice = noticeRepository.readNoticeOne(noticeId);
        printBoard.printOne(notice);
    }

    /**
     * 1:1 문의 출력
     * */
    @Override
    public void showAllInquiryManager() {
        List<Board> boardList = inquiryRepository.readInquiryManagerAll();
        printBoard.printAll(boardList);
    }

    @Override
    public void showAllInquiryMember(String memberId) {
        List<Board> boardList = inquiryRepository.readInquiryMemberAll(memberId);
        printBoard.printAll(boardList);
    }

    @Override
    public void showOneInquiryManager(Integer inquiryId) {
        Board board = inquiryRepository.readInquiryManagerOne(inquiryId);
        printBoard.printOne(board);
    }

    @Override
    public void showOneInquiryMember(String memberId, Integer inquiryId) {
        Board board = inquiryRepository.readInquiryMemberOne(memberId, inquiryId);
        printBoard.printOne(board);
    }

    /**
     * FAQ 출력
     * */
    @Override
    public void showAllFaq() {
        List<Board> boardList = faqRepository.readFaqAll();
        printBoard.printAll(boardList);
    }

    @Override
    public void showOneFaq(Integer faqId) {
        Board board = faqRepository.readFaqOne(faqId);
        printBoard.printOne(board);
    }
}
