package by.haidash.shop.user.messaging.data;

import java.io.Serializable;

public class UserCheckMessage implements Serializable {

    private Long id;
    private String email;
    private String psw;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
