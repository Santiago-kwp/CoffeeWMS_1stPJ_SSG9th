package domain.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inquiry implements Board{
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
}
