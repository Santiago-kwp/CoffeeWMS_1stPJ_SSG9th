package domain.user;

import constant.user.MemberPage;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Member extends User {

    private String companyCode;
    private String address;
    private boolean login;
    private Date start_date;
    private Date expired_date;

    public Member(String id, String pwd, String name, String phone, String email) {
        super(id, pwd, name, phone, email, "일반회원");
    }

    @Override
    public String maskedInfo() {
        return super.maskedInfo()
                + String.format(MemberPage.SEARCHED_MEMBER_INFO.toString(), companyCode, address, start_date, expired_date);
    }

    @Override
    public String toString() {
        return super.toString()
                + String.format(MemberPage.SEARCHED_MEMBER_INFO.toString(), companyCode, address, start_date, expired_date);
    }
}
