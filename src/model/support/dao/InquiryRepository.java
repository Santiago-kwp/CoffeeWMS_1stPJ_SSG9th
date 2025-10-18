package model.support.dao;

import domain.support.Board;
import domain.support.Category;
import domain.support.Inquiry;

import java.util.List;

public interface InquiryRepository {
    // 1:1 문의 생성하는 기능으로 '회원' 권한을 가진 유저만 이 기능을 사용할 수 있다.
    boolean createInquiry(Inquiry inquiry);

    // 1:1 문의를 수정하는 기능으로 '회원' 권한을 가진 유저가 본인의 문의를 수정할 수 있게 하는 기능이다.
    boolean updateInquiryMember(Inquiry inquiry);

    // 1:1 문의에 답변하는 기능으로 '총관리자' 권한을 가진 유저가 '회원' 유저의 문의에 답변을 할 수 있게 하는 기능이다.
    boolean updateInquiryManager(Inquiry inquiry);

    // 1:1 문의를 삭제하는 기능으로 '회원' 권한을 가진 유저가 본인의 문의를 삭제할 수 있게 하는 기능이다.
    boolean deleteInquiryMember(Integer inquiryId, String inquiryMemberId);

    // 1:1 문의를 삭제하는 기능으로 '총관리자' 권한을 가진 유저가 문의를 삭제할 수 있게 하는 기능이다.
    boolean deleteInquiryManager(Integer inquiryId);

    // 1:1 문의 목록 전체를 조회하는 기능으로 로그인한 유저가 작성한 문의만 조회할 수 있는 기능이다.
    List<Board> readInquiryMemberAll(String memberId);

    // 1:1 문의 목록 전체를 조회하는 기능으로 '총관리자' 권한을 가진 유저만 이 기능을 사용할 수 있다.
    List<Board> readInquiryManagerAll();

    // 선택한 1:1 문의를 조회하는 기능으로 로그인한 유저가 작성한 문의만 조회할 수 있는 기능이다.
    Board readInquiryMemberOne(String inquiryMemberId, Integer inquiryId);

    // 선택한 1:1 문의를 조회하는 기능으로 '총관리자' 권한을 가진 유저만 이 기능을 사용할 수 있다.
    Board readInquiryManagerOne(Integer inquiryId);

    // 1:1 문의 카테고리 목록을 조회하는 기능이다.
    List<Category> readInquiryCategory();
}
