package by.haidash.shop.security.exception;

import by.haidash.shop.core.exception.BaseRuntimeException;

public class PermissionDeniedException extends BaseRuntimeException {

    public static final String PERMISSION_DENIED_EXCEPTION_CODE = "403-000";

    public PermissionDeniedException(String msg, Exception cause) {
        super(msg, cause);
    }

    public PermissionDeniedException(String msg) {
        super(msg, PERMISSION_DENIED_EXCEPTION_CODE);
    }
}
