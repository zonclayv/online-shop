package by.haidash.shop.security.exception;

import by.haidash.shop.core.exception.BaseRuntimeException;

public class PermissionDeniedException extends BaseRuntimeException {

    public static final String PERMISSION_DENIDED_EXCEPTION_CODE = "403-000";

    public PermissionDeniedException(String msg) {
        super(msg, PERMISSION_DENIDED_EXCEPTION_CODE);
    }
}
