package by.haidash.shop.security.exception;

public class WrongAuthenticationTokenException extends RuntimeException {

    public WrongAuthenticationTokenException(String msg) {
        super(msg);
    }
}
