package service.support.readService;

import domain.support.Faq;

public interface FaqRead {

    void faqReadAll();

    void faqReadOne(Faq oneFaq);
}
