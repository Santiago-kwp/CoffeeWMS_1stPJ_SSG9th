package model.support.dao;

import domain.support.Board;
import domain.support.Category;
import domain.support.Faq;

import java.util.List;

public interface FaqRepository {
    // FAQ 생성하는 기능으로 '총관리자' 권한을 가진 유저만 이 기능을 사용할 수 있다.
    boolean createFaq(Faq faq);

    // FAQ 수정하는 기능으로 '총관리자' 권한을 가진 유저만 이 기능을 사용할 수 있다.
    boolean updateFaq(Faq faq);

    // FAQ 삭제하는 기능으로 '총관리자' 권한을 가진 유저만 이 기능을 사용할 수 있다.
    boolean deleteFaq(Integer faqId, String faqManagerId);

    // FAQ 전체 데이터를 조회하는 기능이다.
    List<Board> readFaqAll();

    // FAQ 번호를 통해 한가지 데이터를 선택하여 조회하는 기능이다.
    Board readFaqOne(Integer faqId);

    // FAQ 카테고리 목록을 조회하는 기능이다.
    List<Category> readFaqCategory();
}
