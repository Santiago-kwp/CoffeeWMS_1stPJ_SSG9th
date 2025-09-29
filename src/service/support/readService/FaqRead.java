package service.support.readService;

import domain.support.Faq;

public interface FaqRead {
    // FAQ 목록 전체를 출력하는 기능
    void faqReadAll();

    // FAQ 목록 한 가지를 출력하는 기능
    void faqReadOne(Faq oneFaq);
}