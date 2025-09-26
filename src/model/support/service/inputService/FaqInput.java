package model.support.service.inputService;

import domain.support.Faq;

public interface FaqInput {
    // '총관리자' 권한을 가진 유저에게 FAQ 데이터를 입력받는 기능이다.
    Faq faqDataInput(String managerId);

    // '총관리자' 권한을 가진 유저에게 수정할 FAQ 데이터를 입력받는 기능이다.
    Faq faqDataUpdate(Integer readChoice, String managerId);
}
