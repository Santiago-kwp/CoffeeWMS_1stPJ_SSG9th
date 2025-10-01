package view.support;

import domain.support.Board;

public interface PrintBoard {
    // 목록 전체 출력
    void printAll();

    // 한 가지 목록 출력
    void printOne(Board board);
}
