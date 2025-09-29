package service.support.readService;

import domain.support.Inquiry;

public interface InquiryRead {
    // '관리자' 권한을 가진 유저에게 문의 전체 목록을 출력하는 기능
    void managerInquiryReadAll();

    // '회원' 유저 본인이 작성한 문의를 출력하는 기능
    void memberInquiryReadAll(String memberId);

    // 문의 목록 한가지를 출력하는 기능
    void inquiryReadOne(Inquiry oneInquiry);
}