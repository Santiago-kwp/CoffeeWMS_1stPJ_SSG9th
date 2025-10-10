package view.support;

import domain.support.Board;
import java.util.List;

public interface PrintBoard {
    // 목록 전체 출력
    void printAll(List<Board> boards);

    // 목록 상세 출력
    void printOne(Board board);

    // 메인화면 공지 출력
    void printTopNotices(List<Board> boards);
}
