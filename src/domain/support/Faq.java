package domain.support;

import java.util.Date;

public class Faq {
    private Integer faqId;
    private Integer faqCategoryId;
    private String faqCategoryName;
    private String faqQuestion;
    private String faqReply;
    private Date faqDate;
    private String faqManagerId;

    public Integer getFaqId() {
        return faqId;
    }

    public void setFaqId(Integer faqId) {
        this.faqId = faqId;
    }

    public Integer getFaqCategoryId() {
        return faqCategoryId;
    }

    public void setFaqCategoryId(Integer faqCategoryId) {
        this.faqCategoryId = faqCategoryId;
    }

    public String getFaqCategoryName() {
        return faqCategoryName;
    }

    public void setFaqCategoryName(String faqCategoryName) {
        this.faqCategoryName = faqCategoryName;
    }

    public String getFaqQuestion() {
        return faqQuestion;
    }

    public void setFaqQuestion(String faqQuestion) {
        this.faqQuestion = faqQuestion;
    }

    public String getFaqReply() {
        return faqReply;
    }

    public void setFaqReply(String faqReply) {
        this.faqReply = faqReply;
    }

    public Date getFaqDate() {
        return faqDate;
    }

    public void setFaqDate(Date faqDate) {
        this.faqDate = faqDate;
    }

    public String getFaqManagerId() {
        return faqManagerId;
    }

    public void setFaqManagerId(String faqManagerId) {
        this.faqManagerId = faqManagerId;
    }
}
