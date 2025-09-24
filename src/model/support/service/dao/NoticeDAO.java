package model.support.service.dao;

import java.util.List;

public interface NoticeDAO {
    boolean createNotice(domain.support.Notice notice);

    List<domain.support.Notice> readNoticeMain();

    List<domain.support.Notice> readNoticeAll();

    domain.support.Notice readNoticeOne(Integer noticeId);

    boolean updateNotice(domain.support.Notice notice);

    boolean deleteNotice(Integer noticeId, String noticeManagerId);
}
