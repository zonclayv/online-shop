package by.haidash.shop.messaging.user.model;

import by.haidash.shop.core.messaging.model.BaseMessage;

public class UserResponseMessage extends BaseMessage {

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
