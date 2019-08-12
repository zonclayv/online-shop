package by.haidash.shop.jwt.exception;

public class WrongAuthenticationTokenException extends RuntimeException {

    public WrongAuthenticationTokenException(String msg) {
        super(msg);
    }
}
