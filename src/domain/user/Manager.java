package domain.user;

import constant.user.ManagerPage;
import constant.user.validation.InputValidCheck;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class Manager extends User {

    public static final int CARGO_LIMIT = 3;

    private final boolean login;
    private final Date hireDate;
    private final String position;

    private Manager(Builder builder) {
        super(builder);
        this.login = builder.login;
        this.hireDate = builder.hireDate;
        this.position = builder.type;
    }

    @Override
    public String maskedInfo() {
        return super.maskedInfo()
                + String.format(ManagerPage.SEARCHED_MANAGER_INFO.toString(), hireDate, position);
    }

    @Override
    public String toString() {
        return super.toString()
                + String.format(ManagerPage.SEARCHED_MANAGER_INFO.toString(), hireDate, position);
    }

    @NoArgsConstructor
    public static class Builder extends User.AbstractBuilder<Manager, Builder> {

        private boolean login;
        private Date hireDate;

        public Builder(String managerID) {
            InputValidCheck.checkUserID(managerID);
            this.id = managerID;
        }

        public Builder login(boolean login) {
            this.login = login;
            return this;
        }

        public Builder hireDate(Date hireDate) {
            this.hireDate = hireDate;
            return this;
        }

        public Builder position(String position) {
            this.registerType(position);
            return this;
        }

        public static Builder create(String userID, String userPwd, String name) {
            return new Builder(userID)
                    .password(userPwd)
                    .name(name);
        }

        @Override
        public Builder self() {
            return this;
        }

        public static Manager from(ResultSet rs) throws SQLException {
            return create(rs.getString(1), rs.getString(2), rs.getString(3))
                    .phone(rs.getString(4))
                    .email(rs.getString(5))
                    .login(rs.getBoolean(6))
                    .hireDate(rs.getDate(7))
                    .position(rs.getString(8))
                    .build();
        }

        public static Manager from(Manager beforeUpdate, User newInfo) {
            return create(beforeUpdate.id, newInfo.pwd, newInfo.name)
                    .phone(newInfo.phone)
                    .email(newInfo.email)
                    .login(beforeUpdate.login)
                    .hireDate(beforeUpdate.hireDate)
                    .position(beforeUpdate.position)
                    .build();
        }

        @Override
        public Manager build() {
            return new Manager(this);
        }
    }
}
