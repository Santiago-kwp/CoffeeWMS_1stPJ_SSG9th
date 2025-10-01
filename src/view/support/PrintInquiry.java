package view.support;

import domain.support.Board;

public interface PrintInquiry extends PrintBoard {
    @Override
    void printAll();

    @Override
    void printOne(Board board);

    // 일반회원 본인이 작성한 목록 출력
    void printMemberInquiries(String memberId);
}