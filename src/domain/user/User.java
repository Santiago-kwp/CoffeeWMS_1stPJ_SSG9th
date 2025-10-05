package domain.user;

import constant.user.UserPage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {

    protected String id;
    protected String pwd;
    protected String name;
    protected String phone;
    protected String email;
    protected String companyCode;
    protected String address;
    protected String type;

    public User(String id, String pwd, String name, String phone, String email) {
        this(id, pwd, name, phone, email, null);
    }
    public User(String id, String pwd, String name, String phone, String email, String type) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof User that) {
            return this.id.equals(that.id);
        }
        throw new ClassCastException("비교할 수 없는 클래스 타입입니다.");
    }

    public String maskedInfo() {
        return String.format(UserPage.SEARCHED_COMMON_INFO.toString(), id, "*".repeat(8), maskName(name), maskPhone(phone), maskEmail(email), type);
    }
    private String maskName(String name) {
        if (this.type.equals("일반회원")) {
            // 일반회원일 경우에는 회원명 대신 소속사명을 기입하므로 그대로 반환
            return name;
        }
        int fromIndex = 1;
        int toIndex = name.length() > 2 ? name.length()-1 : 1;
        return name.replace(name.substring(fromIndex, toIndex), "*".repeat(toIndex-fromIndex));
    }
    private String maskPhone(String phone) {
        int fromIndex = phone.indexOf("-");
        int toIndex = phone.lastIndexOf("-");
        return phone.replace(phone.substring(fromIndex+1, toIndex), "*".repeat(4));
    }
    private String maskEmail(String email) {
        int fromIndex = 2;
        int toIndex = email.indexOf("@");
        return email.replace(email.substring(fromIndex, toIndex), "*".repeat(6));
    }

    @Override
    public String toString() {
        return String.format(UserPage.SEARCHED_COMMON_INFO.toString(), id, "*".repeat(8), name, phone, email, type);
    }
}
