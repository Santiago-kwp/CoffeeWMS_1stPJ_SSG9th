package model.support.service.dao;

import domain.support.Notice;

import java.util.List;

public interface NoticeDAO {
    // 공지사항을 생성하는 기능으로 '총관리자' 권한을 가진 유저만 이 기능을 사용 가능하다.
    boolean createNotice(Notice notice);

    // 메인화면에서 공지사항 전체 목록을 조회하는 기능으로 상단 3가지 목록까지만 조회할 수 있다.
    List<Notice> readNoticeMain();

    // 공지사항 전체 목록을 조회하는 기능이다.
    List<Notice> readNoticeAll();

    // 선택한 공지사항을 상세하게 조회하는 기능이다.
    Notice readNoticeOne(Integer noticeId);

    // 공지사항을 수정하는 기능으로 '총관리자' 권한을 가진 유저만 이 기능을 사용 가능하다.
    boolean updateNotice(Notice notice);

    // 공지사항을 삭제하는 기능으로 '총관리자' 권한을 가진 유저만 이 기능을 사용 가능하다.
    boolean deleteNotice(Integer noticeId, String noticeManagerId);
}
