package service.support;

import domain.support.Board;

public interface BoardService {
    // 공지 생성
    void createNotice(Board board);

    // 1:1 문의 생성
    void createInquiry(Board board);

    // FAQ 생성
    void createFaq(Board board);

    // 공지 수정
    void updateNotice(Board board);

    // 일반회원의 1:1 문의 수정
    void updateInquiryMember(Board board);

    // 관리자의 1:1 문의 답변
    void updateInquiryManager(Board board);

    // FAQ 수정
    void updateFaq(Board board);

    // 공지 삭제
    void deleteNotice(Integer noticeId, String managerId);

    // 일반회원의 1:1 문의 삭제
    void deleteInquiryMember(Integer inquiryId, String memberId);

    // 관리자의 1:1 문의 삭제
    void deleteInquiryManager(Integer inquiryId);

    // FAQ 삭제
    void deleteFaq(Integer faqId, String managerId);

    // 메인화면에서의 공지 보기
    void showNoticePreview();

    // 공지 전체 보기
    void showAllNotice();

    // 공지 상세 보기
    void showOneNotice(Integer noticeId);

    // 일반회원의 본인이 작성한 문의 전체 보기
    void showAllInquiryMember(String memberId);

    // 관리자의 문의 전체 보기
    void showAllInquiryManager();

    // 일반회원의 본인이 작성한 문의 상세 보기
    void showOneInquiryMember(String memberId, Integer inquiryId);

    // 관리자의 문의 상세 보기
    void showOneInquiryManager(Integer inquiryId);

    // FAQ 전체 보기
    void showAllFaq();

    // FAQ 상세 보기
    void showOneFaq(Integer faqId);
}
