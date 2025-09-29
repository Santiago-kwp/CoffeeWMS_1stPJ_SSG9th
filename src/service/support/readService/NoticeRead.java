package service.support.readService;

import domain.support.Notice;

import java.util.List;

public interface NoticeRead {
    // 공지사항 전체 목록을 출력하는 기능
    void noticeReadAll();

    // 공지사항 한 가지 목록을 출력하는 기능
    void noticeReadOne(Notice oneNotice);

    // 메인화면에서 공지사항 상단 3번째 목록까지 출력하는 기능
    void noticeReadAllMain(List<Notice> noticeListMain);
}