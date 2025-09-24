package model.support.service.input;

import domain.support.Inquiry;

import java.io.IOException;

public interface InquiryInput {

    Inquiry inquiryDataInput() throws IOException;

    Inquiry memberInquiryDataUpdate(Integer readChoice) throws IOException;

    Inquiry managerInquiryDataUpdate(Integer readChoice) throws IOException;
}
