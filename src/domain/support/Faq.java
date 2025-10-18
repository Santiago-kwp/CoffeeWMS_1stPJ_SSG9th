package domain.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Faq implements Board{
    private Integer faqId;
    private Integer faqCategoryId;
    private String faqCategoryName;
    private String faqQuestion;
    private String faqReply;
    private Date faqDate;
    private String faqManagerId;
}
