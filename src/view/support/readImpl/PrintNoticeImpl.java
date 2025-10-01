package view.support.readImpl;

import constant.support.BoardErrorCode;
import constant.support.BoardText;
import domain.support.Board;
import domain.support.Notice;
import model.support.dao.NoticeDAO;
import model.support.dao.daoImpl.NoticeDaoImpl;
import view.support.PrintNotice;

import java.util.List;

public class PrintNoticeImpl implements PrintNotice {
    private static final NoticeDAO noticeDAO = new NoticeDaoImpl();

    // 공지사항 전체 출력
    @Override
    public void printAll() {
        System.out.println(BoardText.NOTICE_READ_ALL.getMessage());

        System.out.printf("%-5S\t | %-10S\t | %-35S\t | %-10S\t\n",
                "NO", "날짜", "제목", "내용");

        System.out.println(BoardText.LINE.getMessage());

        List<Notice> readAll = noticeDAO.readNoticeAll();
        for (Notice notice : readAll) {

            String content = notice.getNoticeContent();
            if (content.length() > 10) content = content.substring(0, 10);

            System.out.printf("%-5S\t | %-10S\t | %-30S\t | %-10S\t",
                    notice.getNoticeId(), notice.getNoticeDate(), notice.getNoticeTitle(), content);
            System.out.println();
        }
    }

    // 한 가지 공지사항 출력
    @Override
    public void printOne(Board board) {
        if (board instanceof Notice oneNotice){
            System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                    BoardText.CREATE_DATE.getMessage(), oneNotice.getNoticeDate(),
                    BoardText.TITLE_.getMessage(), oneNotice.getNoticeTitle(),
                    BoardText.CONTENT_.getMessage(), oneNotice.getNoticeContent());
        } else System.out.println(BoardErrorCode.NOT_FOUND_BOARD.getMessage());
    }

    // 메인 화면에서 세 번째 문의사항까지 출력
    public void printTopNotices(List<Notice> noticeListMain) {
        for (Notice notice : noticeListMain) {
            notice.setNoticeDate(notice.getNoticeDate());
            notice.setNoticeTitle(notice.getNoticeTitle());
            System.out.printf("%S %S", notice.getNoticeDate(), notice.getNoticeTitle());
            System.out.println();
        }
    }
}
