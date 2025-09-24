package domain.user;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Manager extends User {

    private boolean login;
    private Date hireDate;
    private String position;

    public Manager() {
    }

    public Manager(String id, String pwd, String name, String phone, String email, String type) {
        super(id, pwd, name, phone, email, type);
        this.position = super.type;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id='" + getId() + '\'' +
                ", pwd='" + getPwd() + '\'' +
                ", name='" + getName() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", type='" + getType() + '\'' +
                ", login=" + login +
                ", hireDate=" + hireDate +
                ", position='" + position + '\'' +
                '}';
    }
}
