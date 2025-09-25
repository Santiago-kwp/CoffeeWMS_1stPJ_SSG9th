package model.support.service.inputService;

import domain.support.Inquiry;

import java.io.IOException;

public interface InquiryInput {
    // '회원' 권한을 가진 유저에게 1:1 문의 데이터를 입력받는 기능이다.
    Inquiry inquiryDataInput(String memberId) throws IOException;

    // '회원' 권한을 가진 유저에게 수정할 1:1 문의 데이터를 입력받는 기능이다.
    Inquiry memberInquiryDataUpdate(String memberId, Integer readChoice) throws IOException;

    // '총관리자' 권한을 가진 유저에게 답변할 1:1 문의 데이터를 입력받는 기능이다.
    Inquiry managerInquiryDataUpdate(Integer readChoice, String managerId) throws IOException;
}
