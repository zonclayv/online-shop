package by.haidash.shop.user.error;

public class EmailExistException extends RuntimeException {

    public EmailExistException(String msg) {
        super(msg);
    }
}