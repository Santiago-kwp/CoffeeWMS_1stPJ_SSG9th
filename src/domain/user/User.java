package domain.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    protected String id;
    protected String pwd;
    protected String name;
    protected String phone;
    protected String email;
    protected String companyCode;
    protected String address;
    protected String type;

    public User() {
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
}
