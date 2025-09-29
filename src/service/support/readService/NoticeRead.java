package service.support.readService;

import domain.support.Notice;

import java.util.List;

public interface NoticeRead {
    void noticeReadAll();

    void noticeReadOne(Notice oneNotice);

    void noticeReadAllMain(List<Notice> noticeListMain);
}
