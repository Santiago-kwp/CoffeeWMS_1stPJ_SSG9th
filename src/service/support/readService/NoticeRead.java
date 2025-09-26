package service.support.readService;

import domain.support.Notice;

public interface NoticeRead {
    void noticeReadAll();

    void noticeReadOne(Notice oneNotice);
}
