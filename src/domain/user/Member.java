package domain.user;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Member extends User {

    private String companyCode;
    private String address;
    private boolean login;
    private Date start_date;
    private Date expired_date;

    public Member() {
    }

    public Member(String id, String pwd, String name, String phone, String email) {
        super(id, pwd, name, phone, email, "일반회원");
    }

    @Override
    public String toString() {
        return "Member{" +
                "companyCode='" + companyCode + '\'' +
                ", address='" + address + '\'' +
                ", login=" + login +
                ", start_date=" + start_date +
                ", expired_date=" + expired_date +
                ", id='" + getId() + '\'' +
                ", pwd='" + getPwd() + '\'' +
                ", name='" + getName() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", type='" + getType() + '\'' +
                '}';
    }
}
