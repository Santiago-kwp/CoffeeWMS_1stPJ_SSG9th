package domain.user;

import constant.user.UserPage;
import constant.user.validation.InputValidCheck;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class User {

    protected String id;
    protected String pwd;
    protected String name;
    protected String phone;
    protected String email;
    protected String companyCode;
    protected String address;
    protected String type;

    // 제네릭 와일드카드 사용: User 클래스를 제네릭으로 바꿀 필요 없이 제네릭 타입을 활용
    protected User(AbstractBuilder<?, ?> builder) {
        this.id = builder.id;
        this.pwd = builder.pwd;
        this.name = builder.name;
        this.phone = builder.phone;
        this.email = builder.email;
        this.companyCode = builder.companyCode;
        this.address = builder.address;
        this.type = builder.type;
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

    @Override
    public boolean equals(Object o) {
        if (o instanceof User that) {
            return this.id.equals(that.id);
        }
        throw new ClassCastException("비교할 수 없는 클래스 타입입니다.");
    }

    // 빌더 클래스
    @NoArgsConstructor
    public static class Builder extends AbstractBuilder<User, Builder> {

        private Builder(String userID) {
            InputValidCheck.checkUserID(userID);
            this.id = userID;
        }

        public static Builder create(String userID, String userPwd, String name) {
            return new Builder(userID)
                    .password(userPwd)
                    .name(name);
        }

        public static Builder update(String userPwd, String name) {
            return new Builder()
                    .password(userPwd)
                    .name(name);
        }

        @Override
        public Builder self() {
            return this;
        }

        public static User from(ResultSet rs) throws SQLException  {
            return User.Builder.create(rs.getString("user_id"), rs.getString("user_pwd"), rs.getString("user_name"))
                    .phone(rs.getString("user_phone"))
                    .email(rs.getString("user_email"))
                    .registerType(rs.getString("user_type") == null ? "권한없음" : rs.getString("user_type"))
                    .build();
        }

        @Override
        public User build() {
            return new User(this);
        }
    }

    public static abstract class AbstractBuilder<T extends User, B extends AbstractBuilder<T, B>> {

        protected String id;
        protected String pwd;
        protected String name;
        protected String phone;
        protected String email;
        protected String companyCode;
        protected String address;
        protected String type;

        public B password(String pwd) {
            this.pwd = pwd;
            InputValidCheck.checkPwd(pwd);
            // AbstractBuilder 클래스에 setter를 구현하더라도 하위 빌더 클래스에 관한 메서드 체이닝이 가능
            return self();
        }

        public B name(String name) {
            this.name = name;
            return self();
        }

        public B phone(String phone) {
            this.phone = phone;
            InputValidCheck.checkPhoneFormat(phone);
            return self();
        }

        public B email(String email) {
            this.email = email;
            InputValidCheck.checkEmailFormat(email);
            return self();
        }

        public B companyCode(String companyCode) {
            this.companyCode = companyCode;
            InputValidCheck.checkCompanyCode(companyCode);
            return self();
        }

        public B address(String address) {
            this.address = address;
            return self();
        }

        public B registerType(String type) {
            this.type = type;
            return self();
        }

        public abstract T build();
        // AbstractBuilder 클래스를 구현한 하위 클래스 타입을 반환
        public abstract B self();
    }
}
