package domain.user;

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
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.phone = phone;
        this.email = email;
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

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", pwd='" + pwd + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
