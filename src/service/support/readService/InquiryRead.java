package service.support.readService;

import domain.support.Board;
import domain.support.Inquiry;

public interface InquiryRead extends Read {
    @Override
    void readAll();

    @Override
    void readOne(Board board);

    // 일반회원 본인이 작성한 목록 출력
    void memberInquiryReadAll(String memberId);
}