package domain.user;

import constant.user.ManagerPage;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Manager extends User {

    public static final int CARGO_LIMIT = 3;

    private boolean login;
    private Date hireDate;
    private String position;

    public Manager(String id, String pwd, String name, String phone, String email, String type) {
        super(id, pwd, name, phone, email, type);
        this.position = super.type;
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
}
