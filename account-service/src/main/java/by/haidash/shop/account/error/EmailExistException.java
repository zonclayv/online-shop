package by.haidash.shop.account.error;

public class EmailExistException extends RuntimeException {

    public EmailExistException(String msg) {
        super(msg);
    }
}