package model.support.service.dao;

import java.util.List;

public interface FaqDAO {
    boolean createFaq(domain.support.Faq faq);

    List<domain.support.Faq> readFaqAll();

    domain.support.Faq readFaqOne(Integer faqId);

    boolean updateFaq(domain.support.Faq faq);

    boolean deleteFaq(Integer faqId, String faqManagerId);

    List<domain.support.Faq> readFaqCategory();
}
