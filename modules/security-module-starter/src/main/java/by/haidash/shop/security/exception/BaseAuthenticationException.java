package by.haidash.shop.security.exception;

import by.haidash.shop.core.exception.BaseRuntimeException;

public class BaseAuthenticationException extends BaseRuntimeException {

    public static final String UNAUTHORIZED_EXCEPTION_CODE = "401-001";

    public BaseAuthenticationException(String msg) {
        super(msg, UNAUTHORIZED_EXCEPTION_CODE);
    }

    public BaseAuthenticationException(String msg, Exception cause) {
        super(msg, cause);
    }
}
