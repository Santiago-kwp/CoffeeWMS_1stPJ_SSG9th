package model.support.service.input;

import domain.support.Faq;

import java.io.IOException;

public interface FaqInput {
    Faq faqDataInput() throws IOException;

    Faq faqDataUpdate(Integer readChoice) throws IOException;
}
