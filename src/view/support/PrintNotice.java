package view.support;

import domain.support.Board;
import domain.support.Notice;

import java.util.List;

public interface PrintNotice extends PrintBoard {
    @Override
    void printAll();

    @Override
    void printOne(Board board);

    // 메인화면에서 세 번째 목록까지 출력
    void printTopNotices(List<Notice> noticeListMain);
}