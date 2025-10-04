package service.support;

import domain.support.Board;

public interface BoardService {
    void createNotice(Board board);

    void createInquiry(Board board);

    void createFaq(Board board);

    void updateNotice(Board board);

    void updateInquiryMember(Board board);

    void updateInquiryManager(Board board);

    void updateFaq(Board board);

    void deleteNotice(Integer noticeId, String managerId);

    void deleteInquiryMember(Integer inquiryId, String memberId);

    void deleteInquiryManager(Integer inquiryId);

    void deleteFaq(Integer faqId, String managerId);

    void showNoticePreview();

    void showAllNotice();

    void showOneNotice(Integer noticeId);

    void showAllInquiryManager();

    void showAllInquiryMember(String memberId);

    void showOneInquiryManager(Integer inquiryId);

    void showOneInquiryMember(String memberId, Integer inquiryId);

    void showAllFaq();

    void showOneFaq(Integer faqId);
}
