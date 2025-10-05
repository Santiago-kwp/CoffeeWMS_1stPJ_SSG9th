package domain.user;

import constant.user.MemberPage;
import constant.user.validation.InputValidCheck;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class Member extends User {

    private final String companyCode;
    private final String address;
    private final Date startDate;
    private final Date expiredDate;
    private final boolean login;

    private Member(Builder builder) {
        super(builder);
        this.companyCode = builder.companyCode;
        this.address = builder.address;
        this.login = builder.login;
        this.startDate = builder.startDate;
        this.expiredDate = builder.expiredDate;
    }

    @Override
    public String maskedInfo() {
        return super.maskedInfo()
                + String.format(MemberPage.SEARCHED_MEMBER_INFO.toString(), companyCode, address, startDate, expiredDate);
    }

    @Override
    public String toString() {
        return super.toString()
                + String.format(MemberPage.SEARCHED_MEMBER_INFO.toString(), companyCode, address, startDate,
                expiredDate);
    }

    // 빌더 클래스
    @NoArgsConstructor
    public static class Builder extends User.AbstractBuilder<Member, Builder> {
        private String companyCode;
        private String address;
        private Date startDate;
        private Date expiredDate;
        private boolean login;

        public Builder(String memberID) {
            InputValidCheck.checkUserID(memberID);
            this.id = memberID;
        }

        public Builder companyCode(String companyCode) {
            super.companyCode(companyCode);
            this.companyCode = companyCode;
            return this;
        }

        public Builder address(String address) {
            super.address(address);
            this.address = address;
            return this;
        }

        public Builder login(boolean login) {
            this.login = login;
            return this;
        }

        public Builder contract(Date startDate, Date expiredDate) {
            this.startDate = startDate;
            this.expiredDate = expiredDate;
            return this;
        }

        public static Builder create(String userID, String userPwd, String companyName) {
            return new Builder(userID)
                    .password(userPwd)
                    .name(companyName);
        }

        // 메서드 체이닝으로 빌더 패턴을 구현하기 위한 메서드
        @Override
        public Builder self() {
            return this;
        }

        public static Member from(ResultSet rs) throws SQLException {
            return create(rs.getString(1), rs.getString(2), rs.getString(3))
                    .phone(rs.getString(4))
                    .email(rs.getString(5))
                    .companyCode(rs.getString(6))
                    .address(rs.getString(7))
                    .login(rs.getBoolean(8))
                    .contract(rs.getDate(9), rs.getDate(10))
                    .build();
        }

        public static Member from(Member beforeUpdate, User newInfo) {
            return create(beforeUpdate.id, newInfo.pwd, newInfo.name)
                    .phone(newInfo.phone)
                    .email(newInfo.email)
                    .companyCode(newInfo.companyCode)
                    .address(newInfo.address)
                    .login(beforeUpdate.login)
                    .contract(beforeUpdate.startDate, beforeUpdate.expiredDate)
                    .build();
        }

        @Override
        public Member build() {
            this.registerType("일반회원");
            return new Member(this);
        }
    }
}
