package by.haidash.shop.auth.entity;

import by.haidash.shop.jpa.entity.BaseEntity;

import javax.persistence.Entity;

@Entity
public class User extends BaseEntity<Long> {

    private String email;

    private String psw;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }
}
