package service.support.readService;

import domain.support.Board;
import domain.support.Notice;

import java.util.List;

public interface NoticeRead extends Read {
    @Override
    void readAll();

    @Override
    void readOne(Board board);

    // 메인화면에서 세 번째 목록까지 출력
    void noticeReadAllMain(List<Notice> noticeListMain);
}