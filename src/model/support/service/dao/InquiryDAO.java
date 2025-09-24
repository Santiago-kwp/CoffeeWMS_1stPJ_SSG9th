package model.support.service.dao;

import java.util.List;

public interface InquiryDAO {

    boolean createInquiry(domain.support.Inquiry inquiry);

    List<domain.support.Inquiry> readInquiryMemberAll(String inquiryMemberId);

    List<domain.support.Inquiry> readInquiryManagerAll();

    domain.support.Inquiry readInquiryMemberOne(String inquiryMemberId, Integer inquiryId);

    domain.support.Inquiry readInquiryManagerOne(Integer inquiryId);

    boolean updateInquiryMember(domain.support.Inquiry inquiry);

    boolean updateInquiryManager(domain.support.Inquiry inquiry);

    boolean deleteInquiryMember(Integer inquiryId, String inquiryMemberId);

    boolean deleteInquiryManager(Integer inquiryId);

    List<domain.support.Inquiry> readFaqCategory();
}
