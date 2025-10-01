package domain.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notice implements Board{
    private Integer noticeId;
    private String noticeTitle;
    private String noticeContent;
    private Date noticeDate;
    private boolean noticeFixed;
    private String noticeManagerId;
}

