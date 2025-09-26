package service.support.readService.readImpl;

import constant.support.BoardText;
import domain.support.Notice;
import model.support.dao.NoticeDAO;
import model.support.dao.daoImpl.NoticeDaoImpl;
import service.support.readService.NoticeRead;

import java.util.List;

public class NoticeReadImpl implements NoticeRead {
    NoticeDAO noticeDAO = new NoticeDaoImpl();

    // 공지사항 전체 출력 ---------------------------------------------------------------------------------------------------
    public void noticeReadAll() {
        noticeDAO = new NoticeDaoImpl();
        System.out.println(BoardText.NOTICE_READ_ALL.getMessage());

        System.out.printf("%-5S\t | %-10S\t | %-35S\t | %-10S\t\n", "NO", "날짜", "제목", "내용");

        System.out.println(BoardText.LINE.getMessage());

        List<Notice> readAll = noticeDAO.readNoticeAll();
        for (Notice notice : readAll) {

            // 내용을 30글자만 출력
            String content = notice.getNoticeContent();
            if (content.length() > 10) content = content.substring(0, 10);

            System.out.printf("%-5S\t | %-10S\t | %-30S\t | %-10S\t", notice.getNoticeId(), notice.getNoticeDate(), notice.getNoticeTitle(), content);
            System.out.println();
        }
    }

    public void noticeReadOne(Notice oneNotice) {
        System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                BoardText.CREATE_DATE.getMessage(), oneNotice.getNoticeDate(),
                BoardText.TITLE_.getMessage(), oneNotice.getNoticeTitle(),
                BoardText.CONTENT_.getMessage(), oneNotice.getNoticeContent());
    }
}
