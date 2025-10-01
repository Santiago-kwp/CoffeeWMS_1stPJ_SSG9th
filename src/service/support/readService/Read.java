package service.support.readService;

import domain.support.Board;

public interface Read {
    // 목록 전체 출력
    void readAll();

    // 한 가지 목록 출력
    void readOne(Board board);
}
