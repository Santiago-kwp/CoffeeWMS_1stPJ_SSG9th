package model.support.service.dao;

import domain.support.Category;

import java.util.List;

public interface FaqDAO {
    // FAQ 생성하는 기능으로 '총관리자' 권한을 가진 유저만 이 기능을 사용할 수 있다.
    boolean createFaq(domain.support.Faq faq);

    // FAQ 전체 데이터를 조회하는 기능이다.
    List<domain.support.Faq> readFaqAll();

    // FAQ id를 통해 한가지 데이터를 선택하여 조회하는 기능이다.
    domain.support.Faq readFaqOne(Integer faqId);

    // FAQ 수정하는 기능으로 '총관리자' 권한을 가진 유저만 이 기능을 사용할 수 있다.
    boolean updateFaq(domain.support.Faq faq);

    // FAQ 삭제하는 기능으로 '총관리자' 권한을 가진 유저만 이 기능을 사용할 수 있다.
    boolean deleteFaq(Integer faqId, String faqManagerId);


    List<Category> readFaqCategory();
}
