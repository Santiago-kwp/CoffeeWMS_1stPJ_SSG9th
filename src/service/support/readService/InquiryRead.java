package service.support.readService;

import domain.support.Inquiry;

public interface InquiryRead {

    void managerInquiryReadAll();

    void memberInquiryReadAll(String memberId);

    void inquiryReadOne(Inquiry oneInquiry);
}
