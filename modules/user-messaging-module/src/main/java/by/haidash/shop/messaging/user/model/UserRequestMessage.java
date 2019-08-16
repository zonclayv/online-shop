package by.haidash.shop.messaging.user.model;

import by.haidash.shop.core.messaging.model.BaseMessage;

public class UserRequestMessage extends BaseMessage {

    private String email;

    public UserRequestMessage() {
    }

    public UserRequestMessage(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
