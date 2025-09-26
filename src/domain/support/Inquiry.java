package domain.support;

import java.util.Date;

public class Inquiry {
    private Integer inquiryId;
    private Date inquiryDate;
    private Integer inquiryCategoryId;
    private String inquiryCategoryName;
    private String inquiryContent;
    private inquiryStatus inquiryStatus; // ENUM 으로 변경 예정
    private Date replyDate;
    private String replyContent;
    private String inquiryMemberId;
    private String inquiryManagerId;

    public enum inquiryStatus {PENDING, DONE}

    public Integer getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(Integer inquiryId) {
        this.inquiryId = inquiryId;
    }

    public Date getInquiryDate() {
        return inquiryDate;
    }

    public void setInquiryDate(Date inquiryDate) {
        this.inquiryDate = inquiryDate;
    }

    public Integer getInquiryCategoryId() {
        return inquiryCategoryId;
    }

    public void setInquiryCategoryId(Integer inquiryCategoryId) {
        this.inquiryCategoryId = inquiryCategoryId;
    }

    public String getInquiryCategoryName() {
        return inquiryCategoryName;
    }

    public void setInquiryCategoryName(String inquiryCategoryName) {
        this.inquiryCategoryName = inquiryCategoryName;
    }

    public String getInquiryContent() {
        return inquiryContent;
    }

    public void setInquiryContent(String inquiryContent) {
        this.inquiryContent = inquiryContent;
    }

    public inquiryStatus getInquiryStatus() {
        return inquiryStatus;
    }

    public void setInquiryStatus(inquiryStatus inquiryStatus) {
        this.inquiryStatus = inquiryStatus;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getInquiryMemberId() {
        return inquiryMemberId;
    }

    public void setInquiryMemberId(String inquiryMemberId) {
        this.inquiryMemberId = inquiryMemberId;
    }

    public String getInquiryManagerId() {
        return inquiryManagerId;
    }

    public void setInquiryManagerId(String inquiryManagerId) {
        this.inquiryManagerId = inquiryManagerId;
    }
}
