package service.support.inputService;

import domain.support.Board;
import domain.support.Inquiry;

public interface InquiryInput extends BoardInput{
    @Override
    Board dataInput(String memberId);

    @Override
    Board dataUpdate(Integer readChoice, String memberId);

    Inquiry dataReplyUpdate(Integer readChoice, String managerId);
}