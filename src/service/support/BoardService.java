package service.support;

public interface BoardService {
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
