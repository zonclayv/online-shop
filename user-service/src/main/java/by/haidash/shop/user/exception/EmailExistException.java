package by.haidash.shop.user.exception;

public class EmailExistException extends RuntimeException {

    public EmailExistException(String msg) {
        super(msg);
    }
}