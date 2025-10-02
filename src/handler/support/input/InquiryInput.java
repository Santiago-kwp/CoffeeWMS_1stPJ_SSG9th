package handler.support.input;

import domain.support.Board;
import domain.support.Inquiry;

public interface InquiryInput extends BoardInput{
    @Override
    Board dataInput(String memberId);

    @Override
    Board dataUpdate(Integer readChoice, String memberId);

    // 총관리자의 답변 입력(수정)
    Inquiry dataReplyUpdate(Integer readChoice, String managerId);
}