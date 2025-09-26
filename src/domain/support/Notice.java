package domain.support;

import java.util.Date;

public class Notice {
    private Integer noticeId;
    private String noticeTitle;
    private String noticeContent;
    private Date noticeDate;
    private boolean noticeFixed;
    private String noticeManagerId;

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public Date getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(Date noticeDate) {
        this.noticeDate = noticeDate;
    }

    public boolean isNoticeFixed() {
        return noticeFixed;
    }

    public void setNoticeFixed(boolean noticeFixed) {
        this.noticeFixed = noticeFixed;
    }

    public String getNoticeManagerId() {
        return noticeManagerId;
    }

    public void setNoticeManagerId(String noticeManagerId) {
        this.noticeManagerId = noticeManagerId;
    }
}

